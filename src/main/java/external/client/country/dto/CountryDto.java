package external.client.country.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryDto {
    private CountryName name;
    private Map<String , CountryDemonymDto> demonyms;

}
