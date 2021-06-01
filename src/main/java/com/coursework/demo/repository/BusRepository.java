package com.coursework.demo.repository;

import com.coursework.demo.entity.Bus;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface BusRepository extends PagingAndSortingRepository<Bus, Long> {
}
