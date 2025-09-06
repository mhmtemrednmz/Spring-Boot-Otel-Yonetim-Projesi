package com.emrednmz.controllers;

import com.emrednmz.dto.request.AmenityRequest;
import com.emrednmz.dto.response.DtoAmenity;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;

import java.util.List;

public interface IRestAmenityController {
    RootEntity<RestPageableEntity<DtoAmenity>> findAllAmenities(RestPageableRequest request);
    RootEntity<DtoAmenity> getAmenityById(Long id);
    RootEntity<DtoAmenity> createAmenity(AmenityRequest amenityRequest);
    RootEntity<DtoAmenity> updateAmenity(AmenityRequest amenityRequest, Long id);
     void deleteAmenity(Long id);

}
