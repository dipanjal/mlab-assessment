package com.mlab.assessment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Entity
@Table(name = "mlab_book_meta")
@Data
@NoArgsConstructor
public class BookMetaEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "author_name")
    private String authorName;

    @Column(name = "description")
    private String description;

    @Column(name = "no_of_copy")
    private int noOfCopy;

    @Column(name = "release_date")
    private Date releaseDate;

    @Column(name = "book_id", nullable = false)
    private long bookId;

    public BookMetaEntity(String authorName, String description, int noOfCopy, long bookId) {
        this.authorName = authorName;
        this.description = description;
        this.noOfCopy = noOfCopy;
        this.bookId = bookId;
    }
}
