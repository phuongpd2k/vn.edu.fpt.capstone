package vn.edu.fpt.capstone.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

@Data
@Entity
@Table(name = "TRANSACTION")
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class TransactionModel extends Auditable<String> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "TRANSACTION_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "TRANSACTION_SeqGen", sequenceName = "TRANSACTION_Seq", allocationSize = 1)
	private Long id;
	
	@Column(name = "CODE")
	private String code;
	
	@Column(name = "AMOUNT")
	private float amount;
	
	@Column(name = "ACTUAL_AMOUNT")
	private float actualAmount;

	@Column(name = "LAST_BALANCE")
	private float lastBalance;

	@Column(name = "STATUS")
	private String status;

	@Column(name = "ACTION")
	private String action;

	@Column(name = "TRANSFER_CONTENT")
	private String transferContent;

	@Column(name = "TRANSFER_TYPE")
	private String transferType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "user_id")
	@JsonBackReference
	private UserModel user;
	
	@Column(name = "NOTE")
	private String note;
	
	@Column(name = "post_id")
    private Long postId;

}
