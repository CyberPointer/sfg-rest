package guru.springfamework.controller;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.api.v1.model.VendorListDTO;
import guru.springfamework.services.VendorService;
import junit.framework.TestCase;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;

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

public class VendorControllerTest extends TestCase {
    @Mock
    VendorService vendorService;

    VendorController vendorController;
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        vendorController = new VendorController(vendorService);
        mockMvc = MockMvcBuilders
                .standaloneSetup(vendorController)
                .setControllerAdvice(RestResponseEntityExceptionHandler.class).build();
    }

    @Test
    public void testGetAllVendors() throws Exception {
        System.out.println("TEST1");
        //given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(1L);
        vendor1.setName("amaz");

        VendorDTO vendor2 = new VendorDTO();
        vendor2.setId(2L);
        vendor2.setName("expert");

        VendorListDTO vendors = new VendorListDTO(Arrays.asList(vendor1, vendor2));

        //when
        when(vendorService.getAllVendors()).thenReturn(vendors);

        //given
        mockMvc.perform(get(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vendors", hasSize(2)));
    }

    @Test
    public void testGetCustomerById() throws Exception {
        //given
        VendorDTO vendor1 = new VendorDTO();
        vendor1.setId(1L);
        vendor1.setName("amaz");

        //when
        when(vendorService.getVendorById(anyLong())).thenReturn(vendor1);

        //given
        mockMvc.perform(get(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("amaz")));
    }

    @Test
    public void testCreateNewVendor() throws Exception {
        //given
        VendorDTO newVendor = new VendorDTO();
        newVendor.setId(1L);
        newVendor.setName("Fruit loops");
        newVendor.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Fruit loops");
        //when
        when(vendorService.createNewVendor(any(VendorDTO.class))).thenReturn(newVendor);
        //then
        mockMvc.perform(post(VendorController.BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name", equalTo(newVendor.getName())))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void testUpdateVendor() throws Exception {
        //given
        VendorDTO updatedVendor = new VendorDTO();
        updatedVendor.setId(1L);
        updatedVendor.setName("Fruit loops");
        updatedVendor.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Fruit loops");
        //when
        when(vendorService.saveVendorByDTO(anyLong(), any(VendorDTO.class))).thenReturn(updatedVendor);
        //then
        mockMvc.perform(put(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Fruit loops")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void testPatchVendor() throws Exception {
        //given
        VendorDTO updatedVendor = new VendorDTO();
        updatedVendor.setId(1L);
        updatedVendor.setName("Fruit loops");
        updatedVendor.setVendorUrl(VendorController.BASE_URL + "/1");

        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setName("Fruit loops");
        //when
        when(vendorService.patchVendor(anyLong(), any(VendorDTO.class))).thenReturn(updatedVendor);
        //then
        mockMvc.perform(patch(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(asJsonString(vendorDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", equalTo("Fruit loops")))
                .andExpect(jsonPath("$.vendor_url", equalTo(VendorController.BASE_URL + "/1")));
    }

    @Test
    public void testDeleteById() throws Exception {
        //given
        //when
        //then
        mockMvc.perform(delete(VendorController.BASE_URL + "/1")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(vendorService).deleteVendorById(anyLong());
    }


}