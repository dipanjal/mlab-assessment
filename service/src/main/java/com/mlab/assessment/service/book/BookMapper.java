package com.mlab.assessment.service.book;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.model.request.book.UpdateBookDTO;
import com.mlab.assessment.model.response.book.BookResponseDTO;
import com.mlab.assessment.model.response.book.IssuedUser;
import com.mlab.assessment.utils.DateTimeUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mlab.assessment.utils.DateTimeUtils.toDBDateFormat;
import static com.mlab.assessment.utils.DateTimeUtils.toAPIDateFormat;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Component
@Slf4j
public class BookMapper {

    public BookMetaEntity mapToNewBookMetaEntity(CreateBookDTO dto){
        return new BookMetaEntity(
                dto.getName(),
                dto.getAuthorName(),
                dto.getDescription(),
                dto.getNoOfCopy(),
                toDBDateFormat(dto.getReleaseDate()),
                0
        );
    }

    public void fillUpdatableEntity(BookMetaEntity entityToUpdate, UpdateBookDTO dto){
        entityToUpdate.setAuthorName(dto.getAuthorName());
        entityToUpdate.setDescription(dto.getDescription());
        entityToUpdate.setNoOfCopy(dto.getNoOfCopy());
        entityToUpdate.setReleaseDate(
                DateTimeUtils.toDBDateFormat(dto.getReleaseDate()));
    }

    public void fillIssuableEntity(List<BookEntity> entityList, List<BookMetaEntity> metaEntityList, UserEntity user){

        Map<Long, BookMetaEntity> bookMetaMap = this.getAsMetaMap(metaEntityList);

        entityList.forEach(book -> {
            BookMetaEntity metaEntity = bookMetaMap.get(book.getMetaId());
            if(metaEntity.getNoOfCopy() > 0){
                metaEntity.setNoOfCopy(metaEntity.getNoOfCopy() - 1);
                book.addUser(user);
                user.addBook(book);
            }else{
                log.info("Can't issue {} ", metaEntity.getName());
            }
        });
    }

    public BookResponseDTO mapToBookResponseDTO(BookMetaEntity metaEntity, IssuedUser issuedUser){

        return BookResponseDTO.builder()
                .id(metaEntity.getBookId())
                .name(metaEntity.getName())
                .authorName(metaEntity.getAuthorName())
                .description(metaEntity.getDescription())
                .noOfCopy(metaEntity.getNoOfCopy())
                .releaseDate(metaEntity.getReleaseDate())
                .issuedUsers(Collections.singletonList(issuedUser))
                .build();
    }

    public BookResponseDTO mapToBookResponseDTO(BookMetaEntity metaEntity, UserEntity user){

        IssuedUser issuedUser = IssuedUser.builder()
                .id(user.getId())
                .name(user.getFullName())
                .build();

        return this.mapToBookResponseDTO(metaEntity, issuedUser);
    }

    public List<BookResponseDTO> mapToBookResponseDTO(List<BookMetaEntity> metaEntityList, UserEntity user){
        return metaEntityList
                .stream()
                .map(e -> mapToBookResponseDTO(e, user))
                .collect(Collectors.toList());
    }


    public BookResponseDTO mapToBookResponseDTO(BookEntity bookEntity, BookMetaEntity metaEntity){
        return BookResponseDTO
                .builder()
                .id(bookEntity.getId())
                .name(metaEntity.getName())
                .description(metaEntity.getDescription())
                .authorName(metaEntity.getAuthorName())
                .noOfCopy(metaEntity.getNoOfCopy())
                .releaseDate(toAPIDateFormat(
                                metaEntity.getReleaseDate()))
                .issuedUsers(mapToIssuedUserDTO(bookEntity.getUsers()))
                .build();
    }

    public List<BookResponseDTO> mapToBookResponseDTO(List<BookEntity> bookEntities,
                                                      List<BookMetaEntity> bookMetaEntities){

        Map<Long, BookMetaEntity> metaMap = this.getAsMetaMap(bookMetaEntities);

        return bookEntities
                .stream()
                .map(book ->
                        mapToBookResponseDTO(
                                book, metaMap.get(book.getMetaId())))
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
}
