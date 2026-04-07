package com.example.remi.service;

import com.example.remi.dto.PhoneBookDTO;
import com.example.remi.entity.PhoneBook;
import com.example.remi.repository.PhoneBookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class PhoneBookService {
    private final PhoneBookRepository phoneBookRepository;

    //전체목록 : list() / List<PhoneBook>
    public List<PhoneBook> list() {
        return phoneBookRepository.findAll();
    }

    //상세보기 : view() / PhoneBook
    public PhoneBook view(Long id) {
        // return phoneBookRepository.findById(id).orElse(null);

        Optional<PhoneBook> op = phoneBookRepository.findById(id);
        PhoneBook phoneBook = null;
        if (op.isPresent()) {
            phoneBook = op.get();
        }
        return phoneBook;
    }

    //추가처리 : chugaProc() / void
    public void chugaProc(PhoneBookDTO phoneBookDTO) {
        PhoneBook phoneBook = PhoneBook.createEntity(phoneBookDTO);
        phoneBookRepository.save(phoneBook);
    }

    //수정처리 : sujungProc() / void
    public void sujungProc(PhoneBookDTO phoneBookDTO) {
        PhoneBook phoneBook = PhoneBook.createEntity(phoneBookDTO);
        phoneBookRepository.save(phoneBook);
    }

    //삭제처리 : sakjeProc() / void
    public void sakjeProc(PhoneBookDTO phoneBookDTO) {
        PhoneBook phoneBook = PhoneBook.createEntity(phoneBookDTO);
        phoneBookRepository.delete(phoneBook);
    }

//    public void sakjeProc1(Long id) {
//        phoneBookRepository.deleteById(id);
//    }
}
