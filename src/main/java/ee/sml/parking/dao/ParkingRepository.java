package ee.sml.parking.dao;

import ee.sml.parking.model.Parking;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Component
public class ParkingRepository implements Dao<Parking> {
    private JdbcTemplate jdbcTemplate;

    public ParkingRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Parking> rowMapper = (rs, i) -> {
        Parking Parking = new Parking();
        Parking.setId(rs.getLong("id"));
        Parking.setStart(rs.getObject("start_ts", Instant.class));
        Parking.setEnd(rs.getObject("end_ts", Instant.class));
        Parking.setCustomerId(rs.getLong("customer_id"));
        Parking.setPropertyId(rs.getLong("property_id"));

        return Parking;
    };
    
    @Override
    public List<Parking> list() {
        String sql = "SELECT id, start_ts, end_ts, customer_id, property_id FROM parking";
        System.out.println("LIST");

        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void create(Parking parking) {
        String sql = "INSERT INTO parking(start_ts, end_ts, customer_id, property_id) VALUES(?, ?, ?, ?)";
        int updateStatus = jdbcTemplate.update(sql, parking.getStart(), parking.getEnd(), parking.getCustomerId(), parking.getPropertyId());
        if (updateStatus == 1) {
            System.out.println("CREATED PARKING");
        }
    }

    @Override
    public Optional<Parking> get(Long id) {
        return Optional.empty();
    }

    public List<Parking> listForCustomerId(String customerId) {
        String sql = "SELECT id, start_ts, end_ts, customer_id, property_id FROM parking WHERE customer_id = ?";
        return jdbcTemplate.query(sql, rowMapper);
    }

    @Override
    public void update(Parking parking, int id) {

    }

    @Override
    public void delete(int id) {

    }

    public List<Parking> getByCustomerId(Long customerId) {
        String sql = "SELECT id, start_ts, end_ts, customer_id, property_id FROM parking WHERE customer_id = ?";
        return jdbcTemplate.query(sql, rowMapper, customerId.toString());
    }
}
