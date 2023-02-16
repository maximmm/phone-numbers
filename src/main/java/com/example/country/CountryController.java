package com.example.country;

import com.example.country.validation.Phone;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.ResponseEntity.ok;

@Validated
@RequiredArgsConstructor
@RestController
public class CountryController {

    private final CountryService service;

    @PostMapping("/country")
    public ResponseEntity<?> country(@RequestBody @Phone String phone) {
        return ok(service.resolveCountryBy(phone));
    }
}
