package vn.edu.fpt.capstone.service;


import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.RoleDto;
import vn.edu.fpt.capstone.model.RoleModel;

@Service
public interface RoleService {
    RoleDto findById(Long id);
    List<RoleDto> findAll();
    RoleDto updateRole(RoleDto roleDto);
    boolean removeRole(Long id);
    RoleDto createRole(RoleDto roleDto);
    boolean isExist(Long id);
    RoleModel getRoleByCode(String role);
}
