package com.cg.spblaguna.service.booking;

import com.cg.spblaguna.exception.ResourceExistsException;
import com.cg.spblaguna.exception.ResourceNotFoundException;
import com.cg.spblaguna.model.*;
import com.cg.spblaguna.model.dto.req.*;
import com.cg.spblaguna.model.dto.res.BookingDetailResDTO;
import com.cg.spblaguna.model.dto.res.BookingResDTO;
import com.cg.spblaguna.model.enumeration.EBookingServiceType;
import com.cg.spblaguna.model.enumeration.ELockStatus;
import com.cg.spblaguna.model.enumeration.ERole;
import com.cg.spblaguna.model.report.RevenueByMonth;
import com.cg.spblaguna.model.enumeration.*;

import com.cg.spblaguna.repository.*;
import com.cg.spblaguna.service.cardpayment.ICardPaymentService;
import com.cg.spblaguna.service.user.IUserService;
import com.cg.spblaguna.util.AppUtils;
import com.cg.spblaguna.util.EmailUtil;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
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
@ConfigurationProperties(prefix = "application.vat")
public class BookingServiceImpl implements IBookingService {

    @Value("13.4")
    private Float vatBookingDetail;
    @Value("13.4")
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
    private EmailUtil emailUtil;

    @Autowired
    private ICardPaymentService cardPaymentService;

    @Autowired
    private IPaymentRepository paymentRepository;

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


        if (bookingReqCreDTO.getBookingDetail().getChildrenAges() == null) {
            bookingDetail.setChildrenAges("[]");
        } else {
            bookingDetail.setChildrenAges(bookingReqCreDTO.getBookingDetail().getChildrenAges());
        }

        bookingDetail.setDiscountCode(bookingReqCreDTO.getBookingDetail().getDiscountCode());
        bookingDetail.setTotalAmount(appUtils.calculateVAT(bookingDetail.getPrice(), vatBookingDetail));
        bookingDetail.setVat(new BigDecimal(vatBookingDetail));

        BigDecimal money = room.getPricePerNight();
        bookingDetail.setTotal(appUtils.calculateVAT(money, vatBookingDetail));

        bookingDetailRepository.save(bookingDetail);

        BigDecimal totalBookingDetail = BigDecimal.ZERO;
        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());
        for (BookingDetail bd : bookingDetails) {
            totalBookingDetail = totalBookingDetail.add(bd.getTotal());
        }

        // Gán giá trị total cho booking
        booking.setTotal(totalBookingDetail);
        bookingRepository.save(booking);

        // Chuyển đổi sang bookingResDTO: lần đầu tạo mới chỉ add DUY NHẤT 1 bookingdetail
        BookingDetailResDTO bookingDetailResDTO = bookingDetail.toBookingDetailResDTO();
        List<BookingDetailResDTO> bookingDetailResDTOS = new ArrayList<>();
        bookingDetailResDTOS.add(bookingDetailResDTO);

        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(booking.getId());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);
        bookingResDTO.setTotal(booking.getTotal());
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

        //-- Tạo bookingDetailService
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
        } else {
            bookingDetailService.setNumberPerson(bookingReqUpdateBookingServiceCreUpdateDTO.getNumberCarOrPerson());
            bookingDetailService.setDateChooseService(bookingReqUpdateBookingServiceCreUpdateDTO.getDateChooseService());
        }
        bookingDetailServiceRepository.save(bookingDetailService);


        //--- Tính total bookingDetail
        bookingDetailServices = bookingDetailServiceRepository
                .findBookingDetailServiceByBookingDetail_Id(bookingDetail.getId());
        BigDecimal total = new BigDecimal(0);
        for (BookingDetailService bds : bookingDetailServices) {
            total = total.add(bds.getTotal());
        }
        BigDecimal moneyBookingDetail = bookingDetail.getTotalAmount();

        BigDecimal moneyVatBookingDetail = appUtils.calculateVAT(moneyBookingDetail, vatBookingDetail);

        bookingDetail.setTotal(moneyVatBookingDetail.add(total));
        bookingDetail.setTotal(moneyBookingDetail.add(total));
        bookingDetailRepository.save(bookingDetail);

        //--- Tính total của booking
        BigDecimal totalBookingDetail = BigDecimal.ZERO;
        List<BookingDetail> bookingDetailss = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingDetail.getBooking().getId());
        for (BookingDetail bd : bookingDetailss) {
            totalBookingDetail = totalBookingDetail.add(bd.getTotal());
        }

        // Gán giá trị total cho booking
        Booking booking = bookingRepository.findById(bookingDetail.getBooking().getId()).get();
        booking.setTotal(totalBookingDetail);
        bookingRepository.save(booking);


        //--- Chuyển booking thành bookingResDTO
        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(booking.getId());
        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingDetail.getBooking().getId());
        List<BookingDetailResDTO> bookingDetailResDTOS = bookingDetails.stream().map(BDT -> BDT.toBookingDetailResDTO()).collect(Collectors.toList());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);
        bookingResDTO.setTotal(booking.getTotal());
        return bookingResDTO;
    }

    @Override
    public BookingResDTO editBookingReqUpdate_BookingServiceEditDTO(BookingReqUpdate_BookingServiceCreUpdateDTO bookingReqUpdateBookingServiceCreUpdateDTO) {
        BookingDetail bookingDetail = bookingDetailRepository.findById(bookingReqUpdateBookingServiceCreUpdateDTO.getBookingDetailId()).get();
        BookingService bookingService = bookingServiceRepository.findById(bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceId()).get();
        List<BookingDetailService> bookingDetailServices = bookingDetailServiceRepository.findBookingDetailServiceByBookingDetail_Id(bookingDetail.getId());

        boolean checkExists = checkBookingServiceIdExistsBookingDetailService(bookingDetailServices, bookingReqUpdateBookingServiceCreUpdateDTO.getBookingServiceId());

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


        bookingDetail.setTotal(moneyBookingDetail.add(total));
        bookingDetailRepository.save(bookingDetail);

        //--- Tính total của booking
        BigDecimal totalBookingDetail = BigDecimal.ZERO;
        List<BookingDetail> bookingDetailss = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingDetail.getBooking().getId());
        for (BookingDetail bd : bookingDetailss) {
            totalBookingDetail = totalBookingDetail.add(bd.getTotal());
        }

        // Gán giá trị total cho booking
        Booking booking = bookingRepository.findById(bookingDetail.getBooking().getId()).get();
        booking.setTotal(totalBookingDetail);
        bookingRepository.save(booking);

        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(booking.getId());
        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingDetail.getBooking().getId());
        List<BookingDetailResDTO> bookingDetailResDTOS = bookingDetails.stream().map(BDT -> BDT.toBookingDetailResDTO()).collect(Collectors.toList());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);
        bookingResDTO.setTotal(booking.getTotal());
        return bookingResDTO;
    }

    @Override
    public BookingResDTO saveBookingReqUpdate_BookingServiceDeleteDTO(Long bookingId ,Long bookingDetailId, Long bookingServiceId) {
        Optional<BookingDetail> optionalBookingDetail = bookingDetailRepository.findById(bookingDetailId);

        if (optionalBookingDetail.isPresent()) {
            BookingDetail bookingDetail = optionalBookingDetail.get();
            BookingDetailService bookingDetailService = bookingDetailServiceRepository.
                    findBookingDetailServiceByBookingDetail_IdAndBookingService_Id(bookingDetailId, bookingServiceId);

            bookingDetailServiceRepository.delete(bookingDetailService);


            //--- Cập nhật total của bookingdetail
            List<BookingDetailService> bookingDetailServices = bookingDetailServiceRepository
                    .findBookingDetailServiceByBookingDetail_Id(bookingDetail.getId());
            BigDecimal total = new BigDecimal(0);
            for (BookingDetailService bds : bookingDetailServices) {
                total = total.add(bds.getTotal());
            }
            BigDecimal moneyBookingDetail = bookingDetail.getTotalAmount();
            bookingDetail.setTotal(moneyBookingDetail.add(total));
            bookingDetailRepository.save(bookingDetail);

            //--- Cập nhật total của booking
            BigDecimal totalBookingDetail = BigDecimal.ZERO;
            List<BookingDetail> bookingDetailss = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingId);
            for (BookingDetail bd : bookingDetailss) {
                totalBookingDetail = totalBookingDetail.add(bd.getTotal());
            }

            // Gán giá trị total cho booking
            Booking booking = bookingRepository.findById(bookingId).get();
            booking.setTotal(totalBookingDetail);
            bookingRepository.save(booking);


            //--- Chuyển booking thành  bookingResDTO
            BookingResDTO bookingResDTO = new BookingResDTO();
            bookingResDTO.setBookingId(booking.getId());
            List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(bookingDetail.getBooking().getId());
            List<BookingDetailResDTO> bookingDetailResDTOS = bookingDetails.stream().map(BDT -> BDT.toBookingDetailResDTO()).collect(Collectors.toList());
            bookingResDTO.setBookingDetails(bookingDetailResDTOS);
            bookingResDTO.setTotal(booking.getTotal());
            return bookingResDTO;

        }else{
            throw new IllegalArgumentException("Booking with id " + bookingDetailId + " not found.");
        }
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

            if (bookingReqUpdateRoomAddDTO.getBookingDetail().getChildrenAges() == null) {
                bookingDetail.setChildrenAges("[]");
            } else {
                bookingDetail.setChildrenAges(bookingReqUpdateRoomAddDTO.getBookingDetail().getChildrenAges());
            }
            bookingDetail.setDiscountCode(bookingReqUpdateRoomAddDTO.getBookingDetail().getDiscountCode());
            bookingDetail.setTotalAmount(room.getPricePerNight());
            bookingDetail.setVat(new BigDecimal(vatBookingDetail));
            bookingDetail.setPrice(room.getPricePerNight());
            bookingDetail.setTotal(room.getPricePerNight().add(room.getPricePerNight().multiply(new BigDecimal(vatBookingDetail)).divide(new BigDecimal(100))));

            bookingDetailRepository.save(bookingDetail);

            BigDecimal totalBookingDetail = BigDecimal.ZERO;
            List<BookingDetail> bookingDetailss = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());
            for (BookingDetail bd : bookingDetailss) {
                totalBookingDetail = totalBookingDetail.add(bd.getTotal());
            }

            // Gán giá trị total cho booking
            booking.setTotal(totalBookingDetail);
            bookingRepository.save(booking);

        } else {
            throw new ResourceExistsException("Room is exists in Booking");
        }
        bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());

        BookingResDTO bookingResDTO = new BookingResDTO();
        bookingResDTO.setBookingId(booking.getId());
        List<BookingDetailResDTO> bookingDetailResDTOS = bookingDetails.stream()
                .map(bdt -> bdt.toBookingDetailResDTO())
                .collect(Collectors.toList());
        bookingResDTO.setBookingDetails(bookingDetailResDTOS);
        bookingResDTO.setTotal(booking.getTotal());
        return bookingResDTO;
    }


    @Override
    public BookingResDTO saveBookingReqUpdate_RoomEditDTO(BookingReqUpdate_RoomAddDTO bookingReqUpdateRoomAddDTO) {
        Booking booking = bookingRepository.findById(bookingReqUpdateRoomAddDTO.getBookingId()).get();

        List<BookingDetail> bookingDetails = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());
        // Tìm kiếm và cập nhật thông tin BookingDetail
        for (BookingDetail bookingDetail : bookingDetails) {
            if (!bookingDetail.getRoom().getId().equals(bookingReqUpdateRoomAddDTO.getBookingDetail().getRoomId())) {
                //-- Xóa list bookingDetailService
                List<BookingDetailService> bookingDetailServices = bookingDetailServiceRepository
                        .findBookingDetailServiceByBookingDetail_Id(bookingDetail.getId());

                bookingDetailServiceRepository.deleteAll(bookingDetailServices);

                // Lấy phòng mới từ ID
                Room newRoom = roomRepository.findById(bookingReqUpdateRoomAddDTO.getBookingDetail().getRoomId()).get();
                // Cập nhật thông tin cho BookingDetail
                bookingDetail.setRoom(newRoom);
                bookingDetail.setCheckIn(bookingReqUpdateRoomAddDTO.getBookingDetail().getCheckIn());
                bookingDetail.setCheckOut(bookingReqUpdateRoomAddDTO.getBookingDetail().getCheckOut());
                bookingDetail.setNumberAdult(bookingReqUpdateRoomAddDTO.getBookingDetail().getNumberAdult());
                bookingDetail.setChildrenAges(bookingReqUpdateRoomAddDTO.getBookingDetail().getChildrenAges());
                bookingDetail.setDiscountCode(bookingReqUpdateRoomAddDTO.getBookingDetail().getDiscountCode());
                bookingDetail.setPrice(newRoom.getPricePerNight());

                bookingDetail.setNumberAdult(bookingReqUpdateRoomAddDTO.getBookingDetail().getNumberAdult());

                if (bookingReqUpdateRoomAddDTO.getBookingDetail().getChildrenAges() == null) {
                    bookingDetail.setChildrenAges("[]");
                } else {
                    bookingDetail.setChildrenAges(bookingReqUpdateRoomAddDTO.getBookingDetail().getChildrenAges());
                }


                bookingDetail.setTotalAmount(appUtils.calculateVAT(bookingDetail.getPrice(), vatBookingDetail));
                bookingDetail.setVat(new BigDecimal(vatBookingDetail));

                bookingDetail.setTotal(bookingDetail.getTotalAmount());

                // Lưu cập nhật
                bookingDetailRepository.save(bookingDetail);

                BigDecimal totalBookingDetail = BigDecimal.ZERO;
                List<BookingDetail> bookingDetailss = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());
                for (BookingDetail bd : bookingDetailss) {
                    totalBookingDetail = totalBookingDetail.add(bd.getTotal());
                }

                // Gán giá trị total cho booking
                booking.setTotal(totalBookingDetail);
                bookingRepository.save(booking);
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
        bookingResDTO.setTotal(booking.getTotal());
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

            BigDecimal totalBookingDetail = BigDecimal.ZERO;
            List<BookingDetail> bookingDetailss = bookingDetailRepository.findBookingDetailsByBooking_Id(booking.getId());
            for (BookingDetail bd : bookingDetailss) {
                totalBookingDetail = totalBookingDetail.add(bd.getTotal());
            }

            // Gán giá trị total cho booking
            booking.setTotal(totalBookingDetail);
            bookingRepository.save(booking);

            // Tạo đối tượng BookingResDTO mới
            BookingResDTO bookingResDTO = new BookingResDTO();
            bookingResDTO.setBookingId(bookingId);
            List<BookingDetailResDTO> bookingDetailResDTOS = updatedBookingDetails.stream()
                    .map(BookingDetail::toBookingDetailResDTO)
                    .collect(Collectors.toList());
            bookingResDTO.setBookingDetails(bookingDetailResDTOS);
            bookingResDTO.setTotal(booking.getTotal());
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
            user.setERole(ERole.ROLE_CUSTOMER);
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
    public void updateBooking_UpdateBookingDetail_UpdateRoomReal(Long bookingDetailId, Long roomRealId) {
        BookingDetail bookingDetail = bookingDetailRepository.findById(bookingDetailId).orElseThrow(() -> new ResourceNotFoundException("Booking Detail not found"));
        RoomReal r = roomRealRepository.findById(roomRealId).get();
        bookingDetail.setRoomReal(r);
        bookingDetail.setCheckInStatus(true);
        bookingDetailRepository.save(bookingDetail);
    }

    @Override
    public void depositBooking(DepositReqDTO depositReqDTO) {
        Long bookingId = depositReqDTO.getBookingId();
        Payment payment = new Payment();
        payment.setBooking(bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found")));
        payment.setMethod(EMethod.TRANSFER);
        payment.setTotal(payment.getTotal());
        payment.setTransferId(payment.getTransferId());
        paymentRepository.save(payment);

        // Cập nhật thông tin trong Booking
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));
        payment.setBooking(booking);
        payment.setMethod(depositReqDTO.getMethod());
        payment.setNote(depositReqDTO.getNote());
        if (depositReqDTO.getBank() != null) {
            payment.setBank(depositReqDTO.getBank());
        }
        BigDecimal total = depositReqDTO.getTotal();
        payment.setTotal(total);
        Long transferId = depositReqDTO.getTransferId();
        payment.setTransferId(transferId);
        payment.setTransferDate(depositReqDTO.getTransferDate());
        paymentRepository.save(payment);

        // Cập nhật thông tin trong Booking
        BigDecimal depositedNumber = depositReqDTO.getDepositedAmount();
        booking.setDepositedNumber(depositedNumber);
        booking.setDepositedStatus(EDepositedStatus.ACCOMPLISHED);
        booking.setBookingStatus(EBookingStatus.DEPOSITED);
        bookingRepository.save(booking);
    }


    @Override
    public void updateBooking_Complete(Long bookingId) {
        Booking booking = bookingRepository.findById(bookingId).get();
        booking.setActive(true);
        bookingRepository.save(booking);

        String emailContent = "" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <title>Title</title>\n" +
                "</head>\n" +
                "<body>\n" +
                "<div>\n" +
                "      <div lang=\"EN-US\" link=\"#0563C1\" vlink=\"#954F72\"><div><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Kính gửi chị Lê Thuỳ Nhiên,</span><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:#1f497d\"><u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;</span><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Cảm ơn chị và gia đình đã lựa chọn Pilgrimage Village resort &amp; spa cho kỳ nghỉ.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Như trao đổi với chị qua Facebook, em gửi <b>xác nhận đặt phòng </b>như bên dưới:<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;<br><b>Tên chương trình&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; : &nbsp;&nbsp;&nbsp;Super deal – Khuyến mãi ngày thường (áp dụng ở từ thứ 2 – thứ 5 hằng tuần)<u></u><u></u></b></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Mã đặt phòng&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: &nbsp; 103022</span></b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Loại phòng/villa&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: &nbsp; 1 Double (1 giường lớn/phòng) - Superior<u></u><u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Ngày ở&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<wbr>&nbsp;&nbsp;: &nbsp;&nbsp;3 – 4/8/2022 (1 đêm)<u></u><u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Số khách&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; </span></b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<b>:&nbsp; &nbsp;2 người lớn<u></u><u></u></b></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Gía phòng&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :&nbsp; &nbsp;1.000.000vnd<u></u><u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;<b><span style=\"background:yellow\">Tổng cộng&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;: &nbsp; 1.000.000vnd</span> <u></u><u></u></b></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">*Bao gồm: <u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Ăn sáng và sử dụng 2 bể bơi ở resort (Bể bơi Slope và bể bơi Peanut) <u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Lớp yoga buổi sáng theo lịch của resort<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><u><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Lưu ý quan trọng:<u></u><u></u></span></u></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><u><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u><span style=\"text-decoration:none\">&nbsp;</span><u></u></span></u></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><u><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Chương trình này không áp dụng thứ 6, thứ 7, CN &amp; giai </span></u></b><b><u><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">đoạn lễ 1 - 3/9/2022.<span style=\"color:black\"><u></u><u></u></span></span></u></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><u><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Nếu sau khi mua voucher, chị muốn đổi ngày ở sang thứ 6, thứ 7 và CN thì voucher này sẽ không được áp dụng và sẽ áp dụng theo giá đang bán của ngày mà chị muốn ở. Mọi hình thức cấn trừ , phụ thu của voucher này đều không được áp dụng.<u></u><u></u></span></u></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">Em gửi </span><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">chị </span><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">thông tin chuyển khoản với chi tiết như bên dưới. </span><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">Chị </span><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">sẽ chịu phí chuyển khoản. </span><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">Chị </span><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">vui lòng chuyển khoản với nội dung “Tên khách + số điện thoại” và gửi ủy nhiệm chi sau khi chuyển khoản thành công để bên em kiểm tra kế toán ạ.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\"><u></u>&nbsp;<u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">Tên tài khoản&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Công ty TNHH Du lịch và Thương mại Á Đông<u></u><u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">Số tài khoản&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;5561.0000.956.937<u></u><u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif\">Tên ngân hàng&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; :&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp; Ngân hàng BIDV – Chi nhánh Phú Xuân<u></u><u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Trong trường hợp </span></b><b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">chị </span></b><b><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">lấy hóa đơn VAT, vui lòng cung cấp thông tin hóa đơn bây giờ cho bên em hoặc chậm nhất vào ngày nhận phòng để bên em xuất hóa đơn vào ngày trả phòng. <u></u><u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><u><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Chính sách phụ thu trẻ em:</span></u></b><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;background:white\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Miễn phí trẻ em dưới 4 tuổi ngủ chung giường cùng bố mẹ&nbsp; bao gồm ăn sáng. <u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;background:white\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Trẻ từ 4 - 9 tuổi: phụ thu 250.000 vnd/ trẻ/ 1 đêm bao gồm ăn sáng (ngủ chung giường với bố mẹ)<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;background:white\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Trẻ thứ 2 từ 4 - 9 tuổi hoặc trẻ từ 10 tuổi trở lên sẽ yêu cầu đặt thêm giường phụ sofa: 500.000 vnd/ giường phụ/ 1 đêm bao gồm ăn sáng.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;background:white\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><b><u><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Điều kiện hủy phòng &amp; thanh toán:<u></u><u></u></span></u></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- 100%&nbsp; tiền phòng được yêu cầu thanh toán trong vòng 24h sau khi xác nhận đặt phòng và không được hủy và không hoàn trả lại. <u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Đối với đặt phòng từ 1 – 5 phòng: sau khi xác nhận ngày ở, quý khách được phép đổi ngày nếu báo trước 5 ngày trước ngày đến và được dời một lần đến trước 31/10/2022.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Đối với đặt phòng từ 6 – 10&nbsp; phòng: sau khi xác nhận ngày ở, quý khách được phép đổi ngày nếu báo trước 7 ngày trước ngày đến và được dời một lần đến trước 31/10/2022.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Đối với đặt phòng từ 11 phòng trở lên: tùy từng trường hợp resort sẽ đưa ra chính sách phù hợp.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">* Trường hợp không đến nhận phòng hoặc hủy trong ngày dù bất cứ lý do gì (kể cả khách hàng là F0 hay F1 của Covid), đặt phòng sẽ mất hiệu lực và không hoàn lại.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">* Việc dời ngày sẽ không áp dụng vào giai đoạn lễ (2 - 3/9/2022) dưới bất kỳ hình thức nào.<u></u><u></u></span></p><p style=\"text-align:justify\"><b><u><span lang=\"VI\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Chính sách khi đến nhận phòng tại resort và sử dụng dịch vụ: </span></u></b><b><u><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u><u></u></span></u></b></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-family:&quot;Arial&quot;,sans-serif;color:black\">- Thời gian nhận phòng/trả phòng: 14:00/11:00. Resort không nhận khách nhận phòng từ 22:00. Resort có quyền từ chối nhận khách từ 22:00 và đặt phòng đã thanh toán sẽ không hoàn lại.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-family:&quot;Arial&quot;,sans-serif;color:black\">- Quý khách vui lòng cung cấp giấy CMND (đối với người lớn) và giấy khai sinh (đối với trẻ em nếu có). Việc này là yêu cầu bắt buộc áp dụng cho tất cả các khách lưu trú theo qui định của pháp luật. <u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-family:&quot;Arial&quot;,sans-serif;color:black\">- Tất cả phòng của resort là phòng không hút thuốc. <u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-family:&quot;Arial&quot;,sans-serif;color:black\">- Resort toàn cây cối và để đảm bảo công tác phòng cháy chữa cháy quý khách vui lòng không nấu nướng trong phòng/villa. Qúy khách có thể đem đồ ăn nhẹ vào và ăn ở trong villa/phòng nhưng không được đem dụng cụ nấu ăn hoặc đem đồ ăn có mùi vào villa/phòng. Resort bên em không đặt dụng cụ ăn ở trong phòng và chỉ phục vụ ăn ở&nbsp; tại nhà hàng hoặc trong phòng (room service) theo menu. Các dịch vụ khác như nấu ăn giúp theo thực phẩm của khách, cung cấp dụng cụ ăn cho khách là không có. Nên nếu quý khách đem đồ ăn vào thì vui lòng đem theo dụng cụ ăn cho đoàn mình.<br>- Nếu trường hợp quý khách hút thuốc hoặc sử dụng đồ ăn có mùi trong phòng thì chúng tôi sẽ đóng phòng để xử lý mùi và sẽ có tính phí 2.000.000 VND.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-family:&quot;Arial&quot;,sans-serif;color:black\">- Chính sách tiếng ồn: Resort là khu nghỉ dưỡng nên quý khách vui lòng không gây tiếng ổn lớn làm ảnh hưởng các khách khác. Resort cấm quý khách đem các loại loa có kích cỡ lớn hoặc các thiết bị gây tiếng ồn lớn ảnh hưởng xung quanh. Trong trường hợp resort phát hiện quý khách vi phạm chính sách tiếng ồn, resort có quyền từ chối việc ở lại của quý khách và không hoàn trả lại các chi phí đã thanh toán trước.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-family:&quot;Arial&quot;,sans-serif;color:black\">-&nbsp;Đối với khách viếng thăm: Chỉ cho phép những khách có đăng ký lưu trú tại Lễ tân theo đúng tên mới được phép vào phòng.Khách viếng thăm không làm thủ tục check-in nhưng phải xuất trình CMND và gửi lại ở lễ tân cho đến khi ra về. Quý khách chỉ được tiếp khách viếng thăm tại khu vực lễ tân hoặc nhà hàng nếu có nhu cầu dùng bữa. Thời gian viếng thăm: trước 22:00. Khách lưu trú sẽ phải hoàn toàn chịu trách nhiệm về hoạt động và hành vi của khách viếng thăm và chịu trách nhiệm về bất kỳ thiệt hại hoặc chi phí nào của khách đi kèm. Nếu khách lưu trú không chấp hành đúng nội quy, bên phiá resort có quyền hủy phòng và số tiền đã thanh toán không được hoàn lại.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Địa chỉ của resort là 130 đường Minh Mạng Huế, nếu quý khách đi xe riêng đến nhận phòng và qua đêm thì bãi đỗ xe chính là 130 đường Minh Mạng, Huế, khi nào bãi đỗ xe chính hết chỗ thì quý khách sẽ về đỗ xe ở 112 đường Minh Mạng Huế.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt;text-align:justify\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">- Resort tuân thủ các quy định khác của chính quyền về an toàn dịch bệnh. Trách nhiệm của chúng tôi sẽ bao gồm cung cấp các dịch vụ như đã thoả thuận với khách trong quá trình ở, nhưng sẽ không bao gồm việc đảm bảo khách không bị mắc covid hoặc không tiếp xúc với ai khác có tiềm năng mắc covid. Chúng tôi sẽ không chịu trách nhiệm bất cứ việc khách phải cách ly, hay các việc khác liên quan đến covid, hay các bệnh khác, trong và sau khi quý khách ở resort.<u></u><u></u></span></p><p style=\"text-align:justify\"><b><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></b></p><p style=\"text-align:justify\"><b><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Khi quý khách xác nhận đặt voucher và thực hiện thanh toán, việc này sẽ đồng nghĩa với quý khách đồng ý với những điều khoản, điều kiện, nội quy của resort được nêu ra trong thư xác nhận này.<u></u><u></u></span></b></p><p style=\"text-align:justify\"><b><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></b></p><p style=\"text-align:justify\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Văn phòng làm việc hằng ngày từ 8:00 – 17:00.<u></u><u></u></span></p><p style=\"text-align:justify\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Trong trường hợp quý khách cần liên hệ ngoài thời gian trên, vui lòng gọi điện trực tiếp cho bộ phận lễ tân qua số điện thoại </span><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">+(84 234) 3 885 461.</span><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;</span><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">&nbsp;</span><span lang=\"ES\" style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Trân trọng,<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Diem Hoang/ Ms<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Reservation Office.<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\"><u></u>&nbsp;<u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><b><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">Pilgrimage Village - boutique resort &amp; spa<u></u><u></u></span></b></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">A: 130 Minh Mang Rd., Hue, Vietnam<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">T: + (84 234) 3 885 461<u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">E: <a href=\"mailto:reservation@pilgrimagevillage.com\" target=\"_blank\"><span style=\"color:black\">reservation@pilgrimagevillage.<wbr>com</span></a><u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">W: <a href=\"http://www.pilgrimagevillage.com\" target=\"_blank\" data-saferedirecturl=\"https://www.google.com/url?q=http://www.pilgrimagevillage.com&amp;source=gmail&amp;ust=1711502434272000&amp;usg=AOvVaw0uoBb64lP6MfatlCiiIfef\"><span style=\"color:black\">www.pilgrimagevillage.com</span></a>&nbsp;&nbsp;&nbsp; <u></u><u></u></span></p><p style=\"margin:0in;margin-bottom:.0001pt\"><span style=\"font-size:12.0pt;font-family:&quot;Arial&quot;,sans-serif;color:black\">------------------------------<wbr>-------------------------<u></u><u></u></span></p><p class=\"MsoNormal\"><u></u>&nbsp;<u></u></p></div></div></div>\n" +
                "</body>\n" +
                "</html>";

        emailUtil.sendEmail(booking.getUser().getEmail(), "Thông tin đặt phòng của bạn", emailContent);
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

    @Override
    public List<RevenueByMonth> showRevenue() {
        return bookingRepository.showRevenue();
    }

    public RevenueReqDTO findRevenueForByTime(LocalDateTime selectFirstDay, LocalDateTime selectLastDay) {
        try {
            RevenueReqDTO revenues = bookingDetailRepository.findRevenueForByTime(selectFirstDay, selectLastDay);
            if (revenues == null) {
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "There is no revenue during this period");
            }
            BigDecimal total = revenues.getTotal();
            if (total == null) {
                revenues.setTotal(BigDecimal.ZERO);
            }
            return revenues;
        } catch (Exception e) {
            e.printStackTrace();
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "Error: Unexpected exception occurred");
        }
    }
}
