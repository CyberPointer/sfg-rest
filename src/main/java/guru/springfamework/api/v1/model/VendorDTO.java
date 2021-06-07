package guru.springfamework.api.v1.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendorDTO {
    private Long id;
    @ApiModelProperty(value = "name of the vendor", required = true)
    private String name;
    @ApiModelProperty(value = "vendor's url for accessing concrete vendor by id")
    @JsonProperty("vendor_url")
    private String vendorUrl;
}
