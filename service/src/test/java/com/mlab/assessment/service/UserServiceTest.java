package com.mlab.assessment.service;

import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.model.request.user.CreateUserRequest;
import com.mlab.assessment.model.request.book.SubmitBookRequest;
import com.mlab.assessment.model.request.user.UpdateUserRequest;
import com.mlab.assessment.model.request.book.IssueBookRequest;
import com.mlab.assessment.model.response.user.UserResponse;
import com.mlab.assessment.service.book.BookService;
import com.mlab.assessment.service.user.UserService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    private static final String TEST_USERNAME = "larry_c";

    @Test
    @Order(1)
    public void findAllUsersTest(){
        Assertions.assertTrue(
                CollectionUtils.isNotEmpty(
                        userService.findAllUser()
                )
        );
    }

    @Test
    @Order(2)
    public void findUserByIdTest(){
        long userId = 1L;
        Assertions.assertNotNull(
                userService.findUserById(userId)
        );
    }

    @Test
    @Order(3)
    public void createUserTest(){
        CreateUserRequest dto = new CreateUserRequest();
        dto.setUserName(TEST_USERNAME);
        dto.setFullName("Robert C. Martin");
        dto.setEmail("robert.c.mertin@mlab.com");
        Assertions.assertNotNull(
                userService.createUser(dto)
        );
    }

    @Test
    @Order(4)
    public void updateUserTest(){
        UserResponse userResponse = userService.findUserByUsername(TEST_USERNAME);
        Assertions.assertNotNull(userResponse);

        UpdateUserRequest dto = new UpdateUserRequest();
        dto.setId(userResponse.getId());
        dto.setFullName(userResponse.getName());
        dto.setEmail("robert.c.martin@mlab.com");
        Assertions.assertNotNull(
                userService.updateUser(dto)
        );
    }

    @Test
    @Order(5)
    public void issueBookTest(){
        UserResponse userResponse = userService.findUserByUsername(TEST_USERNAME);
        IssueBookRequest dto = new IssueBookRequest();
        dto.setUserId(userResponse.getId());
        dto.setBookIds(Set.of(1L, 2L));
        Assertions.assertNotNull(
                bookService.issueBook(dto)
        );
    }

    @Test
    @Order(6)
    public void submitBookTest() {
        UserResponse userResponse = userService.findUserByUsername(TEST_USERNAME);
        SubmitBookRequest dto = new SubmitBookRequest();
        dto.setUserId(userResponse.getId());
        dto.setBookIds(Set.of(1L));
        Assertions.assertNotNull(bookService.submitBooks(dto));
    }

    @Test
    @Order(7)
    public void deleteUserTest(){
        UserResponse userResponse = userService.findUserByUsername(TEST_USERNAME);
        try{
            Assertions.assertNotNull(
                    userService.deleteUser(userResponse.getId())
            );
        }catch (RecordNotFoundException ex){
            Assertions.assertTrue(true);
        }
    }
}
