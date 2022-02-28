package ee.sml.parking.service;

import ee.sml.parking.dao.CustomerRepository;
import ee.sml.parking.model.Customer;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {

    private CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> listCustomers() {
        return customerRepository.list();
    }


    public Customer createCustomer(Customer customer) {
        customerRepository.create(customer);
        return customer;
    }

    public Optional<Customer> getCustomerById(Long customerId) {
        return customerRepository.get(customerId);
    }
}
