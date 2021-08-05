package com.mlab.assessment.repository;

import com.mlab.assessment.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Collection;
import java.util.List;
import java.util.Set;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface BookRepository extends JpaRepository<BookEntity, Long>, JpaSpecificationExecutor<BookEntity> {

    List<BookEntity> findByIdIn(Set<Long> ids);
}
