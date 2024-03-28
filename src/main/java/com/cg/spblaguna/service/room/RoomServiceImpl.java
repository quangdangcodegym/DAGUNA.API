package com.cg.spblaguna.service.room;

import com.cg.spblaguna.exception.ResourceNotFoundException;
import com.cg.spblaguna.model.*;
import com.cg.spblaguna.model.dto.req.*;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.EImageType;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import com.cg.spblaguna.repository.*;
import com.cg.spblaguna.service.roomreal.IRoomRealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
public class RoomServiceImpl implements IRoomService {
    @Autowired
    private IRoomRepository roomRepository;
    @Autowired
    private IRoomPagingAndSortingRepository roomPagingAndSortingRepository;

    @Autowired
    private IKindOfRoomRepository kindOfRoomRepository;

    @Autowired
    private IPerTypeRepository perTypeRepository;

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoomRealRepository roomRealRepository;

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    private IRoomRealService roomRealService;

    public List<RoomResDTO> getRooms() {
        List<Room> rooms = roomRepository.findAll();
        return rooms.stream().map(Room::toRoomResDto).collect(Collectors.toList());
    }

    public Optional<Room> findById(Long id) {
        return roomRepository.findById(id);
    }

    public RoomResDTO save(RoomReqDTO roomReqDTO) throws RuntimeException {
        KindOfRoom kindOfRoom = kindOfRoomRepository.findById(roomReqDTO.getKindOfRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("KindOfRoom not found"));

        PerType perType = perTypeRepository.findById(roomReqDTO.getPerTypId())
                .orElseThrow(() -> new ResourceNotFoundException("PerType not found"));

        Room room = new Room(roomReqDTO.getName(), roomReqDTO.getRoomType(),
                roomReqDTO.getQuantity(), roomReqDTO.getViewType(), roomReqDTO.getPricePerNight(),
                roomReqDTO.getAcreage(), roomReqDTO.getSleep(), roomReqDTO.getDescription(),
                roomReqDTO.getUtilitie(), kindOfRoom, perType);
        Room roomUpdated = roomRepository.save(room);

        for (int i = 0; i < roomReqDTO.getQuantity(); i++) {
            RoomReal roomReal = new RoomReal();
            roomReal.setRoomId(roomUpdated);
            roomReal.setStatusRoom(EStatusRoom.NOT_READY);
            roomRealRepository.save(roomReal);
        }
        List<Image> images = new ArrayList<>();
        roomReqDTO.getImageIds().forEach(s -> {
            Image image = imageRepository.findById(s).orElseThrow(() -> new ResourceNotFoundException("Image not found"));
            image.setImageType(EImageType.ROOM);
            image.setRoom(roomUpdated);
            imageRepository.save(image);
            images.add(image);
        });
        roomUpdated.setImages(images);
        return roomUpdated.toRoomResDto();
    }


    public RoomResDTO update(RoomReqDTO roomReqDTO) {
        KindOfRoom kindOfRoom = kindOfRoomRepository.findById(roomReqDTO.getKindOfRoomId())
                .orElseThrow(() -> new ResourceNotFoundException("KindOfRoom not found"));
        PerType perType = perTypeRepository.findById(roomReqDTO.getPerTypId())
                .orElseThrow(() -> new ResourceNotFoundException("PerType not found"));
        Room room = roomRepository.findById(roomReqDTO.getId()).orElseThrow();
        room.setName(roomReqDTO.getName());
        room.setRoomType(roomReqDTO.getRoomType());
        room.setQuantity(roomReqDTO.getQuantity());
        room.setViewType(roomReqDTO.getViewType());
        room.setPricePerNight(roomReqDTO.getPricePerNight());
        room.setAcreage(roomReqDTO.getAcreage());
        room.setDescription(roomReqDTO.getDescription());
        room.setUtilitie(roomReqDTO.getUtilitie());
        room.setKindOfRoom(kindOfRoom);
        room.setPerType(perType);
        room.setSleep(roomReqDTO.getSleep());

        Room roomUpdated = roomRepository.save(room);

        List<Image> images = new ArrayList<>();
        roomReqDTO.getImageIds().forEach(s -> {
            Image image = imageRepository.findById(s).get();
            image.setImageType(EImageType.ROOM);
            image.setRoom(roomUpdated);
            imageRepository.save(image);

            images.add(image);
        });

        List<RoomReal> roomReals = new ArrayList<>();


        room.setImages(images);
        return room.toRoomResDto();
    }

    public void delete(Long id) {
        Room room = roomRepository.findById(id).get();

        List<Image> images = room.getImages();
        imageRepository.deleteAll(images);

        roomRepository.delete(room);
    }


    @Override
    public Room save(Room room) {

        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    public List<Room> findAll() {
        return roomRepository.findAll();
    }

    public void change(Room room) {
        roomRepository.save(room);
    }

    @Override
    public Page<RoomResDTO> filterRooms(String kw, ERoomType roomType, Pageable pageable) {
        return roomPagingAndSortingRepository.filterRooms(kw, roomType, pageable);
    }

    public Page<RoomResDTO> filterRoomsByPrice(String kw, ERoomType eRoomType,
                                               BigDecimal minPrice, BigDecimal maxPrice, Pageable pagingSort) {
        return roomPagingAndSortingRepository.filterRoomsByPrice(kw, eRoomType, minPrice, maxPrice, pagingSort);

    }


    public Page<RoomResDTO> searchBarRoomReqDTO(SearchBarRoomReqDTO searchBarRoomReqDTO, Pageable pageable) {
//        int actualChildren = (int) Math.ceil((searchBarRoomReqDTO.getGuest().getNumberChildren() + 1) / 2);
//        Page<RoomResDTO> rooms = roomRepository.searchBarRoomReqDTO(searchBarRoomReqDTO.getGuest().getNumberAdult() + actualChildren,
//                searchBarRoomReqDTO.getRoomType(), searchBarRoomReqDTO.getPerType(),
//                searchBarRoomReqDTO.getViewType(),
//                searchBarRoomReqDTO.getPriceMin(), searchBarRoomReqDTO.getPriceMax(),
//                pageable);
//
//        List<RoomResDTO> rooms1 = roomRepository.searchBarRoomReqDTO(searchBarRoomReqDTO.getGuest().getNumberAdult() + actualChildren,
//                searchBarRoomReqDTO.getRoomType(), searchBarRoomReqDTO.getPerType(),
//                searchBarRoomReqDTO.getViewType(),
//                searchBarRoomReqDTO.getPriceMin(), searchBarRoomReqDTO.getPriceMax());

        Page<RoomResDTO> rooms = roomRepository.searchBarFake(pageable);
        return rooms;
    }

    @Override
    public List<Room> findAllByUser_Unlock(boolean user_unlock) {
        return null;
    }

    @Override
    public Room findByUser_Id(Long id) {
        return null;
    }

    @Override
    public void update(Room room) {

    }


    public RoomResDTO findByIdDTO(Long id) {
        Room room = roomRepository.findById(id).orElse(null);
        if (room == null) {
            return null;
        }
        return new RoomResDTO(room);
    }

    @Override
    public RoomResDTO updateRoom_updateRoomReal(RoomInfoReqDTO roomInfoReqDTO) {
        Long roomId = roomInfoReqDTO.getRoomId();
        List<RoomRealReqDTO> roomReals = roomInfoReqDTO.getRoomReals();

        Room room = roomRepository.findById(roomId).orElseThrow(() -> new RuntimeException("Room not found with id: " + roomId));
//        roomRepository.save(room);
        for (RoomRealReqDTO roomRealReqDTO : roomReals) {
            Long roomRealId = roomRealReqDTO.getId();
            String roomCode = roomRealReqDTO.getRoomCode();

//            List<RoomReal> roomResDTOS= roomRealService.findAll();
//            for (RoomReal roomResDTO : roomResDTOS) {
//                if (roomCode.equals(roomResDTO.getRoomCode())) {
//                    throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Room with code '" + roomCode + "' already exists");
//                }
//            }
//           if ( roomRealRepository.existsByRoomCode(roomCode)){
//               throw new ResponseStatusException(HttpStatus.NOT_FOUND,"Room with code '" + roomCode + "' already exists");
//           }
            Integer floor = roomRealReqDTO.getFloor();

            RoomReal roomReal = roomRealRepository.findById(roomRealId).orElseThrow(() -> new RuntimeException("RoomReal not found with id: " + roomRealId));
            roomReal.setStatusRoom((roomRealReqDTO.getStatusRoom()));

            roomReal.setRoomCode(roomCode);
            roomReal.setERangeRoom(roomRealReqDTO.getRangeRoom());
            roomReal.setStatusRoom(roomRealReqDTO.getStatusRoom());
            roomReal.setFloor(floor);
            roomReal.setRoomId(room);

            roomRealRepository.save(roomReal);
        }
        return new RoomResDTO(room);
    }

//    @Override
//    public List<RoomResDTO> searchBarRoomHeader(SearchBarRoomReqDTO searchBarRoomReqDTO) {
//        int numberOfAdults = Math.toIntExact(searchBarRoomReqDTO.getGuest().getNumberAdult());
//        // Do something with the childrenAge if needed
//
//        LocalDate checkIn = searchBarRoomReqDTO.getCheckIn();
//        LocalDate checkOut = searchBarRoomReqDTO.getCheckOut();
//
//        // Truy vấn cơ sở dữ liệu để tìm kiếm các phòng phù hợp
//        List<Room> matchingRooms = roomRepository.findAvailableRooms(numberOfAdults, checkIn, checkOut);
//
//        // Chuyển đổi danh sách các phòng thành danh sách DTO để trả về
//        List<RoomResDTO> matchingRoomsDTO = matchingRooms.stream()
//                .map(Room::toRoomResDto)
//                .collect(Collectors.toList());
//
//        return matchingRoomsDTO;
//
//    }


    @Override
    public List<RoomFindAvailableRoom> findAvailableRoom(LocalDateTime selectFirstDay, LocalDateTime selectLastDay) {
        try {
            List<RoomFindAvailableRoom> roomReqDTOS = roomRepository.findAvailableRoom(selectFirstDay, selectLastDay);
            if (roomReqDTOS == null || roomReqDTOS.isEmpty()) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "room cannot be null or empty");
            }
            return roomReqDTOS;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Error: Unexpected exception occurred");
        }
    }

}
