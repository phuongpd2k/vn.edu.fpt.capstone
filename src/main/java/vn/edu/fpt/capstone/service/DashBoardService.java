package vn.edu.fpt.capstone.service;

import vn.edu.fpt.capstone.dto.UserDto;
import vn.edu.fpt.capstone.response.DBAdminByYearResponse;
import vn.edu.fpt.capstone.response.DashBoardAdminResponse;
import vn.edu.fpt.capstone.response.DashBoardHostResponse;

public interface DashBoardService {

	DashBoardAdminResponse getDashBoardAdmin();

	DashBoardHostResponse getDashBoardHost(UserDto userDto);

	DBAdminByYearResponse getDashBoardAdminByYear(int year);

}
