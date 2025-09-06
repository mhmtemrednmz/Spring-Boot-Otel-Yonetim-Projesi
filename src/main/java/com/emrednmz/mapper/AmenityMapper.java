package com.emrednmz.mapper;

import com.emrednmz.dto.request.AmenityRequest;
import com.emrednmz.dto.response.DtoAmenity;
import com.emrednmz.model.Amenity;
import org.mapstruct.Mapper;

import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface AmenityMapper {
    AmenityMapper INSTANCE = Mappers.getMapper(AmenityMapper.class);

    Amenity toEntity(AmenityRequest amenityRequest);
    DtoAmenity toDto(Amenity amenity);

    @Mapping(target = "createTime", ignore = true)
    void updateAmenityFromRequest(AmenityRequest amenityRequest,@MappingTarget Amenity amenity);
}
