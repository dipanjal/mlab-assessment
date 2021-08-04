package com.mlab.assessment.service;

import com.mlab.assessment.entity.UserEntity;
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
}
