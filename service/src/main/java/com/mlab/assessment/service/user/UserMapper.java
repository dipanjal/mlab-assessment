package com.mlab.assessment.service.user;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.model.request.user.CreateUserRequest;
import com.mlab.assessment.model.request.user.UpdateUserRequest;
import com.mlab.assessment.model.response.user.IssuedBook;
import com.mlab.assessment.model.response.user.UserResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static com.mlab.assessment.utils.DateTimeUtils.toAPIDateFormat;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Component
public class UserMapper {

    private Map<Long, BookMetaEntity> getAsMap(List<BookMetaEntity> metaEntityList){
        return metaEntityList
                .stream()
                .collect(Collectors.toMap(BookMetaEntity::getId, Function.identity()));
    }

    public UserResponse mapToDto(UserEntity entity) {
        return UserResponse
                .builder()
                .id(entity.getId())
                .name(entity.getFullName())
                .userName(entity.getUsername())
                .email(entity.getEmail())
                .build();
    }

    public UserResponse mapToDto(UserEntity entity, List<BookMetaEntity> metaEntity){
        Map<Long, BookMetaEntity> metaMap = this.getAsMap(metaEntity);
        return UserResponse
                .builder()
                .id(entity.getId())
                .name(entity.getFullName())
                .userName(entity.getUsername())
                .email(entity.getEmail())
                .issuedBooks(this.mapToIssueBooks(entity.getBooks(), metaMap))
                .build();
    }

    public List<UserResponse> mapToDto(List<UserEntity> entityList, List<BookMetaEntity> metaEntityList){

        Map<Long, BookMetaEntity> metaMap = this.getAsMap(metaEntityList);

        return entityList
                .stream()
                .map(user -> UserResponse.builder()
                        .id(user.getId())
                        .name(user.getFullName())
                        .userName(user.getUsername())
                        .email(user.getEmail())
                        .issuedBooks(mapToIssueBooks(user.getBooks(), metaMap))
                        .build()
                ).collect(Collectors.toList());
    }

    private List<IssuedBook> mapToIssueBooks(List<BookEntity> bookEntities, Map<Long, BookMetaEntity> metaMap){
        return bookEntities
                .stream()
                .map(e ->
                        mapToIssueBook(metaMap.get(e.getMetaId())))
                .collect(Collectors.toList());
    }

    private IssuedBook mapToIssueBook(BookMetaEntity metaEntity){
        return IssuedBook.builder()
                .id(metaEntity.getBookId())
                .name(metaEntity.getName())
                .description(metaEntity.getDescription())
                .authorName(metaEntity.getAuthorName())
                .noOfCopy(metaEntity.getNoOfCopy())
                .releaseDate(toAPIDateFormat(metaEntity.getReleaseDate()))
                .build();
    }

    /** ENTITY MAPPING */
    public UserEntity mapToNewUserEntity(CreateUserRequest dto){
        return new UserEntity(dto.getUserName(), dto.getFullName(), dto.getEmail());
    }

    public void fillUpdatableEntity(UserEntity entity, UpdateUserRequest dto){
        entity.setFullName(dto.getFullName());
        entity.setEmail(dto.getEmail());
    }

}
