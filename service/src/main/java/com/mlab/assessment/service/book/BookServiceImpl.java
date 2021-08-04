package com.mlab.assessment.service.book;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.model.dto.BookSearchDTO;
import com.mlab.assessment.model.request.book.BookDTO;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.service.BaseService;
import com.mlab.assessment.service.BookEntityService;
import com.mlab.assessment.service.BookMetaEntityService;
import com.mlab.assessment.service.LocaleMessageHelper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
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
    private final LocaleMessageHelper messageHelper;

    @Override
    public List<BookDTO> findAllBooks() {
        List<BookMetaEntity> metaList = metaEntityService.findAll();
        if(CollectionUtils.isEmpty(metaList))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        return mapper.mapToDTO(metaList);
    }

    @Override
    public BookDTO findBookById(long id) {
        BookEntity book = entityService
                .findById(id)
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        BookMetaEntity meta = metaEntityService
                .findById(book.getMetaId())
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        return mapper.mapToDTO(meta);
    }

    @Override
    public List<BookDTO> searchBook(BookSearchDTO dto) {
        List<BookMetaEntity> metaList = metaEntityService.searchBook(dto);
        if(CollectionUtils.isEmpty(metaList))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        return mapper.mapToDTO(metaList);
    }

    @Override
    public BookDTO createBook(CreateBookDTO dto) {
        BookEntity bookEntity = new BookEntity();
        BookMetaEntity bookMetaEntity = mapper.mapToNewBookMetaEntity(dto);
        this.saveBook(bookEntity, bookMetaEntity);
        return mapper.mapToDTO(bookMetaEntity);
    }

    @Override
    public BookDTO updateBook(BookDTO dto) {
        BookEntity book = entityService.findById(dto.getId())
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        BookMetaEntity meta = metaEntityService.findById(book.getMetaId())
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        mapper.mapToUpdatableBookMetaEntity(meta, dto);

        saveBook(book, meta);
        return mapper.mapToDTO(meta);
    }

    @Override
    public BookDTO deleteBook(long id) {
        BookEntity book = entityService.delete(id);
        BookMetaEntity meta = metaEntityService.delete(book.getMetaId());
        return mapper.mapToDTO(meta);
    }

    private void saveBook(BookEntity entity, BookMetaEntity metaEntity) {
        entityService.save(entity);
        metaEntity.setBookId(entity.getId());
        metaEntityService.save(metaEntity);
        entity.setMetaId(metaEntity.getId());
        entityService.save(entity);
    }
}
