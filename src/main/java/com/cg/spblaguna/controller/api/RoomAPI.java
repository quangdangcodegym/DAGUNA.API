package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.exception.ResourceNotFoundException;
import com.cg.spblaguna.model.dto.req.RoomInfoReqDTO;
import com.cg.spblaguna.model.dto.req.RoomReqDTO;
import com.cg.spblaguna.model.dto.req.SearchBarRoomReqDTO;
import com.cg.spblaguna.model.dto.res.RoomResDTO;
import com.cg.spblaguna.model.enumeration.ERoomType;
import com.cg.spblaguna.service.room.IRoomService;
import com.cg.spblaguna.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.data.domain.Sort.Order;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomAPI {
    @Autowired
    private IRoomService roomService;

    @Autowired
    private AppUtils appUtils;


    @GetMapping("")
    public ResponseEntity<?> showRooms() {
        return new ResponseEntity<>(roomService.getRooms(), HttpStatus.OK);
    }


    @GetMapping("/filters")
    public ResponseEntity<?> filterRooms(
            @RequestParam(required = false) String kw,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "5") int size,

            @RequestParam(defaultValue = "") String roomType,
            @RequestParam(required = false) BigDecimal minPrice,
            @RequestParam(required = false) BigDecimal maxPrice,
            @RequestParam(defaultValue = "id,desc") String[] sort){

        try{
            List<Order> orders = new ArrayList<Order>();
            if (sort[0].contains(",")) {
                for (String sortOrder : sort) {
                    String[] _sort = sortOrder.split(",");
                    orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
                }
            } else {
                orders.add(new Order(getSortDirection(sort[1]), sort[0]));
            }
            Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

            ERoomType eRoomType = ERoomType.parseERoomType(roomType);

            if (roomType == null) {
                throw new ResourceNotFoundException("Param not valid");
            }
            Page<RoomResDTO> roomResDTOS = roomService.filterRoomsByPrice(kw, eRoomType,minPrice, maxPrice, pagingSort );


            if (roomResDTOS.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }else {
                return new ResponseEntity<>(roomResDTOS, HttpStatus.OK);
            }
        }catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }
    private Sort.Direction getSortDirection(String direction) {
        if (direction.equalsIgnoreCase("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equalsIgnoreCase("desc")) {
            return Sort.Direction.DESC;
        }

        throw new IllegalArgumentException("Invalid sort direction: " + direction);
    }
    @PostMapping("/search")
    public ResponseEntity<?> searchBarRooms(@RequestBody SearchBarRoomReqDTO searchBarRoomReqDTO, BindingResult bindingResult, Pageable pageable) {
        Page<RoomResDTO> roomResDTOPage = roomService.searchBarRoomReqDTO(searchBarRoomReqDTO, pageable);
        return new ResponseEntity<>(roomResDTOPage, HttpStatus.OK);
    }
//    @PostMapping("/search-bar")
//    public ResponseEntity<?> searchBarRoomsHeader(@RequestBody SearchBarRoomReqDTO searchBarRoomReqDTO ) {
//        List<RoomResDTO> roomHeaderSearchBar = roomService.searchBarRoomHeader(searchBarRoomReqDTO);
//        return new ResponseEntity<>(roomHeaderSearchBar, HttpStatus.OK);
//    }

    /**
     * Chức năng tạo phòng
     * @param roomReqDTO
     * thuộc tính utilities kiểu chuỗi theo định dạng JSON
     * roomReqDTO:
     * {
     *     "name": "kkkaaa",
     *     "roomType": "SUPERIOR",
     *     "statusRoom": "PLACED",
     *     "viewType": "GARDEN_VIEW",
     *     "kingOfRoomId": 1,
     *     "perTypId":1,
     *     "pricePerNight": 200000.00,
     *     "acreage": 100.00,
     *     "sleep": 3,
     *     "description": "aaaaaaa",
     *     "utilitie": "{\"Shower\": true, \"Room Safe\": true, \"Mini Bar\": false}"
     * }
     * @param bindingResult
     * @return
     */
    @PostMapping
//    @PreAuthorize("hasAnyRole('MODIFIER')")
    public ResponseEntity<?> saveRoom(@Validated @RequestBody RoomReqDTO roomReqDTO, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(appUtils.getValidationErrorJson(bindingResult));
        }
        return new ResponseEntity<>(roomService.save(roomReqDTO), HttpStatus.OK);
    }


    @PatchMapping("/{id}")
//    @PreAuthorize("hasAnyRole('MODIFIER')")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomReqDTO roomReqDTO) {
        roomReqDTO.setId(id);
        return new ResponseEntity<>(roomService.update(roomReqDTO), HttpStatus.OK);
    }

    @PatchMapping("/{roomId}/room-reals")
    public ResponseEntity<?> updateRoom_updateRoomReal(@RequestBody RoomInfoReqDTO roomInfoReqDTO) {
        roomService.updateRoom_updateRoomReal(roomInfoReqDTO);
        return new ResponseEntity<>(roomService.updateRoom_updateRoomReal(roomInfoReqDTO), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('MODIFIER', 'ADMIN')")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id) {
        RoomResDTO roomResDTO = roomService.findByIdDTO(id);
        if (roomResDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(roomResDTO);
    }

//    @PatchMapping("/lock/{id}")
//    public ResponseEntity<?> lockRoom(@PathVariable Long id){
//        Room room = roomService.findById(id).get();
//        room.setStatusRoom(EStatusRoom.NOT_READY);
//        roomService.change(room);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }
//
//    @PatchMapping("/open/{id}")
//    public ResponseEntity<?> openRoom(@PathVariable Long id){
//        Room room = roomService.findById(id).get();
//        room.setStatusRoom(EStatusRoom.READY);
//        roomService.change(room);
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

}
