package com.coursework.demo.dto;

import com.coursework.demo.entity.enums.BusCondition;
import lombok.Data;

@Data
public class AddBusDTO {
    private String name;

    private int capacity;

    private BusCondition status;
}
