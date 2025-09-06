package com.emrednmz.services.impl;

import com.emrednmz.dto.request.RoomRequest;
import com.emrednmz.dto.response.DtoRoom;
import com.emrednmz.exception.BaseException;
import com.emrednmz.exception.ErrorMessage;
import com.emrednmz.exception.MessageType;
import com.emrednmz.mapper.HotelMapper;
import com.emrednmz.mapper.RoomMapper;
import com.emrednmz.model.Hotel;
import com.emrednmz.model.Room;
import com.emrednmz.model.UserEntity;
import com.emrednmz.repositories.HotelRepository;
import com.emrednmz.repositories.RoomRepository;
import com.emrednmz.services.IAutenticationService;
import com.emrednmz.services.IRoomService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoomServiceImpl implements IRoomService {

    private final RoomRepository roomRepository;
    private final RoomMapper roomMapper;
    private final HotelRepository hotelRepository;
    private final IAutenticationService autenticationService;

    public RoomServiceImpl(RoomRepository roomRepository,
                           RoomMapper roomMapper,
                           HotelRepository hotelRepository,
                           IAutenticationService autenticationService) {
        this.roomRepository = roomRepository;
        this.roomMapper = roomMapper;
        this.hotelRepository = hotelRepository;
        this.autenticationService = autenticationService;
    }

    @Override
    public Page<Room> getAllRooms(Pageable pageable) {
        return roomRepository.findAll(pageable);
    }

    @Override
    public List<DtoRoom> getDtoRooms(List<Room> rooms) {
        return rooms.stream()
                .map(roomMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public DtoRoom getRoomById(Long id) {
        return roomMapper.toDto(findRoomById(id));
    }

    @Override
    public DtoRoom createRoom(RoomRequest roomRequest) {
        Room entity = roomMapper.toEntity(roomRequest);
        Hotel hotel = hotelRepository.findById(roomRequest.getHotelId()).orElse(null);

        UserEntity currentUser = autenticationService.getCurrentUser();

        if(!hotel.getManager().getId().equals(currentUser.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, currentUser.getId().toString()));
        }

        entity.setCreateTime(new Date());
        arrangeRoom(entity, roomRequest);
        return roomMapper.toDto(roomRepository.save(entity));
    }

    @Override
    public DtoRoom updateRoom(RoomRequest roomRequest, Long id) {
        Room room = findRoomById(id);

        UserEntity currentUser = autenticationService.getCurrentUser();

        if(!room.getHotel().getManager().getId().equals(currentUser.getId())) {
            throw new BaseException(new ErrorMessage(MessageType.UNAUTHORIZED_TRANSACTION, currentUser.getId().toString()));
        }

        roomMapper.updateRoomFromRequest(roomRequest, room);
        room.setUpdateTime(new Date());
        arrangeRoom(room, roomRequest);
        return roomMapper.toDto(roomRepository.save(room));
    }

    @Override
    public void deleteRoom(Long id) {

        roomRepository.deleteById(id);
    }

    private Room findRoomById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new BaseException(
                        new ErrorMessage(MessageType.NOT_FOUND_ROOM, id.toString())));
    }

    private Room arrangeRoom(Room room, RoomRequest roomRequest) {
        Hotel hotel = hotelRepository.findById(roomRequest.getHotelId())
                .orElseThrow(() -> {
                    return new BaseException(new ErrorMessage(MessageType.NOT_FOUND_HOTEL, roomRequest.getHotelId().toString()));
                });
        room.setHotel(hotel);
        return room;
    }
}
