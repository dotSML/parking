package ee.sml.parking.controller;

import ee.sml.parking.model.Customer;
import ee.sml.parking.service.CustomerService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping( "/user")
public class CustomerController {
    private CustomerService customerService;

    public CustomerController(CustomerService customerService) {
        this.customerService = customerService;
    }

    @GetMapping("/hello")
    public List<Customer> test() {
        return customerService.listCustomers();
    }

    @GetMapping("/new")
    public void createCustomer() {
        Customer customer = Customer.builder()
                .email("new@user.com")
                .firstName("New")
                .lastName("User")
                .build();
        customerService.createCustomer(customer);
    }
}
