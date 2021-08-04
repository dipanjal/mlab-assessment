package com.mlab.assessment.service;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.model.dto.BookSearchDTO;
import com.mlab.assessment.repository.BookMetaRepository;
import com.mlab.assessment.specs.BookMetaSpecification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public List<BookMetaEntity> findBookMetaByIdIn(List<Long> ids){
        return repository.findByIdIn(ids);
    }

    public List<BookMetaEntity> findMetaInBooks(List<BookEntity> bookEntities){
        return repository.findByIdIn(getMetaIds(bookEntities));
    }

    private List<Long> getMetaIds(List<BookEntity> bookEntities){
        return bookEntities
                .stream()
                .mapToLong(BookEntity::getMetaId)
                .boxed()
                .collect(Collectors.toList());
    }
}
