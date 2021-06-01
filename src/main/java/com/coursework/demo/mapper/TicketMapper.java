package com.coursework.demo.mapper;

import com.coursework.demo.dto.AddTicketDTO;
import com.coursework.demo.dto.TicketDTO;
import com.coursework.demo.entity.Ticket;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TicketMapper {

    TicketDTO convertToDto(Ticket ticket);

    Ticket convertToEntity(TicketDTO ticketDTO);

    Ticket convertToEntity(AddTicketDTO ticketDTO);

    List<TicketDTO> convertToDtoList(List<Ticket> tickets);

}