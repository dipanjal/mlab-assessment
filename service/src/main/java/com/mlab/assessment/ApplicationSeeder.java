package com.mlab.assessment;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.entity.BookMetaEntity;
import com.mlab.assessment.entity.UserEntity;
import com.mlab.assessment.repository.BookMetaRepository;
import com.mlab.assessment.repository.BookRepository;
import com.mlab.assessment.repository.UserRepository;
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

            BookEntity bookEntity = new BookEntity("Advanced Java", 0);
            bookRepository.save(bookEntity);
            BookMetaEntity metaEntity = new BookMetaEntity("Rayan Goslin", "shaskhd", 10, "2012-03-18" ,bookEntity.getId());
            bookMetaRepository.save(metaEntity);
            bookEntity.setMetaId(metaEntity.getId());
            bookRepository.save(bookEntity);

            BookEntity bookEntity2 = new BookEntity("C++", 0);
            bookRepository.save(bookEntity2);
            BookMetaEntity metaEntity2 = new BookMetaEntity("Jhon Doe", "C++", 10, "2018-10-07", bookEntity2.getId());
            bookMetaRepository.save(metaEntity2);
            bookEntity2.setMetaId(metaEntity2.getId());
            bookRepository.save(bookEntity2);
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
