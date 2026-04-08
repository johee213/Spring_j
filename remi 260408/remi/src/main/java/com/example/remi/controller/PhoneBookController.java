package com.example.remi.controller;

import com.example.remi.dto.PhoneBookDTO;
import com.example.remi.entity.PhoneBook;
import com.example.remi.service.PhoneBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class PhoneBookController {
    private final PhoneBookService phoneBookService;

    @GetMapping("/phoneBook/list")
    public String list(
            Model model
    ) {
        List<PhoneBook> list = phoneBookService.list();
        model.addAttribute("list", list);
        return "phoneBook/list";
    }

    @GetMapping("/phoneBook/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        PhoneBook phoneBook = phoneBookService.view(id);
        model.addAttribute("phoneBook", phoneBook);
        return "phoneBook/view";
    }

    @GetMapping("/phoneBook/chuga")
    public String chuga(
            Model model
    ) {
        return "phoneBook/chuga";
    }

    @PostMapping("/phoneBook/chugaProc")
    public String chugaProc(
            PhoneBookDTO phoneBookDTO
    ) {
        phoneBookService.chugaProc(phoneBookDTO);
        return "redirect:/phoneBook/list";
    }

    @GetMapping("/phoneBook/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        PhoneBook phoneBook = phoneBookService.view(id);
        model.addAttribute("phoneBook", phoneBook);
        return "phoneBook/sujung";
    }

    @PostMapping("/phoneBook/sujungProc")
    public String sujungProc(
            PhoneBookDTO phoneBookDTO
    ) {
        phoneBookService.sujungProc(phoneBookDTO);
        return "redirect:/phoneBook/view/" + phoneBookDTO.getId();
    }

    @GetMapping("/phoneBook/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        PhoneBook phoneBook = phoneBookService.view(id);
        model.addAttribute("phoneBook", phoneBook);
        return "phoneBook/sakje";
    }

    @PostMapping("/phoneBook/sakjeProc")
    public String sakjeProc(
            PhoneBookDTO phoneBookDTO
    ) {
        phoneBookService.sakjeProc(phoneBookDTO);
        return "redirect:/phoneBook/list";
    }
}
