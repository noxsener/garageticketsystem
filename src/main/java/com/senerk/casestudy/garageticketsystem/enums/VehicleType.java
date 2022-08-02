package com.senerk.casestudy.garageticketsystem.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.lang3.StringUtils;

@JsonFormat(shape = JsonFormat.Shape.STRING)
public enum VehicleType {
    CAR(1),
    JEEP(2),
    TRUCK(4);

    private Integer collapsedSlotSize;

    private VehicleType(Integer collapsedSlotSize) {
        this.collapsedSlotSize = collapsedSlotSize;
    }

    @JsonCreator
    public static VehicleType getValue(final String name) {
        if (StringUtils.isBlank(name)) {
            return null;
        }
        for (VehicleType vehicleTypeIndex : VehicleType.values()) {
            if (vehicleTypeIndex.name().equalsIgnoreCase(name)) {
                return vehicleTypeIndex;
            }
        }
        return null;
    }

    public Integer getCollapsedSlotSize() {
        return collapsedSlotSize;
    }

    public void setCollapsedSlotSize(Integer collapsedSlotSize) {
        this.collapsedSlotSize = collapsedSlotSize;
    }

    @Override
    public String toString() {
        return this.name();
    }
}
