package com.emrednmz.services;

import com.emrednmz.dto.request.LikeRequest;
import com.emrednmz.dto.response.DtoLike;
import com.emrednmz.model.Like;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ILikeService {
    Page<Like> findAllLikes(Pageable pageable);
    List<DtoLike> getDtoLikeLis(List<Like> likes);
    DtoLike getLikeById(Long id);
    DtoLike createLike(LikeRequest likeRequest);
    DtoLike updateLike(LikeRequest likeRequest, Long id);
    void deleteLike(Long id);

}
