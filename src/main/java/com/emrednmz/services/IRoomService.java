package com.emrednmz.services;

import com.emrednmz.dto.request.RoomRequest;
import com.emrednmz.dto.response.DtoRoom;
import com.emrednmz.model.Room;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IRoomService {
    Page<Room> getAllRooms(Pageable pageable);
    List<DtoRoom> getDtoRooms(List<Room> rooms);
    DtoRoom getRoomById(Long id);
    DtoRoom createRoom(RoomRequest roomRequest);
    DtoRoom updateRoom(RoomRequest roomRequest,Long id);
    void deleteRoom(Long id);
}
