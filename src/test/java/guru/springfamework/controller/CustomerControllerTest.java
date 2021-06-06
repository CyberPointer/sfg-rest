package guru.springfamework.controller;

import guru.springfamework.api.v1.model.CustomerDTO;
import guru.springfamework.services.CustomerService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static guru.springfamework.controller.AbstractRestControllerTest.asJsonString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomerControllerTest extends TestCase {
    public static final long ID = 1L;
    public static final String FIRST_NAME = "andrew";
    public static final String LAST_NAME = "Flinstone";
    @Mock
    CustomerService customerService;

    @InjectMocks
    CustomerController customerController;

    MockMvc mockMvc;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(customerController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class)
                .build();
    }

    @Test
    public void testGetCustomers() throws Exception {
        //given
        CustomerDTO andrew = new CustomerDTO();
        andrew.setId(ID);
        andrew.setFirstName(FIRST_NAME);
        CustomerDTO bob = new CustomerDTO();
        bob.setId(2L);
        bob.setFirstName("Bob");
        List<CustomerDTO> customers = Arrays.asList(andrew, bob);

        //when
        when(customerService.getAllCustomers()).thenReturn(customers);

        //then
        mockMvc.perform(MockMvcRequestBuilders
                .get(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.customers", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        //given
        CustomerDTO andrew = new CustomerDTO();
        andrew.setId(ID);
        andrew.setFirstName(FIRST_NAME);

        //when
        when(customerService.getCustomerById(anyLong())).thenReturn(andrew);

        //then
        mockMvc.perform(MockMvcRequestBuilders
                .get(CustomerController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)));
    }

    @Test
    public void testCreateNewCustomer() throws Exception {
        //given
        CustomerDTO andrew = new CustomerDTO();
        andrew.setId(ID);
        andrew.setFirstName(FIRST_NAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setId(ID);
        returnDTO.setFirstName(FIRST_NAME);
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + ID);

        //when
        when(customerService.createNewCustomer(andrew)).thenReturn(returnDTO);

        //then
        mockMvc.perform(post(CustomerController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(andrew)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testUpdateCustomer() throws Exception {
        //given
        CustomerDTO andrew = new CustomerDTO();
        andrew.setId(ID);
        andrew.setFirstName(FIRST_NAME);

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setId(ID);
        returnDTO.setFirstName(FIRST_NAME);
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + ID);

        //when
        when(customerService.saveCustomerByDTO(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //then
        mockMvc.perform(put(CustomerController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(andrew)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testPatchCustomer() throws Exception {
        //given
        CustomerDTO andrew = new CustomerDTO();
        andrew.setId(ID);
        andrew.setFirstName(FIRST_NAME);
        andrew.setLastName("ABABA");

        CustomerDTO returnDTO = new CustomerDTO();
        returnDTO.setId(ID);
        returnDTO.setFirstName(FIRST_NAME);
        returnDTO.setLastName(LAST_NAME);
        returnDTO.setCustomerUrl(CustomerController.BASE_URL + "/" + ID);

        //when
        when(customerService.patchCustomer(anyLong(), any(CustomerDTO.class))).thenReturn(returnDTO);

        //then
        mockMvc.perform(patch(CustomerController.BASE_URL + "/" + ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(andrew)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", equalTo(FIRST_NAME)))
                .andExpect(jsonPath("$.lastName", equalTo(LAST_NAME)))
                .andExpect(jsonPath("$.customer_url", equalTo("/api/v1/customers/1")));
    }

    @Test
    public void testDeleteCustomerById() throws Exception {
        mockMvc.perform(delete(CustomerController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(customerService).deleteCustomerById(anyLong());
    }

}
