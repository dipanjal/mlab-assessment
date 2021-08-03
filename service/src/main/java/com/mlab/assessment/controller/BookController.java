package com.mlab.assessment.controller;

import com.mlab.assessment.exception.BadRequestException;
import com.mlab.assessment.model.ApiResponse;
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

/**
 * @author dipanjal
 * @since 0.0.1
 */
@RestController
@RequiredArgsConstructor
@Slf4j
public class BookController extends BaseController {

    private final BookService bookService;

    @GetMapping("/book/get-by-id/{id}")
    public ResponseEntity<ApiResponse<BookDTO>> getBookById(@PathVariable long id){
        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        bookService.findBookById(id)));
    }

    @PostMapping("/book/create")
    public ResponseEntity<ApiResponse<BookDTO>> create(@RequestBody @Valid CreateBookDTO dto, BindingResult result){
        if(result.hasErrors())
            throw new BadRequestException(super.getJoinedErrorMessage(result));

        return ResponseEntity.ok(
                ResponseBuilder.buildOkResponse(
                        BookDTO.builder().build()));
    }
}
