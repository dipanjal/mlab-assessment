package com.mlab.assessment.service;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.repository.BookRepository;
import org.springframework.stereotype.Service;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class BookEntityService extends BaseCRUDService<BookEntity, BookRepository> {
    public BookEntityService(BookRepository repository) {
        super(repository);
    }
}
