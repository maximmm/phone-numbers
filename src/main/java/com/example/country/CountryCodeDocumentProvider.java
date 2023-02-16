package com.example.country;

import lombok.SneakyThrows;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;

import static org.jsoup.Jsoup.connect;

@Component
public class CountryCodeDocumentProvider {
    private static final String URL = "https://en.wikipedia.org/wiki/List_of_country_calling_codes";

    @SneakyThrows
    public Document getDocument() {
        return connect(URL).get();
    }
}
