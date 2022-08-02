package com.senerk.casestudy.garageticketsystem.service.garage;

import com.senerk.casestudy.garageticketsystem.models.ParkingRecord;
import com.senerk.casestudy.garageticketsystem.models.ValidatorException;

import java.util.List;

public interface GarageService {
    /**
     * Request Park area
     *
     * @return ParkingRecord Data, throws if there is a problem
     */
    ParkingRecord requestParkArea(ParkingRecord parkingRequest) throws ValidatorException;
    /**
     * Request Parked vehicles number plate for leave garage
     *
     * @return Leaved Vehicle's ParkingRecord Data, throws if there is a problem
     */
    ParkingRecord vehicleLeaveParkArea(String numberPlate) throws ValidatorException;

    /**
     * Returns all ParkingRecord in memory
     *
     * @return ParkingRecord Data Collection
     */
    List<ParkingRecord> getParkedVehicleList();
}
