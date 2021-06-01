package com.coursework.demo.repository.specification;

import com.coursework.demo.entity.Route;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDate;

public class RouteSpecification {

    public static Specification<Route> getDestination(final String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("destination"), "%" + value + "%");
    }

    public static Specification<Route> getDeparture(final String value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.like(root.get("departure"), "%" + value + "%");
    }

    public static Specification<Route> getData(final LocalDate value) {
        return (root, criteriaQuery, criteriaBuilder) -> criteriaBuilder.greaterThanOrEqualTo(root.get("departureDate"), value.atStartOfDay());
    }
}
