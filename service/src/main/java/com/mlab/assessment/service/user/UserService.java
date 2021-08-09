package com.mlab.assessment.service.user;

import com.mlab.assessment.model.request.user.CreateUserRequest;
import com.mlab.assessment.model.request.user.UpdateUserRequest;
import com.mlab.assessment.model.response.user.UserResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

public interface UserService {

    @Transactional(readOnly = true)
    List<UserResponse> findAllUser();
    @Transactional(readOnly = true)
    UserResponse findUserById(long id);
    @Transactional(readOnly = true)
    UserResponse findUserByUsername(String userName);
    @Transactional
    UserResponse createUser(CreateUserRequest dto);
    @Transactional
    UserResponse updateUser(UpdateUserRequest dto);
    @Transactional
    UserResponse deleteUser(long id);
}
