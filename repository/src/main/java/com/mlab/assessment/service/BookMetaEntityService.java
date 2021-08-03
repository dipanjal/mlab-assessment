package com.mlab.assessment.service;

import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.repository.BookMetaRepository;
import org.springframework.stereotype.Service;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class BookMetaEntityService extends BaseCRUDService<BookMetaEntity, BookMetaRepository> {
    public BookMetaEntityService(BookMetaRepository repository) {
        super(repository);
    }
}
