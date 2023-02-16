package com.example.country

import spock.lang.Specification
import spock.lang.Subject

class CountryCodeStorageTest extends Specification {

    def fetcher = Stub(CountryCodeFetcher)

    @Subject
    def storage = new CountryCodeStorage(fetcher)

    def 'should populate data from fetcher'() {
        given:
        def data = [
                '+371': 'Latvia',
                '+1'  : 'United States',
                '+49' : 'Germany'
        ]
        fetcher.fetchData() >> data

        when:
        storage.loadData()

        then:
        storage.size() == 3

        and:
        storage.get('+371') == 'Latvia'
        storage.get('+1') == 'United States'
        storage.get('+49') == 'Germany'
    }
}
