package com.example.country;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class CountryCodeStorage extends HashMap<String, String> {

    private final CountryCodeFetcher fetcher;

    @PostConstruct
    public void loadData() {
        putAll(fetcher.fetchData());
    }
}
