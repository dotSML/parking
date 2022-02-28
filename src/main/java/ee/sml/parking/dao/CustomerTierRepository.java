package ee.sml.parking.dao;

import ee.sml.parking.model.CustomerTier;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CustomerTierRepository implements Dao<CustomerTier> {
    private JdbcTemplate jdbcTemplate;

    public CustomerTierRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<CustomerTier> CUSTOMER_TIER_ROW_MAPPER = (rs, i) -> CustomerTier.builder()
            .id(rs.getLong("id"))
            .code(rs.getString("code"))
            .name(rs.getString("name"))
            .baseFee(rs.getBigDecimal("base_fee"))
            .dayParkingFee(rs.getBigDecimal("parking_day_fee"))
            .nightParkingFee(rs.getBigDecimal("parking_night_fee"))
            .build();


    @Override
    public List<CustomerTier> list() {
        return null;
    }

    @Override
    public void create(CustomerTier customerTier) {
        String sql = "INSERT INTO customer_tier (code,name, base_fee, parking_day_fee, parking_night_fee, property_id) VALUES(?, ?, ?, ?, ?, ?)";
        int updateStatus = jdbcTemplate.update(sql, customerTier.getCode(), customerTier.getName(), customerTier.getBaseFee()
                , customerTier.getDayParkingFee(), customerTier.getNightParkingFee(), customerTier.getPropertyId());
    }

    @Override
    public Optional<CustomerTier> get(Long id) {
        String sql = "SELECT id, code, name, base_fee, parking_day_fee, parking_night_fee FROM customer_tier WHERE id = ?";

        return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CUSTOMER_TIER_ROW_MAPPER, id));
    }

    @Override
    public void update(CustomerTier customerTier, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
