package com.mlab.assessment;

import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.repository.BookMetaRepository;
import com.mlab.assessment.service.BookEntityService;
import com.mlab.assessment.service.UserEntityService;
import com.mlab.assessment.service.book.BookService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class ApplicationSeeder implements ApplicationListener<ContextRefreshedEvent> {

    private final UserEntityService userEntityService;

    private final BookEntityService bookEntityService;
    private final BookMetaRepository bookMetaRepository;

    private final BookService bookService;

    private void populateUsers(){
        if(CollectionUtils.isEmpty(userEntityService.findAll())){
            List<UserEntity> userEntities = List.of(
                    new UserEntity("jhon_d","Jhon Doe", "jhon@example.com"),
                    new UserEntity("mariya_tk","Mariya Takeuchi","mariya@example.com"),
                    new UserEntity("robert_c","Robert C Martin","robert.c@mlab.com")
            );
            userEntityService.save(userEntities);
        }
    }

    private void populateBooks(){

        if(CollectionUtils.isEmpty(bookEntityService.findAll())){
            bookMetaRepository.deleteAll();

            bookService.createBook(
                    new CreateBookDTO(
                            "Advanced Java",
                            "Rayan Goslin",
                            "Advanced Java Programming Book",
                            10, "18-03-2012")
            );

            bookService.createBook(
                    new CreateBookDTO(
                            "C++", "Harvard Shield",
                            "Basic C++ Programming Book",
                            10, "05-02-2001")
            );
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        try{
            populateUsers();
            populateBooks();
        }catch (Exception e){
            log.error(e.getMessage(), e);
        }
    }
}
