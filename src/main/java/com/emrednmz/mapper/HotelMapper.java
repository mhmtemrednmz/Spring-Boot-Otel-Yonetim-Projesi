package com.emrednmz.mapper;

import com.emrednmz.dto.request.HotelRequest;
import com.emrednmz.dto.response.DtoHotel;
import com.emrednmz.model.Hotel;
import org.mapstruct.*;

@Mapper(
        componentModel = "spring",
        uses = {UserMapper.class, RoomMapper.class, ReviewMapper.class, LikeMapper.class, FavoriteMapper.class},
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface HotelMapper {

    // Request -> Entity (create için)


    @Mapping(target = "manager", ignore = true) // manager'ı service içinde set edeceğiz
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    Hotel toEntity(HotelRequest request);

    // Entity -> DTO (response için)
    @Mapping(source = "manager.id", target = "managerId")
    DtoHotel toDto(Hotel hotel);

    // Update -> Entity (sadece dolu alanlar güncellesin)
    @Mapping(target = "manager", ignore = true) // manager update service içinde yapılır
    @Mapping(target = "rooms", ignore = true)
    @Mapping(target = "reviews", ignore = true)
    @Mapping(target = "likes", ignore = true)
    @Mapping(target = "favorites", ignore = true)
    void updateHotelFromRequest(HotelRequest request, @MappingTarget Hotel hotel);
}
