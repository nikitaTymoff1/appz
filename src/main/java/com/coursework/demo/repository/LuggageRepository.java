package com.coursework.demo.repository;

import com.coursework.demo.entity.Luggage;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface LuggageRepository extends PagingAndSortingRepository<Luggage, Long> {
}
