package vn.edu.fpt.capstone.controller;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.fasterxml.jackson.databind.ObjectMapper;

import vn.edu.fpt.capstone.constant.Constant;
import vn.edu.fpt.capstone.constant.Message;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.ResponseObject;
import vn.edu.fpt.capstone.dto.SearchDto;
import vn.edu.fpt.capstone.dto.TransactionDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.PostModel;
import vn.edu.fpt.capstone.model.PostTypeModel;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.response.HouseResponse;
import vn.edu.fpt.capstone.response.PageableResponse;
import vn.edu.fpt.capstone.response.PostResponse;
import vn.edu.fpt.capstone.response.PostingResponse;
import vn.edu.fpt.capstone.response.PostingRoomResponse;
import vn.edu.fpt.capstone.service.HouseService;
import vn.edu.fpt.capstone.service.MailService;
import vn.edu.fpt.capstone.service.PostService;
import vn.edu.fpt.capstone.service.PostTypeService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.TransactionService;
import vn.edu.fpt.capstone.service.UserService;
import vn.edu.fpt.capstone.random.RandomString;

import java.time.Duration;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PostController {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostController.class.getName());

	@Autowired
	PostService postService;

	@Autowired
	RoomService roomService;

	@Autowired
	private HouseService houseService;

	@Autowired
	private PostTypeService postTypeService;

	@Autowired
	private UserService userService;

	@Autowired
	ObjectMapper objectMapper;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private TransactionService transactionService;
	
	@Autowired
	private RandomString random;

	@Autowired
	private Constant constant;
	
	@Autowired
	private MailService mailService;

	public static int TIMESTAMP_DAY = 86400000;

	@GetMapping(value = "/post/{id}")
	public ResponseEntity<ResponseObject> getById(@PathVariable String id) {
		ResponseObject responseObject = new ResponseObject();
		try {
			Long lId = Long.valueOf(id);
			if (postService.isExist(lId)) {
				PostDto postDto = postService.findById(lId);
				responseObject.setResults(postDto);
				responseObject.setCode("200");
				responseObject.setMessageCode(Message.OK);
				LOGGER.info("getById: {}", postDto);
				return new ResponseEntity<>(responseObject, HttpStatus.OK);
			} else {
				LOGGER.error("getById: {}", "ID Post is not exist");
				responseObject.setCode("404");
				responseObject.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
			}
		} catch (NumberFormatException e) {
			LOGGER.error("getById: {}", e);
			responseObject.setCode("404");
			responseObject.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(responseObject, HttpStatus.NOT_FOUND);
		} catch (Exception ex) {
			LOGGER.error("getById: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/post-by-token")
	public ResponseEntity<ResponseObject> getAllByToken(@RequestHeader(value = "Authorization") String jwtToken) {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<PostResponse> postDtos = postService.findAllByToken(jwtToken);
			if (postDtos == null || postDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(postDtos);
			}
			LOGGER.info("getAll: {}", postDtos);
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/post")
	public ResponseEntity<ResponseObject> getAll() {
		ResponseObject responseObject = new ResponseObject();
		try {
			List<PostResponse> postDtos = postService.findAll();
			if (postDtos == null || postDtos.isEmpty()) {
				responseObject.setResults(new ArrayList<>());
			} else {
				responseObject.setResults(postDtos);
			}
			LOGGER.info("getAll: {}", postDtos);
			responseObject.setCode("200");
			responseObject.setMessageCode(Message.OK);
			return new ResponseEntity<>(responseObject, HttpStatus.OK);
		} catch (Exception ex) {
			LOGGER.error("getAll: {}", ex);
			responseObject.setCode("500");
			responseObject.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(responseObject, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER')")
	@PostMapping(value = "/post")
	// DungTV29
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	public ResponseEntity<ResponseObject> postCreatePost(@RequestBody PostDto postDto,
			@RequestHeader(value = "Authorization") String jwtToken) {
		try {
			LOGGER.info("postHouseCreate: {}", postDto);
			if (postDto.getHouse().getId() == null || !houseService.isExist(postDto.getHouse().getId())) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: id house null or not exits").messageCode("CREATE_POST_FAILED").build());
			}

			if (postDto.getRoom().getId() == null || !roomService.isExist(postDto.getRoom().getId())) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: id room null or not exits").messageCode("CREATE_POST_FAILED").build());
			}

			if (postDto.getPostType().getId() == null || !postTypeService.isExist(postDto.getPostType().getId())) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: id post type null").messageCode("CREATE_POST_FAILED").build());
			}

			if (postDto.getStartDate() == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: start date null").messageCode("CREATE_POST_FAILED").build());
			}

			if (postDto.getNumberOfDays() <= 0) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Create post: number of day < 0").messageCode("CREATE_POST_FAILED").build());
			}

			// set cost
			int costPerDay = postTypeService.getById(postDto.getPostType().getId()).getPrice();
			int cost = postDto.getNumberOfDays() * costPerDay;

			UserModel userModel = userService.getUserInformationByToken(jwtToken);
			if (userModel == null) {
				throw new Exception();
			}
			if (cost > userModel.getBalance()) {
				LOGGER.error("postPost: {}", "Not enough money for post");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Create post: not enough money!").messageCode("MONEY_NOT_ENOUGH").build());
			} else {
				userModel.setBalance(userModel.getBalance() - cost);
			}

			// Update balance in user
			UserModel user2 = userService.updateUser(modelMapper.map(userModel, UserDto.class));
			if (user2 == null) {
				LOGGER.error("postPost: {}", "update user fail");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
						.message("Create post: update user fail").messageCode("CREATE_POST_FAILED").build());
			}
			
			postDto.setCost(cost);
			// set end date
			long currentDate = postDto.getStartDate().getTime();
			long addDate = Math.abs((postDto.getNumberOfDays() * TIMESTAMP_DAY));
			Long expiredTime = currentDate + addDate;
			postDto.setEndDate(new Date(expiredTime));
			postDto.setStatus(constant.UNCENSORED);
			
			PostTypeModel postType = postTypeService.getById(postDto.getPostType().getId());
			
			postDto.setPostCost(postType.getPrice());
			postDto.setPost_type(postType.getType());
			
			String code = random.generateCode(5);
			while (postService.checkExistCode(code)) {
				code = random.generateCode(5);			
			}
			postDto.setPost_code(code);

			

			// Create post
			PostDto model = postService.createPost(postDto);
			if (model == null) {
				throw new Exception();
			}
			
			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setAction("MINUS");
			transactionDto.setAmount(cost);
			transactionDto.setLastBalance(userModel.getBalance());
			transactionDto.setStatus(constant.SUCCESS);
			transactionDto.setTransferType("POSTING");
			transactionDto.setTransferContent("Đăng tin");
			transactionDto.setUser(modelMapper.map(user2, UserDto.class));
			transactionDto.setNote("Chờ kiểm duyệt bài đăng");
			
			transactionDto.setPostId(model.getId());
			
			TransactionDto model2 = transactionService.createTransaction(transactionDto);
			if (model2 == null) {
				throw new Exception();
			}

			return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
					.message("Create post: successfully").messageCode("CREATE_POST_SUCCESSFULLY").build());
		} catch (Exception e) {
			LOGGER.error("postPost: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Create post: " + e.getMessage()).messageCode("CREATE_POST_FAILED").build());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/post/confirm")
	// DungTV29
	public ResponseEntity<?> confirmPost(@RequestParam(required = true) Long id) {
		try {
			LOGGER.info("confirmPost: {}");
			PostDto postDto = postService.findById(id);
			if (postDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Confirm post: post not exits").messageCode("CONFIRM_POST_FAILED").build());
			}
			postDto.setStatus(constant.CENSORED);

	        long currentDate = (new Date()).getTime();
	        long startDate = postDto.getStartDate().getTime();
	        long addDate = Math.abs((postDto.getNumberOfDays() * TIMESTAMP_DAY));
	        if(currentDate > startDate) {	
				Long expiredTime = currentDate + addDate;
				postDto.setEndDate(new Date(expiredTime));
	        }else {
	        	Long expiredTime = startDate + addDate;
				postDto.setEndDate(new Date(expiredTime));
	        }
	            
	        TransactionDto tr = transactionService.findByPostIdAndTransferTypePosting(id);
	        tr.setNote("Đăng tin thành công");
	        
	        transactionService.updateTransaction(tr);

			PostModel model = postService.confirmPost(postDto);
			if (model != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Confirm post: successfully").messageCode("CONFIRM_POST_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("confirmPost: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Confirm post: " + e.getMessage()).messageCode("CONFIRM_POST_FAILED").build());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/post/reject")
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	// DungTV29
	public ResponseEntity<?> rejectPost(@RequestBody PostDto post) {
		try {
			LOGGER.info("rejectPost: {}");
			PostDto postDto = postService.findById(post.getId());
			if (postDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Reject post: post not exits").messageCode("REJECTED_POST_FAILED").build());
			}
			postDto.setStatus(constant.REJECTED);
			postDto.setNote(post.getNote());
			
			TransactionDto dto = transactionService.findByPostId(postDto.getId());
			if(dto == null) {
				throw new Exception();
			}
			
			UserDto userDto = dto.getUser();
			userDto.setBalance(userDto.getBalance() + dto.getAmount());
			
			UserModel user = userService.updateUser(userDto);
			
			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setAction("PLUS");
			transactionDto.setAmount(dto.getAmount());
			transactionDto.setLastBalance(userDto.getBalance());
			transactionDto.setStatus(constant.SUCCESS);
			transactionDto.setTransferType("REFUND");
			transactionDto.setTransferContent("Hoàn tiền");
			transactionDto.setUser(modelMapper.map(user, UserDto.class));
			transactionDto.setNote(post.getNote());	
			transactionDto.setPostId(postDto.getId());
			transactionDto.setNote("Đăng tin thất bại " + post.getNote());
			
			transactionService.createTransaction(transactionDto);		

			PostModel model = postService.confirmPost(postDto);
			if (model != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Reject post: successfully").messageCode("REJECTED_POST_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("rejectExtend: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Reject post: " + e.getMessage()).messageCode("REJECTED_POST_FAILED").build());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/post/delete")
	// DungTV29
	public ResponseEntity<?> deletedPost(@RequestBody PostDto post) {
		try {
			LOGGER.info("rejectPost: {}");
			PostDto postDto = postService.findById(post.getId());
			if (postDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Delete post: post not exits").messageCode("DELETED_POST_FAILED").build());
			}
			postDto.setStatus(constant.DELETED);
			postDto.setNote(post.getNote());
			postDto.setDeletedDate(new Date());

			PostModel model = postService.confirmPost(postDto);
			if (model != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Delete post: successfully").messageCode("DELETED_POST_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("deleteExtend: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Delete post: " + e.getMessage()).messageCode("DELETED_POST_FAILED").build());
		}
	}
	
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@PutMapping(value = "/post/restore")
	// DungTV29
	public ResponseEntity<?> restorePost(@RequestParam(required = true) Long id) {
		try {
			LOGGER.info("restorePost: {}");
			PostDto postDto = postService.findById(id);
			if (postDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Restore post: post not exits").messageCode("RESTORE_POST_FAILED").build());
			}
			postDto.setStatus(constant.CENSORED);
			
			long deletedDate = postDto.getDeletedDate().getTime();
	        long endDate = postDto.getEndDate().getTime();
	        long expDate = endDate - deletedDate;
	        long currentDate = (new Date()).getTime();
	        
	        if(expDate >= 0) {
	        	long endDateNew = currentDate + expDate;
	        	postDto.setEndDate(new Date(endDateNew));
	        }

			PostModel model = postService.confirmPost(postDto);
			if (model != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Restore post: successfully").messageCode("RESTORE_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("restorePost: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Restore post: " + e.getMessage()).messageCode("RESTORE_POST_FAILED").build());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER')")
	@PutMapping(value = "/post-extend")
	// DungTV29
	public ResponseEntity<?> extendPost(@RequestBody PostDto postDto,
			@RequestHeader(value = "Authorization") String jwtToken) {
		try {
			LOGGER.info("postExtend: {}", postDto);
			if (postDto.getId() == null || !postService.isExist(postDto.getId())) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Extend post: id house null or not exits").messageCode("EXTEND_POST_FAILED").build());
			}

			if (postDto.getNumberOfDays() <= 0) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Extend post: number of day < 0").messageCode("EXTEND_POST_FAILED").build());
			}

			PostDto pd = postService.findById(postDto.getId());

			// set cost
			int costPerDay = postTypeService.getById(pd.getPostType().getId()).getPrice();
			int cost = postDto.getNumberOfDays() * costPerDay;

			UserModel userModel = userService.getUserInformationByToken(jwtToken);
			if (userModel == null) {
				throw new Exception();
			}
			if (cost > userModel.getBalance()) {
				LOGGER.error("postPost: {}", "Not enough money for post");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
						.message("Extend post: not enough money!").messageCode("MONEY_NOT_ENOUGH").build());
			} else {
				userModel.setBalance(userModel.getBalance() - cost);
			}

			// Update balance in user
			UserModel user2 = userService.updateUser(modelMapper.map(userModel, UserDto.class));
			if (user2 == null) {
				LOGGER.error("extendPost: {}", "update user fail");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
						.message("Extend post: update user fail").messageCode("EXTEND_POST_FAILED").build());
			}

			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setAction("MINUS");
			transactionDto.setAmount(cost);
			transactionDto.setLastBalance(userModel.getBalance());
			transactionDto.setStatus("SUCCESS");
			transactionDto.setTransferType("POSTING_EXTEND");
			transactionDto.setTransferContent("Gia hạn");
			transactionDto.setUser(modelMapper.map(user2, UserDto.class));
			transactionDto.setPostId(pd.getId());
			transactionDto.setNote("Gia hạn bài đăng thành công");

			// Create transaction
			TransactionDto transactionDto2 = transactionService.createTransaction(transactionDto);
			if (transactionDto2 == null) {
				LOGGER.error("extendPost: {}", "Transaction fail");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
						.message("Extend post: transaction fail").messageCode("EXTEND_POST_FAILED").build());
			}

			PostModel model = postService.extendPost(postDto);
			if (model != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Extend post: successfully").messageCode("EXTEND_POST_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("postExtend: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Extend post: " + e.getMessage()).messageCode("EXTEND_POST_FAILED").build());
		}
	}

	@PostMapping(value = "/posting")
	public ResponseEntity<?> getAllPosting(@RequestBody SearchDto searchDto) {
		try {
			PageableResponse pageableResponse = postService.findAllPosting(searchDto);
			LOGGER.info("get All posting: {}", pageableResponse.getResults());
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Get posting successfully")
							.messageCode("GET_POSTING_SUCCESSFULLY").results(pageableResponse).build());
		} catch (Exception e) {
			LOGGER.error("getAll posting: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get posting: " + e.getMessage()).messageCode("GET_POSTING_FAILED").build());
		}
	}
	
	@PostMapping(value = "/posting-map")
	public ResponseEntity<?> getAllPostingMap(@RequestBody SearchDto searchDto) {
		try {
			List<PostingResponse> list = postService.findAllPostingMap(searchDto);
			LOGGER.info("get All posting: {}", list);
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Get posting successfully")
							.messageCode("GET_POSTING_SUCCESSFULLY").results(list).build());
		} catch (Exception e) {
			LOGGER.error("getAll posting: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get posting: " + e.getMessage()).messageCode("GET_POSTING_FAILED").build());
		}
	}
	
	@GetMapping(value = "/posting")
	public ResponseEntity<?> getPosting(@RequestParam("id") Long idPost, @RequestParam(value = "idRoom", required=false) Long idRoom) {
		try {
			PostingRoomResponse prr = postService.findPostingById(idPost);
			if(idRoom != null) {
				prr.getPost().setRoom(roomService.findById(idRoom));
			}
			LOGGER.info("get All posting: {}");
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Get posting successfully")
							.messageCode("GET_POSTING_SUCCESSFULLY").results(prr).build());
		} catch (Exception e) {
			LOGGER.error("getAll posting: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get posting: " + e.getMessage()).messageCode("GET_POSTING_FAILED").build());
		}
	}

	@PreAuthorize("hasRole('ROLE_ADMIN') || hasRole('ROLE_LANDLORD') || hasRole('ROLE_USER')")
	@DeleteMapping(value = "/post/{id}")
	public ResponseEntity<ResponseObject> deletePost(@PathVariable String id) {
		ResponseObject response = new ResponseObject();
		try {
			if (id == null || id.isEmpty() || !postService.isExist(Long.valueOf(id))) {
				LOGGER.error("deletePost: {}", "ID Post is not exist");
				response.setCode("404");
				response.setMessageCode(Message.NOT_FOUND);
				return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
			}
			response.setCode("200");
			response.setMessageCode(Message.OK);
			postService.removePost(Long.valueOf(id));
			LOGGER.error("deletePost: {}", "DELETED");
			return new ResponseEntity<>(response, HttpStatus.OK);
		} catch (NumberFormatException ex) {
			LOGGER.error("deletePost: {}", ex);
			response.setCode("404");
			response.setMessageCode(Message.NOT_FOUND);
			return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
		} catch (Exception e) {
			LOGGER.error("deletePost: {}", e);
			response.setCode("500");
			response.setMessageCode(Message.INTERNAL_SERVER_ERROR);
			return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/post/verify")
	@Transactional(rollbackFor = { Exception.class, Throwable.class })
	// DungTV29
	public ResponseEntity<?> verifyPost(@RequestParam(required = true) Long id, 
			@RequestHeader(value = "Authorization") String jwtToken) {
		try {
			LOGGER.info("rejectPost: {}");
			PostDto postDto = postService.findById(id);
			if (postDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Verify post: post not exits").messageCode("VERIFY_POST_FAILED").build());
			}
			postDto.setVerify(constant.WAITING);
			
			UserModel userModel = userService.getUserInformationByToken(jwtToken);
			if (userModel == null) {
				throw new Exception();
			}
			int cost = 100000;
			if (cost > userModel.getBalance()) {
				LOGGER.error("verifyPost: {}", "Not enough money for verify post");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ResponseObject.builder().code("400")
						.message("Verify post: not enough money!").messageCode("MONEY_NOT_ENOUGH").build());
			} else {
				userModel.setBalance(userModel.getBalance() - cost);
			}

			// Update balance in user
			UserModel user2 = userService.updateUser(modelMapper.map(userModel, UserDto.class));
			if (user2 == null) {
				LOGGER.error("verifyPost: {}", "update user fail");
				return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
						.message("Verify post: update user fail").messageCode("VERIFY_POST_FAILED").build());
			}
			
			TransactionDto transactionDto = new TransactionDto();
			transactionDto.setAction("MINUS");
			transactionDto.setAmount(cost);
			transactionDto.setLastBalance(userModel.getBalance());
			transactionDto.setStatus(constant.SUCCESS);
			transactionDto.setTransferType("VERIFY");
			transactionDto.setTransferContent("Xác thực");
			transactionDto.setUser(modelMapper.map(user2, UserDto.class));
			
			transactionDto.setPostId(postDto.getId());
			
			TransactionDto model2 = transactionService.createTransaction(transactionDto);
			if (model2 == null) {
				throw new Exception();
			}
			
			PostModel model = postService.confirmPost(postDto);
			if (model != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Verify post: successfully").messageCode("VERIFY_POST_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("Verify post: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Verify post: " + e.getMessage()).messageCode("VERIFY_POST_FAILED").build());
		}
	}
	
	@PutMapping(value = "/post/verify")
	// DungTV29
	public ResponseEntity<?> verifedPostOk(@RequestParam(required = true) Long id) {
		try {
			LOGGER.info("rejectPost: {}");
			PostDto postDto = postService.findById(id);
			if (postDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Verify post: post not exits").messageCode("VERIFY_POST_FAILED").build());
			}
			postDto.setVerify(constant.VERIFIED);	

			PostModel model = postService.confirmPost(postDto);
			if (model != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Verify post: successfully").messageCode("VERIFY_POST_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("Verify post: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Verify post: " + e.getMessage()).messageCode("VERIFY_POST_FAILED").build());
		}
	}
	
	@PutMapping(value = "/post/verify/fail")
	// DungTV29
	public ResponseEntity<?> verifedPostFail(@RequestBody PostDto dto) {
		try {
			LOGGER.info("rejectPost: {}");
			PostDto postDto = postService.findById(dto.getId());
			if (postDto == null) {
				return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(ResponseObject.builder().code("406")
						.message("Verify post: post not exits").messageCode("VERIFY_POST_FAILED").build());
			}
			postDto.setVerify(constant.REJECTED);
			postDto.setVerifyNote(dto.getVerifyNote());
			
			mailService.sendMailVerifyFail(postDto.getHouse().getUser().getEmail(), postDto.getHouse().getUser().getUsername(),
					postDto.getVerifyNote());

			PostModel model = postService.confirmPost(postDto);
			if (model != null) {
				return ResponseEntity.status(HttpStatus.OK).body(ResponseObject.builder().code("200")
						.message("Verify post: successfully").messageCode("VERIFY_POST_SUCCESSFULLY").build());
			}
			throw new Exception();
		} catch (Exception e) {
			LOGGER.error("Verify post: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Verify post: " + e.getMessage()).messageCode("VERIFY_POST_FAILED").build());
		}
	}
	
	@GetMapping(value = "/posting/top-8")
	public ResponseEntity<?> getPosting() {
		try {
			//PageableResponse pageableResponse = postService.findAllPosting(searchDto);
			List<PostingResponse> list = postService.findTop8Posting();
			LOGGER.info("get All posting: {}", list);
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").message("Get posting successfully")
							.messageCode("GET_POSTING_SUCCESSFULLY").results(list).build());
		} catch (Exception e) {
			LOGGER.error("getAll posting: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get posting: " + e.getMessage()).messageCode("GET_POSTING_FAILED").build());
		}
	}
	

	@GetMapping(value = "/posting/house-name")
	public ResponseEntity<?> getHouseName() {
		try {
			List<HouseResponse> list = postService.getAllHouseNamePosting();
			return ResponseEntity.status(HttpStatus.OK)
					.body(ResponseObject.builder().code("200").messageCode("GET_HOUSE_HISTORY_SUCCESSFULL").results(list).build());
		} catch (Exception e) {
			LOGGER.error("get house name posting: {}", e);
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ResponseObject.builder().code("500")
					.message("Get posting: " + e.getMessage()).messageCode("GET_POSTING_FAILED").build());
		}
	}

}
