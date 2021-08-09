package com.mlab.assessment.controller;

import com.mlab.assessment.model.ApiResponse;
import com.mlab.assessment.model.request.user.CreateUserRequest;
import com.mlab.assessment.model.request.user.UpdateUserRequest;
import com.mlab.assessment.model.response.user.UserResponse;
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
    public ResponseEntity<ApiResponse<List<UserResponse>>> getAllUsers() {
        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(userService.findAllUser())
        );
    }

    @GetMapping("/user/get-by-id/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> getUserById(@PathVariable long id) {
        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(userService.findUserById(id))
        );
    }

    @PostMapping("/user/create")
    public ResponseEntity<ApiResponse<UserResponse>> createNewUser(@RequestBody @Valid CreateUserRequest dto, BindingResult result) {
        super.throwIfHasError(result);

        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(userService.createUser(dto))
        );
    }

    @PostMapping("/user/update")
    public ResponseEntity<ApiResponse<UserResponse>> updateUser(@RequestBody @Valid UpdateUserRequest dto, BindingResult result) {
        super.throwIfHasError(result);

        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(userService.updateUser(dto))
        );
    }

    @DeleteMapping("/user/delete-by-id/{id}")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@PathVariable long id){
        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(
                                userService.deleteUser(id)));
    }
}
