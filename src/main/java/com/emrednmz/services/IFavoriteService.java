package com.emrednmz.services;

import com.emrednmz.dto.request.FavoriteRequest;
import com.emrednmz.dto.response.DtoFavorite;
import com.emrednmz.model.Favorite;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IFavoriteService {
    Page<Favorite> findAllFavorites(Pageable pageable);
    List<DtoFavorite> getDtoFavorites(List<Favorite> favorites);
    List<DtoFavorite> getUserFavorites();
    DtoFavorite getFavoriteById(Long id);
    DtoFavorite createFavorite(FavoriteRequest favoriteRequest);
    DtoFavorite updateFavorite(FavoriteRequest favoriteRequest, Long id);
    void deleteFavoriteById(Long id);
}
