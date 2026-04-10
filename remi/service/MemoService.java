package com.example.remi.service;

import com.example.remi.dto.MemoDTO;
import com.example.remi.entity.Memo;
import com.example.remi.repository.MemoRepository;
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
        Optional<Memo> optionalMemo = memoRepository.findById(id);
        Memo memo = null;
        if (optionalMemo.isPresent()) {
            memo = optionalMemo.get();
        }
        return memo;
    }

    public void chugaProc(MemoDTO memoDTO) {
        Memo memo = Memo.createEntity(memoDTO);
        memoRepository.save(memo);
    }

    public void sujungProc(MemoDTO memoDTO) {
        Memo memo = Memo.createEntity(memoDTO);
        memoRepository.save(memo);
    }

    public void sakjeProc(MemoDTO memoDTO) {
        Memo memo = Memo.createEntity(memoDTO);
        memoRepository.delete(memo);
    }

}
