package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddressDto extends Auditable<String>{
    @JsonProperty(index = 0)
    private Long id;
    @JsonProperty(index = 1)
    private Long xaId;
    @JsonProperty(index = 2)
    private String street;
    @JsonProperty(index = 3)
    private String longiude;
    @JsonProperty(index = 4)
    private String latitude;
}
