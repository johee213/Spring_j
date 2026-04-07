package com.meta12.yuni01.controller;

import com.meta12.yuni01.dto.MemoDTO;
import com.meta12.yuni01.entity.Memo;
import com.meta12.yuni01.service.MemoService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@RequiredArgsConstructor
@Controller
public class MemoController {

    private final MemoService memoService;

    @GetMapping("/memo/list")
    public String list(Model model) {
        List<Memo> list = memoService.list();
        model.addAttribute("list", list);
        return "memo/list";
    }

    @GetMapping("/memo/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id
    ) {
        Memo memo = memoService.view(id);
        if (memo == null) {
            model.addAttribute("errorCode", "Error002");
            model.addAttribute("errorMsg", "해당 글이 없습니다.");
            return "error/error";
        }
        model.addAttribute("memo", memo);
        return "memo/view";
    }

    @GetMapping("/memo/chuga")
    public String chuga(Model model) {
        return "memo/chuga";
    }

    @GetMapping("/memo/sujung/{id}")
    public String sujung(
            Model model,
            @PathVariable("id") Long id
    ) {
        Memo memo = memoService.view(id);
        if (memo == null) {
            model.addAttribute("errorCode", "Error002");
            model.addAttribute("errorMsg", "해당 글이 없습니다.");
            return "error/error";
        }
        model.addAttribute("memo", memo);
        return "memo/sujung";
    }

    @GetMapping("/memo/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ) {
        Memo memo = memoService.view(id);
        if (memo == null) {
            model.addAttribute("errorCode", "Error002");
            model.addAttribute("errorMsg", "해당 글이 없습니다.");
            return "error/error";
        }
        model.addAttribute("memo", memo);
        return "memo/sakje";
    }

    @PostMapping("/memo/chugaProc")
    public String chugaProc(
            MemoDTO memoDTO
    ) {
        memoService.chugaProc(memoDTO);
        return "redirect:/memo/list";
    }

    @PostMapping("/memo/sujungProc")
    public String sujungProc(
            MemoDTO memoDTO
    ) {
        memoService.sujungProc(memoDTO);
        return "redirect:/memo/view/" + memoDTO.getId();
    }

    @PostMapping("/memo/sakjeProc")
    public String sakjeProc(
            MemoDTO memoDTO
    ) {
        memoService.sakjeProc(memoDTO);
        return "redirect:/memo/list";
    }

}
