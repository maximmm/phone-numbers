package com.example.country;

import com.google.i18n.phonenumbers.NumberParseException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance;
import static java.lang.String.valueOf;

@RequiredArgsConstructor
@Service
public class CountryService {
    private final CountryCodeStorage storage;

    @SneakyThrows(NumberParseException.class)
    public String resolveCountryBy(String phone) {
        var phoneNumber = getInstance().parse(phone, "");
        var countryCode = "+".concat(valueOf(phoneNumber.getCountryCode()));
        return storage.get(countryCode);
    }
}
