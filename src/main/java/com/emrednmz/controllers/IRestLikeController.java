package com.emrednmz.controllers;

import com.emrednmz.dto.request.LikeRequest;
import com.emrednmz.dto.response.DtoLike;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;

public interface IRestLikeController {
    RootEntity<RestPageableEntity<DtoLike>> findAllLikes(RestPageableRequest request);
    RootEntity<DtoLike> getLikeById(Long id);
    RootEntity<DtoLike> createLike(LikeRequest request);
    RootEntity<DtoLike> updateLike(LikeRequest request, Long id);
    void deleteLike(Long id);
}
