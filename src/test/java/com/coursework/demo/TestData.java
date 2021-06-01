package com.coursework.demo;

import com.coursework.demo.dto.BusDTO;
import com.coursework.demo.dto.LuggageDTO;
import com.coursework.demo.dto.PassengerDTO;
import com.coursework.demo.dto.RouteDTO;
import com.coursework.demo.dto.TicketDTO;
import com.coursework.demo.entity.Bus;
import com.coursework.demo.entity.Luggage;
import com.coursework.demo.entity.Passenger;
import com.coursework.demo.entity.Route;
import com.coursework.demo.entity.Ticket;
import com.coursework.demo.entity.enums.BusCondition;
import com.coursework.demo.entity.enums.LuggageType;

import java.util.Arrays;
import java.util.List;

public class TestData {

    public static Bus getBus() {
        return Bus.builder()
                .id(1L)
                .name("N25")
                .capacity(50)
                .status(BusCondition.ACTIVE)
                .build();
    }

    public static BusDTO getBusRequest() {
        return BusDTO.builder()
                .id(1L)
                .name("N25")
                .capacity(50)
                .status(BusCondition.ACTIVE)
                .build();
    }

    public static Luggage getLuggage() {
        return Luggage.builder()
                .id(1L)
                .name("N25")
                .luggageType(LuggageType.BIG)
                .weight(50)
                .build();
    }

    public static LuggageDTO getLuggageRequest() {
        return LuggageDTO.builder()
                .id(1L)
                .name("N25")
                .luggageType(LuggageType.BIG)
                .weight(50)
                .build();
    }

    public static Passenger getPassenger() {
        return Passenger.builder()
                .id(1L)
                .name("John")
                .surname("Wick")
                .build();
    }

    public static PassengerDTO getPassengerRequest() {
        return PassengerDTO.builder()
                .id(1L)
                .name("John")
                .surname("Wick")
                .build();
    }

    public static Route getRoute() {
        return Route.builder()
                .id(1L)
                .departure("Kyiv")
                .destination("Wroclaw")
                .build();
    }

    public static RouteDTO getRouteRequest() {
        return RouteDTO.builder()
                .id(1L)
                .departure("Kyiv")
                .destination("Wroclaw")
                .build();
    }


    public static Ticket getTicket() {
        return Ticket.builder()
                .id(1L)
                .build();
    }

    public static TicketDTO getTicketRequest() {
        return TicketDTO.builder()
                .id(1L)
                .build();
    }

    public static List<Passenger> getExpectedPassengersList() {
        return Arrays.asList(
                Passenger.builder().id(1L).email("abc@gmail.com").name("Oleg").surname("Kucherenko").build(),
                Passenger.builder().id(2L).email("adc@gmail.com").name("Dima").surname("Andriichuk").build(),
                Passenger.builder().id(3L).email("azx@gmail.com").name("Andrii").surname("Batuiev").build(),
                Passenger.builder().id(4L).email("zzz@gmail.com").name("Serhii").surname("Kachmarskiy").build()
        );
    }

    public static List<PassengerDTO> getExpectedPassengersDTOList() {
        return Arrays.asList(
                PassengerDTO.builder().id(1L).email("abc@gmail.com").name("Oleg").surname("Kucherenko").build(),
                PassengerDTO.builder().id(2L).email("adc@gmail.com").name("Dima").surname("Andriichuk").build(),
                PassengerDTO.builder().id(3L).email("azx@gmail.com").name("Andrii").surname("Batuiev").build(),
                PassengerDTO.builder().id(4L).email("zzz@gmail.com").name("Serhii").surname("Kachmarskiy").build()
        );
    }

}
