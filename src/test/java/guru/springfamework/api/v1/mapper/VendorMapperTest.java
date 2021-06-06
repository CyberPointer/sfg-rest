package guru.springfamework.api.v1.mapper;

import guru.springfamework.api.v1.model.VendorDTO;
import guru.springfamework.domain.Vendor;
import junit.framework.TestCase;

public class VendorMapperTest extends TestCase {
    final Long ID = 1L;
    final String VENDOR_NAME = "kamazon";

    VendorMapper vendorMapper = VendorMapper.INSTANCE;

    public void testVendorToVendorDTO() {
        //given
        Vendor vendor = new Vendor();
        vendor.setId(ID);
        vendor.setName(VENDOR_NAME);

        //when
        VendorDTO vendorDTO = vendorMapper.vendorToVendorDTO(vendor);
        //then
        assertEquals(ID, vendorDTO.getId());
        assertEquals(VENDOR_NAME, vendorDTO.getName());
    }

    public void testVendorDtoToVendor() {
        //given
        VendorDTO vendorDTO = new VendorDTO();
        vendorDTO.setId(ID);
        vendorDTO.setName(VENDOR_NAME);

        //when
        Vendor vendor = vendorMapper.vendorDTOtoVendor(vendorDTO);
        //then
        assertEquals(ID, vendor.getId());
        assertEquals(VENDOR_NAME, vendor.getName());
    }
}