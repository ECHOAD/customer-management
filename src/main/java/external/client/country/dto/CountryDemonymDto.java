package external.client.country.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDemonymDto {
    private String f; // Femenino
    private String m; // Masculino
}
