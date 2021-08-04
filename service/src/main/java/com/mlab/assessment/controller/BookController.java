package com.mlab.assessment.controller;

import com.mlab.assessment.model.ApiResponse;
import com.mlab.assessment.model.dto.BookSearchDTO;
import com.mlab.assessment.model.request.book.IssueBookDTO;
import com.mlab.assessment.model.request.book.UpdateBookDTO;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.model.response.book.BookResponseDTO;
import com.mlab.assessment.service.book.BookService;
import com.mlab.assessment.utils.ResponseBuilder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class BookController extends BaseController {

    private final BookService bookService;

    @GetMapping("/book/get-all")
    public ResponseEntity<ApiResponse<List<BookResponseDTO>>> getBooks(){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.findAllBooks()));
    }

    @GetMapping("/book/get-by-id/{id}")
    public ResponseEntity<ApiResponse<BookResponseDTO>> getBookById(@PathVariable long id){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.findBookById(id)));
    }

    @PostMapping("/book/search")
    public ResponseEntity<ApiResponse<List<BookResponseDTO>>> searchBook(@RequestBody BookSearchDTO dto){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.searchBook(dto)));
    }

    @PostMapping("/book/create")
    public ResponseEntity<ApiResponse<BookResponseDTO>> create(@RequestBody @Valid CreateBookDTO dto,
                                                             BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.createBook(dto)));
    }

    @PostMapping("/book/update")
    public ResponseEntity<ApiResponse<BookResponseDTO>> update(@RequestBody @Valid UpdateBookDTO dto,
                                                             BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.updateBook(dto)));
    }

    @PostMapping("/book/issue")
    public ResponseEntity<ApiResponse<List<BookResponseDTO>>> issueBook(@RequestBody @Valid IssueBookDTO dto,
                                                                  BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.issueBook(dto)));
    }

    @DeleteMapping("/book/delete-by-id/{id}")
    public ResponseEntity<ApiResponse<BookResponseDTO>> deleteBookById(@PathVariable long id){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.deleteBook(id)));
    }

}
