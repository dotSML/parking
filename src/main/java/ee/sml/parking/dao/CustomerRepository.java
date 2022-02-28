package ee.sml.parking.dao;

import ee.sml.parking.model.Customer;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CustomerRepository implements Dao<Customer> {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    private static final RowMapper<Customer> CUSTOMER_ROW_MAPPER = (rs, i) -> Customer.builder()
            .id(rs.getLong("id"))
            .firstName(rs.getString("first_name"))
            .lastName(rs.getString("last_name"))
            .email(rs.getString("email"))
            .build();

    public CustomerRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Customer> list() {
        String sql = "SELECT id, first_name, last_name, email, tier FROM customer";

        return jdbcTemplate.query(sql, CUSTOMER_ROW_MAPPER);
    }

    @Override
    public void create(Customer customer) {
        String sql = "INSERT INTO customer(first_name, last_name, email) VALUES (:first_name, :last_name, :email)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("first_name", customer.getFirstName())
                .addValue("last_name", customer.getLastName())
                .addValue("email", customer.getEmail());
        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Customer> get(Long id) {
        String sql = "SELECT id, first_name, last_name, email FROM customer WHERE id = :id";
        MapSqlParameterSource params = new MapSqlParameterSource("id", id);
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(sql, params, CUSTOMER_ROW_MAPPER));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public void update(Customer customer, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
