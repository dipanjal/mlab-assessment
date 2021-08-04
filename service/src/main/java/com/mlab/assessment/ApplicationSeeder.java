package com.mlab.assessment;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.model.request.book.CreateBookDTO;
import com.mlab.assessment.repository.BookMetaRepository;
import com.mlab.assessment.repository.BookRepository;
import com.mlab.assessment.repository.UserRepository;
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

    private final UserRepository userRepository;
    private final BookRepository bookRepository;
    private final BookMetaRepository bookMetaRepository;

    private final BookService bookService;

    private void populateUsers(){

        if(CollectionUtils.isEmpty(userRepository.findAll())){
            List<UserEntity> userEntities = List.of(
                    new UserEntity("jhon_d","Jhon Doe"),
                    new UserEntity("mariya_tk","Mariya Takeuchi")
            );
            userRepository.saveAll(userEntities);
        }
    }

    private void populateBooks(){

        if(CollectionUtils.isEmpty(bookRepository.findAll())){
            bookMetaRepository.deleteAll();

            bookService.createBook(
                    CreateBookDTO.builder()
                            .name("Advanced Java")
                            .authorName("Rayan Goslin")
                            .description("Advanced Java Programming Book")
                            .noOfCopy(10)
                            .releaseDate("18-03-2012")
                            .build()
            );

            bookService.createBook(
                    CreateBookDTO.builder()
                            .name("C++")
                            .authorName("Harvard Shield")
                            .description("Basic C++ Programming Book")
                            .noOfCopy(10)
                            .releaseDate("05-02-2001")
                            .build()
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
