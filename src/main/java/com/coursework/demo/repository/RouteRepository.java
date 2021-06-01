package com.coursework.demo.repository;

import com.coursework.demo.entity.Route;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface RouteRepository extends PagingAndSortingRepository<Route, Long>, JpaSpecificationExecutor<Route> {
}
