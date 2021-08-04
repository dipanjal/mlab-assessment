package com.mlab.assessment.controller;

import com.mlab.assessment.model.ApiResponse;
import com.mlab.assessment.model.dto.BookSearchDTO;
import com.mlab.assessment.model.request.book.BookDTO;
import com.mlab.assessment.model.request.book.CreateBookDTO;
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
    public ResponseEntity<ApiResponse<List<BookDTO>>> getBooks(){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.findAllBooks()));
    }

    @GetMapping("/book/get-by-id/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable long id){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.findBookById(id)));
    }

    @PostMapping("/book/search")
    public ResponseEntity<ApiResponse<List<BookDTO>>> searchBook(@RequestBody BookSearchDTO dto){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.searchBook(dto)));
    }

    @PostMapping("/book/create")
    public ResponseEntity<ApiResponse<BookDTO>> create(@RequestBody @Valid CreateBookDTO dto,
                                                       BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.createBook(dto)));
    }

    @PostMapping("/book/update")
    public ResponseEntity<ApiResponse<BookDTO>> update(@RequestBody @Valid BookDTO dto,
                                                       BindingResult result){
        super.throwIfHasError(result);
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.updateBook(dto)));
    }

    @DeleteMapping("/book/delete-by-id/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> deleteBookById(@PathVariable long id){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.deleteBook(id)));
    }

}
