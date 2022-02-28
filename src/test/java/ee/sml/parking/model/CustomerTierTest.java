package ee.sml.parking.model;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class CustomerTierTest {

    @ParameterizedTest
    @MethodSource("params")
    void testCustomerTier(LocalDate start, LocalDate end, double expectedValue) {
        Double baseFeePeriodQuantity = CustomerTier.getBaseFeePeriodQuantity(start, end);

        assertThat(baseFeePeriodQuantity).isEqualTo(expectedValue);
    }

    static Stream<Arguments> params() {
        return Stream.of(
                Arguments.of(LocalDate.of(2022, 2, 14), LocalDate.of(2022, 2, 28), 0.5)
        );
    }
}