package com.emrednmz.services.impl;

import com.emrednmz.dto.request.AmenityRequest;
import com.emrednmz.dto.response.DtoAmenity;
import com.emrednmz.exception.BaseException;
import com.emrednmz.exception.ErrorMessage;
import com.emrednmz.exception.MessageType;
import com.emrednmz.mapper.AmenityMapper;
import com.emrednmz.model.Amenity;
import com.emrednmz.repositories.AmenityRepository;
import com.emrednmz.services.IAmenityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AmenityServiceImpl implements IAmenityService {
    @Autowired
    private AmenityRepository amenityRepository;
    @Autowired
    private AmenityMapper amenityMapper;
    

    @Override
    public Page<Amenity> findAllAmenities(Pageable pageable) {
        return amenityRepository.findAll(pageable);
    }

    @Override
    public List<DtoAmenity> getDtoFavoriteList(List<Amenity> amenities) {
        return amenities.stream()
                .map(amenityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoAmenity getAmenityById(Long id) {
        return amenityMapper.toDto(findAmenityById(id));
    }

    @Override
    public DtoAmenity createAmenity(AmenityRequest amenityRequest) {
        Amenity amenity = amenityMapper.toEntity(amenityRequest);
        amenity.setCreateTime(new Date());
        return amenityMapper.toDto(amenityRepository.save(amenity));
    }

    @Override
    public DtoAmenity updateAmenity(AmenityRequest amenityRequest, Long id) {
        Amenity amenity = findAmenityById(id);
        amenity.setUpdateTime(new Date());
        amenityMapper.updateAmenityFromRequest(amenityRequest, amenity);
        return amenityMapper.toDto(amenityRepository.save(amenity));
    }

    @Override
    public void deleteAmenity(Long id) {
        amenityRepository.deleteById(id);
    }

    private Amenity findAmenityById(Long id) {
        Optional<Amenity> amenity = amenityRepository.findById(id);
        if (amenity.isEmpty()) {
            throw new BaseException(new ErrorMessage(MessageType.NOT_FOUND_AMENITY, id.toString()));
        }
        return amenity.get();
    }
}
