package com.mlab.assessment.service.user;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.model.dto.CreateUserDTO;
import com.mlab.assessment.model.dto.UpdateUserDTO;
import com.mlab.assessment.model.response.user.IssuedBook;
import com.mlab.assessment.model.response.user.UserResponseDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
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
@RequiredArgsConstructor
public class UserMapper {

    private Map<Long, BookMetaEntity> getAsMap(List<BookMetaEntity> metaEntityList){
        return metaEntityList
                .stream()
                .collect(Collectors.toMap(BookMetaEntity::getId, Function.identity()));
    }

    public UserResponseDTO mapToDto(UserEntity entity) {
        return UserResponseDTO
                .builder()
                .id(entity.getId())
                .name(entity.getFullName())
                .userName(entity.getUsername())
                .build();
    }

    public UserResponseDTO mapToDto(UserEntity entity, List<BookMetaEntity> metaEntity){
        Map<Long, BookMetaEntity> metaMap = this.getAsMap(metaEntity);
        return UserResponseDTO
                .builder()
                .id(entity.getId())
                .name(entity.getFullName())
                .userName(entity.getUsername())
                .issuedBooks(this.mapToIssueBooks(entity.getBooks(), metaMap))
                .build();
    }

    public List<UserResponseDTO> mapToDto(List<UserEntity> entityList, List<BookMetaEntity> metaEntityList){

        Map<Long, BookMetaEntity> metaMap = this.getAsMap(metaEntityList);

        return entityList
                .stream()
                .map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .name(user.getFullName())
                        .userName(user.getUsername())
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
    public UserEntity mapToNewUserEntity(CreateUserDTO dto){
        return new UserEntity(dto.getUserName(), dto.getFullName());
    }

    public void fillUpdatableEntity(UserEntity entity, UpdateUserDTO dto){
        entity.setUsername(dto.getUserName());
        entity.setFullName(dto.getFullName());
    }

}
