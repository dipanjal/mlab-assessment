package com.mlab.assessment.service;

import com.mlab.assessment.model.dto.BookSearchDTO;
import com.mlab.assessment.service.book.BookService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@SpringBootTest
public class BookServiceTest {

    @Autowired
    private BookService bookService;

    @Test
    public void findAllBooksTest(){
        Assertions.assertTrue(
                CollectionUtils.isNotEmpty(
                        bookService.findAllBooks()
                )
        );
    }

    @Test
    public void findBookByIdTest() {
        long id = 1L;
        Assertions.assertNotNull(
                bookService.findBookById(id)
        );
    }

    @Test
    public void searchBookTest(){
        BookSearchDTO dto = new BookSearchDTO();
        dto.setBookName("Java");
        dto.setAuthorName("Rayan");
        Assertions.assertTrue(
                CollectionUtils.isNotEmpty(
                        bookService.searchBook(dto)
                )
        );
    }
}
