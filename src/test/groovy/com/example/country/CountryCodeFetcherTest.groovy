package com.example.country

import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import spock.lang.Specification
import spock.lang.Subject

class CountryCodeFetcherTest extends Specification {

    def provider = Stub(CountryCodeDocumentProvider)

    @Subject
    def fetcher = new CountryCodeFetcher(provider)

    def 'should fetch records'() {
        given:
        provider.getDocument() >> Jsoup.parse(this.getClass().getResource(('/country_codes.html')).text)

        when:
        def result = fetcher.fetchData()

        then:
        result.size() == 283

        and:
        result.get('+371') == 'Latvia'
        result.get('+1') == 'United States'
        result.get('+49') == 'Germany'
    }

    def 'should throw unable to fetch table exception'() {
        given:
        provider.getDocument() >> Stub(Document) {
            getElementById(_) >> null
        }

        when:
        fetcher.fetchData()

        then:
        def ex = thrown(RuntimeException)
        ex.message == 'Unable to fetch country codes table'
    }
}
