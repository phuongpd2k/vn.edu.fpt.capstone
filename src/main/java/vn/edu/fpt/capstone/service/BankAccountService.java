package vn.edu.fpt.capstone.service;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.BankAccountDto;
import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.model.BankAccountModel;

@Service

public interface BankAccountService {
	BankAccountModel create(BankAccountDto bankAccountDto);

	void removeListBankAccount(ListIdDto listIdDto);

	List<BankAccountModel> getAllByEnable();
}
