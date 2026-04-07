package com.meta12.yuni01.service;

import com.meta12.yuni01.dto.GuestBookDTO;
import com.meta12.yuni01.entity.GuestBook;
import com.meta12.yuni01.repository.GuestBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class GuestBookService {

    private final GuestBookRepository guestBookRepository;

    public List<GuestBook> list() {
        return guestBookRepository.findAll();
    }

    public GuestBook view(Long id) {
        Optional<GuestBook> og = guestBookRepository.findById(id);
        GuestBook guestBook = null;
        if (og.isPresent()) {
            guestBook = og.get();
        }
        return guestBook;
    }

    public void chugaProc(GuestBookDTO guestBookDTO) {
        GuestBook guestBook = GuestBook.dtoToEntity(guestBookDTO); // dto -> entity
        guestBookRepository.save(guestBook);
    }

    public void sujungProc(GuestBookDTO guestBookDTO) {
        GuestBook guestBook = GuestBook.dtoToEntity(guestBookDTO);
        guestBookRepository.save(guestBook);
    }

    public void sakjeProc(GuestBookDTO guestBookDTO) {
        GuestBook guestBook = GuestBook.dtoToEntity(guestBookDTO);
        guestBookRepository.delete(guestBook);
    }

}
