package com.example.country.validation;

import com.google.i18n.phonenumbers.NumberParseException;
import io.micrometer.common.util.StringUtils;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import static com.google.i18n.phonenumbers.PhoneNumberUtil.getInstance;
import static io.micrometer.common.util.StringUtils.isBlank;

public class PhoneNumberValidator implements ConstraintValidator<Phone, String> {

    @Override
    public void initialize(Phone constraintAnnotation) {
    }

    @Override
    public boolean isValid(String phoneNumber, ConstraintValidatorContext context) {
        if (isBlank(phoneNumber)) {
            return false;
        }
        try {
            var phoneNumberUtil = getInstance();
            return phoneNumberUtil.isValidNumber(phoneNumberUtil.parse(phoneNumber, ""));
        } catch (NumberParseException e) {
            return false;
        }
    }
}