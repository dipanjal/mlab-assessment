package com.mlab.assessment.service.user;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.exception.BadRequestException;
import com.mlab.assessment.exception.RecordNotFoundException;
import com.mlab.assessment.model.dto.CreateUserDTO;
import com.mlab.assessment.model.dto.SubmitBookRequestDTO;
import com.mlab.assessment.model.dto.UpdateUserDTO;
import com.mlab.assessment.model.response.user.UserResponseDTO;
import com.mlab.assessment.service.BaseService;
import com.mlab.assessment.service.BookEntityService;
import com.mlab.assessment.service.BookMetaEntityService;
import com.mlab.assessment.service.UserEntityService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author dipanjal
 * @since 0.0.1
 */

@Service
@RequiredArgsConstructor
public class UserServiceImpl extends BaseService implements UserService {

    private final UserEntityService userEntityService;
    private final BookMetaEntityService metaEntityService;
    private final BookEntityService bookEntityService;
    private final UserMapper mapper;

    @Override
    public List<UserResponseDTO> findAllUser() {
        List<UserEntity> userEntities = userEntityService.findAll();
        List<BookMetaEntity> metaEntities = metaEntityService.findAll();
        return mapper.mapToDto(userEntities, metaEntities);
    }

    @Override
    public UserResponseDTO findUserById(long id) {
        UserEntity userEntity = userEntityService.findById(id)
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        List<BookMetaEntity> metaEntityList = metaEntityService.findMetaInBooks(userEntity.getBooks());
        return mapper.mapToDto(userEntity, metaEntityList);

    }

    @Override
    public UserResponseDTO createUser(CreateUserDTO dto) {
        UserEntity entity = mapper.mapToNewUserEntity(dto);
        userEntityService.save(entity);
        return getUserResponseDTO(entity);
    }

    @Override
    public UserResponseDTO updateUser(UpdateUserDTO dto) {
        UserEntity updatableEntity = userEntityService.findById(dto.getId())
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        mapper.fillUpdatableEntity(updatableEntity, dto);
        userEntityService.save(updatableEntity);
        return getUserResponseDTO(updatableEntity);
    }

    @Override
    public UserResponseDTO deleteUser(long id) {
        try{
            return mapper.mapToDto(userEntityService.delete(id));
        }catch (RecordNotFoundException e) {
            throw new RecordNotFoundException(messageHelper.getLocalMessage(e.getMessage()));
        }
    }

    @Override
    public UserResponseDTO submitBooks(SubmitBookRequestDTO dto) {
        if(CollectionUtils.isEmpty(dto.getBookIds()))
            throw new BadRequestException(messageHelper.getLocalMessage("api.response.BAD_REQUEST.message"));

        UserEntity userEntity = userEntityService.findById(dto.getUserId())
                .orElseThrow(supplyRecordNotFoundException("validation.constraints.userId.NotFound.message"));

        List<BookEntity> issuedBooks = userEntity
                .getBooks()
                .stream()
                .filter(book -> dto.getBookIds().contains(book.getId()))
                .collect(Collectors.toList());

        if(CollectionUtils.isEmpty(issuedBooks))
            throw new RecordNotFoundException(messageHelper.getLocalMessage("validation.constraints.issueBook.Empty.message"));

        List<BookMetaEntity> metaEntities = metaEntityService.findMetaInBooks(issuedBooks);
        if(CollectionUtils.isEmpty(metaEntities))
            throw new RecordNotFoundException(messageHelper.getLocalMessage(RECORD_NOT_FOUND_MSG_KEY));

        userEntity.getBooks().removeIf(book -> dto.getBookIds().contains(book.getId()));
        issuedBooks.forEach(book -> book.getUsers().remove(userEntity));

        metaEntities.forEach(meta -> meta.setNoOfCopy(meta.getNoOfCopy() + issuedBooks.size()));

        userEntityService.save(userEntity);
        metaEntityService.save(metaEntities);

        List<BookMetaEntity> newMetaList = metaEntityService.findMetaInBooks(userEntity.getBooks());
        return mapper.mapToDto(userEntity, newMetaList);
    }

    private UserResponseDTO getUserResponseDTO(UserEntity entity) {
        if(CollectionUtils.isEmpty(entity.getBooks()))
            return mapper.mapToDto(entity);

        List<BookMetaEntity> metaEntities = metaEntityService.findMetaInBooks(entity.getBooks());
        return mapper.mapToDto(entity, metaEntities);
    }
}
