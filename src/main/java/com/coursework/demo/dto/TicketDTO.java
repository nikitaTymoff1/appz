package com.coursework.demo.dto;

import com.coursework.demo.entity.Passenger;
import com.coursework.demo.entity.Route;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TicketDTO {
    private long id;

    private Route route;

    private Passenger passenger;

    private int price;

}
