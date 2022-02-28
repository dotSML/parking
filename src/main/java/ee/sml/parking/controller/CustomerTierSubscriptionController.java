package ee.sml.parking.controller;

import ee.sml.parking.model.CustomerTierSubscription;
import ee.sml.parking.service.CustomerTierSubscriptionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/customer-tier-subscription")
public class CustomerTierSubscriptionController {
    private CustomerTierSubscriptionService customerTierSubscriptionService;

    public CustomerTierSubscriptionController(CustomerTierSubscriptionService customerTierSubscriptionService) {
        this.customerTierSubscriptionService = customerTierSubscriptionService;
    }

    @GetMapping("/")
    public List<CustomerTierSubscription> list() {
        return customerTierSubscriptionService.list();
    }


    @PostMapping("/{customerId}/{tierId}")
    public CustomerTierSubscription subscribeCustomerToTier(@PathVariable Long customerId, @PathVariable Long tierId) {
        return customerTierSubscriptionService.subscribeCustomerToTier(customerId, tierId);
    }

}
