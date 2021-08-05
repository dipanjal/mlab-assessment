package com.mlab.assessment.service.user;

import com.mlab.assessment.annotation.EnableLogging;
import com.mlab.assessment.model.dto.CreateUserDTO;
import com.mlab.assessment.model.dto.UpdateUserDTO;
import com.mlab.assessment.model.response.user.UserResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */

public interface UserService {

    @Transactional(readOnly = true)
    List<UserResponseDTO> findAllUser();
    @Transactional(readOnly = true)
    UserResponseDTO findUserById(long id);
    @Transactional
    UserResponseDTO createUser(CreateUserDTO dto);
    @Transactional
    UserResponseDTO updateUser(UpdateUserDTO dto);
    @Transactional
    UserResponseDTO deleteUser(long id);
}
