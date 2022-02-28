package ee.sml.parking.service;


import ee.sml.parking.dao.InvoiceRepository;
import ee.sml.parking.dao.InvoiceRowRepository;
import ee.sml.parking.model.*;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicInteger;

@Service
public class InvoiceService {
    private final CustomerTierSubscriptionService customerTierSubscriptionService;
    private final CustomerTierService customerTierService;
    private final InvoiceRepository invoiceRepository;
    private final InvoiceRowRepository invoiceRowRepository;

    public InvoiceService(CustomerTierSubscriptionService customerTierSubscriptionService, CustomerTierService customerTierService, InvoiceRepository invoiceRepository, InvoiceRowRepository invoiceRowRepository) {
        this.invoiceRepository = invoiceRepository;
        this.invoiceRowRepository = invoiceRowRepository;
        this.customerTierSubscriptionService = customerTierSubscriptionService;
        this.customerTierService = customerTierService;
    }

    public String generate(Customer customer, List<Parking> parkings, LocalDate start, LocalDate end) {
        List<InvoiceRow> invoiceItems = new ArrayList<>();

        customerTierSubscriptionService.listForPeriodForCustomer(customer.getId(), start, end).forEach((ct) -> {
            CustomerTier foundCustomerTier = customerTierService.getCustomerTierById(ct.getCustomerTierId());
            InvoiceRow invoiceItem = new InvoiceRow();
            invoiceItem.setName(foundCustomerTier.getName());
            invoiceItem.setItemPrice(foundCustomerTier.getBaseFee());
            LocalDate periodStart = LocalDate.ofInstant(ct.getPeriodStart(), ZoneId.systemDefault());
            LocalDate periodEnd = LocalDate.ofInstant(ct.getBillablePeriodEnd(), ZoneId.systemDefault());
            invoiceItem.setQuantity(CustomerTier.getBaseFeePeriodQuantity(periodStart, periodEnd));
            invoiceItems.add(invoiceItem);
        });

        AtomicInteger totalParkingDayHours = new AtomicInteger();
        AtomicInteger totalParkingNightHours = new AtomicInteger();

        parkings.forEach((parking) -> {
            ParkingBillableHours billableHours = parking.getBillableHours();
            totalParkingDayHours.addAndGet(billableHours.dayHours());
            totalParkingNightHours.addAndGet(billableHours.nightHours());

            Optional<CustomerTierSubscription> subscriptionAtTheTimeOfParking = customerTierSubscriptionService.getCustomerTierByCustomerIdAndPeriod(parking.getCustomerId(), parking.getStart());

            CustomerTier tierAtTimeOfParking;
            if (subscriptionAtTheTimeOfParking.isEmpty()) {
                tierAtTimeOfParking = customerTierService.getDefaultCustomerTier();
            } else {
                tierAtTimeOfParking = customerTierService.getCustomerTierById(subscriptionAtTheTimeOfParking.get().getId());
            }

            ParkingInvoiceRow parkingInvoiceRow = new ParkingInvoiceRow();
            ParkingBillableHours parkingHours = parking.getBillableHours();
            parkingInvoiceRow.setName("Parking");
            parkingInvoiceRow.setDayParkingHours(parkingHours.dayHours());
            parkingInvoiceRow.setNightParkingHours(parkingHours.nightHours());
            parkingInvoiceRow.setDayParkingPrice(tierAtTimeOfParking.getDayParkingFee());
            parkingInvoiceRow.setNightParkingPrice(tierAtTimeOfParking.getNightParkingFee());

            invoiceItems.add(parkingInvoiceRow);
        });

        Invoice invoiceToCreate = Invoice.builder()
                .customerId(customer.getId())
                .propertyId(1L)
                .attachment("")
                .periodStart(Instant.now())
                .periodEnd(Instant.now())
                .created_at(Instant.now())
                .updated_at(Instant.now())
                .build();

        invoiceRepository.create(invoiceToCreate);
        invoiceItems.forEach(invoiceRowRepository::create);


        invoiceItems.forEach(System.out::println);
        return new InvoiceDto(Invoice.builder().customerId(customer.getId()).propertyId(1L).attachment("").periodStart(Instant.now()).periodEnd(Instant.now()).created_at(Instant.now()).updated_at(Instant.now()).build(), invoiceItems).toString();
    }
}
