package com.mlab.assessment.service;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.repository.BookRepository;
import com.mlab.assessment.specs.BookSpecification;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class BookEntityService extends BaseCRUDService<BookEntity, BookRepository> {
    public BookEntityService(BookRepository repository) {
        super(repository);
    }

    public List<BookEntity> findBooksByIdIn(Set<Long> ids){
        return repository.findByIdIn(ids);
    }

    public List<BookEntity> findBooksInMeta(Collection<BookMetaEntity> metaEntityList){
        return repository.findByIdIn(getBookIds(metaEntityList));
    }

    @Override
    public BookEntity delete(long id){
        return repository.findById(id)
                .map(bookEntity -> {
                    bookEntity.getUsers().forEach( user -> user.setBooks(null));
                    bookEntity.getUsers().clear();
                    repository.delete(bookEntity);
                    return bookEntity;
                }).orElseThrow(() -> new RecordNotFoundException("api.response.NOT_FOUND.message"));
    }

    private Set<Long> getBookIds(Collection<BookMetaEntity> metaEntityList){
        return metaEntityList
                .stream()
                .mapToLong(BookMetaEntity::getBookId)
                .boxed()
                .collect(Collectors.toSet());
    }
}
