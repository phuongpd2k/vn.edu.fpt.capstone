package vn.edu.fpt.capstone.service.impl;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.BankAccountDto;
import vn.edu.fpt.capstone.dto.ListIdDto;
import vn.edu.fpt.capstone.model.BankAccountModel;
import vn.edu.fpt.capstone.repository.BankAccountRepository;
import vn.edu.fpt.capstone.service.BankAccountService;

@Service
public class BankAccountServiceImpl implements BankAccountService{
	private static final Logger LOGGER = LoggerFactory.getLogger(BankAccountServiceImpl.class.getName());
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BankAccountRepository bankAccountRepos;

	@Override
	public BankAccountModel create(BankAccountDto bankAccountDto) {
		return bankAccountRepos.save(modelMapper.map(bankAccountDto, BankAccountModel.class));
	}

	@Override
	public void removeListBankAccount(ListIdDto listIdDto) {
		try {
			for (Long id : listIdDto.getList()) {
				BankAccountModel accountModel = bankAccountRepos.getById(id);
				if(accountModel != null) {
					accountModel.setEnable(false);
					accountModel = bankAccountRepos.save(accountModel);
				}
			}
		} catch (Exception e) {
			LOGGER.error("deleteBankAccount: {}", e.getMessage());
		}	
	}

	@Override
	public List<BankAccountModel> getAllByEnable() {
		return bankAccountRepos.getAllByEnable();
	}

}
