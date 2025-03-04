package infrastructure.country.service;

import infrastructure.country.dto.CountryDto;

public interface CountryService {
    CountryDto getCountryByCode(String countryCode);
    String findDemonymByCountryCode(String countryCode);
}
