package vn.edu.fpt.capstone.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class AmenityDto {
    private Long id;
    private Long roomId;
    private boolean privateToilet;
    private boolean wifi;
    private boolean free;
    private boolean bed;
    private boolean wardrobe;
    private boolean parking;
    private boolean fridge;
    private boolean airConditioner;
    private boolean tivi;
    private boolean guard;
    private boolean kitchen;
    private boolean fingerPrintLock;
    private boolean electricWaterHeater;
}
