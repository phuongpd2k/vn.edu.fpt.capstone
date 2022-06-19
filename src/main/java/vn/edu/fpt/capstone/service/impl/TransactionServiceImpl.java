package vn.edu.fpt.capstone.service.impl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vn.edu.fpt.capstone.dto.SearchTransactionDto;
import vn.edu.fpt.capstone.dto.TransactionDto;
import vn.edu.fpt.capstone.model.TransactionModel;
import vn.edu.fpt.capstone.repository.TransactionRepository;
import vn.edu.fpt.capstone.response.TransactionResponse;
import vn.edu.fpt.capstone.service.TransactionService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Service
public class TransactionServiceImpl implements TransactionService {
	private static final Logger LOGGER = LoggerFactory.getLogger(TransactionServiceImpl.class.getName());

	@Autowired
	private TransactionRepository transactionRepository;
	@Autowired
	ModelMapper modelMapper;
	
	@PersistenceContext
	private EntityManager entityManager;

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

	@Override
	public List<TransactionResponse> search(SearchTransactionDto search) {
		// Query
		String sql = "select entity from TransactionModel as entity where (1=1)";
		String whereClause = "";
		
		if(search.getStatus() != null) {
			whereClause += " AND (entity.status = :text)";
		}
		
		if(search.getUsername() != null) {
			whereClause += " AND (entity.user.fullName LIKE :text2)";
		}
		
		if(search.getFromDate() != null) {
			whereClause += " AND (entity.createdDate >= :text3)";
		}
		
		if(search.getToDate() != null) {
			whereClause += " AND (entity.createdDate <= :text4)";
		}
		
		sql += whereClause;
		
		Query query = entityManager.createQuery(sql, TransactionModel.class);
		
		if(search.getStatus() != null) {
			query.setParameter("text", search.getStatus().trim() );
		}
		
		if(search.getUsername() != null) {
			query.setParameter("text2", '%' + search.getUsername().trim() + '%');
		}
		
		if(search.getFromDate() != null) {
			query.setParameter("text3", new Date(search.getFromDate()));
		}
		
		if(search.getToDate() != null) {
			query.setParameter("text4", new Date(search.getToDate()));
		}

		@SuppressWarnings("unchecked")
		List<TransactionModel> list = query.getResultList();
		return convertToListDto(list);
	}

	private List<TransactionResponse> convertToListDto(List<TransactionModel> list) {
		List<TransactionResponse> listObject = new ArrayList<TransactionResponse>();
		for (TransactionModel t : list) {
			TransactionResponse tr = new TransactionResponse(t.getUser().getFullName(), t.getAmount(), t.getActualAmount(), t.getCode(), t.getCreatedDate(), t.getStatus());
			listObject.add(tr);
		}
		return listObject;
	}

}
