package ee.sml.parking.model;

import java.math.BigDecimal;

public class InvoiceRow {
    private Long id;
    private String name;
    private BigDecimal itemPrice;
    private Double quantity;
    private Long invoiceId;
    private String type;

    public Long getInvoiceId() {
        return invoiceId;
    }

    public void setInvoiceId(Long invoiceId) {
        this.invoiceId = invoiceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(BigDecimal itemPrice) {
        this.itemPrice = itemPrice;
    }

    public Double getQuantity() {
        return quantity;
    }

    public void setQuantity(Double quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getTotalCost() {
        return itemPrice.multiply(BigDecimal.valueOf(quantity));
    }

    @Override
    public String toString() {
        return "InvoiceRow{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", itemPrice=" + itemPrice +
                ", quantity=" + quantity +
                ", totalPrice=" + getTotalCost() +
                '}';
    }
}
