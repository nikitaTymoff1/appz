package com.coursework.demo.dto;

import com.coursework.demo.entity.Passenger;
import com.coursework.demo.entity.Route;
import lombok.Data;

@Data
public class AddTicketDTO {
    private long id;

    private Route route;

    private Passenger passenger;

    private int price;
}
