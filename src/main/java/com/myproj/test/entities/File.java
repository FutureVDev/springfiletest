package com.myproj.test.entities;

import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "files")
public class File {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "content")
    private byte[] content;

    @Column(name = "signature")
    private String signature;

    @Column(name = "name")
    private String name;

    public File() {
    }

    @ManyToOne
    private User user;

}
