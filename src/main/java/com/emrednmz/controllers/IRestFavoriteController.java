package com.emrednmz.controllers;

import com.emrednmz.dto.request.FavoriteRequest;
import com.emrednmz.dto.response.DtoFavorite;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;

import java.util.List;

public interface IRestFavoriteController {
    RootEntity<RestPageableEntity<DtoFavorite>> findAllFavorites(RestPageableRequest request);
    RootEntity<List<DtoFavorite>> getUserFavorites();
    RootEntity<DtoFavorite> getFavoriteById(Long id);
    RootEntity<DtoFavorite> createFavorite(FavoriteRequest favoriteRequest);
    RootEntity<DtoFavorite> updateFavorite(FavoriteRequest favoriteRequest, Long id);
    void deleteFavorite(Long id);
}
