package ee.sml.parking.service;

import ee.sml.parking.dao.ParkingRepository;
import ee.sml.parking.model.Parking;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ParkingService {

    ParkingRepository parkingRepository;

    public ParkingService(ParkingRepository parkingRepository) {
        this.parkingRepository = parkingRepository;
    }

    public List<Parking> listParkings() {
        return parkingRepository.list();
    }

    public List<Parking> getParkingsForCustomer(Long customerId) {
        return parkingRepository.getByCustomerId(customerId);
    }

    public void add(Long propertyId, Long customerId) {
        Instant start = Instant.now();
        Instant end = Instant.now().plus(30, ChronoUnit.DAYS);
        Parking parkingToInsert = new Parking(start, end, customerId, propertyId);
        parkingRepository.create(parkingToInsert);
    }
}
