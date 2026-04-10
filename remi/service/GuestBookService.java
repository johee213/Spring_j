package com.example.remi.service;

import com.example.remi.dto.GuestBookDTO;
import com.example.remi.entity.GuestBook;
import com.example.remi.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GuestBookService {
    private final GuestBookRepository guestBookRepository; //리포지터리 객체를 주입.

    private GuestBook createEntity(GuestBookDTO guestBookDTO) { // dto -> entity
        GuestBook guestBook = new GuestBook();
        guestBook.setId(guestBookDTO.getId());
        guestBook.setName(guestBookDTO.getName());
        guestBook.setEmail(guestBookDTO.getEmail());
        guestBook.setPasswd(guestBookDTO.getPasswd());
        guestBook.setContent(guestBookDTO.getContent());
        return guestBook;
    }

    public List<GuestBook> list() {
        return guestBookRepository.findAll();
    }

    public GuestBook view(Long id) {
        Optional<GuestBook> og = guestBookRepository.findById(id); // O, X
        GuestBook guestBook = null;
        if (og.isPresent()) {
            guestBook = og.get();
        }
        return guestBook;
    }

    public void chugaProc(GuestBookDTO guestBookDTO) {
        GuestBook guestBook = this.createEntity(guestBookDTO);
        guestBookRepository.save(guestBook);
    }

    public void sujungProc(GuestBookDTO guestBookDTO) {
        GuestBook guestBook = this.createEntity(guestBookDTO);
        guestBookRepository.save(guestBook);
    }

    public void sakjeProc(GuestBookDTO guestBookDTO) {
        GuestBook guestBook = createEntity(guestBookDTO);
        guestBookRepository.delete(guestBook);
    }
}
