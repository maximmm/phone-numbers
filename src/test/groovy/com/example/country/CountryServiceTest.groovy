package com.example.country

import com.google.i18n.phonenumbers.NumberParseException
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class CountryServiceTest extends Specification {

    def storage = Stub(CountryCodeStorage)

    @Subject
    def service = new CountryService(storage)

    @Unroll
    def "should return '#country' for '#phone'"() {
        given:
        storage.get(code) >> country

        expect:
        country == service.resolveCountryBy(phone)

        where:
        phone             | code   || country
        '+37129343536'    | '+371' || 'Latvia'
        '+372 605 8888'   | '+372' || 'Estonia'
        '+1 802 200 9500' | '+1'   || 'United State'
    }

    def 'should throw NumberParseException'() {
        when:
        service.resolveCountryBy('abcdefg')

        then:
        def ex = thrown(NumberParseException)
        ex.message == 'The string supplied did not seem to be a phone number.'

    }
}
