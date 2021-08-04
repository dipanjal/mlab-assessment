package com.mlab.assessment.repository;

import com.mlab.assessment.entity.BookMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface BookMetaRepository extends JpaRepository<BookMetaEntity, Long>, JpaSpecificationExecutor<BookMetaEntity> {

}
