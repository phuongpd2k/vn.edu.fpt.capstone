package vn.edu.fpt.capstone.dto;


import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper = false)
public class FeedbackRoomDto extends BaseDto{
    private Long id;
    private Long userId;
    private String content;
    private int numberOfStar;
    private Long roomId;
}
