package com.example.country.validation

import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Unroll

class PhoneNumberValidatorTest extends Specification {

    @Subject
    def validator = new PhoneNumberValidator()

    @Unroll
    def "should return valid for phone number #phone"() {
        expect:
        validator.isValid(phone, null)

        where:
        phone << ['+37129323734', '+372 605 8888', '+1 (718) 917-3999']
    }

    @Unroll
    def "should return invalid for phone number #phone"() {
        expect:
        !validator.isValid(phone, null)

        where:
        phone << ['abc', '372 605 88882', '1 (718) 917-3999']
    }

    def 'should return invalid if phone is blank'() {
        expect:
        !validator.isValid('', null)
    }
}
