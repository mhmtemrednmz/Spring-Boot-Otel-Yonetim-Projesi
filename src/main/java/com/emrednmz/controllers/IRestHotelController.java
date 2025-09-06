package com.emrednmz.controllers;

import com.emrednmz.dto.response.DtoHotel;
import com.emrednmz.dto.request.HotelRequest;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;

import java.util.List;

public interface IRestHotelController {
    RootEntity<RestPageableEntity<DtoHotel>> findAllHotels(RestPageableRequest request);
    RootEntity<DtoHotel> getHotelById(Long hotelId);
    RootEntity<DtoHotel> createHotel(HotelRequest hotelRequest);
    RootEntity<DtoHotel> updateHotel(HotelRequest hotelRequest, Long hotelId);
    void deleteHotel(Long hotelId);
}
