package com.emrednmz.mapper;

import com.emrednmz.dto.request.FavoriteRequest;
import com.emrednmz.dto.response.DtoFavorite;
import com.emrednmz.model.Favorite;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface FavoriteMapper {

    // Request -> Entity


    @Mapping(target = "hotel", ignore = true) // service içinde set edilir
    @Mapping(target = "user", ignore = true)  // service içinde set edilir
    Favorite toEntity(FavoriteRequest request);

    // Entity -> DTO
    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "user.id", target = "userId")
    DtoFavorite toDto(Favorite favorite);

    // Update (favoriler genelde update edilmez ama koyabiliriz)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateFavoriteFromRequest(FavoriteRequest request, @MappingTarget Favorite favorite);
}
