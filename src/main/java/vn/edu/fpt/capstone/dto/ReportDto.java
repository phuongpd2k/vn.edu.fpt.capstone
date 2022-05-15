package vn.edu.fpt.capstone.dto;


import lombok.Data;
@Data
public class ReportDto extends BaseDto{
    private Long id;
    private Long userId;
    private String content;
}
