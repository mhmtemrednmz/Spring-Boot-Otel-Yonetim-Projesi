package com.emrednmz.controllers;

import com.emrednmz.dto.request.RoomRequest;
import com.emrednmz.dto.response.DtoRoom;
import com.emrednmz.utils.RestPageableEntity;
import com.emrednmz.utils.RestPageableRequest;

public interface IRestRoomController {
    RootEntity<RestPageableEntity<DtoRoom>> getAllRooms(RestPageableRequest request);
    RootEntity<DtoRoom> getRoomById(Long id);
    RootEntity<DtoRoom> createRoom(RoomRequest request);
    RootEntity<DtoRoom> updateRoom(RoomRequest request,Long id);
    void deleteRoom(Long id);
}
