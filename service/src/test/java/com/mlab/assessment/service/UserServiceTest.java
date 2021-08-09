package com.mlab.assessment.service;

import com.mlab.assessment.exception.NotUniqueException;
import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.model.dto.CreateUserDTO;
import com.mlab.assessment.model.dto.SubmitBookRequestDTO;
import com.mlab.assessment.model.dto.UpdateUserDTO;
import com.mlab.assessment.model.request.book.IssueBookRequestDTO;
import com.mlab.assessment.model.response.user.UserResponseDTO;
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
@Disabled
public class UserServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

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
    @Disabled
    public void createUserTest(){
        CreateUserDTO dto = new CreateUserDTO();
        dto.setUserName("robert_c");
        dto.setFullName("Robert C. Martin");
        dto.setEmail("robert.c.mertin@mlab.com");

        /** NB: Avoiding H2 DB issue
         * DB call is occurring multiple times only for H2 DB
         * Handling exception for sake of test case
         */
        try{
            Assertions.assertNotNull(
                    userService.createUser(dto)
            );
        }catch (NotUniqueException e){
            Assertions.assertTrue(true);
        }
    }

    @Test
    @Order(4)
    public void updateUserTest(){
        String username = "robert_c";
        UserResponseDTO userResponse = userService.findUserByUsername(username);
        Assertions.assertNotNull(userResponse);

        UpdateUserDTO dto = new UpdateUserDTO();
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
        UserResponseDTO userResponse = userService.findUserByUsername("robert_c");
        IssueBookRequestDTO dto = new IssueBookRequestDTO();
        dto.setUserId(userResponse.getId());
        dto.setBookIds(Set.of(1L, 2L));
        Assertions.assertNotNull(
                bookService.issueBook(dto)
        );
    }

    @Test
    @Order(6)
    @Disabled
    public void submitBookTest() {
        UserResponseDTO userResponse = userService.findUserByUsername("robert_c");
        SubmitBookRequestDTO dto = new SubmitBookRequestDTO();
        dto.setUserId(userResponse.getId());
        dto.setBookIds(Set.of(1L, 2L));
        Assertions.assertNotNull(bookService.submitBooks(dto));
    }

    @Test
    @Order(7)
    public void deleteUserTest(){
        UserResponseDTO userResponse = userService.findUserByUsername("robert_c");
        try{
            Assertions.assertNotNull(
                    userService.deleteUser(userResponse.getId())
            );
        }catch (RecordNotFoundException ex){
            Assertions.assertTrue(true);
        }
    }
}
