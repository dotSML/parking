package ee.sml.parking.model;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class Invoice {
    Long id;
    Long customerId;
    Long propertyId;
    String attachment;
    Instant periodStart;
    Instant periodEnd;
    Instant created_at;
    Instant updated_at;
}
