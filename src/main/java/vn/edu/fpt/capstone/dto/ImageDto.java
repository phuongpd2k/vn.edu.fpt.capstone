package vn.edu.fpt.capstone.dto;



import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class ImageDto extends Auditable<String>{
    private Long id;
    private String imageUrl;
}
