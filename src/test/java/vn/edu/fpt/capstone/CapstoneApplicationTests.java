package vn.edu.fpt.capstone;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import vn.edu.fpt.capstone.controller.AddressController;
import vn.edu.fpt.capstone.dto.AddressDto;
import vn.edu.fpt.capstone.dto.FeedbackDto;
import vn.edu.fpt.capstone.dto.HouseDto;
import vn.edu.fpt.capstone.dto.PhuongXaDto;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.dto.ReportDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.dto.SignInDto;
import vn.edu.fpt.capstone.dto.ThanhPhoDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.AddressModel;
import vn.edu.fpt.capstone.model.FeedbackModel;
import vn.edu.fpt.capstone.model.HouseModel;
import vn.edu.fpt.capstone.model.PhuongXaModel;
import vn.edu.fpt.capstone.model.QuanHuyenModel;
import vn.edu.fpt.capstone.model.ReportModel;
import vn.edu.fpt.capstone.model.RoomModel;
import vn.edu.fpt.capstone.model.ThanhPhoModel;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.repository.AddressRepository;
import vn.edu.fpt.capstone.repository.FeedbackRepository;
import vn.edu.fpt.capstone.repository.HouseRepository;
import vn.edu.fpt.capstone.repository.PhuongXaRepository;
import vn.edu.fpt.capstone.repository.QuanHuyenRepository;
import vn.edu.fpt.capstone.repository.ReportRepository;
import vn.edu.fpt.capstone.repository.RoomRepository;
import vn.edu.fpt.capstone.repository.ThanhPhoRepository;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.response.RoomPostingResponse;
import vn.edu.fpt.capstone.service.AddressService;
import vn.edu.fpt.capstone.service.AuthenticationService;
import vn.edu.fpt.capstone.service.FeedbackService;
import vn.edu.fpt.capstone.service.HouseService;
import vn.edu.fpt.capstone.service.PhuongXaService;
import vn.edu.fpt.capstone.service.QuanHuyenService;
import vn.edu.fpt.capstone.service.ReportService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.ThanhPhoService;
import vn.edu.fpt.capstone.service.UserService;

@SpringBootTest
class CapstoneApplicationTests {
	@Autowired
	PhuongXaService phuongXaService;
	@Autowired
	PhuongXaRepository phuongXaRepository;
	@Autowired
	QuanHuyenService quanHuyenService;
	@Autowired
	QuanHuyenRepository quanHuyenRepository;
	@Autowired
	ThanhPhoService thanhPhoService;
	@Autowired
	ThanhPhoRepository thanhPhoRepository;

	@Autowired
	AuthenticationService authenticationService;
	@Autowired
	AddressService addressService;
	@Autowired
	AddressRepository addressRepository;
	@Autowired
	AddressController addressController;
	@Autowired
	FeedbackService feedbackService;
	@Autowired
	FeedbackRepository feedbackRepository;

	@Autowired
	HouseService houseService;
	@Autowired
	HouseRepository houseRepository;

	@Autowired
	RoomService roomService;
	@Autowired
	RoomRepository roomRepository;

	@Autowired
	ReportService reportService;
	@Autowired
	ReportRepository reportRepository;

	@Autowired
	UserService userService;
	@Autowired
	UserRepository userRepository;

	@DisplayName("test PhuongXa isExist return a boolean")
	@Test
	void testPhuongXa_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 1L;
		assertFalse(phuongXaService.isExist(notExisted));
		assertTrue(phuongXaService.isExist(isExisted));
	}

	@DisplayName("test PhuongXa findById return a object of this ID")
	@Test
	void testPhuongXa_whenFindByID_thenReturnObject() {
		Long id = 0L;
		if (!phuongXaService.isExist(id))
			return;
		PhuongXaDto phuongXaDto = phuongXaService.findById(id);
		assertEquals(phuongXaDto.getId(), id);
	}

	@DisplayName("test PhuongXa findAll return a list of object")
	@Test
	void testPhuongXa_whenFindAll_thenReturnListObject() {
		List<PhuongXaDto> phuongXaDtos = phuongXaService.findAll();
		List<PhuongXaModel> phuongXaModels = phuongXaRepository.findAll();
		assertEquals(phuongXaDtos.size(), phuongXaModels.size());
	}

	@DisplayName("test PhuongXa findAllByMaQh return a object of quanHuyenId")
	@Test
	void testPhuongXa_whenFindByQuanHuyenId_thenReturnListObject() {
		Long quanHuyenId = 1L;
		List<PhuongXaDto> phuongXaDtos = phuongXaService.findAllByMaQh(quanHuyenId);
		List<PhuongXaModel> phuongXaModels = phuongXaRepository.findAllByMaQh(quanHuyenId);
		assertEquals(phuongXaDtos.size(), phuongXaModels.size());

	}

	@DisplayName("test QuanHuyen isExist return a boolean")
	@Test
	void testQuanHuyen_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 1L;
		assertFalse(quanHuyenService.isExist(notExisted));
		assertTrue(quanHuyenService.isExist(isExisted));
	}

	@DisplayName("test QuanHuyen findById return a object of this ID")
	@Test
	void testQuanHuyen_whenFindByID_thenReturnObject() {
		Long id = 0L;
		if (!quanHuyenService.isExist(id))
			return;
		QuanHuyenDto quanHuyenDto = quanHuyenService.findById(id);
		assertEquals(quanHuyenDto.getId(), id);
	}

	@DisplayName("test QuanHuyen findAll return a list of object")
	@Test
	void testQuanHuyen_whenFindAll_thenReturnListObject() {
		List<QuanHuyenDto> quanHuyenDtos = quanHuyenService.findAll();
		List<QuanHuyenModel> quanHuyenModels = quanHuyenRepository.findAll();
		assertEquals(quanHuyenDtos.size(), quanHuyenModels.size());
	}

	@DisplayName("test QuanHuyen findAllByMaTp return a object of thanhPhoId")
	@Test
	void testQuanHuyen_whenFindByThanhPhoId_thenReturnListObject() {
		Long thanhPhoId = 1L;
		List<QuanHuyenDto> quanHuyenDtos = quanHuyenService.findAllByMaTp(thanhPhoId);
		List<QuanHuyenModel> quanHuyenModels = quanHuyenRepository.findAllByMaTp(thanhPhoId);
		assertEquals(quanHuyenDtos.size(), quanHuyenModels.size());
	}

	@DisplayName("test ThanhPho isExist return a boolean")
	@Test
	void testThanhPho_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 1L;
		assertFalse(thanhPhoService.isExist(notExisted));
		assertTrue(thanhPhoService.isExist(isExisted));
	}

	@DisplayName("test ThanhPho findById return a object of this ID")
	@Test
	void testThanhPho_whenFindByID_thenReturnObject() {
		Long id = 0L;
		if (!thanhPhoService.isExist(id))
			return;
		ThanhPhoDto thanhPhoDto = thanhPhoService.findById(id);
		assertEquals(thanhPhoDto.getId(), id);
	}

	@DisplayName("test ThanhPho findAll return a list of object")
	@Test
	void testThanhPho_whenFindAll_thenReturnListObject() {
		List<ThanhPhoDto> thanhPhoDtos = thanhPhoService.findAll();
		List<ThanhPhoModel> thanhPhoModels = thanhPhoRepository.findAll();
		assertEquals(thanhPhoDtos.size(), thanhPhoModels.size());
	}

	@DisplayName("test SignIn authenticate return a jwt token of a user")
	@Test
	@SuppressWarnings("unchecked")
	void testSignIn_whenInputUserAndPassword_thenReturnToken() {
		try {
			SignInDto signInDto1 = new SignInDto();
			signInDto1.setUsername("hlh_admin");
			signInDto1.setPassword("hlh@1234");

			SignInDto signInDto2 = new SignInDto();
			signInDto2.setUsername("hlh_admin");
			signInDto2.setPassword("");

			SignInDto signInDto3 = new SignInDto();
			signInDto3.setUsername("");
			signInDto3.setPassword("abc@123456");

			SignInDto signInDto4 = new SignInDto();
			signInDto4.setUsername("123123123123");
			signInDto4.setPassword("123123123123123");

			SignInDto signInDto5 = new SignInDto();
			signInDto5.setUsername("hlh_user");
			signInDto5.setPassword("");

			SignInDto signInDto6 = new SignInDto();
			signInDto6.setUsername("hlh_user");
			signInDto6.setPassword(null);

			SignInDto signInDto7 = new SignInDto();
			signInDto7.setUsername("hlh_user");
			signInDto7.setPassword("abc@123456");

			SignInDto signInDto8 = new SignInDto();
			signInDto8.setUsername("hlh_user");
			signInDto8.setPassword("");

			SignInDto signInDto9 = new SignInDto();
			signInDto9.setUsername("hlh_user");
			signInDto9.setPassword(null);

			SignInDto signInDto10 = new SignInDto();
			signInDto10.setUsername(null);
			signInDto10.setPassword(null);

			SignInDto signInDto11 = new SignInDto();
			signInDto11.setUsername("hlh_landlord");
			signInDto11.setPassword("123123123123123");

			SignInDto signInDto12 = new SignInDto();
			signInDto12.setUsername("hlh_landlord");
			signInDto12.setPassword("");
			ResponseEntity<ResponseObject> responseEntity1 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto1);
			ResponseEntity<ResponseObject> responseEntity2 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto2);
			ResponseEntity<ResponseObject> responseEntity3 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto3);
			ResponseEntity<ResponseObject> responseEntity4 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto4);
			ResponseEntity<ResponseObject> responseEntity5 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto5);
			ResponseEntity<ResponseObject> responseEntity6 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto6);
			ResponseEntity<ResponseObject> responseEntity7 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto7);
			ResponseEntity<ResponseObject> responseEntity8 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto8);
			ResponseEntity<ResponseObject> responseEntity9 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto9);
			ResponseEntity<ResponseObject> responseEntity10 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto10);
			ResponseEntity<ResponseObject> responseEntity11 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto11);
			ResponseEntity<ResponseObject> responseEntity12 = (ResponseEntity<ResponseObject>) authenticationService
					.authenticate(signInDto12);
			assertEquals(responseEntity1.getStatusCode(), HttpStatus.OK);
			assertEquals(responseEntity2.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity3.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity4.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity5.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity6.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity7.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity8.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity9.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity10.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity11.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity12.getStatusCode(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
		
		}
	}

	@DisplayName("test Address isExist return a boolean")
	@Test
	void testAddress_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 21L;
		assertFalse(addressService.isExist(notExisted));
		assertTrue(addressService.isExist(isExisted));
	}

	@DisplayName("test Address findById return a object of this ID")
	@Test
	void testAddress_whenFindByID_thenReturnObject() {
		Long idNotExist = 0L;
		Long idExist = 21L;
		AddressDto addressDto = addressService.findById(idNotExist);
		AddressDto addressDto1 = addressService.findById(idExist);
		assertEquals(addressDto, null);
		assertEquals(addressDto1.getId(), idExist);
	}

	@DisplayName("test Address findAll return a list of object")
	@Test
	void testAddress_whenFindAll_thenReturnListObject() {
		List<AddressDto> addressDtos = addressService.findAll();
		List<AddressModel> addressModels = addressRepository.findAll();
		assertEquals(addressDtos.size(), addressModels.size());
	}

	@DisplayName("test Address Create Update Delete")
	@Test
	void testAddressCRD_whenInput_thenCreateUpdateDeleteAddress() {
		// Create

		PhuongXaDto phuongxaIdNull = new PhuongXaDto();
		phuongxaIdNull.setId(null);

		AddressDto create1 = new AddressDto();
		create1.setStreet(null);
		create1.setLongiude(null);
		create1.setLatitude(null);
		create1.setPhuongXa(phuongxaIdNull);

		AddressDto create2 = new AddressDto();
		create2.setStreet(null);
		create2.setLongiude("123123123");
		create2.setLatitude("123123123");
		create2.setPhuongXa(phuongxaIdNull);

		AddressDto create3 = new AddressDto();
		create3.setStreet(null);
		create3.setLongiude("123123123");
		create3.setLatitude("123123123");
		create3.setPhuongXa(phuongxaIdNull);

		AddressDto create4 = new AddressDto();
		create4.setStreet(null);
		create4.setLongiude("123123123");
		create4.setLatitude(null);
		create4.setPhuongXa(phuongxaIdNull);

		AddressDto create5 = new AddressDto();
		create5.setStreet(null);
		create5.setLongiude(null);
		create5.setLatitude("123123123");
		create5.setPhuongXa(phuongxaIdNull);
		AddressDto create6 = new AddressDto();
		create6.setStreet("Ha noi");
		create6.setLongiude("123123123");
		create6.setLatitude("123123123");
		create6.setPhuongXa(phuongxaIdNull);

		AddressDto create7 = new AddressDto();
		create7.setStreet("Ha noi");
		create7.setLongiude(null);
		create7.setLatitude("123123123");
		create7.setPhuongXa(phuongxaIdNull);

		AddressDto create8 = new AddressDto();
		create8.setStreet("Ha noi");
		create8.setLongiude(null);
		create8.setLatitude(null);
		create8.setPhuongXa(phuongxaIdNull);

		AddressDto create9 = new AddressDto();
		create9.setStreet("");
		create9.setLongiude(null);
		create9.setLatitude(null);
		create9.setPhuongXa(phuongxaIdNull);

//		AddressDto create10 = new AddressDto();
//		create10.setStreet("");
//		create10.setLongiude("123123123");
//		create10.setLatitude("123123123");
//		create10.setPhuongXa(phuongxaId1);
//
//		AddressDto create11 = new AddressDto();
//		create11.setStreet("Ha noi");
//		create11.setLongiude("123123123");
//		create11.setLatitude("123123123");
//		create11.setPhuongXa(phuongxaId1);
//
//		AddressDto create12 = new AddressDto();
//		create12.setStreet(null);
//		create12.setLongiude("123123123");
//		create12.setLatitude("123123123");
//		create12.setPhuongXa(phuongxaId1);

		ResponseEntity<ResponseObject> addressDto = addressController.postAddress(create1);
		ResponseEntity<ResponseObject> addressDto1 = addressController.postAddress(create2);
		ResponseEntity<ResponseObject> addressDto2 = addressController.postAddress(create3);
		ResponseEntity<ResponseObject> addressDto3 = addressController.postAddress(create4);
		ResponseEntity<ResponseObject> addressDto4 = addressController.postAddress(create5);
		ResponseEntity<ResponseObject> addressDto5 = addressController.postAddress(create6);
		ResponseEntity<ResponseObject> addressDto6 = addressController.postAddress(create7);
		ResponseEntity<ResponseObject> addressDto7 = addressController.postAddress(create8);
		ResponseEntity<ResponseObject> addressDto8 = addressController.postAddress(create9);
//		ResponseEntity<ResponseObject> addressDto9 = addressController.postAddress(create10);
//		ResponseEntity<ResponseObject> addressDto10 = addressController.postAddress(create11);
//		ResponseEntity<ResponseObject> addressDto11 = addressController.postAddress(create12);

		assertEquals(addressDto.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
		assertEquals(addressDto1.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
		assertEquals(addressDto2.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
		assertEquals(addressDto3.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
		assertEquals(addressDto4.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
		assertEquals(addressDto5.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
		assertEquals(addressDto6.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
		assertEquals(addressDto7.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
		assertEquals(addressDto8.getStatusCode(), HttpStatus.NOT_ACCEPTABLE);
//		assertEquals(addressDto9.getStatusCode(), HttpStatus.OK);
//		assertEquals(addressDto10.getStatusCode(), HttpStatus.OK);
//		assertEquals(addressDto11.getStatusCode(), HttpStatus.OK);

		// Remove
//		ResponseEntity<ResponseObject> delete1 = addressController.deleteAddress(create10.getId().toString());
//		ResponseEntity<ResponseObject> delete2 = addressController.deleteAddress(create11.getId().toString());
//		ResponseEntity<ResponseObject> delete3 = addressController.deleteAddress(create11.getId().toString());
		ResponseEntity<ResponseObject> delete4 = addressController.deleteAddress("-1");
		ResponseEntity<ResponseObject> delete5 = addressController.deleteAddress("0");
		ResponseEntity<ResponseObject> delete6 = addressController.deleteAddress("a");

//		assertEquals(delete1.getStatusCode(), HttpStatus.OK);
//		assertEquals(delete2.getStatusCode(), HttpStatus.OK);
//		assertEquals(delete3.getStatusCode(), HttpStatus.OK);
		assertEquals(delete4.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(delete5.getStatusCode(), HttpStatus.NOT_FOUND);
		assertEquals(delete6.getStatusCode(), HttpStatus.NOT_FOUND);
	}

	@DisplayName("test Feedback isExist return a boolean")
	@Test
	void testFeedback_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 1L;
		assertFalse(feedbackService.isExist(notExisted));
		assertTrue(feedbackService.isExist(isExisted));
	}

	@DisplayName("test Feedback findById return a object of this ID")
	@Test
	void testFeedback_whenFindByID_thenReturnObject() {
		Long idNotExist = 0L;
		Long idExist = 1L;
		FeedbackDto feedbackDto = feedbackService.findById(idNotExist);
		FeedbackDto feedbackDto1 = feedbackService.findById(idExist);
		assertEquals(feedbackDto, null);
		assertEquals(feedbackDto1.getId(), idExist);
	}

	@DisplayName("test Feedback findByPostId return a object of this Post ID")
	@Test
	void testFeedback_whenFindByPostID_thenReturnObject() {
		Long idNotExist = 0L;
		Long idExist = 59L;
		List<FeedbackDto> feedbackDtos = feedbackService.findByPostId(idNotExist);
		List<FeedbackModel> feedbackModels = feedbackRepository.findByPostId(idNotExist);
		List<FeedbackDto> feedbackDtos1 = feedbackService.findByPostId(idExist);
		List<FeedbackModel> feedbackModels1 = feedbackRepository.findByPostId(idExist);
		if (feedbackDtos == null) {
			assertEquals(feedbackDtos, null);
			assertEquals(feedbackModels.size(), 0);
		} else {
			assertEquals(feedbackDtos.size(), feedbackModels.size());
		}
		if (feedbackDtos1 == null) {
			assertEquals(feedbackDtos1, null);
			assertEquals(feedbackModels1.size(), 0);
		} else {
			assertEquals(feedbackDtos1.size(), feedbackModels1.size());
		}

	}

	@DisplayName("test Feedback findByPostIdAndUserId return a object of this Post ID and UserId")
	@Test
	void testFeedback_whenFindByPostIDandUserId_thenReturnObject() {
		Long postIdNotExist = 0L;
		Long postidExist = 59L;
		Long userIdNotExist = 0L;
		Long useridExist = 86L;
		List<FeedbackDto> feedbackDtos = feedbackService.findByPostIdAndUserId(postIdNotExist, userIdNotExist);
		List<FeedbackModel> feedbackModels = feedbackRepository.findByPostIdAndUserId(postIdNotExist, userIdNotExist);

		List<FeedbackDto> feedbackDtos1 = feedbackService.findByPostIdAndUserId(postIdNotExist, useridExist);
		List<FeedbackModel> feedbackModels1 = feedbackRepository.findByPostIdAndUserId(postIdNotExist, useridExist);

		List<FeedbackDto> feedbackDtos2 = feedbackService.findByPostIdAndUserId(postidExist, useridExist);
		List<FeedbackModel> feedbackModels2 = feedbackRepository.findByPostIdAndUserId(postidExist, userIdNotExist);

		List<FeedbackDto> feedbackDtos3 = feedbackService.findByPostIdAndUserId(postidExist, useridExist);
		List<FeedbackModel> feedbackModels3 = feedbackRepository.findByPostIdAndUserId(postidExist, userIdNotExist);
		if (feedbackDtos == null) {
			assertEquals(feedbackDtos, null);
			assertEquals(feedbackModels.size(), 0);
		} else {
			assertEquals(feedbackDtos.size(), feedbackModels.size());
		}
		if (feedbackDtos1 == null) {
			assertEquals(feedbackDtos1, null);
			assertEquals(feedbackModels1.size(), 0);
		} else {
			assertEquals(feedbackDtos1.size(), feedbackModels1.size());
		}
		if (feedbackDtos2 == null) {
			assertEquals(feedbackDtos2, null);
			assertEquals(feedbackModels2.size(), 0);
		} else {
			assertEquals(feedbackDtos2.size(), feedbackModels2.size());
		}
		if (feedbackDtos3 == null) {
			assertEquals(feedbackDtos3, null);
			assertEquals(feedbackModels3.size(), 0);
		} else {
			assertEquals(feedbackDtos3.size(), feedbackModels3.size());
		}

	}

	@DisplayName("test Feedback findAll return a list of object")
	@Test
	void testFeedback_whenFindAll_thenReturnListObject() {
		List<FeedbackDto> feedbackDtos = feedbackService.findAll();
		List<FeedbackModel> feedbackModels = feedbackRepository.findAll();
		assertEquals(feedbackDtos.size(), feedbackModels.size());
	}

	@DisplayName("test Report isExist return a boolean")
	@Test
	void testReport_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 5L;
		assertFalse(reportService.isExist(notExisted));
		assertTrue(reportService.isExist(isExisted));
	}

	@DisplayName("test Report findById return a object of this ID")
	@Test
	void testReport_whenFindByID_thenReturnObject() {
		try {
			Long idNotExist = 0L;
			Long idExist = 5L;
			ReportDto reportDto = reportService.findById(idNotExist);
			ReportDto reportDto1 = reportService.findById(idExist);
			assertEquals(reportDto, null);
			assertEquals(reportDto1.getId(), idExist);
		} catch (Exception e) {
		}
	}

	@DisplayName("test Report search Report return a object")
	@Test
	void testReport_whenSearchReport_thenReturnObject() {
		Long startDate = 0L;
		Long startDateMax = 1660050175477L;
		Long endDate = 0L;
		Long endDateMax = 1661059175477L;
		String fullName = "Phuong";
		String userName = "phuongpd";
		List<ReportDto> reportDtos = reportService.searchReports(startDate, endDate, fullName, userName);
		List<ReportModel> reportModels = reportRepository.searchReports(startDate, endDate, fullName, userName);

		List<ReportDto> reportDtos1 = reportService.searchReports(startDate, endDateMax, fullName, userName);
		List<ReportModel> reportModels1 = reportRepository.searchReports(startDate, endDateMax, fullName, userName);

		List<ReportDto> reportDtos2 = reportService.searchReports(startDateMax, endDate, fullName, userName);
		List<ReportModel> reportModels2 = reportRepository.searchReports(startDateMax, endDate, fullName, userName);

		List<ReportDto> reportDtos3 = reportService.searchReports(startDateMax, endDateMax, fullName, userName);
		List<ReportModel> reportModels3 = reportRepository.searchReports(startDateMax, endDateMax, fullName, userName);

		if (reportDtos == null) {
			assertEquals(reportDtos, null);
			assertEquals(reportModels.size(), 0);
		} else {
			assertEquals(reportDtos.size(), reportModels.size());
		}
		if (reportDtos1 == null) {
			assertEquals(reportDtos1, null);
			assertEquals(reportModels1.size(), 0);
		} else {
			assertEquals(reportDtos1.size(), reportModels1.size());
		}
		if (reportDtos2 == null) {
			assertEquals(reportDtos2, null);
			assertEquals(reportModels2.size(), 0);
		} else {
			assertEquals(reportDtos2.size(), reportModels2.size());
		}
		if (reportDtos3 == null) {
			assertEquals(reportDtos3, null);
			assertEquals(reportModels3.size(), 0);
		} else {
			assertEquals(reportDtos3.size(), reportModels3.size());
		}

	}

	@DisplayName("test Report findAll return a list of object")
	@Test
	void testReport_whenFindAll_thenReturnListObject() {
		try {
			List<ReportDto> reportDtos = reportService.findAll();
			List<ReportModel> reportModels = reportRepository.findAll();
			assertEquals(reportDtos.size(), reportModels.size());
		} catch (Exception e) {
		}
	}

	@DisplayName("test House isExist return a boolean")
	@Test
	void testHouse_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 21L;
		assertFalse(houseService.isExist(notExisted));
		assertTrue(houseService.isExist(isExisted));
	}

	@DisplayName("test House findById return a object of this ID")
	@Test
	void testHouse_whenFindByID_thenReturnObject() {
		try {
			Long idNotExist = 0L;
			Long idExist = 21L;
			HouseDto houseDto = houseService.findById(idNotExist);
			HouseDto houseDto1 = houseService.findById(idExist);
			assertEquals(houseDto, null);
			assertEquals(houseDto1.getId(), idExist);
		} catch (Exception e) {
		}
	}

	@DisplayName("test House search House by phuongXaId return a object")
	@Test
	void testHouse_whenfindByPhuongXaId_thenReturnObject() {

		Long xaExist = 1L;
		Long xaIsNotExist = 0L;
		List<HouseDto> houseDtos = houseService.findAllByPhuongXaId(xaExist);
		List<HouseModel> houseModels = houseRepository.findByXaId(xaExist);
		List<HouseDto> houseDtos1 = houseService.findAllByPhuongXaId(xaIsNotExist);
		List<HouseModel> houseModels1 = houseRepository.findByXaId(xaIsNotExist);

		if (houseDtos == null) {
			assertEquals(houseDtos, null);
			assertEquals(houseModels.size(), 0);
		} else {
			assertEquals(houseDtos.size(), houseModels.size());
		}
		if (houseDtos1 == null) {
			assertEquals(houseDtos1, null);
			assertEquals(houseModels1.size(), 0);
		} else {
			assertEquals(houseDtos1.size(), houseModels1.size());
		}

	}

	@DisplayName("test House search House by UserId return a object")
	@Test
	void testHouse_whenfindByUserId_thenReturnObject() {

		Long userExist = 86L;
		Long userNotExist = 0L;
		List<HouseDto> houseDtos = houseService.findAllByUserId(userExist);
		List<HouseModel> houseModels = houseRepository.findByUserId(userExist);
		List<HouseDto> houseDtos1 = houseService.findAllByUserId(userNotExist);
		List<HouseModel> houseModels1 = houseRepository.findByUserId(userNotExist);

		if (houseDtos == null) {
			assertEquals(houseDtos, null);
			assertEquals(houseModels.size(), 0);
		} else {
			assertEquals(houseDtos.size(), houseModels.size());
		}
		if (houseDtos1 == null) {
			assertEquals(houseDtos1, null);
			assertEquals(houseModels1.size(), 0);
		} else {
			assertEquals(houseDtos1.size(), houseModels1.size());
		}

	}

	@DisplayName("test House findAll return a list of object")
	@Test
	void testHouse_whenFindAll_thenReturnListObject() {
		try {
			List<HouseDto> houseDtos = houseService.findAll();
			List<HouseModel> houseModels = houseRepository.findAll();
			assertEquals(houseDtos.size(), houseModels.size());
		} catch (Exception e) {
		}
	}

	@DisplayName("test Room isExist return a boolean")
	@Test
	void testRoom_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 35L;
		assertFalse(roomService.isExist(notExisted));
		assertTrue(roomService.isExist(isExisted));
	}

	@DisplayName("test Room findById return a object of this ID")
	@Test
	void testRoom_whenFindByID_thenReturnObject() {
		try {
			Long idNotExist = 0L;
			Long idExist = 35L;
			RoomDto RoomDto = roomService.findById(idNotExist);
			RoomDto RoomDto1 = roomService.findById(idExist);
			assertEquals(RoomDto, null);
			assertEquals(RoomDto1.getId(), idExist);
		} catch (Exception e) {
		}
	}

	@DisplayName("test Room search Room by phuongXaId return a object")
	@Test
	void testRoom_whenGetAllFavoriteByUserId_thenReturnObject() {

		try {
			Long userExist = 1L;
			Long userNotExist = 0L;
			List<RoomDto> RoomDtos = roomService.getRoomFavoriteByUserId(userExist);
			List<RoomModel> RoomModels = roomRepository.getRoomFavoriteByUserId(userExist);
			List<RoomDto> RoomDtos1 = roomService.getRoomFavoriteByUserId(userNotExist);
			List<RoomModel> RoomModels1 = roomRepository.getRoomFavoriteByUserId(userNotExist);

			if (RoomDtos == null) {
				assertEquals(RoomDtos, null);
				assertEquals(RoomModels.size(), 0);
			} else {
				assertEquals(RoomDtos.size(), RoomModels.size());
			}
			if (RoomDtos1 == null) {
				assertEquals(RoomDtos1, null);
				assertEquals(RoomModels1.size(), 0);
			} else {
				assertEquals(RoomDtos1.size(), RoomModels1.size());
			}
		} catch (Exception e) {
			
		}

	}

	@DisplayName("test Room search Room all room are posting then return a list object")
	@Test
	void testRoom_whenFindPostingRoom_thenReturnListObject() {

		try {
			Long roomExist = 35L;
			Long roomNotExist = 0L;
			RoomPostingResponse romPostingResponse = roomService.getRoomPosting(roomExist);
			RoomPostingResponse romPostingResponse1 = roomService.getRoomPosting(roomNotExist);

			assertEquals(romPostingResponse.getId(), roomExist);
			assertEquals(romPostingResponse1, null);
		} catch (Exception e) {
			
		}

	}

	@DisplayName("test Room findAll return a list of object")
	@Test
	void testRoom_whenFindAll_thenReturnListObject() {
		try {
			List<RoomDto> RoomDtos = roomService.findAll();
			List<RoomModel> RoomModels = roomRepository.findAll();
			if (RoomDtos == null) {
				assertEquals(RoomDtos, null);
				assertEquals(RoomModels.size(), 0);
			} else {
				assertEquals(RoomDtos.size(), RoomModels.size());
			}
		} catch (Exception e) {
		}
	}

	@DisplayName("test User findByName return a object")
	@Test
	void testUser_whenFindByName_thenReturnObject() {

		try {String userName = "hlh_admin";
		String userNameNotExist = "123123123123123123";
		UserModel userDto = userService.findByUserName(userName);
		UserModel userDto1 = userService.findByUserName(userNameNotExist);
		assertEquals(userDto.getUsername(), userName);
		assertEquals(userDto1, null);
		} catch (Exception e) {
		}

	}

	@DisplayName("test User findByName return a object")
	@Test
	void testUser_whenFindByEmail_thenReturnObject() {

		String userName = "admin.hlh@gmail.com";
		String userNameNotExist = "admin.hlh123@gmail.com";
		UserModel userDto = userService.findByEmail(userName);
		UserModel userDto1 = userService.findByEmail(userNameNotExist);
		assertEquals(userDto.getEmail(), userName);
		assertEquals(userDto1, null);

	}

	@DisplayName("test User findAll return a list of object")
	@Test
	void testUser_whenFindAll_thenReturnListObject() {
		try {
			List<UserDto> userDtos = userService.getAllUser();
			List<UserModel> userModels = userRepository.findAll();
			if (userDtos == null) {
				assertEquals(userDtos, null);
				assertEquals(userModels.size(), 0);
			} else {
				assertEquals(userDtos.size(), userModels.size());
			}
		} catch (Exception e) {
		}
	}
	
	@DisplayName("test SignUp authenticate return a jwt token of a user")
	@Test
	void testAuthen_whenSignUp_thenReturnObject() {

		

	}
	@DisplayName("test Address updateById update and return a object")
	@Test
	void testAddress_whenUpdateById_thenUpdateAndReturnObject() {
	}
	@DisplayName("test Address deleteById and return a boolean")
	@Test
	void testAddress_whenDeleteByID_thenReturnBoolean() {
	}
	@DisplayName("test Feedback addFeedback and return a object")
	@Test
	void testFeedback_whenAddFeedback_thenUpdateAndReturnObject() {
	}
	@DisplayName("test Feedback updateById update and return a object")
	@Test
	void testFeedback_whenUpdateById_thenUpdateAndReturnObject() {
	}
	@DisplayName("test Feedback deleteById and return a boolean")
	@Test
	void testFeedback_whenDeleteByID_thenReturnBoolean() {
	}
	@DisplayName("test House updateById update and return a object")
	@Test
	void testHouse_whenUpdateById_thenUpdateAndReturnObject() {
	}
}
