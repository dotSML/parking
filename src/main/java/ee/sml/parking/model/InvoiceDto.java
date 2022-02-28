package ee.sml.parking.model;

import java.util.List;

public record InvoiceDto(Invoice invoice, List<InvoiceRow> invoiceItems) {
}
