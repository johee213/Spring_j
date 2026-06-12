package com.example.masil.controller;


import com.example.masil.dto.SiteUserDTO;
import com.example.masil.entity.SiteUser;
import com.example.masil.repository.SiteUserRepository;
import com.example.masil.service.SiteUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@Controller
public class SiteUserController {
    private final SiteUserService siteUserService;
    private final SiteUserRepository siteUserRepository;

    @GetMapping("/siteUser/list")
    public String list(
            Model model,
            @RequestParam(value="page", defaultValue = "0") int page
    ){
        Page<SiteUser> paging = siteUserService.list(page);
        model.addAttribute("paging", paging);
        return "siteUser/list";
    }

    @GetMapping("/siteUser/view/{id}")
    public String view(
            Model model,
            @PathVariable("id") Long id,
            SiteUserDTO siteUserDTO
    ){
        SiteUser siteUser = siteUserService.view(id);
        model.addAttribute("siteUser", siteUser);
        return "siteUser/view";
    }

    @GetMapping("/siteUser/chuga")
    public String chuga(
            Model model,
            SiteUserDTO siteUserDTO
    ){
        return "siteUser/chuga";
    }

    @GetMapping("/siteUser/sujung/{id}") // 이 줄을 반드시 추가해야 합니다!
    public String sujung(
            Model model,
            @PathVariable("id") Long id,
            SiteUserDTO siteUserDTO // HTML의 th:object와 연결될 DTO 객체도 필요합니다.
    ){
        SiteUser siteUser = siteUserService.view(id);


        // DTO에 기존 유저 정보를 담아서 화면에 넘겨줘야 합니다.
        siteUserDTO.setId(siteUser.getId());
        siteUserDTO.setUsername(siteUser.getUsername());
        siteUserDTO.setName(siteUser.getName());
        siteUserDTO.setBirth(siteUser.getBirth());
        siteUserDTO.setPhone(siteUser.getPhone());

        model.addAttribute("siteUserDTO", siteUserDTO);
        return "siteUser/sujung";
    }

    @GetMapping("/siteUser/sakje/{id}")
    public String sakje(
            Model model,
            @PathVariable("id") Long id
    ){
        SiteUser siteUser = siteUserService.view(id);
        model.addAttribute("siteUser", siteUser);
        return "siteUser/sakje";
    }

    @PostMapping("/siteUser/chugaProc")
    public String chugaProc(
            @Valid SiteUserDTO siteUserDTO,
            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "siteUser/chuga";
        }
        if (!siteUserDTO.getPassword().equals(siteUserDTO.getPasswordChk())) {
            bindingResult.rejectValue("passwordChk", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "siteUser/chuga";
        }

        try{
            siteUserService.chugaProc(siteUserDTO);
        } catch(DataIntegrityViolationException e){
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "siteUser/chuga";
        } catch (Exception e) {
            bindingResult.reject("chugaFailed", e.getMessage());
            return "siteUser/chuga";
            }
            return "redirect:/";
        }

    // 기존 @PostMapping("/siteUser/sujungProc") 대신 아래와 같이 변경
    @PostMapping("/siteUser/sujung/{id}")
    public String sujungProc(
            @PathVariable("id") Long id, // URL의 {id}를 받아오기 위해 추가
            SiteUserDTO siteUserDTO
    ){
        // 기존 로직 유지
        SiteUser siteUser = siteUserService.view(id);

        if(siteUser == null){
            return "redirect:/";
        }

        // 비밀번호 검증 (주의: DB는 암호화되어 있어 equals는 실패할 수 있음)
        // if (!siteUserDTO.getPassword().equals(siteUser.getPassword())) { ... }

        siteUserService.sujungProc(siteUserDTO);
        return "redirect:/siteUser/view/" + id;
    }


        @GetMapping("/siteUser/login")
        public String login(){
         return "siteUser/login";
        }

        // SiteUserController 클래스 내부에 추가
        @GetMapping("/siteUser/check-username")
        @ResponseBody // 중요: 페이지 이동이 아닌 데이터를 반환해야 함
        public boolean checkUsername(@RequestParam("username") String username) {
            // 서비스 로직: 존재하면 false, 사용 가능하면 true 반환
            return !siteUserService.existsByUsername(username);
        }

    @PostMapping("/siteUser/modifyRole/{id}")
    public String modifyRole(
            @PathVariable("id") Long id,
            @RequestParam("role") String role
    ) {
        // 1. 서비스에 권한 변경 요청 (id와 새로 선택한 role 문자열 전달)
        siteUserService.modifyRole(id, role);

        // 2. 변경 후 다시 리스트 페이지로 리다이렉트 (현재 페이지 유지는 기술적 처리가 더 필요하므로 우선 기본 리스트로)
        return "redirect:/siteUser/list";
    }

    // SiteUserService.java

    // 컨트롤러 클래스 내부

    @PostMapping("/siteUser/sakjeProc")
    public String sakjeProc(SiteUserDTO siteUserDTO) {
        // 1. 서비스의 sakjeProc 메서드 호출 (유저 삭제)
        siteUserService.sakjeProc(siteUserDTO);

        // 2. [수정] 삭제 완료 후 회원관리 리스트(/siteUser/list)로 이동
        return "redirect:/siteUser/list";
    }


}


