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

import vn.edu.fpt.capstone.dto.PhuongXaDto;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.SignInDto;
import vn.edu.fpt.capstone.dto.ThanhPhoDto;
import vn.edu.fpt.capstone.model.PhuongXaModel;
import vn.edu.fpt.capstone.model.QuanHuyenModel;
import vn.edu.fpt.capstone.model.ThanhPhoModel;
import vn.edu.fpt.capstone.repository.PhuongXaRepository;
import vn.edu.fpt.capstone.repository.QuanHuyenRepository;
import vn.edu.fpt.capstone.repository.ThanhPhoRepository;
import vn.edu.fpt.capstone.service.AuthenticationService;
import vn.edu.fpt.capstone.service.PhuongXaService;
import vn.edu.fpt.capstone.service.QuanHuyenService;
import vn.edu.fpt.capstone.service.ThanhPhoService;

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

	@DisplayName("isExist return a object of this ID")
	@Test
	void testPhuongXa_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 1L;
		assertFalse(phuongXaService.isExist(notExisted));
		assertTrue(phuongXaService.isExist(isExisted));
	}

	@DisplayName("findById return a object of this ID")
	@Test
	void testPhuongXa_whenFindByID_thenReturnObject() {
		Long id = 0L;
		if (!phuongXaService.isExist(id))
			return;
		PhuongXaDto phuongXaDto = phuongXaService.findById(id);
		assertEquals(phuongXaDto.getId(), id);
	}

	@DisplayName("findAll return a list of object")
	@Test
	void testPhuongXa_whenFindAll_thenReturnListObject() {
		List<PhuongXaDto> phuongXaDtos = phuongXaService.findAll();
		List<PhuongXaModel> phuongXaModels = phuongXaRepository.findAll();
		assertEquals(phuongXaDtos.size(), phuongXaModels.size());
	}

	@DisplayName("findAllByMaQh return a object of quanHuyenId")
	@Test
	void testPhuongXa_whenFindByQuanHuyenId_thenReturnListObject() {
		Long quanHuyenId = 1L;
		List<PhuongXaDto> phuongXaDtos = phuongXaService.findAllByMaQh(quanHuyenId);
		List<PhuongXaModel> phuongXaModels = phuongXaRepository.findAllByMaQh(quanHuyenId);
		assertEquals(phuongXaDtos.size(), phuongXaModels.size());
		
	}

	@DisplayName("isExist return a object of this ID")
	@Test
	void testQuanHuyen_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 1L;
		assertFalse(quanHuyenService.isExist(notExisted));
		assertTrue(quanHuyenService.isExist(isExisted));
	}

	@DisplayName("findById return a object of this ID")
	@Test
	void testQuanHuyen_whenFindByID_thenReturnObject() {
		Long id = 0L;
		if (!quanHuyenService.isExist(id))
			return;
		QuanHuyenDto quanHuyenDto = quanHuyenService.findById(id);
		assertEquals(quanHuyenDto.getId(), id);
	}

	@DisplayName("findAll return a list of object")
	@Test
	void testQuanHuyen_whenFindAll_thenReturnListObject() {
		List<QuanHuyenDto> quanHuyenDtos = quanHuyenService.findAll();
		List<QuanHuyenModel> quanHuyenModels = quanHuyenRepository.findAll();
		assertEquals(quanHuyenDtos.size(), quanHuyenModels.size());
	}

	@DisplayName("findAllByMaTp return a object of thanhPhoId")
	@Test
	void testQuanHuyen_whenFindByThanhPhoId_thenReturnListObject() {
		Long thanhPhoId = 1L;
		List<QuanHuyenDto> quanHuyenDtos = quanHuyenService.findAllByMaTp(thanhPhoId);
		List<QuanHuyenModel> quanHuyenModels = quanHuyenRepository.findAllByMaTp(thanhPhoId);
		assertEquals(quanHuyenDtos.size(), quanHuyenModels.size());
	}

	@DisplayName("isExist return a object of this ID")
	@Test
	void testThanhPho_whenIdIsExist_thenReturnBool() {
		Long notExisted = 0L;
		Long isExisted = 1L;
		assertFalse(thanhPhoService.isExist(notExisted));
		assertTrue(thanhPhoService.isExist(isExisted));
	}

	@DisplayName("findById return a object of this ID")
	@Test
	void testThanhPho_whenFindByID_thenReturnObject() {
		Long id = 0L;
		if (!thanhPhoService.isExist(id))
			return;
		ThanhPhoDto thanhPhoDto = thanhPhoService.findById(id);
		assertEquals(thanhPhoDto.getId(), id);
	}

	@DisplayName("findAll return a list of object")
	@Test
	void testThanhPho_whenFindAll_thenReturnListObject() {
		List<ThanhPhoDto> thanhPhoDtos = thanhPhoService.findAll();
		List<ThanhPhoModel> thanhPhoModels = thanhPhoRepository.findAll();
		assertEquals(thanhPhoDtos.size(), thanhPhoModels.size());
	}
	
	@DisplayName("authenticate return a jwt token of a user")
	@Test
	void testSignIn_whenInputUserAndPassword_thenReturnToken() {
		try {
			SignInDto signInDto1 = new SignInDto();
			signInDto1.setUsername("hlh_admin");
			signInDto1.setPassword("abc@123456");
			
			SignInDto signInDto2 = new SignInDto();
			signInDto2.setUsername("hlh_admin");
			signInDto2.setPassword("");
			
			SignInDto signInDto3 = new SignInDto();
			signInDto3.setUsername("");
			signInDto3.setPassword("abc@123456");
			
			SignInDto signInDto4 = new SignInDto();
			signInDto4.setUsername("123123123123");
			signInDto4.setPassword("123123123123123");
			
			ResponseEntity<ResponseObject> responseEntity1= (ResponseEntity<ResponseObject>) authenticationService.authenticate(signInDto1);
			ResponseEntity<ResponseObject> responseEntity2= (ResponseEntity<ResponseObject>) authenticationService.authenticate(signInDto2);
			ResponseEntity<ResponseObject> responseEntity3= (ResponseEntity<ResponseObject>) authenticationService.authenticate(signInDto3);
			ResponseEntity<ResponseObject> responseEntity4= (ResponseEntity<ResponseObject>) authenticationService.authenticate(signInDto4);

			assertEquals(responseEntity1.getStatusCode(), HttpStatus.OK);
			assertEquals(responseEntity2.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity3.getStatusCode(), HttpStatus.UNAUTHORIZED);
			assertEquals(responseEntity4.getStatusCode(), HttpStatus.UNAUTHORIZED);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
