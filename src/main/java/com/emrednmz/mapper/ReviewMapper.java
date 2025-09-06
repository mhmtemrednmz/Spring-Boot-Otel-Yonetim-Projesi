package com.emrednmz.mapper;

import com.emrednmz.dto.request.ReviewRequest;
import com.emrednmz.dto.response.DtoReview;
import com.emrednmz.model.Review;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;


@Mapper(componentModel = "spring")
public interface ReviewMapper {


    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "user", ignore = true)
    Review toEntity(ReviewRequest reviewRequest);

    @Mapping(source = "hotel.id", target = "hotelId")
    @Mapping(source = "user.id", target = "userId")
    DtoReview toDto(Review review);


    @Mapping(target = "createTime", ignore = true)
    @Mapping(target = "updateTime", ignore = true)
    @Mapping(target = "hotel", ignore = true)
    @Mapping(target = "user", ignore = true)
    void updateReviewFromRequest(ReviewRequest reviewRequest, @MappingTarget Review review);
}
