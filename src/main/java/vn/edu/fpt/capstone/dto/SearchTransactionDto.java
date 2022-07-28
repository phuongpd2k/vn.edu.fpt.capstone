package vn.edu.fpt.capstone.dto;

import lombok.Data;

@Data
public class SearchTransactionDto extends SearchDto{
	private String username;
	private String fullName;
	private String userCode;
	private String status; //status : (thành công, thất bại, chờ xác nhận)
	private String type;
	
	private String transactionCode;// (mã giao dịch) (mã chyển về chữ thường rồ search)
    private String transactionType; // (đăng tin , xác thực, nạp tiền,..)
    
}
