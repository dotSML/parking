package ee.sml.parking.model;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;

public class Parking {
    private Long id;
    private Instant start;
    private Instant end;
    private Long customerId;
    private Long propertyId;

    public Parking(Long id, Instant start, Instant end, Long customerId, Long propertyId) {
        this.id = id;
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.propertyId = propertyId;
    }

    public Parking(Instant start, Instant end, Long customerId, Long propertyId) {
        this.start = start;
        this.end = end;
        this.customerId = customerId;
        this.propertyId = propertyId;
    }

    public Parking() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Instant getStart() {
        return start;
    }

    public void setStart(Instant start) {
        this.start = start;
    }

    public Instant getEnd() {
        return end;
    }

    public void setEnd(Instant end) {
        this.end = end;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Long getPropertyId() {
        return propertyId;
    }

    public void setPropertyId(Long propertyId) {
        this.propertyId = propertyId;
    }

    private static long hoursDifference(LocalDateTime ldt1, LocalDateTime ldt2) {
        long minutesDiff = ChronoUnit.MINUTES.between(ldt1, ldt2);
        long hoursDiff = Math.round(Math.ceil(minutesDiff / 30.0));
        return hoursDiff;
    }


    public static int determineDayCycle(int dayTimeIntervalStart, int nightTimeIntervalLateStart) {
        return nightTimeIntervalLateStart - dayTimeIntervalStart;
    }

    public ParkingBillableHours getBillableHours() {
        LocalDateTime startTime = LocalDateTime.ofInstant(start, ZoneId.systemDefault());
        LocalDateTime endTime = LocalDateTime.ofInstant(end, ZoneId.systemDefault());
        // Rate intervals
        int dayTimeIntervalStart = 7;
        int nightTimeIntervalLateStart = 19;
        // Counted hours per rate
        int dayHoursTotal = 0;
        int nightHoursTotal = 0;

        int startHour = startTime.getHour();

        // Calculate the hours difference
        long hourDiff = hoursDifference(
                startTime,
                endTime);
        System.out.println("Hour difference found: " + hourDiff);

        // Handle parking for full days
        if (hourDiff > 24) {
            int dayCycle = determineDayCycle(dayTimeIntervalStart, nightTimeIntervalLateStart);
            long fullDays = hourDiff / 24;
            nightHoursTotal += (24 - dayCycle) * fullDays;
            dayHoursTotal += dayCycle * fullDays;
            hourDiff = hourDiff % 24;
        }

        // Handle the parking for less than full day
        while (hourDiff > 0) {
            if (startHour < dayTimeIntervalStart) { // Before the day interval -> night
                nightHoursTotal++;
            } else if (startHour < nightTimeIntervalLateStart) { // Before the night interval -> day
                dayHoursTotal++;
            } else { // After the day interval -> night
                nightHoursTotal++;
            }
            startHour++;
            if (startHour > 23) // At midnight reset the hour to 0
                startHour = 0;
            hourDiff--;
        }

        return new ParkingBillableHours(dayHoursTotal, nightHoursTotal);
    }

    @Override
    public String toString() {
        return "Parking{" +
                "id=" + id +
                ", start=" + start +
                ", end=" + end +
                ", customerId='" + customerId + '\'' +
                ", propertyId='" + propertyId + '\'' +
                '}';
    }
}
