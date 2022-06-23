package vn.edu.fpt.capstone.service.impl;

import java.util.Arrays;
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
		List<UserModel> listModel = userRepository.findAll();
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
		return userRepository.findByUsername(username).orElse(null);
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

		if (!userSearch.getKeyword().isEmpty()) {
			whereClause += " AND ( entity.fullName LIKE :text)";
		}

		if (userSearch.getIsActive() == 1) {
			whereClause += " AND (entity.isActive = true)";
		}

		if (userSearch.getIsActive() == 0) {
			whereClause += " AND (entity.isActive = false)";
		}

		sql += whereClause;

		Query query = entityManager.createQuery(sql, UserModel.class);

		if (!userSearch.getKeyword().isEmpty()) {
			query.setParameter("text", '%' + userSearch.getKeyword().trim() + '%');
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
	public void lockUserById(Long id) {
		UserModel userModel = userRepository.getById(id);
		userModel.setActive(false);

		userRepository.save(userModel);
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
		String username = jwtTokenUtil.getUsernameFromToken(jwtToken.substring(7));
		UserModel model = userRepository.findByUsername(username).orElse(null);
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
	public UserModel updateUserByToken(String jwtToken, UserDto userDto) {
		UserModel user = userService.getUserInformationByToken(jwtToken);
		if (user != null) {
			if(userDto.getCccd() != null)
				user.setCccd(userDto.getCccd());
			if(userDto.getEmail() != null)
				user.setEmail(userDto.getEmail());
			if(userDto.getFullName() != null)
				user.setFullName(userDto.getFullName());
			if(userDto.getPhoneNumber() != null)
				user.setPhoneNumber(userDto.getPhoneNumber());
			if(userDto.getUsername() != null)
				user.setUsername(userDto.getFullName());
			
			user.setGender(userDto.isGender());
			
			return userRepository.save(user);
		}
		return null;
	}
}