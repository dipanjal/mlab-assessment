package com.mlab.assessment.service.book;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.model.request.book.BookDTO;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.utils.DateTimeUtils;
import org.springframework.stereotype.Component;

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
public class BookMapper {
    public BookDTO mapToDTO(BookEntity book, BookMetaEntity meta){
        return BookDTO.builder()
                .id(book.getId())
                .name(book.getName())
                .authorName(meta.getAuthorName())
                .description(meta.getDescription())
                .noOfCopy(meta.getNoOfCopy())
                .releaseDate(toAPIDateFormat(meta.getReleaseDate()))
                .build();
    }

    public List<BookDTO> mapToDTOs(List<BookEntity> books, List<BookMetaEntity> metas){
        Map<Long, BookMetaEntity> metaMap = metas.stream()
                .collect(Collectors.toMap(BookMetaEntity::getId, Function.identity()));

        return books.stream()
                .map(b -> mapToDTO(b , metaMap.get(b.getMetaId())))
                .collect(Collectors.toList());
    }

    public BookEntity mapToNewBookEntity(CreateBookDTO dto){
        return new BookEntity(dto.getName(), 0);
    }

    public BookMetaEntity mapToNewBookMetaEntity(CreateBookDTO dto){
        return new BookMetaEntity(
                dto.getAuthorName(),
                dto.getDescription(),
                dto.getNoOfCopy(),
                toDBDateFormat(dto.getReleaseDate()),
                0
        );
    }

    public BookEntity mapToUpdatableBookEntity(BookEntity entityToUpdate, BookDTO dto){
        entityToUpdate.setName(dto.getName());
        return entityToUpdate;
    }

    public BookMetaEntity mapToUpdatableBookMetaEntity(BookMetaEntity entityToUpdate, BookDTO dto){
        entityToUpdate.setAuthorName(dto.getAuthorName());
        entityToUpdate.setDescription(dto.getDescription());
        entityToUpdate.setNoOfCopy(dto.getNoOfCopy());
        entityToUpdate.setReleaseDate(
                DateTimeUtils.toDBDateFormat(dto.getReleaseDate()));
        return entityToUpdate;
    }
}
