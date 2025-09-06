package com.emrednmz.services.impl;


import com.emrednmz.dto.request.LikeRequest;
import com.emrednmz.dto.response.DtoLike;
import com.emrednmz.exception.BaseException;
import com.emrednmz.exception.ErrorMessage;
import com.emrednmz.exception.MessageType;
import com.emrednmz.mapper.LikeMapper;
import com.emrednmz.model.Hotel;
import com.emrednmz.model.Like;
import com.emrednmz.model.UserEntity;
import com.emrednmz.repositories.HotelRepository;
import com.emrednmz.repositories.LikeRepository;
import com.emrednmz.repositories.UserRepository;
import com.emrednmz.services.IAutenticationService;
import com.emrednmz.services.ILikeService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class LikeServiceImpl implements ILikeService {
    private final LikeRepository likeRepository;
    private final LikeMapper likeMapper;
    private final HotelRepository hotelRepository;
    private final IAutenticationService autenticationService;

    public LikeServiceImpl(LikeRepository likeRepository,
                           LikeMapper likeMapper,
                           HotelRepository hotelRepository,
                           IAutenticationService autenticationService) {
        this.likeRepository = likeRepository;
        this.likeMapper = likeMapper;

        this.hotelRepository = hotelRepository;
        this.autenticationService = autenticationService;
    }

    @Override
    public Page<Like> findAllLikes(Pageable pageable) {
        return likeRepository.findAll(pageable);
    }

    @Override
    public List<DtoLike> getDtoLikeLis(List<Like> likes) {
        return likes.stream()
                .map(likeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoLike getLikeById(Long id) {
        return likeMapper.toDto(findLikeById(id));
    }

    @Override
    public DtoLike createLike(LikeRequest likeRequest) {
        UserEntity currentUser = autenticationService.getCurrentUser();

        Like like = likeMapper.toEntity(likeRequest);
        like.setCreateTime(new Date());
        like.setUser(currentUser);
        arrangeLike(like, likeRequest);
        return likeMapper.toDto(likeRepository.save(like));
    }

    @Override
    public DtoLike updateLike(LikeRequest likeRequest, Long id) {
        Like like = findLikeById(id);
        like.setUpdateTime(new Date());
        arrangeLike(like, likeRequest);
        return likeMapper.toDto(likeRepository.save(like));
    }

    @Override
    public void deleteLike(Long id) {
        Like like = findLikeById(id);

        UserEntity currentUser = autenticationService.getCurrentUser();
        if (!like.getUser().getId().equals(currentUser.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, currentUser.getId().toString()));
        }

        likeRepository.deleteById(id);
    }

    private Like findLikeById(Long id) {
        return likeRepository.findById(id)
                .orElseThrow(() -> {
                    return new BaseException(new ErrorMessage(MessageType.NOT_FOUND_LIKE, id.toString()));
                });
    }


    private void arrangeLike(Like like, LikeRequest likeRequest) {

        // Hotel kontrolü
        Hotel hotel = hotelRepository.findById(likeRequest.getHotelId())
                .orElseThrow(() -> {
                    return new BaseException(new ErrorMessage(MessageType.NOT_FOUND_HOTEL, likeRequest.getHotelId().toString()));
                });
        like.setHotel(hotel);
    }


}
