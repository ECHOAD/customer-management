package external.client.country.service;

import external.client.country.dto.CountryDto;

public interface CountryService {
    CountryDto getCountryByCode(String countryCode);
    String findDemonymByCountryCode(String countryCode);
}
