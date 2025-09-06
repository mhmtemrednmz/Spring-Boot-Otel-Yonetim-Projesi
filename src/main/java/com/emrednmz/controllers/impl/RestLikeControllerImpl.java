package com.emrednmz.controllers.impl;

import com.emrednmz.controllers.IRestLikeController;
import com.emrednmz.controllers.RestBaseController;
import com.emrednmz.controllers.RootEntity;
import com.emrednmz.dto.request.LikeRequest;
import com.emrednmz.dto.response.DtoLike;
import com.emrednmz.model.Like;
import com.emrednmz.services.ILikeService;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("like")
public class RestLikeControllerImpl extends RestBaseController implements IRestLikeController {

    private final ILikeService likeService;

    public RestLikeControllerImpl(ILikeService likeService) {
        this.likeService = likeService;
    }

    @Override
    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<RestPageableEntity<DtoLike>> findAllLikes(RestPageableRequest request) {
        Page<Like> page = likeService.findAllLikes(getPageable(request));
        List<DtoLike> dtoLikeList = likeService.getDtoLikeLis(page.getContent());
        return ok(toRestPageableResponse(page, dtoLikeList));
    }

    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<DtoLike> getLikeById(@PathVariable Long id) {
        return ok(likeService.getLikeById(id));
    }

    @Override
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public RootEntity<DtoLike> createLike(@RequestBody @Valid LikeRequest request) {
        return ok(likeService.createLike(request));
    }

    @Override
    @PutMapping("{id}")
    public RootEntity<DtoLike> updateLike(@RequestBody @Valid LikeRequest request,@PathVariable Long id) {
        return ok(likeService.updateLike(request, id));
    }

    @Override
    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_MANAGER', 'ROLE_ADMIN')")
    public void deleteLike(Long id) {
        likeService.deleteLike(id);
    }
}
