package com.senerk.casestudy.garageticketsystem.entitydto;

import com.senerk.casestudy.garageticketsystem.enums.VehicleType;
import com.senerk.casestudy.garageticketsystem.models.ParkingRecord;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

public class ParkingRecordDto {
    @NotNull(message = "NumberPlate cannot be Null")
    @NotBlank(message = "NumberPlate Number cannot be empty")
    private String numberPlate;
    private List<Integer> occupiedParkingArea;
    @NotNull(message = "VehicleType cannot be Null/Invalid")
    private VehicleType vehicleType;
    private String color;

    public String getNumberPlate() {
        return numberPlate;
    }

    public void setNumberPlate(String numberPlate) {
        this.numberPlate = numberPlate;
    }

    public List<Integer> getOccupiedParkingArea() {
        return occupiedParkingArea;
    }

    public void setOccupiedParkingArea(List<Integer> occupiedParkingArea) {
        this.occupiedParkingArea = occupiedParkingArea;
    }

    public VehicleType getVehicleType() {
        return vehicleType;
    }

    public void setVehicleType(VehicleType vehicleType) {
        this.vehicleType = vehicleType;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (o == null || getClass() != o.getClass()) return false;

        ParkingRecord that = (ParkingRecord) o;

        return new EqualsBuilder().append(getNumberPlate(), that.getNumberPlate()).isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37).append(getNumberPlate()).toHashCode();
    }
}
