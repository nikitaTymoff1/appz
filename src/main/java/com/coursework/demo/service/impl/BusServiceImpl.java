package com.coursework.demo.service.impl;

import com.coursework.demo.entity.Bus;
import com.coursework.demo.repository.BusRepository;
import com.coursework.demo.service.BusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
@Service
public class BusServiceImpl implements BusService {

    private BusRepository busRepository;

    @Autowired
    public BusServiceImpl(BusRepository busRepository) {
        this.busRepository = busRepository;
    }

    @Override
    public Bus getById(Long id) {
        return busRepository.findById(id).get();
    }

    @Override
    public List<Bus> getAll(Pageable pageable) {
        return busRepository.findAll(pageable).getContent();
    }

    @Override
    public Bus save(Bus object) {
        return busRepository.save(object);
    }

    @Override
    public Bus delete(Bus object) {
        busRepository.delete(object);
        return object;
    }
}
