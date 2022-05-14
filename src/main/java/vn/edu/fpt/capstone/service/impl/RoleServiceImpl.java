package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.RoleDto;
import vn.edu.fpt.capstone.model.RoleModel;
import vn.edu.fpt.capstone.repository.RoleRepository;
import vn.edu.fpt.capstone.service.RoleService;

import java.util.Arrays;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    RoleRepository roleRepository;
    @Autowired
    ModelMapper modelMapper;

    @Override
    public RoleDto findById(Long id) {
        RoleDto roleDto = modelMapper.map(roleRepository.findById(id).get(), RoleDto.class);
        return roleDto;
    }

    @Override
    public List<RoleDto> findAll() {
        List<RoleModel> roleModels = roleRepository.findAll();
        if (roleModels == null || roleModels.isEmpty()) {
            return null;
        }
        List<RoleDto> roleDtos = Arrays.asList(modelMapper.map(roleModels, RoleDto[].class));
        return roleDtos;
    }

    @Override
    public RoleDto updateRole(RoleDto roleDto) {
        RoleModel roleModel = modelMapper.map(roleDto,RoleModel.class);
        RoleModel saveModel = roleRepository.save(roleModel);
        return modelMapper.map(saveModel,RoleDto.class);
    }

    @Override
    public boolean removeRole(Long id) {
        if(roleRepository.existsById(id)){
            roleRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Override
    public RoleDto createRole(RoleDto roleDto) {
        RoleModel roleModel = modelMapper.map(roleDto,RoleModel.class);
        RoleModel saveModel = roleRepository.save(roleModel);
        return modelMapper.map(saveModel,RoleDto.class);
    }

    @Override
    public boolean isExist(Long id) {
        if(roleRepository.existsById(id)){
            return true;
        }
        return false;
    }

	@Override
	public RoleModel getRoleByCode(String role) {
		return roleRepository.getRoleByCode(role);
	}
}
