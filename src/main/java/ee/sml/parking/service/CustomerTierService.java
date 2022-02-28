package ee.sml.parking.service;

import ee.sml.parking.dao.CustomerTierRepository;
import ee.sml.parking.model.CustomerTier;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CustomerTierService {
    private CustomerTierRepository customerTierRepository;
    private CustomerService customerService;


    public CustomerTierService(CustomerService customerService, CustomerTierRepository customerTierRepository) {
        this.customerService = customerService;
        this.customerTierRepository = customerTierRepository;
    }


    public CustomerTier getCustomerTierById(Long tierId) {
        return customerTierRepository.get(tierId).orElseGet(this::getDefaultCustomerTier);
    }

    public CustomerTier getDefaultCustomerTier() {
        return CustomerTier.builder().baseFee(BigDecimal.valueOf(0)).nightParkingFee(BigDecimal.valueOf(0.75)).dayParkingFee(BigDecimal.valueOf(1.50)).build();
    }

    public void createCustomerTier(CustomerTier customerTier) {
        customerTierRepository.create(customerTier);
    }

}
