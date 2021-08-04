package com.mlab.assessment.service.book;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.model.request.book.BookDTO;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.service.BaseService;
import com.mlab.assessment.service.BookEntityService;
import com.mlab.assessment.service.BookMetaEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl extends BaseService implements BookService {

    private final BookEntityService entityService;
    private final BookMetaEntityService metaEntityService;
    private final BookMapper mapper;

    @Override
    public List<BookDTO> findAllBooks() {
        List<BookEntity> bookList = entityService.findAll();
        List<BookMetaEntity> metaList = metaEntityService.findAll();
        return mapper.mapToDTOs(bookList, metaList);
    }

    @Override
    public BookDTO findBookById(long id) {
        BookEntity book = entityService
                .findById(id)
                .orElseThrow(super.supplyRecordNotFoundException("api.response.NOT_FOUND.message"));

        BookMetaEntity meta = metaEntityService
                .findById(book.getMetaId())
                .orElseThrow(super.supplyRecordNotFoundException("api.response.NOT_FOUND.message"));

        return mapper.mapToDTO(book, meta);
    }

    @Override
    public BookDTO createBook(CreateBookDTO dto) {
        BookEntity bookEntity = new BookEntity();
        BookMetaEntity bookMetaEntity = mapper.mapToNewBookMetaEntity(dto);
        this.saveBook(bookEntity, bookMetaEntity);
        return mapper.mapToDTO(bookEntity, bookMetaEntity);
    }

    @Override
    public BookDTO updateBook(BookDTO dto) {
        BookEntity book = entityService.findById(dto.getId())
                .orElseThrow(super.supplyRecordNotFoundException("api.response.NOT_FOUND.message"));

        BookMetaEntity meta = metaEntityService.findById(book.getMetaId())
                .orElseThrow(super.supplyRecordNotFoundException("api.response.NOT_FOUND.message"));

//        mapper.mapToUpdatableBookEntity(book, dto);
        mapper.mapToUpdatableBookMetaEntity(meta, dto);

        saveBook(book, meta);
        return mapper.mapToDTO(book, meta);
    }

    @Override
    public BookDTO deleteBook(long id) {
        BookEntity book = entityService.delete(id);
        BookMetaEntity meta = metaEntityService.delete(book.getMetaId());
        return mapper.mapToDTO(book, meta);
    }

    private void saveBook(BookEntity entity, BookMetaEntity metaEntity) {
        entityService.save(entity);
        metaEntity.setBookId(entity.getId());
        metaEntityService.save(metaEntity);
        entity.setMetaId(metaEntity.getId());
        entityService.save(entity);
    }
}
