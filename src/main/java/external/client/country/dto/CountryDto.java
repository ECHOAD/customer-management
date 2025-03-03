package external.client.country.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@NoArgsConstructor
public class CountryDto {
    private CountryName name;
    private Map<String , CountryDemonymDto> demonyms;

}
