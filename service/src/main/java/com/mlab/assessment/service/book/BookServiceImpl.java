package com.mlab.assessment.service.book;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.exception.BadRequestException;
import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.model.dto.BookSearchDTO;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.model.request.book.IssueBookDTO;
import com.mlab.assessment.model.request.book.UpdateBookDTO;
import com.mlab.assessment.model.response.book.BookResponseDTO;
import com.mlab.assessment.model.response.user.UserResponseDTO;
import com.mlab.assessment.service.BaseService;
import com.mlab.assessment.service.BookEntityService;
import com.mlab.assessment.service.BookMetaEntityService;
import com.mlab.assessment.service.UserEntityService;
import com.mlab.assessment.service.user.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class BookServiceImpl extends BaseService implements BookService {

    private final BookEntityService bookEntityService;
    private final BookMetaEntityService metaEntityService;
    private final UserEntityService userEntityService;
    private final BookMapper mapper;
    private final UserMapper userMapper;

    @Override
    public List<BookResponseDTO> findAllBooks() {
        List<BookEntity> books = bookEntityService.findAll();
        if(CollectionUtils.isEmpty(books))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        List<BookMetaEntity> metaList = metaEntityService.findAll();
        if(CollectionUtils.isEmpty(metaList))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        return mapper.mapToBookResponseDTO(books, metaList);
    }

    @Override
    public BookResponseDTO findBookById(long id) {
        BookEntity book = bookEntityService
                .findById(id)
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        BookMetaEntity meta = metaEntityService
                .findById(book.getMetaId())
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        return mapper.mapToBookResponseDTO(book, meta);
    }

    @Override
    public List<BookResponseDTO> searchBook(BookSearchDTO dto) {
        List<BookMetaEntity> metaEntities = metaEntityService.searchBook(dto);
        if(CollectionUtils.isEmpty(metaEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        List<BookEntity> bookEntities = bookEntityService.findBooksInMeta(metaEntities);
        return mapper.mapToBookResponseDTO(bookEntities, metaEntities);
    }

    @Override
    public BookResponseDTO createBook(CreateBookDTO dto) {
        BookEntity bookEntity = new BookEntity();
        BookMetaEntity metaEntity = mapper.mapToNewBookMetaEntity(dto);
        this.saveBook(bookEntity, metaEntity);
        return mapper.mapToBookResponseDTO(bookEntity, metaEntity);
    }

    @Override
    public BookResponseDTO updateBook(UpdateBookDTO dto) {
        BookEntity bookEntity = bookEntityService.findById(dto.getId())
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        BookMetaEntity metaEntity = metaEntityService.findById(bookEntity.getMetaId())
                .orElseThrow(super.supplyRecordNotFoundException(RECORD_NOT_FOUND_MSG_KEY));

        mapper.fillUpdatableEntity(metaEntity, dto);

        saveBook(bookEntity, metaEntity);
        return mapper.mapToBookResponseDTO(bookEntity, metaEntity);
    }

    @Override
    public UserResponseDTO issueBook(IssueBookDTO dto) {

        if(CollectionUtils.isEmpty(dto.getBookIds()))
            throw new BadRequestException("validation.constraints.issueBook.Empty.message");

        if(dto.getBookIds().size() > props.getMaxIssueBook())
            throw new BadRequestException("validation.constraints.issueBook.Invalid.Max.message");

        UserEntity userEntity = userEntityService.findById(dto.getUserId())
                .orElseThrow(super.supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        List<BookEntity> bookEntities = bookEntityService.findBooksByIdIn(dto.getBookIds());
        if(CollectionUtils.isEmpty(bookEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        List<BookMetaEntity> metaEntities = metaEntityService.findMetaInBooks(bookEntities);
        if(CollectionUtils.isEmpty(metaEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        mapper.fillIssuableEntity(bookEntities, metaEntities, userEntity);
        bookEntityService.save(bookEntities);
        metaEntityService.save(metaEntities);
//        return mapper.mapToBookResponseDTO(metaEntities, userEntity);
        return userMapper.mapToDto(userEntity, metaEntityService.findMetaInBooks(userEntity.getBooks()));
    }

    @Override
    public BookResponseDTO deleteBook(long id) {
        try{
            BookEntity bookEntity = bookEntityService.delete(id);
            BookMetaEntity metaEntity = metaEntityService.delete(bookEntity.getMetaId());
            return mapper.mapToBookResponseDTO(bookEntity, metaEntity);
        }catch (RecordNotFoundException e){
            throw new RecordNotFoundException(messageHelper.getLocalMessage(e.getMessage()));
        }
    }

    private void saveBook(BookEntity entity, BookMetaEntity metaEntity) {
        bookEntityService.save(entity);
        metaEntity.setBookId(entity.getId());
        metaEntityService.save(metaEntity);
        entity.setMetaId(metaEntity.getId());
        bookEntityService.save(entity);
    }


}
