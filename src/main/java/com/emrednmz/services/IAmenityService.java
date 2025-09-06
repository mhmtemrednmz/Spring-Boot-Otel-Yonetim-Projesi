package com.emrednmz.services;

import com.emrednmz.dto.request.AmenityRequest;
import com.emrednmz.dto.response.DtoAmenity;
import com.emrednmz.model.Amenity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IAmenityService {
    Page<Amenity> findAllAmenities(Pageable pageable);
    List<DtoAmenity> getDtoFavoriteList(List<Amenity> amenities );
    DtoAmenity getAmenityById(Long id);
    DtoAmenity createAmenity(AmenityRequest amenityRequest);
    DtoAmenity updateAmenity(AmenityRequest amenityRequest, Long id);
    void deleteAmenity(Long id);


}
