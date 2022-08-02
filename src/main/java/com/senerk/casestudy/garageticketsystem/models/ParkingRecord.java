package com.senerk.casestudy.garageticketsystem.models;

import com.senerk.casestudy.garageticketsystem.enums.VehicleType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

import java.util.List;

public class ParkingRecord {
    private String numberPlate;
    private List<Integer> occupiedParkingArea;
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
