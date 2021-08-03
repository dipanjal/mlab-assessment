package com.mlab.assessment.service.book;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.model.request.book.BookDTO;
import org.springframework.stereotype.Component;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Component
public class BookMapper {
    public BookDTO mapToDTO(BookEntity book, BookMetaEntity meta){
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .authorName(meta.getAuthorName())
                .description(meta.getDescription())
                .noOfCopy(meta.getNoOfCopy())
                .build();
    }
}
