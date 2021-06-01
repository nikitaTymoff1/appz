package com.coursework.demo.service.impl;

import com.coursework.demo.entity.Route;
import com.coursework.demo.repository.RouteRepository;
import com.coursework.demo.service.RouteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.util.List;

import static com.coursework.demo.repository.specification.RouteSpecification.getData;
import static com.coursework.demo.repository.specification.RouteSpecification.getDeparture;
import static com.coursework.demo.repository.specification.RouteSpecification.getDestination;
import static org.springframework.data.jpa.domain.Specification.where;

@Transactional
@Service
public class RouteServiceImpl implements RouteService {

    private RouteRepository routeRepository;

    @Autowired
    public RouteServiceImpl(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    @Override
    public Route getById(Long id) {
        return routeRepository.findById(id).get();
    }

    @Override
    public List<Route> getAll(String from, String to, LocalDate date, Pageable pageable) {
        return routeRepository.findAll(
                where(getDeparture(from)).and(getDestination(to).and(getData(date))),
                pageable).getContent();
    }

    @Override
    public Route save(Route object) {
        return routeRepository.save(object);
    }

    @Override
    public Route delete(Route object) {
        routeRepository.delete(object);
        return object;
    }
}
