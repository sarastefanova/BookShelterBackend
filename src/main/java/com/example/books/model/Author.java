package com.example.books.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Data
public class Author {

    @Id
    String nameAndSurname;

    String shortAuthorBiography;

    @Column(nullable = true)
    @Lob
    private byte[] file;

    public Author(String nameAndSurname, String shortAuthorBiography) {
        this.nameAndSurname = nameAndSurname;
        this.shortAuthorBiography = shortAuthorBiography;
    }
}
