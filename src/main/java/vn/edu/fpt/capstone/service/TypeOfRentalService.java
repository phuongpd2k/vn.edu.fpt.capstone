package vn.edu.fpt.capstone.service;

import java.util.List;

import vn.edu.fpt.capstone.dto.TypeOfRentalDto;

public interface TypeOfRentalService {
	TypeOfRentalDto findById(Long id);
    List<TypeOfRentalDto> findAll();
    TypeOfRentalDto updateTypeOfRental(TypeOfRentalDto typeOfRentalDto);
    boolean removeTypeOfRental(Long id);
    TypeOfRentalDto createTypeOfRental(TypeOfRentalDto typeOfRentalDto);
    boolean isExist(Long id);
}
