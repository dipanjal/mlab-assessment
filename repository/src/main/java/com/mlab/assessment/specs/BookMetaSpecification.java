package com.mlab.assessment.specs;

import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.model.request.user.BookSearchRequest;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@UtilityClass
public class BookMetaSpecification {

    private final String FIELD_BOOK_NAME = "name";
    private final String FIELD_AUTHOR_NAME = "authorName";


    public static Specification<BookMetaEntity> getSearchSpecification(BookSearchRequest request){
        return Specification.where(generateSearchSpecification(request));
    }

    private static Specification<BookMetaEntity> generateSearchSpecification(BookSearchRequest request){
        return ( (root, criteriaQuery, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if(StringUtils.isNotBlank(request.getBookName()))
                predicates.add(criteriaBuilder.like(root.get(FIELD_BOOK_NAME),"%"+request.getBookName()+"%"));

            if(StringUtils.isNotBlank(request.getAuthorName()))
                predicates.add(criteriaBuilder.like(root.get(FIELD_AUTHOR_NAME), "%"+request.getAuthorName()+"%"));

            return criteriaBuilder.and(
                    predicates.stream()
                            .toArray(Predicate[]::new));
        });
    }
}
