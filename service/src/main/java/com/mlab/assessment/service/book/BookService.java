package com.mlab.assessment.service.book;

import com.mlab.assessment.model.dto.BookSearchDTO;
import com.mlab.assessment.model.dto.SubmitBookRequestDTO;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.model.request.book.IssueBookRequestDTO;
import com.mlab.assessment.model.request.book.UpdateBookDTO;
import com.mlab.assessment.model.response.book.BookResponseDTO;
import com.mlab.assessment.model.response.user.UserResponseDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
public interface BookService {

    List<BookResponseDTO> findAllBooks();
    BookResponseDTO findBookById(long id);
    List<BookResponseDTO> searchBook(BookSearchDTO dto);

    @Transactional(rollbackFor = Exception.class)
    BookResponseDTO createBook(CreateBookDTO dto);
    @Transactional(rollbackFor = Exception.class)
    BookResponseDTO updateBook(UpdateBookDTO dto);
    @Transactional(rollbackFor = Exception.class)
    BookResponseDTO deleteBook(long id);

    @Transactional(rollbackFor = Exception.class)
    UserResponseDTO issueBook(IssueBookRequestDTO dto);
    @Transactional(rollbackFor = Exception.class)
    UserResponseDTO submitBooks(SubmitBookRequestDTO dto);


}
