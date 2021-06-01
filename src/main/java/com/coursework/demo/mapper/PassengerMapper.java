package com.coursework.demo.mapper;

import com.coursework.demo.dto.AddPassengerDTO;
import com.coursework.demo.dto.PassengerDTO;
import com.coursework.demo.entity.Passenger;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PassengerMapper {

    PassengerDTO convertToDto(Passenger passenger);

    Passenger convertToEntity(PassengerDTO passengerDTO);

    Passenger convertToEntity(AddPassengerDTO passengerDTO);

    List<PassengerDTO> convertToDtoList(List<Passenger> passengers);

}
