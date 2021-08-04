package com.mlab.assessment.repository;

import com.mlab.assessment.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface BookRepository extends JpaRepository<BookEntity, Long> {

    List<BookEntity> findByIdIn(Set<Long> ids);
}
