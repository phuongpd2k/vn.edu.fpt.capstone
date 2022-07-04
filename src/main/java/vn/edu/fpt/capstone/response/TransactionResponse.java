package vn.edu.fpt.capstone.response;

import java.util.Date;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionResponse {
	private Long id;
	private Long userId;
	private String fullname;
	private String username;
	private float amount;
	private float actualAmount;
	private String code;
	private String userCode;
	private Date dateCreate;
	private String status;
	private String typeOfTransaction;
	private String typeOfPosting;
	private Date datePostingOrExtend;
	private int dayAmount;
	private String action;
	private String note;
	private Date lastModifiedDate;
	private float lastBalance;
}
