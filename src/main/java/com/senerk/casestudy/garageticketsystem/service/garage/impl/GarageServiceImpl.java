package com.senerk.casestudy.garageticketsystem.service.garage.impl;

import com.senerk.casestudy.garageticketsystem.enums.VehicleType;
import com.senerk.casestudy.garageticketsystem.models.ParkingRecord;
import com.senerk.casestudy.garageticketsystem.models.ValidatorException;
import com.senerk.casestudy.garageticketsystem.service.garage.GarageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class GarageServiceImpl implements GarageService {

    private static final Logger logger = LoggerFactory.getLogger(GarageServiceImpl.class);

    private static final Integer GARAGE_SIZE = 10;

    private List<ParkingRecord> parkingRecordList = new ArrayList<>();
    private List<Integer> garageParkSlots;

    @Override
    public ParkingRecord requestParkArea(ParkingRecord parkingRequest) throws ValidatorException {
        VehicleType vehicleType = parkingRequest.getVehicleType();
        List<Integer> suitableParkingSlot = findSuitableParkingSlot(vehicleType);
        if (CollectionUtils.isEmpty(suitableParkingSlot)) {
            throw new ValidatorException("There is not found any suitable parking range in garage");
        }
        parkingRequest.setOccupiedParkingArea(suitableParkingSlot);
        parkingRecordList.add(parkingRequest);
        logger.info(String.format("Vehicle Parked: %s %s %s in range %s", parkingRequest.getNumberPlate(), parkingRequest.getVehicleType().name(), parkingRequest.getColor(), suitableParkingSlot.stream().map(i -> i+"").collect(Collectors.joining(","))));
        return parkingRequest;
    }

    @Override
    public ParkingRecord vehicleLeaveParkArea(String numberPlate) throws ValidatorException {
        Optional<ParkingRecord> parkingRecordOptional = parkingRecordList.stream().filter(parkingRecordIndex -> parkingRecordIndex.getNumberPlate().equalsIgnoreCase(numberPlate)).findFirst();
        if(parkingRecordOptional.isPresent()) {
            ParkingRecord parkingRecord = parkingRecordOptional.get();
            parkingRecordList.remove(parkingRecord);
            logger.info(String.format("Vehicle was left: %s %s %s in range %s", parkingRecord.getNumberPlate(), parkingRecord.getVehicleType().name(), parkingRecord.getColor(), parkingRecord.getOccupiedParkingArea().stream().map(i -> i+"").collect(Collectors.joining(","))));
            return parkingRecord;
        } else {
            throw new ValidatorException(String.format("There is not found any vehicle with plate number %s in garage", numberPlate));
        }
    }

    private List<Integer> getEmptyParkAreas() {
        if(CollectionUtils.isEmpty(garageParkSlots)) {
            initializeGarageSlots();
        }

        List<Integer> emptySlots = new ArrayList<>(garageParkSlots);
        for(ParkingRecord parkingRecord : parkingRecordList) {
            emptySlots.removeAll(parkingRecord.getOccupiedParkingArea());
        }
        return emptySlots;
    }

    private void initializeGarageSlots() {
        garageParkSlots = new ArrayList<>();
        for(int i = 1 ; i <= GARAGE_SIZE ; i++) {
            garageParkSlots.add(i);
        }
    }

    @Override
    public List<ParkingRecord> getParkedVehicleList() {
        logger.info("Requested Garage Park Records");
        return parkingRecordList;
    }

    private List<Integer> findSuitableParkingSlot(VehicleType vehicleType) {
        Integer searchingSlotSize = vehicleType.getCollapsedSlotSize();
        List<Integer> emptySlots = getEmptyParkAreas();
        Integer previousNumber = null;
        List<Integer> suitableRange = new ArrayList<>();
        boolean thereIsVehicleBefore = !CollectionUtils.isEmpty(emptySlots) && emptySlots.get(0) > 1;
        for(int i = 0; i < emptySlots.size() ; i++) {
            Integer slot = emptySlots.get(i);
            if (previousNumber != null && previousNumber+1 == slot) {
                suitableRange.add(slot);
            } else {
                suitableRange = new ArrayList<>();
                if(thereIsVehicleBefore) {
                    previousNumber = slot;
                    continue; //For When a vehicle holds number of slots with its own width, you have to leave 1 unit slot to next one.
                }
                suitableRange.add(slot);
            }
            if (suitableRange.size() == searchingSlotSize) {
                return suitableRange;
            }
            previousNumber = slot;
        }
        return Collections.emptyList();
    }
}
