package com.mlab.assessment.controller;

import com.mlab.assessment.model.ApiResponse;
import com.mlab.assessment.model.dto.CreateUserDTO;
import com.mlab.assessment.model.dto.SubmitBookRequestDTO;
import com.mlab.assessment.model.dto.UpdateUserDTO;
import com.mlab.assessment.model.response.user.UserResponseDTO;
import com.mlab.assessment.service.user.UserService;
import com.mlab.assessment.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping("/user/get-all")
    public ResponseEntity<ApiResponse<List<UserResponseDTO>>> getAllUsers() {
        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(userService.findAllUser())
        );
    }

    @GetMapping("/user/get-by-id/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(userService.findUserById(id))
        );
    }

    @PostMapping("/user/create")
    public ResponseEntity<ApiResponse<UserResponseDTO>> createNewUser(@RequestBody @Valid CreateUserDTO dto, BindingResult result) {
        super.throwIfHasError(result);

        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(userService.createUser(dto))
        );
    }

    @PostMapping("/user/update")
    public ResponseEntity<ApiResponse<UserResponseDTO>> updateUser(@RequestBody @Valid UpdateUserDTO dto, BindingResult result) {
        super.throwIfHasError(result);

        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(userService.updateUser(dto))
        );
    }

    @DeleteMapping("/user/delete-by-id/{id}")
    public ResponseEntity<ApiResponse<UserResponseDTO>> deleteUserById(@PathVariable long id){
        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(
                                userService.deleteUser(id)));
    }

    @PostMapping("/user/submit-book")
    public ResponseEntity<ApiResponse<UserResponseDTO>> deleteUserById(@RequestBody @Valid SubmitBookRequestDTO dto){
        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(
                                userService.submitBooks(dto)));
    }
}
