package com.mlab.assessment.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

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

    @ManyToMany(mappedBy = "books", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private List<UserEntity> users = new ArrayList<>();

    public void addUser(UserEntity userEntity){
        users.add(userEntity);
    }

    public void remove(UserEntity userEntity){
        users.remove(userEntity);
    }
}
