package com.emrednmz.controllers.impl;

import com.emrednmz.controllers.IRestRoomController;
import com.emrednmz.controllers.RestBaseController;
import com.emrednmz.controllers.RootEntity;
import com.emrednmz.dto.request.RoomRequest;
import com.emrednmz.dto.response.DtoRoom;
import com.emrednmz.model.Room;
import com.emrednmz.services.IRoomService;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("room")
public class RestRoomControllerImpl extends RestBaseController implements IRestRoomController {
    private final IRoomService roomService;

    public RestRoomControllerImpl(IRoomService roomService) {
        this.roomService = roomService;
    }

    @Override
    @GetMapping("list")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public RootEntity<RestPageableEntity<DtoRoom>> getAllRooms(RestPageableRequest request) {
        Page<Room> page = roomService.getAllRooms(getPageable(request));
        List<DtoRoom> dtoRooms = roomService.getDtoRooms(page.getContent());
        return ok(toRestPageableResponse(page, dtoRooms));
    }

    @Override
    @GetMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER', 'ROLE_USER')")
    public RootEntity<DtoRoom> getRoomById(@PathVariable Long id) {
        return ok(roomService.getRoomById(id));
    }

    @Override
    @PostMapping("save")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public RootEntity<DtoRoom> createRoom(@RequestBody @Valid RoomRequest request) {
        return ok(roomService.createRoom(request));
    }

    @Override
    @PutMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public RootEntity<DtoRoom> updateRoom(@RequestBody @Valid RoomRequest request,
                                          @PathVariable Long id) {
        return ok(roomService.updateRoom(request, id));
    }

    @Override
    @DeleteMapping("{id}")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_MANAGER')")
    public void deleteRoom(Long id) {
        roomService.deleteRoom(id);
    }
}
