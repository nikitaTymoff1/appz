package com.coursework.demo.service.impl;

import com.coursework.demo.entity.Luggage;
import com.coursework.demo.repository.LuggageRepository;
import com.coursework.demo.service.LuggageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class LuggageServiceImpl implements LuggageService {

    private LuggageRepository luggageRepository;

    @Autowired
    public LuggageServiceImpl(LuggageRepository luggageRepository) {
        this.luggageRepository = luggageRepository;
    }

    @Override
    public Luggage getById(Long id) {
        return luggageRepository.findById(id).get();
    }

    @Override
    public List<Luggage> getAll(Pageable pageable) {
        return luggageRepository.findAll(pageable).getContent();
    }

    @Override
    public Luggage save(Luggage object) {
        return luggageRepository.save(object);
    }

    @Override
    public Luggage delete(Luggage object) {
        luggageRepository.delete(object);
        return object;
    }
}
