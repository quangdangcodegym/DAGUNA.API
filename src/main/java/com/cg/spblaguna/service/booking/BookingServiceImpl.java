package com.cg.spblaguna.service.booking;

import com.cg.spblaguna.exception.ResourceExistsException;
import com.cg.spblaguna.model.*;
import com.cg.spblaguna.model.dto.req.BookingReqCreDTO;
import com.cg.spblaguna.model.dto.req.BookingReqUpdate_BookingServiceCreUpdateDTO;
import com.cg.spblaguna.model.dto.req.BookingReqUpdate_CustomerDTO;
import com.cg.spblaguna.model.dto.req.BookingReqUpdate_RoomAddDTO;
import com.cg.spblaguna.model.dto.res.BookingDetailResDTO;
import com.cg.spblaguna.model.dto.res.BookingResDTO;
import com.cg.spblaguna.model.enumeration.EBookingServiceType;
import com.cg.spblaguna.model.enumeration.ELockStatus;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.repository.*;
import com.cg.spblaguna.service.cardpayment.ICardPaymentService;
import com.cg.spblaguna.service.user.IUserService;
import com.cg.spblaguna.util.AppUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Transactional
@ConfigurationProperties(prefix = "application.vat")
public class BookingServiceImpl implements IBookingService {

    @Value("${application.vat.booking-detail}")
    private Float vatBookingDetail;
    @Value("${application.vat.booking-detail-service}")
    private Float vatBookingDetailService;


    @Autowired
    private IBookingRepository bookingRepository;

    @Autowired
    private AppUtils appUtils;

    @Autowired
    private IRoomRepository roomRepository;

    @Autowired
    private IBookingDetailRepository bookingDetailRepository;

    @Autowired
    private IBookingServiceRepository bookingServiceRepository;

    @Autowired
    private IBookingDetailServiceRepository bookingDetailServiceRepository;

    @Autowired
    private IRoomRealRepository roomRealRepository;

    @Autowired
    private ICardPaymentService cardPaymentService;

    @Autowired
    private IUserService userService;
    @Override
    public List<Booking> findAll() {
        return bookingRepository.findAll();
    }

    @Override
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Override
    public Booking save(Booking booking) {
        bookingRepository.save(booking);
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    @Override
    public List<BookingResDTO> findAllBookingResDTO() {
        return bookingRepository.findAllBookingResDTO();
    }

    @Override
    public BookingResDTO findBookingResDTOById(Long id) {
        return bookingRepository.findBookingResDTOById(id);
    }

    @Override
    public List<Booking> findAllByCustomerId(Long customerId) {
        return null;
    }

    @Override
    public List<Booking> findByRoomIdAndBookingDate(Long roomId, String bookingDate) {
        return null;
    }

    @Override
    public BookingResDTO saveBooking(BookingReqCreDTO bookingReqCreDTO) {
        // Lưu mới booking
        Booking booking = new Booking();
        String bookingCode = AppUtils.randomCode();
        booking.setBookingCode(bookingCode);
        booking.setCreateAt(LocalDateTime.now());
        bookingRepository.save(booking);


        // Lưu booking detail
        BookingDetail bookingDetail = new BookingDetail();
        bookingDetail.setCheckIn(bookingReqCreDTO.getBookingDetail().getCheckIn());
        bookingDetail.setCheckOut(bookingReqCreDTO.getBookingDetail().getCheckOut());
        bookingDetail.setCheckInStatus(true);

        Room room = roomRepository.findById(bookingReqCreDTO.getBookingDetail().getRoomId()).get();
        bookingDetail.setRoom(room);
        bookingDetail.setBooking(booking);
        bookingDetail.setPrice(room.getPricePerNight());
        bookingDetail.setNumberAdult(bookingReqCreDTO.getBookingDetail().getNumberAdult());
        bookingDetail.setNumberChildren(bookingReqCreDTO.getBookingDetail().getNumberChildren());
        bookingDetail.setDiscountCode(bookingReqCreDTO.getBookingDetail().getDiscountCode());
        bookingDetail.setTotalAmount(appUtils.calculateVAT(bookingDetail.getPrice(), vatBookingDetail));
        bookingDetail.setVat(new BigDecimal(vatBookingDetail));

        BigDecimal money = room.getPricePerNight();
        bookingDetail.setTotal(appUtils.calculateVAT(money, vatBookingDetail));

        bookingDetailRepository.save(bookingDetail);

        // Chuyển đổi sang bookingResDTO: lần đầu tạo mới chỉ add DUY NHẤT 1 bookingdetail
        BookingDetailResDTO bookingDetailResDTO = bookingDetail.toBookingDetailResDTO();
        List<BookingDetailResDTO> bookingDetailResDTOS = new ArrayList<>();
        bookingDetailResDTOS.add(bookingDetailResDTO);

        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(booking.getId());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);


        return bookingResDTO;
    }

    @Override
    public BookingResDTO saveBookingReqUpdate_BookingServiceAddDTO(BookingReqUpdate_BookingServiceCreUpdateDTO bookingReqUpdateBookingServiceCreUpdateDTO) {
        BookingDetail bookingDetail = bookingDetailRepository.findById(bookingReqUpdateBookingServiceCreUpdateDTO.getBookingDetailId()).get();
        BookingService bookingService = bookingServiceRepository.findById(bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceId()).get();

        List<BookingDetailService> bookingDetailServices = bookingDetailServiceRepository.findBookingDetailServiceByBookingDetail_Id(bookingDetail.getId());
        boolean exists = checkBookingServiceIdExistsBookingDetailService(bookingDetailServices, bookingService.getId());

        if (exists) {
            throw new ResourceExistsException("Booking-Detail-Service is exits in Booking-Detail");
        }
        BookingDetailService bookingDetailService = new BookingDetailService();
        bookingDetailService.setBookingDetail(bookingDetail);
        bookingDetailService.setBookingService(bookingService);
        bookingDetailService.setBookingServiceType(bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceType());
        bookingDetailService.setVat(new BigDecimal(vatBookingDetailService));
        bookingDetailService.setPrice(bookingService.getPrice());

        BigDecimal money = bookingService.getPrice().multiply(new BigDecimal(bookingReqUpdateBookingServiceCreUpdateDTO.getNumberCarOrPerson()));

        bookingDetailService.setTotal(appUtils.calculateVAT(money, vatBookingDetailService));

        if (bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceType().equals(EBookingServiceType.SCAR)) {
            bookingDetailService.setNumberCar(bookingReqUpdateBookingServiceCreUpdateDTO.getNumberCarOrPerson());
        }else{
            bookingDetailService.setNumberPerson(bookingReqUpdateBookingServiceCreUpdateDTO.getNumberCarOrPerson());
            bookingDetailService.setDateChooseService(bookingReqUpdateBookingServiceCreUpdateDTO.getDateChooseService());
        }

        bookingDetailServiceRepository.save(bookingDetailService);

        bookingDetailServices = bookingDetailServiceRepository
                .findBookingDetailServiceByBookingDetail_Id(bookingDetail.getId());
        BigDecimal total = new BigDecimal(0);
        for (BookingDetailService bds : bookingDetailServices) {
            total = total.add(bds.getTotal());
        }

        BigDecimal moneyBookingDetail = bookingDetail.getTotalAmount();
        BigDecimal moneyVatBookingDetail = appUtils.calculateVAT(moneyBookingDetail, vatBookingDetail);


        bookingDetail.setTotal(moneyVatBookingDetail.add(total));
        bookingDetailRepository.save(bookingDetail);

        Booking booking = bookingDetail.getBooking();
        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(booking.getId());
        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingDetail.getBooking().getId());
        List<BookingDetailResDTO> bookingDetailResDTOS = bookingDetails.stream().map(BDT -> BDT.toBookingDetailResDTO()).collect(Collectors.toList());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);

        return bookingResDTO;
    }


    @Override
    public BookingResDTO editBookingReqUpdate_BookingServiceEditDTO(BookingReqUpdate_BookingServiceCreUpdateDTO bookingReqUpdateBookingServiceCreUpdateDTO) {
        BookingDetail bookingDetail = bookingDetailRepository.findById(bookingReqUpdateBookingServiceCreUpdateDTO.getBookingDetailId()).get();
        BookingService bookingService = bookingServiceRepository.findById(bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceId()).get();
        List<BookingDetailService> bookingDetailServices = bookingDetailServiceRepository.findBookingDetailServiceByBookingDetail_Id(bookingDetail.getId());


        boolean checkExists = checkBookingServiceIdExistsBookingDetailService(bookingDetailServices,bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceId() );

        if (checkExists) {
            BookingDetailService bookingDetailService = bookingDetailServiceRepository.
                    findBookingDetailServiceByBookingDetail_IdAndBookingService_Id(bookingReqUpdateBookingServiceCreUpdateDTO.getBookingDetailId(), bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceId());

            bookingDetailService.setBookingDetail(bookingDetail);
            bookingDetailService.setBookingService(bookingService);
            bookingDetailService.setBookingServiceType(bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceType());
            bookingDetailService.setVat(new BigDecimal(vatBookingDetailService));
            bookingDetailService.setPrice(bookingService.getPrice());

            BigDecimal money = bookingService.getPrice().multiply(new BigDecimal(bookingReqUpdateBookingServiceCreUpdateDTO.getNumberCarOrPerson()));

            bookingDetailService.setTotal(appUtils.calculateVAT(money, vatBookingDetailService));
            if (bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceType().equals(EBookingServiceType.SCAR)) {
                bookingDetailService.setNumberCar(bookingReqUpdateBookingServiceCreUpdateDTO.getNumberCarOrPerson());
            } else {
                bookingDetailService.setNumberPerson(bookingReqUpdateBookingServiceCreUpdateDTO.getNumberCarOrPerson());
                bookingDetailService.setDateChooseService(bookingReqUpdateBookingServiceCreUpdateDTO.getDateChooseService());
            }

            bookingDetailServiceRepository.save(bookingDetailService);
        }


        bookingDetailServices = bookingDetailServiceRepository
                .findBookingDetailServiceByBookingDetail_Id(bookingDetail.getId());
        BigDecimal total = new BigDecimal(0);
        for (BookingDetailService bds : bookingDetailServices) {
            total = total.add(bds.getTotal());
        }

        BigDecimal moneyBookingDetail = bookingDetail.getTotalAmount();
        BigDecimal moneyVatBookingDetail = appUtils.calculateVAT(moneyBookingDetail, vatBookingDetail);


        bookingDetail.setTotal(moneyVatBookingDetail.add(total));
        bookingDetailRepository.save(bookingDetail);

        Booking booking = bookingDetail.getBooking();
        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(booking.getId());
        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingDetail.getBooking().getId());
        List<BookingDetailResDTO> bookingDetailResDTOS = bookingDetails.stream().map(BDT -> BDT.toBookingDetailResDTO()).collect(Collectors.toList());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);

        return bookingResDTO;
    }

    @Override
    public BookingResDTO saveBookingReqUpdate_RoomAddDTO(BookingReqUpdate_RoomAddDTO bookingReqUpdateRoomAddDTO) {
        Booking booking = bookingRepository.findById(bookingReqUpdateRoomAddDTO.getBookingId()).get();

        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());
        boolean isExists = checkRoomIdExistsBookingDetails(bookingDetails, bookingReqUpdateRoomAddDTO.getBookingDetail().getRoomId());
        if (!isExists) {
            // nếu roomId chưa tồn tại trong danh sách bookingId thì cho add vào
            BookingDetail bookingDetail = new BookingDetail();
            bookingDetail.setCheckIn(bookingReqUpdateRoomAddDTO.getBookingDetail().getCheckIn());
            bookingDetail.setCheckOut(bookingReqUpdateRoomAddDTO.getBookingDetail().getCheckOut());

            Room room = roomRepository.findById(bookingReqUpdateRoomAddDTO.getBookingDetail().getRoomId()).get();
            bookingDetail.setRoom(room);
            bookingDetail.setBooking(booking);
            bookingDetail.setNumberAdult(bookingReqUpdateRoomAddDTO.getBookingDetail().getNumberAdult());
            bookingDetail.setNumberChildren(bookingReqUpdateRoomAddDTO.getBookingDetail().getNumberChildren());
            bookingDetail.setDiscountCode(bookingReqUpdateRoomAddDTO.getBookingDetail().getDiscountCode());
            bookingDetail.setTotalAmount(room.getPricePerNight());
            bookingDetail.setVat(new BigDecimal(vatBookingDetail));
            bookingDetail.setPrice(room.getPricePerNight());
            bookingDetail.setTotal(room.getPricePerNight().add(room.getPricePerNight().multiply(new BigDecimal(vatBookingDetail)).divide(new BigDecimal(100))));

            bookingDetailRepository.save(bookingDetail);

        }else{
            throw new ResourceExistsException("Room is exists in Booking");
        }
        // findBookingDetails again
        bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());

        //
        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(booking.getId());
        List<BookingDetailResDTO> bookingDetailResDTOS = bookingDetails.stream()
                .map(bdt -> bdt.toBookingDetailResDTO())
                .collect(Collectors.toList());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);

        return bookingResDTO;
    }


    @Override
    public BookingResDTO saveBookingReqUpdate_RoomEditDTO(BookingReqUpdate_RoomAddDTO bookingReqUpdateRoomAddDTO) {
        Booking booking = bookingRepository.findById(bookingReqUpdateRoomAddDTO.getBookingId()).get();

        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());

        // Tìm kiếm và cập nhật thông tin BookingDetail
        for (BookingDetail bookingDetail : bookingDetails) {
            if (!bookingDetail.getRoom().getId().equals(bookingReqUpdateRoomAddDTO.getBookingDetail().getRoomId())) {
                // Lấy phòng mới từ ID
                Room newRoom = roomRepository.findById(bookingReqUpdateRoomAddDTO.getBookingDetail().getRoomId()).get();

                // Cập nhật thông tin cho BookingDetail
                bookingDetail.setRoom(newRoom);
                bookingDetail.setCheckIn(bookingReqUpdateRoomAddDTO.getBookingDetail().getCheckIn());
                bookingDetail.setCheckOut(bookingReqUpdateRoomAddDTO.getBookingDetail().getCheckOut());
                bookingDetail.setNumberAdult(bookingReqUpdateRoomAddDTO.getBookingDetail().getNumberAdult());
                bookingDetail.setNumberChildren(bookingReqUpdateRoomAddDTO.getBookingDetail().getNumberChildren());
                bookingDetail.setDiscountCode(bookingReqUpdateRoomAddDTO.getBookingDetail().getDiscountCode());
                bookingDetail.setTotalAmount(bookingReqUpdateRoomAddDTO.getBookingDetail().getTotalAmount());
                bookingDetail.setPrice(bookingReqUpdateRoomAddDTO.getBookingDetail().getPrice());
                bookingDetail.setTotal(bookingReqUpdateRoomAddDTO.getBookingDetail().getTotal());

                // Lưu cập nhật
                bookingDetailRepository.save(bookingDetail);
            }
        }

        // Lấy lại danh sách BookingDetail sau khi cập nhật
        bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());

        // Tạo đối tượng BookingResDTO mới
        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(booking.getId());
        List<BookingDetailResDTO> bookingDetailResDTOS = bookingDetails.stream()
                .map(bdt -> bdt.toBookingDetailResDTO())
                .collect(Collectors.toList());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);

        return bookingResDTO;
    }

    @Override
    public BookingResDTO saveBookingReqUpdate_RoomDeleteDTO(Long bookingId, Long roomId) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingId);


        if (optionalBooking.isPresent()) {
            Booking booking = optionalBooking.get();
            List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingId);

            // Tìm kiếm và xóa chi tiết đặt phòng có roomId tương ứng
            List<BookingDetail> updatedBookingDetails = new ArrayList<>();
            for (BookingDetail bookingDetail : bookingDetails) {
                if (!bookingDetail.getId().equals(roomId)) {
                    updatedBookingDetails.add(bookingDetail);
                } else {
                    // Xóa chi tiết đặt phòng có roomId tương ứng
                    bookingDetailRepository.delete(bookingDetail);
                }
            }

            // Cập nhật danh sách chi tiết đặt phòng mới
            booking.setBookingDetails(updatedBookingDetails);
            bookingRepository.save(booking);

            // Tạo đối tượng BookingResDTO mới
            BookingResDTO bookingResDTO = new BookingResDTO();
            bookingResDTO.setBookingId(bookingId);
            List<BookingDetailResDTO> bookingDetailResDTOS = updatedBookingDetails.stream()
                    .map(BookingDetail::toBookingDetailResDTO)
                    .collect(Collectors.toList());
            bookingResDTO.setBookingDetails(bookingDetailResDTOS);
            return bookingResDTO;
        } else {
            throw new IllegalArgumentException("Booking with id " + bookingId + " not found.");
        }
    }

    @Override
    public BookingResDTO updateBooking_AddCustomer(BookingReqUpdate_CustomerDTO bookingReqUpdateCustomerDTO) {
        Optional<Booking> optionalBooking = bookingRepository.findById(bookingReqUpdateCustomerDTO.getBookingId());


        CardPayment cardPayment = cardPaymentService.findCardPaymentByEmailAndPhone(bookingReqUpdateCustomerDTO.getCardType(), bookingReqUpdateCustomerDTO.getPhone());
        if (cardPayment == null) {
            cardPayment = new CardPayment();
            cardPayment.setCardNumber(bookingReqUpdateCustomerDTO.getCardNumber());
            cardPayment.setCardType(bookingReqUpdateCustomerDTO.getCardType());
            cardPayment.setCvv(bookingReqUpdateCustomerDTO.getCvv());
            cardPayment.setExpirationDate(bookingReqUpdateCustomerDTO.getExpirationDate());
            cardPayment.setNameCard(bookingReqUpdateCustomerDTO.getNameCard());
            cardPaymentService.save(cardPayment);
        }

        User user = userService.findUserByEmailAndPhone(bookingReqUpdateCustomerDTO.getEmail(), bookingReqUpdateCustomerDTO.getPhone());
        if (user == null) {
            user = new User();
            user.setAddress(bookingReqUpdateCustomerDTO.getAddress());
            user.setCreateAt(LocalDate.now());
            user.setDeleted(false);
            user.setERole(ERole.CUSTOMER);
            user.setPhone(bookingReqUpdateCustomerDTO.getPhone());
            user.setEmail(bookingReqUpdateCustomerDTO.getEmail());
            user.setELockStatus(ELockStatus.UNLOCK);
            user.setCardPayment(cardPayment);

            user.setCountry(bookingReqUpdateCustomerDTO.getCountry());
            user.setEPrefix(bookingReqUpdateCustomerDTO.getEPrefix());


            userService.save(user);
        }


        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingReqUpdateCustomerDTO.getBookingId());
        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(bookingReqUpdateCustomerDTO.getBookingId());
        List<BookingDetailResDTO> bookingDetailResDTOS = bookingDetails.stream()
                .map(BookingDetail::toBookingDetailResDTO)
                .collect(Collectors.toList());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);

        bookingResDTO.setCustomerInfo(user.toCustomerInfoResDTO());
        return null;
    }

    @Override
    public BookingResDTO updateBooking_UpdateBookingDetail_UpdateRoomReal(Long bookingDetailId, Long roomRealId) {
        BookingDetail bookingDetail = bookingDetailRepository.findById(bookingDetailId).get();
        RoomReal roomReal = roomRealRepository.findById(roomRealId).get();

        bookingDetail.setRoomReal(roomReal);
        bookingDetailRepository.save(bookingDetail);


        return findBookingResDTOById(bookingDetail.getBooking().getId());
    }


    private boolean checkRoomIdExistsBookingDetails(List<BookingDetail> bookingDetails, Long roomId) {
        for (BookingDetail bdt : bookingDetails) {
            if (bdt.getRoom().getId().equals(roomId)) {
                return true;
            }
        }
        return false;
    }
    private boolean checkBookingServiceIdExistsBookingDetailService(List<BookingDetailService> bookingDetailServices, Long bookingServiceId) {
        for (BookingDetailService bdts : bookingDetailServices) {
            if (bdts.getBookingService().getId().equals(bookingServiceId)) {
                return true;
            }
        }
        return false;
    }


}
