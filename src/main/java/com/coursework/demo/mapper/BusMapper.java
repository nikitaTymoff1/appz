package com.coursework.demo.mapper;

import com.coursework.demo.dto.AddBusDTO;
import com.coursework.demo.dto.BusDTO;
import com.coursework.demo.entity.Bus;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BusMapper {

    BusDTO convertToDto(Bus bus);

    Bus convertToEntity(BusDTO busDTO);

    Bus convertToEntity(AddBusDTO busDTO);

    List<BusDTO> convertToDtoList(List<Bus> buses);

}
