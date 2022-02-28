package ee.sml.parking.dao;

import ee.sml.parking.model.InvoiceRow;
import jdk.jshell.spi.ExecutionControl;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.web.client.HttpServerErrorException;

import java.util.List;
import java.util.Optional;

@Repository
public class InvoiceRowRepository implements Dao<InvoiceRow> {

    private final NamedParameterJdbcTemplate jdbcTemplate;

    public InvoiceRowRepository(NamedParameterJdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final RowMapper<InvoiceRow> INVOICE_ROW_MAPPER = (rs, i) -> {
        InvoiceRow invoiceRowToMap = new InvoiceRow();
        invoiceRowToMap.setId(rs.getLong("id"));
        invoiceRowToMap.setInvoiceId(rs.getLong("invoice_id"));
        invoiceRowToMap.setItemPrice(rs.getBigDecimal("item_price"));
        invoiceRowToMap.setQuantity(rs.getDouble("quantity"));
        invoiceRowToMap.setName(rs.getString("name"));
        invoiceRowToMap.setType(rs.getString("type"));
        return invoiceRowToMap;
    };

    @Override
    public List<InvoiceRow> list() {
        return null;
    }

    @Override
    public void create(InvoiceRow invoiceRow) {
        String sql = "INSERT INTO invoice_row (invoice_id, item_price, quantity, name, type) VALUES (:invoice_id, :item_price, :quantity, :name, :type)";
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("invoice_id", invoiceRow.getInvoiceId())
                .addValue("item_price", invoiceRow.getItemPrice())
                .addValue("quantity", invoiceRow.getQuantity())
                .addValue("name", invoiceRow.getName())
                .addValue("type", invoiceRow.getType());

        jdbcTemplate.update(sql, params);
    }

    @Override
    public Optional<InvoiceRow> get(Long id) {
        return Optional.empty();
    }

    @Override
    public void update(InvoiceRow invoiceRow, int id) {
    }

    @Override
    public void delete(int id) {

    }
}
