package com.example.remi.entity;

import com.example.remi.dto.GuestBookDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class GuestBook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String passwd;
    private String content;

//    public static GuestBook createEntity(GuestBookDTO guestBookDTO) { // dto -> entity
//        GuestBook guestBook = new GuestBook();
//        guestBook.setId(guestBookDTO.getId());
//        guestBook.setName(guestBookDTO.getName());
//        guestBook.setEmail(guestBookDTO.getEmail());
//        guestBook.setPasswd(guestBookDTO.getPasswd());
//        guestBook.setContent(guestBookDTO.getContent());
//        return guestBook;
//    }
}
