package com.mlab.assessment.service;

import com.mlab.assessment.exception.RecordNotFoundException;
import org.springframework.context.MessageSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public abstract class BaseCRUDService<ENTITY, REPO extends JpaRepository<ENTITY, Long>> {

    protected REPO repository;

    public BaseCRUDService(REPO repository){
        this.repository = repository;
    }

    public ENTITY save(ENTITY entity) {
        return repository.save(entity);
    }

    public List<ENTITY> save(List<ENTITY> entityList) {
        return repository.saveAll(entityList);
    }

    @Transactional(readOnly = true)
    public List<ENTITY> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<ENTITY> findById(long id) {
        return repository.findById(id);
    }

    @Transactional(readOnly = true)
    public ENTITY delete(long id) {
        return repository.findById(id)
                .map(entity -> {
                    repository.delete(entity);
                    return entity;
                }).orElseThrow(() -> new RecordNotFoundException("api.response.NOT_FOUND.message"));
    }

}
