package com.example.remi.controller;

import com.example.remi.dto.GuestBookDTO;
import com.example.remi.entity.GuestBook;
import com.example.remi.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("/guestBook")
@RequiredArgsConstructor
@Controller
public class GuestBookController {
    private final GuestBookService guestBookService;

    @GetMapping("/list")
    public String list(
            Model model
    ){
        List<GuestBook> list = guestBookService.list();
        model.addAttribute("list", list);
        return "guestBook/list";
    }

    @GetMapping("/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        GuestBook guestBook = guestBookService.view(id);
        model.addAttribute("guestBook", guestBook);
        return "guestBook/view";
    }

    @GetMapping("/chuga")
    public String chuga(
            Model model
    ) {
        return "guestBook/chuga";
    }

    @GetMapping("/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        GuestBook guestBook = guestBookService.view(id);
        model.addAttribute("guestBook", guestBook);
        return "guestBook/sujung";
    }

    @GetMapping("/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        GuestBook guestBook = guestBookService.view(id);
        model.addAttribute("guestBook", guestBook);
        return "guestBook/sakje";
    }

    @PostMapping("/chugaProc")
    public String chugaProc(
            GuestBookDTO guestBookDTO
    ) {
        guestBookService.chugaProc(guestBookDTO);
        return "redirect:/guestBook/list";
    }

    @PostMapping("/sujungProc")
    public String sujungProc(
            GuestBookDTO guestBookDTO
    ) {
        GuestBook guestBook = guestBookService.view(guestBookDTO.getId());
        if (!guestBook.getPasswd().equals(guestBookDTO.getPasswd())) {
            return "redirect:/guestBook/list";
        }
        guestBookService.sujungProc(guestBookDTO);
        return "redirect:/guestBook/view/" + guestBookDTO.getId();
    }

    @PostMapping("/sakjeProc")
    public String sakjeProc(
            GuestBookDTO guestBookDTO
    ) {
        GuestBook guestBook = guestBookService.view(guestBookDTO.getId());
        if (!guestBookDTO.getPasswd().equals(guestBook.getPasswd())) {
            return "redirect:/";
        }
        guestBookService.sakjeProc(guestBookDTO);
        return "redirect:/guestBook/list";
    }

}
