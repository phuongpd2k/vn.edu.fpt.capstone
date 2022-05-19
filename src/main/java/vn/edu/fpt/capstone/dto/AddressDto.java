package vn.edu.fpt.capstone.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import vn.edu.fpt.capstone.model.Auditable;

@Data
@EqualsAndHashCode(callSuper = false)
public class AddressDto extends Auditable<String>{
    @JsonProperty("id")
    private Long id;
    @JsonProperty("xaId")
    private Long xaId;
    @JsonProperty("street")
    private Long street;
    @JsonProperty("longiude")
    private String longiude;
    @JsonProperty("latitude")
    private String latitude;
}
