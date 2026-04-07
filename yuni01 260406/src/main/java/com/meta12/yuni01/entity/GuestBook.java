package com.meta12.yuni01.entity;

import com.meta12.yuni01.dto.GuestBookDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
//@Table(name = "guestBook")
@Getter
@Setter
public class GuestBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", insertable = true, updatable = false, length = 50, nullable = false)
    private String name;

    private String email;
    private String password;
    private String content;

    public static GuestBook dtoToEntity(GuestBookDTO guestBookDTO) {
        GuestBook guestBook = new GuestBook();
        guestBook.setId(guestBookDTO.getId());
        guestBook.setName(guestBookDTO.getName());
        guestBook.setEmail(guestBookDTO.getEmail());
        guestBook.setContent(guestBookDTO.getContent());
        return guestBook;
    }
}
