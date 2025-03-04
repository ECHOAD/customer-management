package infrastructure.country.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CountryName {
    private String common;
    private String official;
    private Map<String, Map<String, String>> nativeName;
}
