package vn.edu.fpt.capstone.service.impl;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.constant.Constant;
import vn.edu.fpt.capstone.dto.SignUpDto;
import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.dto.UserSearchDto;
import vn.edu.fpt.capstone.model.RoleModel;
import vn.edu.fpt.capstone.model.UserModel;
import vn.edu.fpt.capstone.random.RandomString;
import vn.edu.fpt.capstone.repository.UserRepository;
import vn.edu.fpt.capstone.security.JwtTokenUtil;
import vn.edu.fpt.capstone.service.UserService;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class UserServiceImpl implements UserService {
	// private static final Logger logger =
	// LoggerFactory.getLogger(UserServiceImpl.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private UserService userService;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private Constant constant;
	
	@Autowired
	private RandomString random;

	private UserModel convertToEntity(SignUpDto signUpDto) {
		signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));

		return modelMapper.map(signUpDto, UserModel.class);
	}

	private UserModel convertToEntity(UserDto userDto) {
		return modelMapper.map(userDto, UserModel.class);
	}

	public UserDto convertToDto(UserModel userModel) {
		return modelMapper.map(userModel, UserDto.class);
	}

	private List<UserDto> convertToListDto(List<UserModel> usersModel) {
		List<UserDto> usersDto = Arrays.asList(modelMapper.map(usersModel, UserDto[].class));
		return usersDto;
	}

	@Override
	public UserModel createUser(SignUpDto signUpDto) {
		UserModel userModel = convertToEntity(signUpDto);
		String code = "";
		while (true) {
			code = "HLH" + random.generateCodeTransaction(5);
			
			if(userRepository.getTotalUserCode(code) == 0)
				break;
		}
		
		userModel.setCodeTransaction(code);
		userModel.setBalance(constant.DEFAULT_BALANCE);
		
		return userRepository.save(userModel);
	}

	@Override
	public List<UserDto> getAllUser() {
		List<UserModel> listModel = userRepository.findAllByOrderByCreatedDateDesc();
		if (listModel == null || listModel.isEmpty())
			return null;
		return convertToListDto(listModel);
	}

	@Override
	public boolean checkIdExist(Long id) {
		return userRepository.existsById(id);
	}

	@Override
	public boolean existsByUsername(String username) {
		return userRepository.existsByUsername(username);
	}

	@Override
	public UserModel getUserInformationById(Long id) {
		return userRepository.findById(id).orElse(null);
	}

	@Override
	public UserModel getUserInformationByToken(String jwtToken) {
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken.substring(7));
		return userRepository.findByEmail(username).orElse(null);
	}

	@Override
	public UserModel findByVerificationCode(String code) {
		return userRepository.findByVerificationCode(code).orElse(null);
	}

	@Override
	public UserModel userUpdateRole(UserDto userDto, String jwtToken) {
		UserModel userModel = userService.getUserInformationByToken(jwtToken);
		RoleModel roleModel = modelMapper.map(userDto.getRole(), RoleModel.class);
		userModel.setRole(roleModel);
		userModel.setPhoneNumber(userDto.getPhoneNumber());
		userModel.setCccd(userDto.getCccd());

		return userRepository.save(userModel);
	}

	@Override
	public void deleteUserById(Long id) {
		UserModel userModel = userRepository.getById(id);
		userModel.setEnable(false);

		userRepository.save(userModel);
	}

	@Override
	public int getTotalUserActive() {
		return userRepository.countUserActive();
	}

	@Override
	public UserModel findByEmail(String email) {
		return userRepository.findByEmail(email).orElse(null);
	}

	@Override
	public List<UserDto> getAllUserSearch(UserSearchDto userSearch) {	
		String sql = "select entity from UserModel as entity where (1=1) ";
		String whereClause = "";

		if (!userSearch.getFullname().isEmpty()) {
			whereClause += " AND ( entity.fullName LIKE :text)";
		}
		
		if (!userSearch.getUsername().isEmpty()) {
			whereClause += " AND ( entity.username LIKE :text2)";
		}
		
		if (userSearch.getFromDate() != null) {
			whereClause += " AND (entity.createdDate >= :text3)";
		}

		if (userSearch.getToDate() != null) {
			whereClause += " AND (entity.createdDate <= :text4)";
		}

		if (userSearch.getIsActive() == 1) {
			whereClause += " AND (entity.isActive = true)";
		}

		if (userSearch.getIsActive() == 0) {
			whereClause += " AND (entity.isActive = false)";
		}

		whereClause += " order by entity.createdDate desc";
		sql += whereClause;

		Query query = entityManager.createQuery(sql, UserModel.class);

		if (!userSearch.getFullname().isEmpty()) {
			query.setParameter("text", '%' + userSearch.getFullname().trim() + '%');
		}
		
		if (!userSearch.getUsername().isEmpty()) {
			query.setParameter("text2", '%' + userSearch.getUsername().trim() + '%');
		}

		Calendar cal = Calendar.getInstance();
		
		if (userSearch.getFromDate() != null) {
			cal.setTimeInMillis(userSearch.getFromDate());
			cal.set(Calendar.HOUR_OF_DAY, 0);
			cal.set(Calendar.MINUTE, 0);
			cal.set(Calendar.SECOND, 0);
			cal.set(Calendar.MILLISECOND, 0);
			
			Date dateWithoutTime = cal.getTime();
			
			query.setParameter("text3", dateWithoutTime);
		}

		if (userSearch.getToDate() != null) {
			cal.setTimeInMillis(userSearch.getToDate());
			query.setParameter("text4", cal.getTime());
		}

		@SuppressWarnings("unchecked")
		List<UserModel> list = query.getResultList();
		return convertToListDto(list);
	}

	@Override
	public int getTotalUser() {
		return userRepository.getTotalUser();
	}

	@Override
	public void unLockUserById(Long id) {
		UserModel userModel = userRepository.getById(id);
		userModel.setActive(true);

		userRepository.save(userModel);

	}

	@Override
	public UserDto getUserById(Long id) {
		UserModel userModel = userRepository.getById(id);
		if(userModel == null) {
			return null;
		}
		return modelMapper.map(userModel, UserDto.class);
	}

	@Override
	public UserDto getUserByToken(String jwtToken) {
		String email = jwtTokenUtil.getUsernameFromToken(jwtToken.substring(7));
		UserModel model = userRepository.findByEmail(email).orElse(null);
		if (model == null)
			return null;

		return modelMapper.map(model, UserDto.class);
	}

	@Override
	public UserModel updateUser(UserDto userDto) {
		UserModel user = userRepository.getById(userDto.getId());
		if (user != null) {
			userDto.setPassword(user.getPassword());
			return userRepository.save(convertToEntity(userDto));
		}
		return null;

	}

	@Override
	public UserModel findByUserName(String username) {
		return userRepository.findByUsername(username).orElse(null);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public UserModel createByAdmin(SignUpDto signUpDto) {
		signUpDto.setPassword(passwordEncoder.encode(signUpDto.getPassword()));
		UserModel userModel = modelMapper.map(signUpDto, UserModel.class);
		String code = "";
		while (true) {
			code = "HLH" + random.generateCodeTransaction(5);
			
			if(userRepository.getTotalUserCode(code) == 0)
				break;
		}
		
		userModel.setCodeTransaction(code);
		userModel.setBalance(constant.DEFAULT_BALANCE);
		userModel.setEnable(true);
		userModel.setActive(true);
		userModel.setVerify(true);
		
		return userRepository.save(userModel);
	}

	@Override
	public void lockUser(UserDto user) {
		UserModel userModel = modelMapper.map(user, UserModel.class);
		userRepository.save(userModel);
	}

}