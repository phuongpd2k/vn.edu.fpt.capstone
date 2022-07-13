package vn.edu.fpt.capstone.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "FEEDBACK")
@EqualsAndHashCode(callSuper = false)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class FeedbackModel extends Auditable<String> {
	@Id
	@Column(name = "ID")
	@GeneratedValue(generator = "FEEDBACK_SeqGen", strategy = GenerationType.SEQUENCE)
	@SequenceGenerator(name = "FEEDBACK_SeqGen", sequenceName = "FEEDBACK_Seq", allocationSize = 1)
	private Long id;
	@OneToOne
	@JoinColumn(name = "user_id")
    private UserModel user;
	@Column(name = "CONTENT", columnDefinition = "LONGTEXT NULL", nullable = false)
	private String content;
	@Column(name = "RATING", nullable = false)
	private float rating;
	@Column(name = "POSTID", nullable = false)
	private Long postId;
}
