package com.mlab.assessment.service.book;

import com.mlab.assessment.model.dto.BookSearchDTO;
import com.mlab.assessment.model.request.book.BookDTO;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface BookService {

    List<BookDTO> findAllBooks();
    BookDTO findBookById(long id);
    List<BookDTO> searchBook(BookSearchDTO dto);

    @Transactional(rollbackFor = Exception.class)
    BookDTO createBook(CreateBookDTO dto);
    @Transactional(rollbackFor = Exception.class)
    BookDTO updateBook(BookDTO dto);
    @Transactional(rollbackFor = Exception.class)
    BookDTO deleteBook(long id);
}
