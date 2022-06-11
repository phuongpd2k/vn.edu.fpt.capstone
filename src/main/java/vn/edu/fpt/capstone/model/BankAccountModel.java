package vn.edu.fpt.capstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "BANK_ACCOUNT")
@EqualsAndHashCode(callSuper = false)
public class BankAccountModel extends Auditable<String>{
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "BANK_ACCOUNT_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "BANK_ACCOUNT_SeqGen", sequenceName = "BANK_ACCOUNT_Seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "BANK_NAME")
	private String bankName;
	
	@Column(name = "USERNAME")
	private String username;
	
	@Column(name = "NUMBER_ACCOUNT")
	private Long numberAccount;
	
	@Column(name = "ENABLE", nullable = false)
	private boolean enable = true;
	
}
