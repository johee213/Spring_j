package com.example.sbb.service;

import com.example.sbb.dto.SiteUserDTO;
import com.example.sbb.entity.Question;
import com.example.sbb.entity.SiteUser;
import com.example.sbb.repository.SiteUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
public class SiteUserService {
    private final SiteUserRepository siteUserRepository;

    public Page<SiteUser> list(int page) {
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
        return siteUserRepository.findAll(pageable);
    }

    public void view() {

    }

    public void chugaProc(SiteUserDTO siteUserDTO) {
        SiteUser siteUser = new SiteUser();
        siteUser.setId(siteUserDTO.getId());
        siteUser.setUsername(siteUserDTO.getUsername());
        siteUser.setPassword(siteUserDTO.getPassword());
        siteUser.setEmail(siteUserDTO.getEmail());
        siteUserRepository.save(siteUser);
    }

    public void sujungProc() {

    }

    public void sakjeProc() {

    }
}
