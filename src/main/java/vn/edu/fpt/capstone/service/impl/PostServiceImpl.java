package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.FilterRoomDto;
import vn.edu.fpt.capstone.dto.ImageDto;
import vn.edu.fpt.capstone.dto.PostDto;
import vn.edu.fpt.capstone.dto.PostSearchDto;
import vn.edu.fpt.capstone.dto.QuanHuyenDto;
import vn.edu.fpt.capstone.dto.RoomDto;
import vn.edu.fpt.capstone.dto.SearchDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.model.PostModel;
import vn.edu.fpt.capstone.model.RoomModel;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.repository.FeedbackRepository;
import vn.edu.fpt.capstone.repository.PostRepository;
import vn.edu.fpt.capstone.repository.PostTypeRepository;
import vn.edu.fpt.capstone.response.HouseResponse;
import vn.edu.fpt.capstone.response.PageableResponse;
import vn.edu.fpt.capstone.response.PostResponse;
import vn.edu.fpt.capstone.response.PostingResponse;
import vn.edu.fpt.capstone.response.PostingRoomResponse;
import vn.edu.fpt.capstone.service.PostService;
import vn.edu.fpt.capstone.service.QuanHuyenService;
import vn.edu.fpt.capstone.service.RoomService;
import vn.edu.fpt.capstone.service.ThanhPhoService;
import vn.edu.fpt.capstone.service.UserService;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class PostServiceImpl implements PostService {
	private static final Logger LOGGER = LoggerFactory.getLogger(PostServiceImpl.class.getName());

	@Autowired
	private PostRepository postRepository;

	@Autowired
	private FeedbackRepository feedbackRepository;

	@Autowired
	ModelMapper modelMapper;

	@Autowired
	private RoomService roomService;

	@Autowired
	private PostTypeRepository postTypeRepository;

	@Autowired
	private UserService userService;

	@Autowired
	private QuanHuyenService quanHuyenService;

	@Autowired
	private ThanhPhoService thanhPhoService;

	@PersistenceContext
	private EntityManager entityManager;

	public int TIMESTAMP_DAY = 86400000;

	public List<PostDto> convertEntity2Dto(List<PostModel> models) {
		List<PostDto> postDtos = Arrays.asList(modelMapper.map(models, PostDto[].class));
		for (int i = 0; i < models.size(); i++) {
			Long roomId = postDtos.get(i).getRoom().getId();
			RoomDto roomDto = roomService.findById(roomId);
			postDtos.get(i).setRoom(roomDto);
		}
		return postDtos;
	}

	public PostDto convertEntity2Dto(PostModel model) {
		PostDto postDto = modelMapper.map(model, PostDto.class);
		Long roomId = postDto.getRoom().getId();
		RoomDto roomDto = roomService.findById(roomId);
		postDto.setRoom(roomDto);
		return postDto;
	}

	@Override
	public PostDto findById(Long id) {
		PostDto postDto = convertEntity2Dto(postRepository.findById(id).get());
		return postDto;
	}

	@Override
	public List<PostResponse> findAll() {
		List<PostModel> postModels = postRepository.findAllQuery();
		if (postModels == null || postModels.isEmpty()) {
			return null;
		}
		List<PostResponse> postRes = convertEntity2Response(postModels);
		return postRes;
	}

	private List<PostResponse> convertEntity2Response(List<PostModel> postModels) {
		List<PostResponse> postRes = new ArrayList<PostResponse>();
		for (PostModel model : postModels) {
			PostResponse postResponse = new PostResponse();
			postResponse.setId(model.getId());
			postResponse.setPostTypeId(model.getPostType().getId());
			postResponse.setPostType(model.getPostType().getType());
			postResponse.setRoomName(model.getRoom().getName());
			postResponse.setHouseName(model.getHouse().getName());
			postResponse.setStartDate(model.getStartDate());
			postResponse.setEndDate(model.getEndDate());
			postResponse.setCost(model.getCost());
			postResponse.setNumberOfDays(model.getNumberOfDays());
			postResponse.setStatus(model.getStatus());

			postResponse.setRoomType(model.getRoom().getRoomType().getName());
			postResponse.setRoomCategory(model.getRoom().getRoomCategory().getName());
			postResponse.setArea(model.getHouse().getArea());
			postResponse.setRentalPrice(model.getRoom().getRentalPrice());

			postResponse.setStreet(model.getHouse().getAddress().getStreet());
			postResponse.setPhuongXa(model.getHouse().getAddress().getPhuongXa().getName());
			QuanHuyenDto dto = new QuanHuyenDto();
			dto = quanHuyenService.findById(model.getHouse().getAddress().getPhuongXa().getMaQh());
			postResponse.setQuanHuyen(dto.getName());
			postResponse.setThanhPho(thanhPhoService.findById(dto.getMaTp()).getName());

			postResponse.setHostName(model.getHouse().getUser().getFullName());
			postResponse.setHostPhone(model.getHouse().getUser().getPhoneNumber());

			postResponse.setImages(Arrays.asList(modelMapper.map(model.getRoom().getImages(), ImageDto[].class)));
			postResponse.setVerify(model.getVerify());
			postResponse.setNote(model.getNote());

			postResponse.setPostCode(model.getPost_code());
			postResponse.setUsername(model.getHouse().getUser().getUsername());
			postResponse.setVerifyNote(model.getVerifyNote());
			postResponse.setRoomId(model.getRoom().getId());

			postRes.add(postResponse);
		}
		return postRes;
	}

	@Override
	public PostDto updatePost(PostDto postDto) {
		PostModel postModel = modelMapper.map(postDto, PostModel.class);
		PostModel saveModel = postRepository.save(postModel);
		return convertEntity2Dto(saveModel);
	}

	@Override
	public boolean removePost(Long id) {
		if (postRepository.existsById(id)) {
			postRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public PostDto createPost(PostDto postDto) {
		try {
			PostModel postModel = modelMapper.map(postDto, PostModel.class);
			PostModel saveModel = postRepository.save(postModel);
			return convertEntity2Dto(saveModel);
		} catch (Exception e) {
			LOGGER.error("createPost: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (postRepository.existsById(id)) {
			return true;
		}
		return false;
	}

	@Override
	public List<PostResponse> findAllByToken(String jwtToken) {
		UserModel userModel = userService.getUserInformationByToken(jwtToken);
		List<PostModel> postModels = postRepository.findAllPostByUserId(userModel.getId());
		if (postModels == null || postModels.isEmpty()) {
			return null;
		}
		List<PostResponse> postRes = convertEntity2Response(postModels);
		return postRes;
	}

	@Override
	public PageableResponse findAllPosting(SearchDto searchDto) {
		// If SearchDto equal null then return
		if (searchDto == null) {
			return null;
		}

		// Get page index, page size
		int pageIndex = searchDto.getPageIndex();
		int pageSize = searchDto.getPageSize();
		String key = searchDto.getKeyword();

		if (searchDto.getKeyword() == null) {
			key = "";
		}

		// If page index > 0 then reduction 1, else page index = 0
		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		List<PostingResponse> listPostingResponse = null;
		Date dateNow = new Date();
		PageableResponse pageableResponse = new PageableResponse();

		Pageable pageable = PageRequest.of(pageIndex, pageSize);

		Page<PostModel> result = postRepository.getListPage(key, dateNow, pageable);
		listPostingResponse = convertToPostingResponse(result.getContent());

		pageableResponse.setCurrentPage(pageIndex + 1);
		pageableResponse.setPageSize(pageSize);
		pageableResponse.setTotalPages(result.getTotalPages());
		pageableResponse.setTotalItems(result.getTotalElements());
		pageableResponse.setResults(listPostingResponse);

		return pageableResponse;
	}

	private List<PostingResponse> convertToPostingResponse(List<PostModel> list) {
		List<PostingResponse> listPostingResponse = new ArrayList<PostingResponse>();
		for (PostModel p : list) {

			Long idHouse = p.getHouse().getId();
			QuanHuyenDto dto = new QuanHuyenDto();
			dto = quanHuyenService.findById(p.getHouse().getAddress().getPhuongXa().getMaQh());

			int amountRating = feedbackRepository.getAmountByPostId(p.getId());
			float rating = 0;

			if (amountRating > 0) {
				rating = feedbackRepository.getTotalRatingByPostId(p.getId()) / amountRating;
			}

			PostingResponse pr = PostingResponse.builder().post(modelMapper.map(p, PostDto.class))
					.minPrice(roomService.minPrice(idHouse)).maxPrice(roomService.maxPrice(idHouse))
					.minArea(roomService.minArea(idHouse)).maxArea(roomService.maxArea(idHouse))
					.street(p.getHouse().getAddress().getStreet())
					.phuongXa(p.getHouse().getAddress().getPhuongXa().getName()).quanHuyen(dto.getName())
					.thanhPho(thanhPhoService.findById(dto.getMaTp()).getName()).amountRating(amountRating)
					.rating(rating).build();
			pr.getPost().getRoom().setHouse(null);
			listPostingResponse.add(pr);
		}
		return listPostingResponse;
	}

	@Override
	public PostModel extendPost(PostDto postDto) {
		PostModel postModel = postRepository.getById(postDto.getId());
		if (postModel != null) {
			int costPerDay = postTypeRepository.getById(postModel.getPostType().getId()).getPrice();
			postModel.setCost(postModel.getCost() + postDto.getNumberOfDays() * costPerDay);
			postModel.setNumberOfDays(postModel.getNumberOfDays() + postDto.getNumberOfDays());

			long addDate = Math.abs((postDto.getNumberOfDays() * TIMESTAMP_DAY));
			Date dateNow = new Date();
			Date endDate = postModel.getEndDate();
			if(endDate.getTime() > dateNow.getTime()) {
				postModel.setEndDate(new Date(endDate.getTime() + addDate));
			}else {
				postModel.setStartDate(dateNow);
				postModel.setEndDate(new Date(dateNow.getTime() + addDate));
			}

			return postRepository.save(postModel);
		}
		return null;
	}

	@Override
	public PostModel confirmPost(PostDto postDto) {
		PostModel postModel = modelMapper.map(postDto, PostModel.class);
		return postRepository.save(postModel);
	}

	@Override
	public PageableResponse filterPosting(FilterRoomDto dto) {
		// If SearchDto equal null then return
		if (dto == null) {
			return null;
		}

		// Get page index, page size
		int pageIndex = dto.getPageIndex();
		int pageSize = dto.getPageSize();

		// If page index > 0 then reduction 1, else page index = 0
		if (pageIndex > 0) {
			pageIndex--;
		} else {
			pageIndex = 0;
		}

		Pageable pageable = PageRequest.of(pageIndex, pageSize);

//		Page<PostModel> result = postRepository.getFilterPage(dto.getHouseTypeIds(), dto.getMinPrice(),
//				dto.getMaxPrice(), dto.getRoomCategoryIds(), dto.getMaximumNumberOfPeople(), pageable);
		// , dto.getAmenityIds()

		//List<PostingResponse> listPostingResponse = convertToPostingResponse(result.getContent());

		PageableResponse pageableResponse = new PageableResponse();
		pageableResponse.setCurrentPage(pageIndex + 1);
		pageableResponse.setPageSize(pageSize);
		//pageableResponse.setTotalPages(result.getTotalPages());
//		pageableResponse.setTotalItems(result.getTotalElements());
//		pageableResponse.setResults(listPostingResponse);

		return pageableResponse;
	}

	@Override
	public List<PostResponse> findAllFavoritePostByUserId(Long userId) {
		List<PostModel> postModels = postRepository.findAllFavoritePostByUserId(userId);
		if (postModels == null || postModels.isEmpty()) {
			return null;
		}
		List<PostResponse> postRes = convertEntity2Response(postModels);
		return postRes;
	}

	@Override
	public List<PostingResponse> findTop8Posting() {
		Date dateNow = new Date();
		Pageable pageable = PageRequest.of(0, 8);
		Page<PostModel> result = postRepository.getFilterPageTop8(dateNow, pageable);
		List<PostModel> listPost = result.getContent();

		return convertToPostingResponse(listPost);
	}

	@Override
	public PostingRoomResponse findPostingById(Long id) {
		PostModel p = postRepository.getById(id);
		if (p == null) {
			return null;
		}
		Long idHouse = p.getHouse().getId();
		QuanHuyenDto dto = new QuanHuyenDto();
		dto = quanHuyenService.findById(p.getHouse().getAddress().getPhuongXa().getMaQh());
		
		List<RoomModel> listRoomModel = new ArrayList<RoomModel>();
		for (RoomModel r : p.getHouse().getRoom()) {
			if(r.isEnable() == true)
				listRoomModel.add(r);
		}

		PostingRoomResponse prr = PostingRoomResponse.builder().post(modelMapper.map(p, PostDto.class))
				.minPrice(roomService.minPrice(idHouse)).maxPrice(roomService.maxPrice(idHouse))
				.minArea(roomService.minArea(idHouse)).maxArea(roomService.maxArea(idHouse))
				.street(p.getHouse().getAddress().getStreet())
				.phuongXa(p.getHouse().getAddress().getPhuongXa().getName()).quanHuyen(dto.getName())
				.thanhPho(thanhPhoService.findById(dto.getMaTp()).getName()).build();
		prr.getPost().getRoom().setHouse(null);
		prr.getPost().getHouse().setRooms(Arrays.asList(modelMapper.map(listRoomModel, RoomDto[].class)));
		return prr;
	}

	@Override
	public boolean checkExistCode(String code) {
		return postRepository.checkExistCode(code);
	}

	@Override
	public List<HouseResponse> getAllHouseNamePosting() {
		Date dateNow = new Date();
		return postRepository.getAllHouseNamePosting(dateNow);
	}

	@Override
	public List<PostingResponse> findAllPostingMap(SearchDto searchDto) {
		if (searchDto == null) {
			return null;
		}

		String key = searchDto.getKeyword();
		
		if (searchDto.getKeyword() == null) {
			key = "";
		}

		List<PostingResponse> listPostingResponse = null;
		Date dateNow = new Date();

		List<PostModel> result = postRepository.getAllPostModelContainKey(key, dateNow);
		listPostingResponse = convertToPostingResponse(result);

		return listPostingResponse;
	}

	@Override
	public List<PostResponse> findAllPostSearch(PostSearchDto dto, UserDto user) {
		String sql = "select entity from PostModel as entity where (1=1) ";
		String whereClause = " AND entity.enable = true";

		if (!dto.getFullname().isEmpty()) {
			whereClause += " AND ( entity.house.user.fullName LIKE :text)";
		}
		
		if (!dto.getUsername().isEmpty()) {
			whereClause += " AND ( entity.house.user.username LIKE :text2)";
		}
		
		if (dto.getFromDate() != null) {
			whereClause += " AND (entity.startDate >= :text3)";
		}

		if (dto.getToDate() != null) {
			whereClause += " AND (entity.endDate < :text4)";
		}
		
		if (!dto.getPostCode().isEmpty()) {
			whereClause += " AND ( entity.post_code LIKE :text5)";
		}
		if(user.getRole().getRole().equalsIgnoreCase("ROLE_LANDLORD")) {
			whereClause += " AND ( entity.createdBy LIKE :text6)";
		}

		whereClause += " order by entity.createdDate desc";
		sql += whereClause;

		Query query = entityManager.createQuery(sql, PostModel.class);

		if (!dto.getFullname().isEmpty()) {
			query.setParameter("text", '%' + dto.getFullname().trim() + '%');
		}
		
		if (!dto.getUsername().isEmpty()) {
			query.setParameter("text2", '%' + dto.getUsername().trim() + '%');
		}
		
		if (dto.getFromDate() != null) {
			Date dateWithoutTime = new Date((dto.getFromDate()/1000) * 1000);
			query.setParameter("text3", dateWithoutTime);
			
		}

		if (dto.getToDate() != null) {
			Date dateWithoutTime = new Date((dto.getToDate()/1000) * 1000 + TIMESTAMP_DAY);
			query.setParameter("text4", dateWithoutTime);
		}
		
		if (!dto.getPostCode().isEmpty()) {
			query.setParameter("text5", '%' + dto.getPostCode().trim().toLowerCase() + '%');
		}
		
		if(user.getRole().getRole().equalsIgnoreCase("ROLE_LANDLORD")) {
			query.setParameter("text6", '%' + user.getEmail() + '%');
		}


		@SuppressWarnings("unchecked")
		List<PostModel> list = query.getResultList();
		return convertEntity2Response(list);
	}

}
