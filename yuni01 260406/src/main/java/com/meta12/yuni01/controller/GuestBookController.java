package com.meta12.yuni01.controller;

import com.meta12.yuni01.dto.GuestBookDTO;
import com.meta12.yuni01.entity.GuestBook;
import com.meta12.yuni01.service.GuestBookService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class GuestBookController {

    private final GuestBookService guestBookService;

    @GetMapping("/guestBook/list")
    public String list(
            Model model
    ) {
        List<GuestBook> list = guestBookService.list();
        model.addAttribute("list", list);
        return "guestBook/list";
    }

    @GetMapping("/guestBook/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        GuestBook guestBook = guestBookService.view(id);
        model.addAttribute("guestBook", guestBook);
        return "guestBook/view";
    }

    @GetMapping("/guestBook/chuga")
    public String chuga(
            Model model
    ) {
        return "guestBook/chuga";
    }

    @GetMapping("/guestBook/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        GuestBook guestBook = guestBookService.view(id);
        model.addAttribute("guestBook", guestBook);
        return "guestBook/sujung";
    }

    @GetMapping("/guestBook/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        GuestBook guestBook = guestBookService.view(id);
        model.addAttribute("guestBook", guestBook);
        return "guestBook/sakje";
    }

    @PostMapping("/guestBook/chugaProc")
    public String chugaProc(
            GuestBookDTO guestBookDTO
    ) {
        guestBookService.chugaProc(guestBookDTO);
        return "redirect:/guestBook/list";
    }

    @PostMapping("/guestBook/sujungProc")
    public String sujungProc(
            GuestBookDTO guestBookDTO
    ) {
        guestBookService.sujungProc(guestBookDTO);
        return "redirect:/guestBook/view/" + guestBookDTO.getId();
    }

    @PostMapping("/guestBook/sakjeProc")
    public String sakjeProc(
            GuestBookDTO guestBookDTO
    ) {
        guestBookService.sakjeProc(guestBookDTO);
        return "redirect:/guestBook/list";
    }

}
