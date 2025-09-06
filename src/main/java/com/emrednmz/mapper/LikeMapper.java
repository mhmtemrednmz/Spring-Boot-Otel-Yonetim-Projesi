package com.emrednmz.mapper;

import com.emrednmz.dto.request.LikeRequest;
import com.emrednmz.dto.response.DtoLike;
import com.emrednmz.model.Like;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LikeMapper {

    // Request -> Entity


    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "user", ignore = true)
    Like toEntity(LikeRequest likeRequest);

    // Entity -> Dto
    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "user.id", target = "userId")
    DtoLike toDto(Like like);


    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateLikeFromRequest(LikeRequest likeRequest, @MappingTarget Like like);
}
