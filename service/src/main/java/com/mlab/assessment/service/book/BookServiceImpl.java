package com.mlab.assessment.service.book;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.model.request.book.BookDTO;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.service.BaseService;
import com.mlab.assessment.service.BookEntityService;
import com.mlab.assessment.service.BookMetaEntityService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

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
    public BookDTO findBookById(long id) {
        BookEntity book = entityService
                .findById(id)
                .orElseThrow(() -> new RecordNotFoundException(super.getLocalMessage("api.response.NOT_FOUND.message")));

        BookMetaEntity meta = metaEntityService
                .findById(book.getMetaId())
                .orElseThrow(() -> new RecordNotFoundException(super.getLocalMessage("api.response.NOT_FOUND.message")));

        return mapper.mapToDTO(book, meta);
    }

    @Override
    public BookDTO createBook(CreateBookDTO dto) {
        return null;
    }

    @Override
    public BookDTO updateBook(BookDTO dto) {
        return null;
    }
}
