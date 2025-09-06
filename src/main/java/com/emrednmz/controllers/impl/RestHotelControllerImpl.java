package com.emrednmz.controllers.impl;

import com.emrednmz.controllers.IRestHotelController;
import com.emrednmz.controllers.RestBaseController;
import com.emrednmz.controllers.RootEntity;
import com.emrednmz.dto.response.DtoHotel;
import com.emrednmz.dto.request.HotelRequest;
import com.emrednmz.model.Hotel;
import com.emrednmz.services.IHotelService;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("hotel")
public class RestHotelControllerImpl extends RestBaseController implements IRestHotelController {

    @Autowired
    private IHotelService hotelService;

    @Override
    @GetMapping("list")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public RootEntity<RestPageableEntity<DtoHotel>> findAllHotels(RestPageableRequest request) {
        Page<Hotel> page = hotelService.findAllHotels(getPageable(request));
        List<DtoHotel> dtoHotelList = hotelService.getDtoHotelList(page.getContent());
        return ok(toRestPageableResponse(page, dtoHotelList));
    }

    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('USER','MANAGER','ADMIN')")
    public RootEntity<DtoHotel> getHotelById(@PathVariable Long id) {
        return ok(hotelService.getHotelById(id));
    }

    @Override
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public RootEntity<DtoHotel> createHotel(@RequestBody @Valid HotelRequest hotelRequest) {
        return ok(hotelService.createHotel(hotelRequest));
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public RootEntity<DtoHotel> updateHotel(@RequestBody @Valid HotelRequest hotelRequest, @PathVariable Long id) {
        return ok(hotelService.updateHotel(hotelRequest, id));
    }


    @Override
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('MANAGER','ADMIN')")
    public void deleteHotel(@PathVariable Long id) {
        hotelService.deleteHotel(id);
    }
}
