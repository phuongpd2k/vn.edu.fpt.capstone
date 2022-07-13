package vn.edu.fpt.capstone.dto.response;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.dto.FeedbackDto;

@Data
@EqualsAndHashCode(callSuper = false)
public class FeedbackResponse {
	private float averageRating=0;
	private int countFeedback=0;
	List<FeedbackDto> feedbackDtos;
}
