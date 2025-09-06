package com.emrednmz.services.impl;

import com.emrednmz.dto.request.FavoriteRequest;
import com.emrednmz.dto.response.DtoFavorite;
import com.emrednmz.exception.BaseException;
import com.emrednmz.exception.ErrorMessage;
import com.emrednmz.exception.MessageType;
import com.emrednmz.mapper.FavoriteMapper;
import com.emrednmz.model.Favorite;
import com.emrednmz.model.Hotel;
import com.emrednmz.model.UserEntity;
import com.emrednmz.repositories.FavoriteRepository;
import com.emrednmz.repositories.HotelRepository;
import com.emrednmz.repositories.UserRepository;
import com.emrednmz.services.IAutenticationService;
import com.emrednmz.services.IFavoriteService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class FavoriteServiceImpl implements IFavoriteService {

    private static final Logger logger = LoggerFactory.getLogger(FavoriteServiceImpl.class);

    private final FavoriteRepository favoriteRepository;
    private final FavoriteMapper favoriteMapper;
    private final HotelRepository hotelRepository;
    private final IAutenticationService autenticationService;

    public FavoriteServiceImpl(FavoriteRepository favoriteRepository,
                               FavoriteMapper favoriteMapper,
                               HotelRepository hotelRepository,
                               IAutenticationService autenticationService) {
        this.favoriteRepository = favoriteRepository;
        this.favoriteMapper = favoriteMapper;
        this.hotelRepository = hotelRepository;
        this.autenticationService = autenticationService;
    }

    @Override
    public Page<Favorite> findAllFavorites(Pageable pageable) {
        return favoriteRepository.findAll(pageable);
    }

    @Override
    public List<DtoFavorite> getDtoFavorites(List<Favorite> favorites) {
        return favorites.stream()
                .map(favoriteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DtoFavorite> getUserFavorites() {
        UserEntity currentUser = autenticationService.getCurrentUser();

        return favoriteRepository.findByUserId(currentUser.getId()).get().stream()
                .map(favoriteMapper::toDto)
                .collect(Collectors.toList());
    }


    @Override
    public DtoFavorite getFavoriteById(Long id) {
        logger.info("ID: {} favori getiriliyor.", id);
        return favoriteMapper.toDto(findFavoriteById(id));
    }

    @Override
    public DtoFavorite createFavorite(FavoriteRequest favoriteRequest) {
        UserEntity currentUser = autenticationService.getCurrentUser();

        Favorite favorite = favoriteMapper.toEntity(favoriteRequest);
        favorite.setUser(currentUser);
        favorite.setCreateTime(new Date());
        arrangeFavorite(favorite, favoriteRequest);

        DtoFavorite dto = favoriteMapper.toDto(favoriteRepository.save(favorite));
        logger.info("Favori oluşturuldu. ID: {}", dto.getId());
        return dto;
    }

    @Override
    public DtoFavorite updateFavorite(FavoriteRequest favoriteRequest, Long id) {
        logger.info("Favori güncelleme işlemi başladı. FavoriteId: {}", id);

        Favorite favorite = findFavoriteById(id);
        favorite.setUpdateTime(new Date());
        arrangeFavorite(favorite, favoriteRequest);

        DtoFavorite dto = favoriteMapper.toDto(favoriteRepository.save(favorite));
        logger.info("Favori güncellendi. ID: {}", dto.getId());
        return dto;
    }

    @Override
    public void deleteFavoriteById(Long id) {
        logger.info("Favori silme işlemi başladı. FavoriteId: {}", id);

        Favorite favorite = findFavoriteById(id);// kontrol

        UserEntity currentUser = autenticationService.getCurrentUser();

        if(!favorite.getUser().getId().equals(currentUser.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, currentUser.getId().toString()));
        }

        favoriteRepository.deleteById(id);
        logger.info("Favori silindi. FavoriteId: {}", id);
    }

    private Favorite findFavoriteById(Long id) {
        return favoriteRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Favorite bulunamadı. FavoriteId: {}", id);
                    return new BaseException(new ErrorMessage(MessageType.NOT_FOUND_FAVORITE, id.toString()));
                });
    }

    private void arrangeFavorite(Favorite favorite, FavoriteRequest favoriteRequest) {
        // Hotel kontrolü
        Hotel hotel = hotelRepository.findById(favoriteRequest.getHotelId())
                .orElseThrow(() -> {
                    logger.warn("Hotel bulunamadı. HotelId: {}", favoriteRequest.getHotelId());
                    return new BaseException(new ErrorMessage(MessageType.NOT_FOUND_HOTEL, favoriteRequest.getHotelId().toString()));
                });
        favorite.setHotel(hotel);
    }
}
