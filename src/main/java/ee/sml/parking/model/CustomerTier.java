package ee.sml.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Value;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.Period;

@Value
@Builder
public class CustomerTier {
    Long id;
    String name;
    String code;
    BigDecimal baseFee;
    BigDecimal dayParkingFee;
    BigDecimal nightParkingFee;
    Long propertyId;


    @JsonIgnore
    public static Double getBaseFeePeriodQuantity(LocalDate start, LocalDate end) {
        double res;

        if (start.getMonthValue() == end.getMonthValue()) {
            Period period = Period.between(start, end.plusDays(1));
            long days = period.getDays();
            res = (double) days / start.lengthOfMonth();
        } else {
            int daysLeftInPrevMonth = start.lengthOfMonth() - start.getDayOfMonth();
            int diffOfMonths = end.getMonthValue() - start.getMonthValue() - 1;
            int daysLeftInNextMonth = end.getDayOfMonth();
            res = (double) daysLeftInPrevMonth / start.lengthOfMonth() + diffOfMonths
                    + (double) daysLeftInNextMonth / end.lengthOfMonth();
        }

        return BigDecimal.valueOf(res)
                .setScale(1, RoundingMode.HALF_UP).doubleValue();
    }
}
