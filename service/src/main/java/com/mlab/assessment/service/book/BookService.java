package com.mlab.assessment.service.book;

import com.mlab.assessment.model.request.user.BookSearchRequest;
import com.mlab.assessment.model.request.book.SubmitBookRequest;
import com.mlab.assessment.model.request.book.CreateBookRequest;
import com.mlab.assessment.model.request.book.IssueBookRequest;
import com.mlab.assessment.model.request.book.UpdateBookRequest;
import com.mlab.assessment.model.response.book.BookResponse;
import com.mlab.assessment.model.response.user.UserResponse;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface BookService {

    List<BookResponse> findAllBooks();
    BookResponse findBookById(long id);
    List<BookResponse> searchBook(BookSearchRequest dto);

    @Transactional(rollbackFor = Exception.class)
    BookResponse createBook(CreateBookRequest dto);
    @Transactional(rollbackFor = Exception.class)
    BookResponse updateBook(UpdateBookRequest dto);
    @Transactional(rollbackFor = Exception.class)
    BookResponse deleteBook(long id);

    @Transactional(rollbackFor = Exception.class)
    UserResponse issueBook(IssueBookRequest dto);
    @Transactional(rollbackFor = Exception.class)
    UserResponse submitBooks(SubmitBookRequest dto);
    @Transactional(rollbackFor = Exception.class)
    UserResponse submitAllBooks(long userId);
}
