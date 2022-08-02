package com.senerk.casestudy.garageticketsystem.converter;

import com.senerk.casestudy.garageticketsystem.entitydto.ParkingRecordDto;
import com.senerk.casestudy.garageticketsystem.models.ParkingRecord;
import org.mapstruct.Mapper;
import org.mapstruct.NullValueCheckStrategy;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.SET_TO_NULL,
        nullValueCheckStrategy = NullValueCheckStrategy.ALWAYS)
public interface EntityMapper {
    EntityMapper INSTANCE = Mappers.getMapper(EntityMapper.class);
    ParkingRecord map(ParkingRecordDto value);
    ParkingRecordDto map(ParkingRecord value);
}
