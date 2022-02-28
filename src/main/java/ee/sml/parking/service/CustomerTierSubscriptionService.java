package ee.sml.parking.service;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ee.sml.parking.dao.CustomerTierSubscriptionRepository;
import ee.sml.parking.model.Customer;
import ee.sml.parking.model.CustomerTier;
import ee.sml.parking.model.CustomerTierSubscription;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.util.List;
import java.util.MissingResourceException;
import java.util.Optional;

@Service
public class CustomerTierSubscriptionService {
    private final CustomerTierSubscriptionRepository customerTierSubscriptionRepository;
    private final CustomerService customerService;
    private final CustomerTierService customerTierService;

    public CustomerTierSubscriptionService(CustomerTierSubscriptionRepository customerTierSubscriptionRepository, CustomerService customerService, CustomerTierService customerTierService) {
        this.customerTierSubscriptionRepository = customerTierSubscriptionRepository;
        this.customerService = customerService;
        this.customerTierService = customerTierService;
    }

    public List<CustomerTierSubscription> list() {
        return customerTierSubscriptionRepository.list();
    }

    public List<CustomerTierSubscription> listForPeriodForCustomer(Long customerId, LocalDate start, LocalDate end) {
        return customerTierSubscriptionRepository.listForPeriodForCustomer(customerId, start, end);
    }

    public CustomerTierSubscription subscribeCustomerToTier(Long customerId, Long tierId) {
        Optional<Customer> customer = customerService.getCustomerById(customerId);
        if (customer.isEmpty()) {
            throw new MissingResourceException("Some error?", "Customer", customerId.toString());
        }
        CustomerTier customerTier = customerTierService.getCustomerTierById(tierId);


        Optional<CustomerTierSubscription> previousActiveSubscription = getActiveSubscriptionForCustomer(customerId);

        previousActiveSubscription.ifPresent(customerTierSubscription -> customerTierSubscriptionRepository.endCustomerTierSubscription(customerTierSubscription.getId()));

        CustomerTierSubscription customerTierSubscriptionToCreate = CustomerTierSubscription.builder()
                .customerTierId(tierId)
                .customerId(customerId)
                .periodStart(Instant.now())
                .build();


        customerTierSubscriptionRepository.create(customerTierSubscriptionToCreate);

        return customerTierSubscriptionToCreate;
    }

    public Optional<CustomerTierSubscription> getActiveSubscriptionForCustomer(Long customerId) {
        return customerTierSubscriptionRepository.findActiveByCustomerId(customerId);
    }

    public Optional<CustomerTierSubscription> getTierByCustomerId(Long id) {
        return customerTierSubscriptionRepository.findByCustomerId(id);
    }

    @JsonIgnore
    public Optional<CustomerTierSubscription> getCustomerTierByCustomerIdAndPeriod(Long customerId, Instant start) {
        return customerTierSubscriptionRepository.findByCustomerIdAndPeriod(customerId, start);
    }
}
