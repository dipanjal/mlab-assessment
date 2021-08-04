package com.mlab.assessment.service;

import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.model.dto.BookSearchDTO;
import com.mlab.assessment.repository.BookMetaRepository;
import com.mlab.assessment.specs.BookMetaSpecification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Service
public class BookMetaEntityService extends BaseCRUDService<BookMetaEntity, BookMetaRepository> {
    public BookMetaEntityService(BookMetaRepository repository) {
        super(repository);
    }

    public List<BookMetaEntity> searchBook(BookSearchDTO searchDTO){
        return repository.findAll(
                BookMetaSpecification.getSearchSpecification(searchDTO));
    }
}
