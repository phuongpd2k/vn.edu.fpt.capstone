package vn.edu.fpt.capstone.dto;


import lombok.Data;
@Data
public class FeedbackRoomDto {
    private Long id;
    private Long userId;
    private String content;
    private int numberOfStar;
    private Long roomId;
}
