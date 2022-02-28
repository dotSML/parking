package ee.sml.parking.dao;

import ee.sml.parking.model.CustomerTierSubscription;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Component
public class CustomerTierSubscriptionRepository implements Dao<CustomerTierSubscription> {
    private JdbcTemplate jdbcTemplate;

    public CustomerTierSubscriptionRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<CustomerTierSubscription> CUSTOMER_TIER_SUBSCRIPTION_ROW_MAPPER = (rs, i) -> CustomerTierSubscription.builder()
            .id(rs.getLong("id"))
            .customerId(rs.getLong("customer_id"))
            .customerTierId(rs.getLong("customer_tier_id"))
            .periodStart(Instant.ofEpochMilli(rs.getDate("start_ts").getTime()))
            .periodEnd(rs.getDate("end_ts") != null ? Instant.ofEpochMilli(rs.getDate("end_ts").getTime()) : null)
            .build();

    @Override
    public List<CustomerTierSubscription> list() {
        String sql = "SELECT id, customer_id, customer_tier_id, start_ts, end_ts FROM customer_tier_subscription";
        return jdbcTemplate.query(sql, CUSTOMER_TIER_SUBSCRIPTION_ROW_MAPPER);
    }

    public List<CustomerTierSubscription> listForPeriodForCustomer(Long customerId, LocalDate start, LocalDate end) {
        String sql = "SELECT id, customer_id, customer_tier_id, start_ts, end_ts FROM customer_tier_subscription WHERE customer_id = ? AND (start_ts >= ? AND end_ts <= ?) OR (start_ts >= ? AND end_ts IS NULL)";
        return jdbcTemplate.query(sql, CUSTOMER_TIER_SUBSCRIPTION_ROW_MAPPER, customerId, start, end, start);
    }

    @Override
    public void create(CustomerTierSubscription customerTierSubscription) {
        String sql = "INSERT INTO customer_tier_subscription (customer_id, customer_tier_id, start_ts, end_ts) VALUES (?, ?, ?, ?)";
        int updateStatus = jdbcTemplate.update(sql,
                customerTierSubscription.getCustomerId(),
                customerTierSubscription.getCustomerTierId(),
                customerTierSubscription.getPeriodStart(),
                customerTierSubscription.getPeriodEnd());
    }

    @Override
    public Optional<CustomerTierSubscription> get(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(CustomerTierSubscription customerTierSubscription, int id) {

    }

    @Override
    public void delete(int id) {

    }

    public Optional<CustomerTierSubscription> findByCustomerId(Long id) {
        String sql = "SELECT id, customer_id, customer_tier_id, start_ts, end_ts FROM customer_tier_subscription WHERE customer_id = ?";

        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CUSTOMER_TIER_SUBSCRIPTION_ROW_MAPPER, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<CustomerTierSubscription> findByCustomerIdAndPeriod(Long customerId, Instant start) {
        System.out.println(customerId);
        System.out.println(Timestamp.from(start));
        String sql = "SELECT id, customer_id, customer_tier_id, start_ts, end_ts FROM customer_tier_subscription WHERE customer_id = ? AND (start_ts <= ? AND end_ts IS NULL OR start_ts >= ? AND end_ts >= ?)";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CUSTOMER_TIER_SUBSCRIPTION_ROW_MAPPER, customerId, Timestamp.from(start), Timestamp.from(start), Timestamp.from(start)));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public Optional<CustomerTierSubscription> findActiveByCustomerId(Long customerId) {
        String sql = "SELECT id, customer_id, customer_tier_id, start_ts, end_ts FROM customer_tier_subscription WHERE customer_id = ? AND end_ts IS NULL";
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, CUSTOMER_TIER_SUBSCRIPTION_ROW_MAPPER, customerId));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    public void endCustomerTierSubscription(Long id) {
        String sql = "UPDATE customer_tier_subscription SET end_ts = CURRENT_TIMESTAMP WHERE id = ?";
        jdbcTemplate.update(sql, id);
    }
}
