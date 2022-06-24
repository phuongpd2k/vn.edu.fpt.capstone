package vn.edu.fpt.capstone.service;

import vn.edu.fpt.capstone.dto.SearchTransactionDto;
import vn.edu.fpt.capstone.dto.TransactionDto;
import vn.edu.fpt.capstone.response.TransactionResponse;

import java.util.List;

public interface TransactionService {

	TransactionDto findById(Long id);

	List<TransactionDto> findAll();

	List<TransactionDto> findAllByUserId(Long userId);

	TransactionDto updateTransaction(TransactionDto transactionDto);

	boolean removeTransaction(Long id);

	TransactionDto createTransaction(TransactionDto transactionDto);

	boolean isExist(Long id);

	List<TransactionResponse> search(SearchTransactionDto search);

	TransactionDto findByPostId(Long id);

}
