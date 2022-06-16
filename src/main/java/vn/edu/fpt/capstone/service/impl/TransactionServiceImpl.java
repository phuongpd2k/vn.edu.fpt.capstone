package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import vn.edu.fpt.capstone.dto.TransactionDto;
import vn.edu.fpt.capstone.model.TransactionModel;
import vn.edu.fpt.capstone.repository.TransactionRepository;
import vn.edu.fpt.capstone.service.TransactionService;

import java.util.Arrays;
import java.util.List;

@Service
public class TransactionServiceImpl implements TransactionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class.getName());

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	ModelMapper modelMapper;

	@Override
	public TransactionDto findById(Long id) {
		TransactionDto transactionDto = modelMapper.map(transactionRepository.findById(id).get(), TransactionDto.class);
		return transactionDto;
	}

	@Override
	public List<TransactionDto> findAll() {
		List<TransactionModel> transactionModels = transactionRepository.findAll();
		if (transactionModels == null || transactionModels.isEmpty()) {
			return null;
		}
		List<TransactionDto> transactionDtos = Arrays
				.asList(modelMapper.map(transactionModels, TransactionDto[].class));
		return transactionDtos;
	}

	@Override
	public TransactionDto updateTransaction(TransactionDto transactionDto) {
		TransactionModel transactionModel = modelMapper.map(transactionDto, TransactionModel.class);
		TransactionModel saveModel = transactionRepository.save(transactionModel);
		return modelMapper.map(saveModel, TransactionDto.class);
	}

	@Override
	public boolean removeTransaction(Long id) {
		if (transactionRepository.existsById(id)) {
			transactionRepository.deleteById(id);
			return true;
		}
		return false;
	}

	@Override
	public TransactionDto createTransaction(TransactionDto transactionDto) {
		try {
			TransactionModel transactionModel = modelMapper.map(transactionDto, TransactionModel.class);
			TransactionModel saveModel = transactionRepository.save(transactionModel);
			return modelMapper.map(saveModel, TransactionDto.class);
		} catch (Exception e) {
			LOGGER.error("createTransaction: {}", e);
			return null;
		}
	}

	@Override
	public boolean isExist(Long id) {
		if (transactionRepository.existsById(id)) {
			return true;
		}
		return false;
	}

	@Override
	public List<TransactionDto> findAllByUserId(Long userId) {
		List<TransactionModel> transactionModels = transactionRepository.findByUserId(userId);
		if (transactionModels == null || transactionModels.isEmpty()) {
			return null;
		}
		List<TransactionDto> transactionDtos = Arrays
				.asList(modelMapper.map(transactionModels, TransactionDto[].class));
		return transactionDtos;
	}

}
