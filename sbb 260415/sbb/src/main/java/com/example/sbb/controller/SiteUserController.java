package com.example.sbb.controller;

import com.example.sbb.dto.SiteUserDTO;
import com.example.sbb.entity.Question;
import com.example.sbb.entity.SiteUser;
import com.example.sbb.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class SiteUserController {
    private final SiteUserService siteUserService;

    @GetMapping("/siteUser/list")
    public String list(
            Model model,
            @RequestParam(value="page", defaultValue="0") int page
    ) {
        Page<SiteUser> paging = siteUserService.list(page);
        model.addAttribute("paging", paging);
        return "siteUser/list";
    }

    @GetMapping("/siteUser/view/{id}")
    public String view() {
        return "siteUser/view"; //forwarding
    }

    @GetMapping("/siteUser/chuga")
    public String chuga(
            Model model,
            SiteUserDTO siteUserDTO
    ) {
        return "siteUser/chuga"; //forwarding
    }

    @GetMapping("/siteUser/sujung/{id}")
    public String sujung() {
        return "siteUser/sujung"; //forwarding
    }

    @GetMapping("/siteUser/sakje/{id}")
    public String sakje() {
        return "siteUser/sakje"; //forwarding
    }

    @PostMapping("/siteUser/chugaProc")
    public String chugaProc(
            @Valid SiteUserDTO siteUserDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "siteUser/chuga"; //forwarding
        }

        if (!siteUserDTO.getPassword().equals(siteUserDTO.getPasswordChk())) {
            bindingResult.rejectValue("passwordChk", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "siteUser/chuga";
        }

        try {
            siteUserService.chugaProc(siteUserDTO);
        } catch(DataIntegrityViolationException e) {
            //e.printStackTrace();
            bindingResult.reject("chugaFailed", "이미 등록된 사용자입니다.");
            return "siteUser/chuga";
        } catch(Exception e) {
            //e.printStackTrace();
            bindingResult.reject("chugaFailed", e.getMessage());
            return "siteUser/chuga";
        }

        return "redirect:/siteUser/list"; //redirect
    }

    @PostMapping("/siteUser/sujungProc")
    public String sujungProc() {
        return "redirect:/siteUser/view"; //redirect
    }

    @PostMapping("/siteUser/sakjeProc")
    public String sakjeProc() {
        return "redirect:/siteUser/list"; //redirect
    }
}
