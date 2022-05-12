package vn.edu.fpt.capstone.service;


import java.util.List;

import vn.edu.fpt.capstone.dto.RoleDto;

public interface RoleService {
    RoleDto findById(Long id);
    List<RoleDto> findAll();
    RoleDto updateRole(RoleDto roleDto);
    boolean removeRole(Long id);
    RoleDto createRole(RoleDto roleDto);
    boolean isExist(Long id);

}
