package com.mlab.assessment.service;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.repository.UserRepository;
import org.springframework.stereotype.Service;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
public class UserEntityService extends BaseCRUDService<UserEntity, UserRepository> {
    public UserEntityService(UserRepository repository) {
        super(repository);
    }

    @Override
    public UserEntity delete(long id){
        return repository.findById(id)
                .map(userEntity -> {
                    userEntity.getBooks().forEach( book -> book.setUsers(null));
                    userEntity.getBooks().clear();
                    repository.delete(userEntity);
                    return userEntity;
                }).orElseThrow(() -> new RecordNotFoundException("validation.constraints.userId.NotFound.message"));
    }
}
