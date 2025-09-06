package com.emrednmz.services;

import com.emrednmz.dto.response.DtoHotel;
import com.emrednmz.dto.request.HotelRequest;
import com.emrednmz.model.Hotel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface IHotelService {
    Page<Hotel> findAllHotels(Pageable pageable);
    List<DtoHotel> getDtoHotelList(List<Hotel> hotels);
    DtoHotel getHotelById(Long id);
    DtoHotel updateHotel(HotelRequest hotelRequest, Long id);
    DtoHotel createHotel(HotelRequest hotelRequest);
    void deleteHotel(Long id);
}
