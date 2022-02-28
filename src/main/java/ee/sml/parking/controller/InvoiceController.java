package ee.sml.parking.controller;


import ee.sml.parking.model.Customer;
import ee.sml.parking.model.Parking;
import ee.sml.parking.service.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Optional;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;
    private final CustomerService customerService;
    private final ParkingService parkingService;

    public InvoiceController(CustomerService customerService, ParkingService parkingService, InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
        this.customerService = customerService;
        this.parkingService = parkingService;
    }

    @PostMapping("/generate")
    public String generateInvoiceForCustomer(@RequestBody Map<String, Object> payload) {
        Long customerId = Long.valueOf(payload.get("customerId").toString());
        Optional<Customer> maybeCustomer = customerService.getCustomerById(customerId);
        if (maybeCustomer.isEmpty()) {
            throw new MissingResourceException("Customer not found", "Customer", customerId.toString());
        }

        Customer customer = maybeCustomer.get();

        List<Parking> parkings = parkingService.getParkingsForCustomer(customer.getId());

        LocalDate periodStart = LocalDate.parse(payload.get("periodStart").toString());
        LocalDate periodEnd = LocalDate.parse(payload.get("periodEnd").toString());

        return invoiceService.generate(customer, parkings, periodStart, periodEnd);
        // 5. Calculate parking price according to customer tier
        // 6. Get CustomerTier price
        // 7. Add all together
    }
}
