package vn.edu.fpt.capstone.dto;

import lombok.Data;
import vn.edu.fpt.capstone.model.Auditable;

@Data
public class BankAccountDto extends Auditable<String> {
	private Long id;
	private String bankName;
	private String username;
	private String branch;
	private String imageUrl;
	private Long numberAccount;
}
