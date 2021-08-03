package com.mlab.assessment.repository;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface BookMetaRepository extends JpaRepository<BookMetaEntity, Long> {

}
