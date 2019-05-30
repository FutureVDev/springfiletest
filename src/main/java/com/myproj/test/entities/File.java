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

    @Column(name = "content", length = 10000)
    private byte[] content;

    @Column(name = "signature")
    private String signature;

    @Column(name = "name")
    private String name;

    @Column(name = "content_type")
    private String contentType;

    public File() {
    }

    @ManyToOne(fetch = FetchType.EAGER)
    private User user;

}
