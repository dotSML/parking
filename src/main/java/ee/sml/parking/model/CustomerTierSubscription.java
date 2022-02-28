package ee.sml.parking.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class CustomerTierSubscription {
    Long id;
    Long customerId;
    Long customerTierId;
    Instant periodStart;
    Instant periodEnd;

    @JsonIgnore
    public Instant getBillablePeriodEnd() {
        if (periodEnd != null) {
            return periodEnd;
        } else {
            return Instant.now();
        }
    }

}
