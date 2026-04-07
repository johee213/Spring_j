package com.meta12.yuni01.service;

import com.meta12.yuni01.dto.MemoDTO;
import com.meta12.yuni01.entity.Memo;
import com.meta12.yuni01.repository.MemoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemoService {

    private final MemoRepository memoRepository;

    public List<Memo> list() {
        return memoRepository.findAll();
    }

    public Memo view(Long id) {
        Optional<Memo> om = memoRepository.findById(id);
        Memo memo = null;
        if (om.isPresent()) {
            memo = om.get();
        }
        return memo;
    }

    public void chugaProc(MemoDTO memoDTO) {
        Memo memo = new Memo();
        memo.setId(memoDTO.getId());
        memo.setSubject(memoDTO.getSubject());
        memo.setContent(memoDTO.getContent());
        memo.setWriter(memoDTO.getWriter());

        memoRepository.save(memo);
    }

    public void sujungProc(MemoDTO memoDTO) {
        Memo memo = new Memo();
        memo.setId(memoDTO.getId());
        memo.setSubject(memoDTO.getSubject());
        memo.setContent(memoDTO.getContent());
        memo.setWriter(memoDTO.getWriter());

        memoRepository.save(memo);
    }

    public void sakjeProc(MemoDTO memoDTO) {
        Memo memo = new Memo();
        memo.setId(memoDTO.getId());
        memo.setSubject(memoDTO.getSubject());
        memo.setContent(memoDTO.getContent());
        memo.setWriter(memoDTO.getWriter());

        memoRepository.delete(memo);
    }

}
