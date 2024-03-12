package com.cg.spblaguna.controller.api;

import com.cg.spblaguna.model.Room;
import com.cg.spblaguna.model.dto.req.RoomReqDTO;
import com.cg.spblaguna.model.enumeration.EStatusRoom;
import com.cg.spblaguna.service.room.RoomServiceImpl;
import com.cg.spblaguna.util.AppUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/rooms")
@CrossOrigin(origins = "*")
public class RoomAPI {
    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private AppUtils appUtils;

//    @Autowired
//    private UserService userService;

    @GetMapping("")
    public ResponseEntity<?> showRooms() {
        return new ResponseEntity<>(roomService.getRooms(), HttpStatus.OK);
    }

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



    @PutMapping("/{id}")
//    @PreAuthorize("hasAnyRole('MODIFIER')")
    public ResponseEntity<?> updateRoom(@PathVariable Long id, @RequestBody RoomReqDTO roomReqDTO) {
        roomReqDTO.setId(id);
        return new ResponseEntity<>(roomService.update(roomReqDTO), HttpStatus.OK);
    }


    @DeleteMapping("/{id}")
//    @PreAuthorize("hasAnyRole('MODIFIER', 'ADMIN')")
    public ResponseEntity<?> deleteRoom(@PathVariable Long id) {
        roomService.delete(id);
        return new ResponseEntity<>(new HashMap<>(), HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> getRoom(@PathVariable Long id) {
        Room room = roomService.findById(id).get();
        return new ResponseEntity<>(room, HttpStatus.OK);
    }

    @PatchMapping("/lock/{id}")
    public ResponseEntity<?> lockRoom(@PathVariable Long id){
        Room room = roomService.findById(id).get();
        room.setStatusRoom(EStatusRoom.NOT_READY);
        roomService.change(room);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PatchMapping("/open/{id}")
    public ResponseEntity<?> openRoom(@PathVariable Long id){
        Room room = roomService.findById(id).get();
        room.setStatusRoom(EStatusRoom.READY);
        roomService.change(room);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
