package vn.edu.fpt.capstone.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class ReportDto extends BaseDto{
    private Long id;
    private Long userId;
    private String content;
}
