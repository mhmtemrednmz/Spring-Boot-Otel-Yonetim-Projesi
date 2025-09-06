package com.emrednmz.controllers.impl;

import com.emrednmz.controllers.IRestFavoriteController;
import com.emrednmz.controllers.RestBaseController;
import com.emrednmz.controllers.RootEntity;
import com.emrednmz.dto.request.FavoriteRequest;
import com.emrednmz.dto.response.DtoFavorite;
import com.emrednmz.model.Favorite;
import com.emrednmz.services.IFavoriteService;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("favorite")
public class RestFavoriteControllerImpl extends RestBaseController implements IRestFavoriteController {

    private final IFavoriteService favoriteService;

    public RestFavoriteControllerImpl(IFavoriteService favoriteService) {
        this.favoriteService = favoriteService;
    }


    @Override
    @GetMapping("list")
    @PreAuthorize("hasRole('ADMIN')")
    public RootEntity<RestPageableEntity<DtoFavorite>> findAllFavorites(RestPageableRequest request) {
        Page<Favorite> page = favoriteService.findAllFavorites(getPageable(request));
        List<DtoFavorite> dtoFavorites = favoriteService.getDtoFavorites(page.getContent());
        return ok(toRestPageableResponse(page, dtoFavorites));
    }

    @Override
    @GetMapping("user-favorites")
    public RootEntity<List<DtoFavorite>> getUserFavorites() {
        return ok(favoriteService.getUserFavorites());
    }


    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RootEntity<DtoFavorite> getFavoriteById(@PathVariable Long id) {
        return ok(favoriteService.getFavoriteById(id));
    }

    @Override
    @PostMapping("save")
    public RootEntity<DtoFavorite> createFavorite(@RequestBody FavoriteRequest favoriteRequest) {
        return ok(favoriteService.createFavorite(favoriteRequest));
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RootEntity<DtoFavorite> updateFavorite(@RequestBody FavoriteRequest favoriteRequest, @PathVariable Long id) {
        return ok(favoriteService.updateFavorite(favoriteRequest, id));
    }

    @Override
    @DeleteMapping("{id}")
    public void deleteFavorite(@PathVariable Long id) {
        favoriteService.deleteFavoriteById(id);
    }
}
