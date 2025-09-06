package com.emrednmz.services.impl;

import com.emrednmz.dto.response.DtoHotel;
import com.emrednmz.dto.request.HotelRequest;
import com.emrednmz.exception.BaseException;
import com.emrednmz.exception.ErrorMessage;
import com.emrednmz.exception.MessageType;
import com.emrednmz.mapper.HotelMapper;
import com.emrednmz.model.Hotel;
import com.emrednmz.model.UserEntity;
import com.emrednmz.repositories.HotelRepository;
import com.emrednmz.repositories.UserRepository;
import com.emrednmz.services.IAutenticationService;
import com.emrednmz.services.IHotelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HotelServiceImpl implements IHotelService {
    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private HotelMapper hotelMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private IAutenticationService authService;



    @Override
    public Page<Hotel> findAllHotels(Pageable pageable) {
        return hotelRepository.findAll(pageable);
    }

    @Override
    public List<DtoHotel> getDtoHotelList(List<Hotel> hotels) {
        return hotels.stream()
                .map(hotelMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoHotel getHotelById(Long id) {
        return hotelMapper.toDto(findHotelById(id));
    }

    @Override
    public DtoHotel createHotel(HotelRequest hotelRequest) {
        UserEntity currentUser = authService.getCurrentUser();

        Hotel entity = hotelMapper.toEntity(hotelRequest);
        entity.setManager(currentUser);
        entity.setCreateTime(new Date());
        return hotelMapper.toDto(hotelRepository.save(entity));
    }

    @Override
    public DtoHotel updateHotel(HotelRequest hotelRequest, Long id) {

        Hotel hotel = findHotelById(id);

        UserEntity currentUser = authService.getCurrentUser();

        if (authService.hasRole("MANAGER")) {
            if (!hotel.getManager().getId().equals(currentUser.getId())) {
                throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION,currentUser.getId().toString()));
            }
        }

        hotel.setUpdateTime(new Date());
        hotel.setManager(userRepository.findById(currentUser.getId()).get());
        hotelMapper.updateHotelFromRequest(hotelRequest, hotel);
        return hotelMapper.toDto(hotelRepository.save(hotel));
    }

    @Override
    public void deleteHotel(Long id) {
        Hotel hotel = findHotelById(id);

        UserEntity currentUser = authService.getCurrentUser();

        if (authService.hasRole("MANAGER")) {
            if (!hotel.getManager().getId().equals(currentUser.getId())) {
                throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, currentUser.getId().toString()));
            }
        }

        hotelRepository.deleteById(id);
    }

    private Hotel findHotelById(Long id) {
        Optional<Hotel> optHotel = hotelRepository.findById(id);
        if (optHotel.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NOT_FOUND_HOTEL,id.toString()));
        }
        return optHotel.get();
    }
}
