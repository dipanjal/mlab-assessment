package com.mlab.assessment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.*;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Entity
@Table(name = "mlab_user")
@Data
@NoArgsConstructor
public class UserEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "user_name")
    private String username;

    @Column(name = "full_name")
    private String fullName;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "mlab_user_book",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "book_id")}
    )
    List<BookEntity> books = new ArrayList<>();

    public void addBook(BookEntity bookEntity){
        books.add(bookEntity);
    }
    public void removeBook(BookEntity bookEntity){
        books.add(bookEntity);
    }

    public UserEntity(String username, String fullName) {
        this.username = username;
        this.fullName = fullName;
    }
}
