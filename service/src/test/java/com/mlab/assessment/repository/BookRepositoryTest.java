package com.mlab.assessment.repository;

import com.mlab.assessment.entity.BookEntity;
import com.mlab.assessment.service.BookEntityService;
import org.apache.commons.collections4.CollectionUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Set;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@SpringBootTest
public class BookRepositoryTest {

    @Autowired
    BookEntityService bookEntityService;

    @Test
    public void bookInAndGratedTHanZeroTest(){
        Set<Long> ids = Set.of(1L,2L);
        List<BookEntity> bookEntities = bookEntityService.findBooksByIdIn(ids);
        Assertions.assertTrue(CollectionUtils.isNotEmpty(bookEntities));
    }
}
