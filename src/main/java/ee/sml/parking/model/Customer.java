package ee.sml.parking.model;

import lombok.Builder;
import lombok.Value;

import java.time.Instant;

@Value
@Builder
public class Customer {
    Long id;
    String firstName;
    String lastName;
    String email;
    Instant createdAt;
    Instant updatedAt;
}
