package com.example.country;

import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

import static java.util.Arrays.stream;
import static java.util.Optional.ofNullable;
import static java.util.stream.IntStream.range;

@Component
@RequiredArgsConstructor
public class CountryCodeFetcher {
    private static final String CLOSEST_SIBLING_ID = "Alphabetical_listing_by_country_or_region";
    private static final String ROW_TAG = "tr";
    private static final String COLUMN_TAG = "td";

    private static final int COUNTRY_COLUMN_IDX = 0;
    private static final int PHONE_CODE_COLUMN_IDX = 1;

    private final CountryCodeDocumentProvider provider;

    public Map<String, String> fetchData() {
        var doc = provider.getDocument();
        var table = ofNullable(doc.getElementById(CLOSEST_SIBLING_ID))
                .map(Element::parent)
                .map(Element::nextElementSibling)
                .map(Element::firstElementChild)
                .orElseThrow(unableToFetchTable());

        Map<String, String> resultMap = new HashMap<>();

        var rows = table.select(ROW_TAG);

        range(1, rows.size())
                .mapToObj(rows::get)
                .map(row -> row.select(COLUMN_TAG))
                .forEach(cols -> {
                    var country = columnValue(cols, COUNTRY_COLUMN_IDX);
                    var codes = columnValue(cols, PHONE_CODE_COLUMN_IDX);

                    stream(codes.split("(, |\\[)"))
                            .filter(code -> code.startsWith("+"))
                            .forEach(code -> resultMap.put(code, country));
                });

        return resultMap;
    }

    private Supplier<RuntimeException> unableToFetchTable() {
        return () -> new RuntimeException("Unable to fetch country phone numbers table");
    }

    private String columnValue(Elements cols, int countryColumnIdx) {
        return cols.select(COLUMN_TAG).get(countryColumnIdx).text();
    }
}
