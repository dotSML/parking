package ee.sml.parking.dao;

import ee.sml.parking.model.Invoice;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public class InvoiceRepository implements Dao<Invoice> {
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InvoiceRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    RowMapper<Invoice> INVOICE_ROW_MAPPER = (rs, i) -> Invoice.builder()
            .id(rs.getLong("id"))
            .periodStart(rs.getObject("period_start_ts", Instant.class))
            .periodEnd(rs.getObject("period_end_ts", Instant.class))
            .customerId(rs.getLong("customer_id"))
            .build();
    
    @Override
    public List<Invoice> list() {
        return null;
    }

    @Override
    public void create(Invoice invoice) {
        String sql = "INSERT INTO invoice (customer_id, property_id, attachment, period_start, period_end) VALUES (:customer_id, :property_id, :attachment, :period_start, :period_end)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("customer_id", invoice.getCustomerId())
                .addValue("property_id", invoice.getPropertyId())
                .addValue("attachment", invoice.getAttachment())
                .addValue("period_start", invoice.getPeriodStart())
                .addValue("period_end", invoice.getPeriodEnd());
        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<Invoice> get(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(Invoice invoice, int id) {

    }

    @Override
    public void delete(int id) {

    }
}
