package com.coursework.demo.dto;

import com.coursework.demo.entity.enums.BusCondition;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BusDTO {
    private Long id;

    private String name;

    private int capacity;

    private BusCondition status;
}
