package com.mlab.assessment.model.dto;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.Singular;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Getter
@Setter
@Builder
public class IssueBookValidationDTO {
    private UserEntity userEntity;
    @Singular
    private List<BookEntity> bookEntities;
    @Singular
    private List<BookMetaEntity> metaEntities;
}
