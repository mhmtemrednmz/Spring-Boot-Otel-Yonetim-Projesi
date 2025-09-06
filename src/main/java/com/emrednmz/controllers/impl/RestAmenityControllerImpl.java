package com.emrednmz.controllers.impl;

import com.emrednmz.controllers.IRestAmenityController;
import com.emrednmz.controllers.RestBaseController;
import com.emrednmz.controllers.RootEntity;
import com.emrednmz.dto.request.AmenityRequest;
import com.emrednmz.dto.response.DtoAmenity;
import com.emrednmz.model.Amenity;
import com.emrednmz.services.IAmenityService;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("amenity")
public class RestAmenityControllerImpl extends RestBaseController implements IRestAmenityController {
    @Autowired
    private IAmenityService amenityService;


    @Override
    @GetMapping("list")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public RootEntity<RestPageableEntity<DtoAmenity>> findAllAmenities(RestPageableRequest pageableRequest) {
        Page<Amenity> page = amenityService.findAllAmenities(getPageable(pageableRequest));
        List<DtoAmenity> dtoList = amenityService.getDtoFavoriteList(page.getContent());
        RestPageableEntity<DtoAmenity> pageableResponse = toRestPageableResponse(page, dtoList);
        return ok(pageableResponse);
    }


    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public RootEntity<DtoAmenity> getAmenityById(@PathVariable Long id) {
        return ok(amenityService.getAmenityById(id));
    }

    @Override
    @PostMapping("save")
    @PreAuthorize("hasRole('ADMIN')")
    public RootEntity<DtoAmenity> createAmenity(@RequestBody @Valid AmenityRequest amenityRequest) {
        return ok(amenityService.createAmenity(amenityRequest));
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public RootEntity<DtoAmenity> updateAmenity(@RequestBody @Valid AmenityRequest amenityRequest,
                                                @PathVariable Long id) {
        return ok(amenityService.updateAmenity(amenityRequest, id));
    }

    @Override
    @DeleteMapping("{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteAmenity(@PathVariable Long id) {
        amenityService.deleteAmenity(id);
    }
}
