package com.mlab.assessment.service.book;

import com.mlab.assessment.annotation.EnableLogging;
import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.exception.BadRequestException;
import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.model.dto.IssueBookValidationDTO;
import com.mlab.assessment.model.request.book.CreateBookRequest;
import com.mlab.assessment.model.request.book.IssueBookRequest;
import com.mlab.assessment.model.request.book.SubmitBookRequest;
import com.mlab.assessment.model.request.book.UpdateBookRequest;
import com.mlab.assessment.model.request.user.BookSearchRequest;
import com.mlab.assessment.model.response.EmailResponse;
import com.mlab.assessment.model.response.book.BookResponse;
import com.mlab.assessment.model.response.user.UserResponse;
import com.mlab.assessment.service.BaseService;
import com.mlab.assessment.service.BookEntityService;
import com.mlab.assessment.service.BookMetaEntityService;
import com.mlab.assessment.service.UserEntityService;
import com.mlab.assessment.service.email.EmailService;
import com.mlab.assessment.service.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
@Slf4j
@RequiredArgsConstructor
@EnableLogging
public class BookServiceImpl extends BaseService implements BookService {

    private final BookEntityService bookEntityService;
    private final BookMetaEntityService metaEntityService;
    private final UserEntityService userEntityService;
    private final EmailService emailService;

    private final BookMapper mapper;
    private final UserMapper userMapper;

    @Override
    public List<BookResponse> findAllBooks() {
        List<BookEntity> books = bookEntityService.findAll();
        if(CollectionUtils.isEmpty(books))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        List<BookMetaEntity> metaList = metaEntityService.findAll();
        if(CollectionUtils.isEmpty(metaList))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        return mapper.mapToBookResponseDTO(books, metaList);
    }

    @Override
    public BookResponse findBookById(long id) {
        BookEntity book = bookEntityService
                .findById(id)
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        BookMetaEntity meta = metaEntityService
                .findById(book.getMetaId())
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        return mapper.mapToBookResponseDTO(book, meta);
    }

    @Override
    public List<BookResponse> searchBook(BookSearchRequest dto) {
        List<BookMetaEntity> metaEntities = metaEntityService.searchBook(dto);
        if(CollectionUtils.isEmpty(metaEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        List<BookEntity> bookEntities = bookEntityService.findBooksInMeta(metaEntities);
        return mapper.mapToBookResponseDTO(bookEntities, metaEntities);
    }

    @Override
    public BookResponse createBook(CreateBookRequest dto) {
        BookEntity bookEntity = new BookEntity();
        BookMetaEntity metaEntity = mapper.mapToNewBookMetaEntity(dto);
        this.saveBook(bookEntity, metaEntity);
        return mapper.mapToBookResponseDTO(bookEntity, metaEntity);
    }

    @Override
    public BookResponse updateBook(UpdateBookRequest dto) {
        BookEntity bookEntity = bookEntityService
                .findById(dto.getId())
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        BookMetaEntity metaEntity = metaEntityService
                .findById(bookEntity.getMetaId())
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        mapper.fillUpdatableEntity(metaEntity, dto);

        saveBook(bookEntity, metaEntity);
        return mapper.mapToBookResponseDTO(bookEntity, metaEntity);
    }

    @Override
    public UserResponse issueBook(IssueBookRequest request) {
        if(CollectionUtils.isEmpty(request.getBookIds()))
            throw new BadRequestException("validation.constraints.issueBook.Empty.message");

        if(request.getBookIds().size() > props.getMaxIssueBook())
            throw new BadRequestException("validation.constraints.issueBook.Invalid.Max.message");

        IssueBookValidationDTO validationDTO = this.validateIssueBookRequest(request);

        mapper.fillIssuableEntity(validationDTO.getBookEntities(), validationDTO.getMetaEntities(), validationDTO.getUserEntity());
        bookEntityService.save(validationDTO.getBookEntities());
        metaEntityService.save(validationDTO.getMetaEntities());

        emailService.sendEmail(EmailResponse
                .builder()
                .email(validationDTO.getUserEntity().getEmail())
                .body(String.format("Book issued by: %s", validationDTO.getUserEntity().getFullName()))
                .build());

        return userMapper.mapToDto(
                validationDTO.getUserEntity(),
                metaEntityService.findMetaInBooks(validationDTO.getUserEntity().getBooks())
        );
    }

    @Override
    public UserResponse submitBooks(SubmitBookRequest dto) {
        if(CollectionUtils.isEmpty(dto.getBookIds()))
            throw new BadRequestException(messageHelper.getLocalMessage("api.response.BAD_REQUEST.message"));

        UserEntity userEntity = userEntityService
                .findById(dto.getUserId())
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        if(CollectionUtils.isEmpty(userEntity.getBooks()))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("validation.constraints.user.NotIssued.books.message"));

        submitBooks(userEntity, dto.getBookIds());

        emailService.sendEmail(EmailResponse
                .builder()
                .email(userEntity.getEmail())
                .body(String.format("Book submitted by: %s", userEntity.getFullName()))
                .build());

        List<BookMetaEntity> newMetaList = metaEntityService.findMetaInBooks(userEntity.getBooks());
        return userMapper.mapToDto(userEntity, newMetaList);
    }

    @Override
    public UserResponse submitAllBooks(long userId) {
        UserEntity userEntity = userEntityService
                .findById(userId)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        if(CollectionUtils.isEmpty(userEntity.getBooks()))
            return userMapper.mapToDto(userEntity);

        List<BookMetaEntity> metaEntities = metaEntityService.findMetaInBooks(userEntity.getBooks());
        if(CollectionUtils.isEmpty(metaEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        mapper.fillBookSubmissionEntity(userEntity, userEntity.getBooks(), metaEntities);
        userEntityService.save(userEntity);
        metaEntityService.save(metaEntities);
        return userMapper.mapToDto(userEntity);
    }

    @Override
    public BookResponse deleteBook(long id) {
        try{
            BookEntity bookEntity = bookEntityService.delete(id);
            BookMetaEntity metaEntity = metaEntityService.delete(bookEntity.getMetaId());
            return mapper.mapToBookResponseDTO(bookEntity, metaEntity);
        }catch (RecordNotFoundException e){
            throw new RecordNotFoundException(messageHelper.getLocalMessage(e.getMessage()));
        }
    }

    private IssueBookValidationDTO validateIssueBookRequest(IssueBookRequest dto){
        UserEntity userEntity = userEntityService
                .findById(dto.getUserId())
                .orElseThrow(super.supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        List<BookEntity> bookEntities = bookEntityService.findBooksByIdIn(dto.getBookIds());
        if(CollectionUtils.isEmpty(bookEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        List<BookMetaEntity> metaEntities = metaEntityService.findMetaInBooks(bookEntities);
        if(CollectionUtils.isEmpty(metaEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        return IssueBookValidationDTO.builder()
                .userEntity(userEntity)
                .bookEntities(bookEntities)
                .metaEntities(metaEntities)
                .build();
    }

    private void submitBooks(UserEntity userEntity, Set<Long> bookIds){
        List<BookEntity> issuedBooks = mapper.extractIssuedBooks(userEntity, bookIds);

        if(CollectionUtils.isEmpty(issuedBooks))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("validation.constraints.issueBook.NotFound.message"));

        List<BookMetaEntity> metaEntities = metaEntityService.findMetaInBooks(issuedBooks);
        if(CollectionUtils.isEmpty(metaEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        mapper.fillBookSubmissionEntity(userEntity, bookIds, issuedBooks, metaEntities);
        userEntityService.save(userEntity);
        metaEntityService.save(metaEntities);
    }

    private void saveBook(BookEntity entity, BookMetaEntity metaEntity) {
        bookEntityService.save(entity);
        metaEntity.setBookId(entity.getId());
        metaEntityService.save(metaEntity);
        entity.setMetaId(metaEntity.getId());
        bookEntityService.save(entity);
    }


}
