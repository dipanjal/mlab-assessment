package com.mlab.assessment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;

/**
 * @author dipanjal
 * @since 0.0.1
 */
@Entity
@Table(name = "mlab_book")
@Data
@NoArgsConstructor
public class BookEntity extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "meta_id", nullable = false)
    private long metaId;

    @ManyToMany(mappedBy = "books")
    private Collection<UserEntity> users = new HashSet<>();
}
