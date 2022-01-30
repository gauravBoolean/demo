package com.example.ibanvalidator.demo;

import java.math.BigInteger;
import java.util.Objects;

public class IBANValidator {

    private static final BigInteger MODULUS_NUMBER = new BigInteger("97");

    private final String countryCode;
    private final int length;
    private final String codeRegex;
    private final String country;

    public IBANValidator(String countryCode, int length, String codeRegex, String country) {
        this.countryCode = countryCode;
        this.length = length;
        this.codeRegex = codeRegex;
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public int getLength() {
        return length;
    }

    public String getCodeRegex() {
        return codeRegex;
    }

    public String getCountry() {
        return country;
    }

    public boolean isValidCode(String ibanCode) {
        return ibanCode.matches(getCodeRegex()) && ibanCode.length() == getLength() && checkibanmodulasvalid(ibanCode);
    }


    public boolean checkibanmodulasvalid(String ibanNumber) {
        String re = ibanNumber.substring(4) + ibanNumber.substring(0,4);

        BigInteger intVal = BigInteger.ZERO;
        StringBuilder sb = new StringBuilder();

        for(int i = 0; i < re.length(); i++){
            char ch = re.charAt(i);
            sb.append(Character.getNumericValue(ch));
        }

        intVal = new BigInteger(sb.toString());
        boolean isValid = intVal.mod(MODULUS_NUMBER).intValue() == 1;
        return isValid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IBANValidator validator = (IBANValidator) o;
        return length == validator.length && countryCode.equals(validator.countryCode) && codeRegex.equals(validator.codeRegex) && country.equals(validator.country);
    }

    @Override
    public int hashCode() {
        return Objects.hash(countryCode, length, codeRegex, country);
    }
}
