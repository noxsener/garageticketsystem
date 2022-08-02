package com.senerk.casestudy.garageticketsystem.controller;

import com.senerk.casestudy.garageticketsystem.converter.EntityMapper;
import com.senerk.casestudy.garageticketsystem.entitydto.ParkingRecordDto;
import com.senerk.casestudy.garageticketsystem.models.ApiResponse;
import com.senerk.casestudy.garageticketsystem.models.ParkingRecord;
import com.senerk.casestudy.garageticketsystem.models.ValidatorException;
import com.senerk.casestudy.garageticketsystem.service.garage.GarageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RequestMapping(path = "/api/rest/garage-park-controller")
@RestController
public class GarageParkController {
    private final GarageService garageService;

    public GarageParkController(GarageService garageService) {
        this.garageService = garageService;
    }

    /**
     * Returns all ParkingRecord in memory
     *
     * @return ParkingRecord Data Collection
     */
    @GetMapping
    public ResponseEntity<ApiResponse<List<ParkingRecordDto>>> getParkedVehicleList() {
        List<ParkingRecord> parkingRecordList = garageService.getParkedVehicleList();
        List<ParkingRecordDto> parkingRecordDtoList = parkingRecordList.stream().map(EntityMapper.INSTANCE::map).collect(Collectors.toList());
        return ApiResponse.ok(parkingRecordDtoList);
    }

    /**
     * Request Park area
     *
     * @return ParkingRecord Data, throws if there is a problem
     */
    @PostMapping
    public ResponseEntity<ApiResponse<ParkingRecordDto>> requestParkArea(@RequestBody @Valid ParkingRecordDto parkingRecordDto) {
        try {
        ParkingRecord parkingRecord = garageService.requestParkArea(EntityMapper.INSTANCE.map(parkingRecordDto));
        return ApiResponse.ok(EntityMapper.INSTANCE.map(parkingRecord));
        } catch (ValidatorException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }

    /**
     * Request Parked vehicles number plate for leave garage
     *
     * @return Leaved Vehicle's ParkingRecord Data, throws if there is a problem
     */
    @DeleteMapping("/{numberPlate}")
    public ResponseEntity<ApiResponse<ParkingRecordDto>> getParkedVehicleList(@PathVariable String numberPlate) {
        try {
            ParkingRecord parkingRecord = garageService.vehicleLeaveParkArea(numberPlate);
            return ApiResponse.ok(EntityMapper.INSTANCE.map(parkingRecord));
        } catch (ValidatorException e) {
            return ApiResponse.badRequest(e.getMessage());
        }
    }
}
