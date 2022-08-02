import com.senerk.casestudy.garageticketsystem.enums.VehicleType;
import com.senerk.casestudy.garageticketsystem.models.ParkingRecord;
import com.senerk.casestudy.garageticketsystem.models.ValidatorException;
import com.senerk.casestudy.garageticketsystem.service.garage.GarageService;
import com.senerk.casestudy.garageticketsystem.service.garage.impl.GarageServiceImpl;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest(classes = {GarageServiceImpl.class})
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class GarageServiceTest {

    private static final Logger logger = LoggerFactory.getLogger(GarageServiceTest.class);

    @Autowired
    private GarageService garageService;

    @TestConfiguration
    static class EmployeeServiceImplTestContextConfiguration {
        @Bean
        public GarageService employeeService() {
            return new GarageServiceImpl();
        }
    }

    @Test
    @Order(1)
    public void parkACar() throws ValidatorException {
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setVehicleType(VehicleType.CAR);
        parkingRecord.setColor("White");
        parkingRecord.setNumberPlate("34-LO-2000");
        parkingRecord = garageService.requestParkArea(parkingRecord);
        Assert.notEmpty(parkingRecord.getOccupiedParkingArea(), "Failed empty area assignment");
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(1);
        Assert.isTrue(expectedResult.containsAll(parkingRecord.getOccupiedParkingArea()) && parkingRecord.getOccupiedParkingArea().containsAll(expectedResult), "Failed correct empty area assignment");
        logger.info("park 34-SO-1988 Black Car");
    }

    @Test
    @Order(2)
    public void parkATruck() throws ValidatorException {
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setVehicleType(VehicleType.TRUCK);
        parkingRecord.setColor("Red");
        parkingRecord.setNumberPlate("34-BO-1987");
        parkingRecord = garageService.requestParkArea(parkingRecord);

        Assert.notEmpty(parkingRecord.getOccupiedParkingArea(), "Failed empty area assignment");
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(3);
        expectedResult.add(4);
        expectedResult.add(5);
        expectedResult.add(6);
        Assert.isTrue(expectedResult.containsAll(parkingRecord.getOccupiedParkingArea()) && parkingRecord.getOccupiedParkingArea().containsAll(expectedResult), "Failed correct empty area assignment");
        logger.info("park 34-BO-1987 Red Truck");
    }

    @Test
    @Order(3)
    public void parkAJeep() throws ValidatorException {
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setVehicleType(VehicleType.JEEP);
        parkingRecord.setColor("Blue");
        parkingRecord.setNumberPlate("34-VO-2018");
        parkingRecord = garageService.requestParkArea(parkingRecord);

        Assert.notEmpty(parkingRecord.getOccupiedParkingArea(), "Failed empty area assignment");
        logger.info(parkingRecord.getOccupiedParkingArea().stream().map(i -> i + "").collect(Collectors.joining(",")));
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(8);
        expectedResult.add(9);
        Assert.isTrue(expectedResult.containsAll(parkingRecord.getOccupiedParkingArea()) && parkingRecord.getOccupiedParkingArea().containsAll(expectedResult),
                "Failed correct empty area assignment");
        logger.info("park 34-VO-2018 Blue Jeep");
    }

    @Test()
    @Order(4)
    public void parkATrucKMoreWhileThereIsNoArea() throws ValidatorException {
        try {
            ParkingRecord parkingRecord = new ParkingRecord();
            parkingRecord.setVehicleType(VehicleType.TRUCK);
            parkingRecord.setColor("Black");
            parkingRecord.setNumberPlate("34-HBO-2020");
            garageService.requestParkArea(parkingRecord);
        } catch (Exception e) {
            Assert.isTrue(e instanceof ValidatorException, "Failed correct exception");
        }
        logger.info("park 34-HBO-2020 Black Truck");
    }

    @Order(5)
    public void jeepLeaveFromGarage() throws ValidatorException {
        ParkingRecord parkingRecord = garageService.vehicleLeaveParkArea("34-VO-2018");
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(8);
        expectedResult.add(9);
        Assert.isTrue(expectedResult.containsAll(parkingRecord.getOccupiedParkingArea()) && parkingRecord.getOccupiedParkingArea().containsAll(expectedResult),
                "Failed correct leaves car from garage");
    }

    @Test()
    @Order(6)
    public void vehicleDoNotFoundInGarageExceptionTest() throws ValidatorException {
        try {
            garageService.vehicleLeaveParkArea("34-VO-2018");
        } catch (Exception e) {
            Assert.isTrue(e instanceof ValidatorException, "Failed correct exception");
            Assert.isTrue(e.getMessage().equals(String.format("There is not found any vehicle with plate number %s in garage", "34-VO-2018")), "Failed correct exception message");
        }
    }

    @Test
    @Order(7)
    public void parkACarMore() throws ValidatorException {
        ParkingRecord parkingRecord = new ParkingRecord();
        parkingRecord.setVehicleType(VehicleType.CAR);
        parkingRecord.setColor("White");
        parkingRecord.setNumberPlate("34-LO-2000");
        parkingRecord = garageService.requestParkArea(parkingRecord);
        List<Integer> expectedResult = new ArrayList<>();
        expectedResult.add(8);
        Assert.isTrue(expectedResult.containsAll(parkingRecord.getOccupiedParkingArea()) && parkingRecord.getOccupiedParkingArea().containsAll(expectedResult), "Failed correct empty area assignment");
        logger.info("park 34-LO-2000 White Car");
    }
}
