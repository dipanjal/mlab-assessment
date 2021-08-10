package com.mlab.assessment.service;

import com.mlab.assessment.model.request.book.CreateBookRequest;
import com.mlab.assessment.model.request.user.BookSearchRequest;
import com.mlab.assessment.service.book.BookService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    @Order(1)
    public void findAllBooksTest(){
        Assertions.assertTrue(
                CollectionUtils.isNotEmpty(
                        bookService.findAllBooks()
                )
        );
    }

    @Test
    @Order(2)
    public void findBookByIdTest() {
        long id = 1L;
        Assertions.assertNotNull(
                bookService.findBookById(id)
        );
    }

    @Test
    @Order(3)
    public void createNewBookTest(){
        Assertions.assertNotNull(
                bookService.createBook(
                        new CreateBookRequest(
                                "Headfirst Design Pattern",
                                "Elisabeth Freeman, Kathy Sierra",
                                "All about learning Design Patterns",
                                10,
                                "10-09-2004"
                        )
                )
        );
    }

    @Test
    @Order(4)
    public void searchBookTest(){
        Assertions.assertTrue(
                CollectionUtils.isNotEmpty(
                        bookService.searchBook(
                                new BookSearchRequest(
                                        "Design",
                                        "Kathy"
                                )
                        )
                )
        );
    }
}
