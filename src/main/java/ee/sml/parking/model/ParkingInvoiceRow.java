package ee.sml.parking.model;

import java.math.BigDecimal;

public class ParkingInvoiceRow extends InvoiceRow {
    private double dayParkingHours;
    private double nightParkingHours;
    private BigDecimal dayParkingPrice;
    private BigDecimal nightParkingPrice;
    private BigDecimal totalCost;
    private String type = "parking";


    public ParkingInvoiceRow() {
        this.setType("parking");
    }

    @Override
    public BigDecimal getTotalCost() {
        return dayParkingPrice.multiply(BigDecimal.valueOf(dayParkingHours)).add(nightParkingPrice.multiply(BigDecimal.valueOf(nightParkingHours)));
    }

    @Override
    public String toString() {
        return "ParkingInvoiceRow{" +
                "dayParkingHours=" + dayParkingHours +
                ", nightParkingHours=" + nightParkingHours +
                ", dayParkingPrice=" + dayParkingPrice +
                ", nightParkingPrice=" + nightParkingPrice +
                ", totalCost=" + getTotalCost() +
                '}';
    }

    public double getDayParkingHours() {
        return dayParkingHours;
    }

    public void setDayParkingHours(double dayParkingHours) {
        this.dayParkingHours = dayParkingHours;
    }

    public double getNightParkingHours() {
        return nightParkingHours;
    }

    public void setNightParkingHours(double nightParkingHours) {
        this.nightParkingHours = nightParkingHours;
    }

    public BigDecimal getDayParkingPrice() {
        return dayParkingPrice;
    }

    public void setDayParkingPrice(BigDecimal dayParkingPrice) {
        this.dayParkingPrice = dayParkingPrice;
    }

    public BigDecimal getNightParkingPrice() {
        return nightParkingPrice;
    }

    public void setNightParkingPrice(BigDecimal nightParkingPrice) {
        this.nightParkingPrice = nightParkingPrice;
    }
}
