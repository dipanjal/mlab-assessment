package com.mlab.assessment.controller;

import com.mlab.assessment.model.ApiResponse;
import com.mlab.assessment.model.request.user.BookSearchRequest;
import com.mlab.assessment.model.request.book.SubmitBookRequest;
import com.mlab.assessment.model.request.book.CreateBookRequest;
import com.mlab.assessment.model.request.book.IssueBookRequest;
import com.mlab.assessment.model.request.book.UpdateBookRequest;
import com.mlab.assessment.model.response.book.BookResponse;
import com.mlab.assessment.model.response.user.UserResponse;
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
    public ResponseEntity<ApiResponse<List<BookResponse>>> getBooks(){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.findAllBooks()));
    }

    @GetMapping("/book/get-by-id/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> getBookById(@PathVariable long id){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.findBookById(id)));
    }

    @PostMapping("/book/search")
    public ResponseEntity<ApiResponse<List<BookResponse>>> searchBook(@RequestBody BookSearchRequest dto){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.searchBook(dto)));
    }

    @PostMapping("/book/create")
    public ResponseEntity<ApiResponse<BookResponse>> create(@RequestBody @Valid CreateBookRequest dto,
                                                            BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.createBook(dto)));
    }

    @PostMapping("/book/update")
    public ResponseEntity<ApiResponse<BookResponse>> update(@RequestBody @Valid UpdateBookRequest dto,
                                                            BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.updateBook(dto)));
    }

    @DeleteMapping("/book/delete-by-id/{id}")
    public ResponseEntity<ApiResponse<BookResponse>> deleteBookById(@PathVariable long id){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.deleteBook(id)));
    }

    @PostMapping("/book/issue")
    public ResponseEntity<ApiResponse<UserResponse>> issueBook(@RequestBody @Valid IssueBookRequest dto,
                                                               BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.issueBook(dto)));
    }

    @PostMapping("/book/submit")
    public ResponseEntity<ApiResponse<UserResponse>> deleteUserById(@RequestBody @Valid SubmitBookRequest dto){
        return ResponseEntity.ok(
                ResponseBuilder
                        .buildOkResponse(
                                bookService.submitBooks(dto)));
    }

}
