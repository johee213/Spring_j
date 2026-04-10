package com.example.remi.entity;

import com.example.remi.dto.PhoneBookDTO;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PhoneBook extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String phone;
    private String address;

    public static PhoneBook createEntity(PhoneBookDTO phoneBookDTO) {
        PhoneBook phoneBook = new PhoneBook();
        phoneBook.setId(phoneBookDTO.getId());
        phoneBook.setName(phoneBookDTO.getName());
        phoneBook.setPhone(phoneBookDTO.getPhone());
        phoneBook.setAddress(phoneBookDTO.getAddress());
        phoneBook.setCreatedDate(phoneBookDTO.getCreatedDate());
        phoneBook.setModifiedDate(phoneBookDTO.getModifiedDate());
        phoneBook.setCreatedBy(phoneBookDTO.getCreatedBy());
        phoneBook.setModifiedBy(phoneBookDTO.getModifiedBy());
        return phoneBook;
    }
}
