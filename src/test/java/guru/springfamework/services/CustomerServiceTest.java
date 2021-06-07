package guru.springfamework.services;

import guru.springfamework.api.v1.mapper.CustomerMapper;
import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.api.v1.model.CustomerListDTO;
import guru.springfamework.domain.Customer;
import guru.springfamework.repositories.CustomerRepository;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CustomerServiceTest extends TestCase {
    public static final long ID = 1L;
    public static final String FIRST_NAME = "Andrew";
    public static final String LAST_NAME = "Golotoa";
    CustomerService customerService;
    @Mock
    CustomerRepository customerRepository;


    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerServiceImpl(customerRepository, CustomerMapper.INSTANCE);
    }

    @Test
    public void testGetAllCustomers() {
        //given
        Customer andrew = new Customer();
        andrew.setId(ID);
        andrew.setFirstName(FIRST_NAME);
        andrew.setFirstName(LAST_NAME);

        Customer bob = new Customer();
        bob.setId(2L);
        bob.setFirstName("Bob");
        bob.setFirstName("Marley");

        List<Customer> customers = Arrays.asList(andrew, bob);
        //when
        when(customerRepository.findAll()).thenReturn(customers);

        //then
        CustomerListDTO customerDTOS = customerService.getAllCustomers();
        assertEquals(2, customerDTOS.getCustomers().size());
    }

    @Test
    public void testGetCustomerById() {
        //given
        Customer andrew = new Customer();
        andrew.setId(ID);
        andrew.setFirstName(FIRST_NAME);
        andrew.setFirstName(LAST_NAME);

        //when
        when(customerRepository.findById(anyLong())).thenReturn(java.util.Optional.of(andrew));

        //then
        CustomerDTO customerDTO = customerService.getCustomerById(ID);
        assertEquals(customerDTO.getFirstName(), andrew.getFirstName());
        assertEquals(customerDTO.getLastName(), andrew.getLastName());
    }

    @Test
    public void testSaveCustomer() {
        //given
        CustomerDTO customerDTO = new CustomerDTO();
        customerDTO.setFirstName(FIRST_NAME);

        Customer andrew = new Customer();
        andrew.setId(ID);
        andrew.setFirstName(FIRST_NAME);
        andrew.setLastName(LAST_NAME);

        //when
        when(customerRepository.save(any(Customer.class))).thenReturn(andrew);
        CustomerDTO savedDto = customerService.createNewCustomer(customerDTO);

        //then
        assertEquals(FIRST_NAME, savedDto.getFirstName());
        assertEquals("/api/v1/customers/1", savedDto.getCustomerUrl());
    }

    @Test
    public void testDeleteCustomerById(){
        //given
        //when
        customerRepository.deleteById(ID);
        //then
        verify(customerRepository).deleteById(anyLong());

    }
}