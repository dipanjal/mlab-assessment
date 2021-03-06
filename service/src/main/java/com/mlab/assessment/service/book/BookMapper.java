package com.mlab.assessment.service.book;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.model.request.book.CreateBookRequest;
import com.mlab.assessment.model.request.book.UpdateBookRequest;
import com.mlab.assessment.model.response.book.BookResponse;
import com.mlab.assessment.model.response.book.IssuedUser;
import com.mlab.assessment.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import static com.mlab.assessment.utils.DateTimeUtils.toAPIDateFormat;
import static com.mlab.assessment.utils.DateTimeUtils.toDBDateFormat;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Component
@Slf4j
public class BookMapper {

    public BookMetaEntity mapToNewBookMetaEntity(CreateBookRequest dto){
        return new BookMetaEntity(
                dto.getName(),
                dto.getAuthorName(),
                dto.getDescription(),
                dto.getNoOfCopy(),
                toDBDateFormat(dto.getReleaseDate()),
                0
        );
    }

    public void fillUpdatableEntity(BookMetaEntity entityToUpdate, UpdateBookRequest dto){
        entityToUpdate.setAuthorName(dto.getAuthorName());
        entityToUpdate.setDescription(dto.getDescription());
        entityToUpdate.setNoOfCopy(dto.getNoOfCopy());
        entityToUpdate.setReleaseDate(
                DateTimeUtils.toDBDateFormat(dto.getReleaseDate()));
    }

    public void fillIssuableEntity(List<BookEntity> entityList, List<BookMetaEntity> metaEntityList, UserEntity user){

        Map<Long, BookMetaEntity> bookMetaMap = this.getAsMetaMap(metaEntityList);

        Consumer<BookEntity> issueBookMappingConsumer = book -> {
            BookMetaEntity metaEntity = bookMetaMap.get(book.getMetaId());
            if(metaEntity.getNoOfCopy() > 0){
                metaEntity.setNoOfCopy(metaEntity.getNoOfCopy() - 1);
                book.addUser(user);
                user.addBook(book);
            }else{
                log.info("Can't issue {} ", metaEntity.getName());
            }
        };

        entityList.forEach(issueBookMappingConsumer);
    }

    public BookResponse mapToBookResponseDTO(BookMetaEntity metaEntity, IssuedUser issuedUser){

        return BookResponse.builder()
                .id(metaEntity.getBookId())
                .name(metaEntity.getName())
                .authorName(metaEntity.getAuthorName())
                .description(metaEntity.getDescription())
                .noOfCopy(metaEntity.getNoOfCopy())
                .releaseDate(metaEntity.getReleaseDate())
                .issuedUsers(Collections.singletonList(issuedUser))
                .build();
    }

    public BookResponse mapToBookResponseDTO(BookMetaEntity metaEntity, UserEntity user){

        IssuedUser issuedUser = IssuedUser.builder()
                .id(user.getId())
                .name(user.getFullName())
                .build();

        return this.mapToBookResponseDTO(metaEntity, issuedUser);
    }

    public List<BookResponse> mapToBookResponseDTO(List<BookMetaEntity> metaEntityList, UserEntity user){
        return metaEntityList
                .stream()
                .map(e -> mapToBookResponseDTO(e, user))
                .collect(Collectors.toList());
    }


    public BookResponse mapToBookResponseDTO(BookEntity bookEntity, BookMetaEntity metaEntity){
        return BookResponse
                .builder()
                .id(bookEntity.getId())
                .name(metaEntity.getName())
                .description(metaEntity.getDescription())
                .authorName(metaEntity.getAuthorName())
                .noOfCopy(metaEntity.getNoOfCopy())
                .releaseDate(toAPIDateFormat(metaEntity.getReleaseDate()))
                .issuedUsers(mapToIssuedUserDTO(bookEntity.getUsers()))
                .build();
    }

    public List<BookResponse> mapToBookResponseDTO(List<BookEntity> bookEntities,
                                                   List<BookMetaEntity> bookMetaEntities){

        Map<Long, BookMetaEntity> metaMap = this.getAsMetaMap(bookMetaEntities);

        Function<BookEntity, BookResponse> bookResponseMapper =
                book -> mapToBookResponseDTO(
                        book,
                        metaMap.get(book.getMetaId())
                );

        return bookEntities
                .stream()
                .map(bookResponseMapper)
                .collect(Collectors.toList());
    }

    private Map<Long, BookMetaEntity> getAsMetaMap(List<BookMetaEntity> metaEntityList){
        return metaEntityList
                .stream()
                .collect(Collectors.toMap(BookMetaEntity::getId, Function.identity()));
    }

    private IssuedUser mapToIssuedUserDTO(UserEntity entity){
        return IssuedUser
                .builder()
                .name(entity.getFullName())
                .id(entity.getId())
                .build();
    }

    private List<IssuedUser> mapToIssuedUserDTO(List<UserEntity> entityList){
        return entityList
                .stream()
                .map(this::mapToIssuedUserDTO)
                .collect(Collectors.toList());
    }

    public List<BookEntity> extractIssuedBooks(UserEntity userEntity, Set<Long> bookIds) {
        return userEntity
                .getBooks()
                .stream()
                .filter(book -> bookIds.contains(book.getId()))
                .collect(Collectors.toList());
    }

    public void fillBookSubmissionEntity(UserEntity userEntity, Set<Long> requestedBookIds, List<BookEntity> issuedBooks, List<BookMetaEntity> metaEntities) {

        Predicate<BookEntity> isBookInRequestedBookList = book -> requestedBookIds.contains(book.getId()); //O(n)

        userEntity.getBooks().removeIf(isBookInRequestedBookList);
        issuedBooks.forEach(book -> book.getUsers().remove(userEntity));
        metaEntities.forEach(meta -> meta.setNoOfCopy(meta.getNoOfCopy() + issuedBooks.size()));
    }

    public void fillBookSubmissionEntity(UserEntity userEntity, List<BookEntity> issuedBooks, List<BookMetaEntity> metaEntities) {
        Map<Long, List<BookEntity>> bookMetaMap = issuedBooks
                .stream()
                .collect(Collectors.groupingBy(BookEntity::getMetaId));

        userEntity.getBooks().removeAll(issuedBooks);
        issuedBooks.forEach(book -> book.getUsers().remove(userEntity));
        metaEntities.forEach(meta -> meta.setNoOfCopy(meta.getNoOfCopy() + bookMetaMap.get(meta.getId()).size())); //O(n)
    }
}
