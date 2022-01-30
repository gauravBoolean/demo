package com.example.ibanvalidator.demo;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ValidatorService {
    private final String InvalidIbanNumber = "Invalid IBAN Number";
    private final String UnrecognizedIbanNumber = "Unrecognized IBAN Number";
    private final String SuccessMsg = "Valid IBAN number for %s";

    private static final List<IBANValidator> DEFAULT_FORMATS = Arrays.asList(
            new IBANValidator("AD", 24, "AD\\d{10}[A-Z0-9]{12}" , "Andorra"),
            new IBANValidator("AE", 23, "AE\\d{21}" , "United Arab Emirates"),
            new IBANValidator("AL", 28, "AL\\d{10}[A-Z0-9]{16}" , "Albania"),
            new IBANValidator("AT", 20, "AT\\d{18}" , "Austria"),
            new IBANValidator("AZ", 28, "AZ\\d{2}[A-Z]{4}[A-Z0-9]{20}" , "Republic of Azerbaijan"),
            new IBANValidator("BA", 20, "BA\\d{18}" , "Bosnia and Herzegovina"),
            new IBANValidator("BE", 16, "BE\\d{14}" , "Belgium"),
            new IBANValidator("BG", 22, "BG\\d{2}[A-Z]{4}\\d{6}[A-Z0-9]{8}" , "Bulgaria"),
            new IBANValidator("BH", 22, "BH\\d{2}[A-Z]{4}[A-Z0-9]{14}" , "Bahrain (Kingdom of)"),
            new IBANValidator("BR", 29, "BR\\d{25}[A-Z]{1}[A-Z0-9]{1}" , "Brazil"),
            new IBANValidator("BY", 28, "BY\\d{2}[A-Z0-9]{4}\\d{4}[A-Z0-9]{16}" , "Republic of Belarus"),
            new IBANValidator("CH", 21, "CH\\d{7}[A-Z0-9]{12}"  , "Switzerland"),
            new IBANValidator("CR", 22, "CR\\d{20}" , "Costa Rica"),
            new IBANValidator("CY", 28, "CY\\d{10}[A-Z0-9]{16}" , "Cyprus"),
            new IBANValidator("CZ", 24, "CZ\\d{22}" , "Czech Republic"),
            new IBANValidator("DE", 22, "DE\\d{20}" , "Germany"),
            new IBANValidator("DK", 18, "DK\\d{16}" , "Denmark"),
            new IBANValidator("DO", 28, "DO\\d{2}[A-Z0-9]{4}\\d{20}" , "Dominican Republic"),
            new IBANValidator("EE", 20, "EE\\d{18}" , "Estonia"),
            new IBANValidator("ES", 24, "ES\\d{22}" , "Spain"),
            new IBANValidator("FI", 18, "FI\\d{16}" , "Finland"),
            new IBANValidator("FO", 18, "FO\\d{16}" , "Denmark (Faroes)"),
            new IBANValidator("FR", 27, "FR\\d{12}[A-Z0-9]{11}\\d{2}"  , "France"),
            new IBANValidator("GB", 22, "GB\\d{2}[A-Z]{4}\\d{14}" , "United Kingdom"),
            new IBANValidator("GE", 22, "GE\\d{2}[A-Z]{2}\\d{16}" , "Georgia"),
            new IBANValidator("GI", 23, "GI\\d{2}[A-Z]{4}[A-Z0-9]{15}" , "Gibraltar"),
            new IBANValidator("GL", 18, "GL\\d{16}" , "Denmark (Greenland)"),
            new IBANValidator("GR", 27, "GR\\d{9}[A-Z0-9]{16}"  , "Greece"),
            new IBANValidator("GT", 28, "GT\\d{2}[A-Z0-9]{24}"  , "Guatemala"),
            new IBANValidator("HR", 21, "HR\\d{19}" , "Croatia"),
            new IBANValidator("HU", 28, "HU\\d{26}" , "Hungary"),
            new IBANValidator("IE", 22, "IE\\d{2}[A-Z]{4}\\d{14}" , "Ireland"),
            new IBANValidator("IL", 23, "IL\\d{21}" , "Israel"),
            new IBANValidator("IS", 26, "IS\\d{24}" , "Iceland"),
            new IBANValidator("IT", 27, "IT\\d{2}[A-Z]{1}\\d{10}[A-Z0-9]{12}" , "Italy"),
            new IBANValidator("IQ", 23, "IQ\\d{2}[A-Z]{4}\\d{15}" , "Iraq"),
            new IBANValidator("JO", 30, "JO\\d{2}[A-Z]{4}\\d{4}[A-Z0-9]{18}"  , "Jordan"),
            new IBANValidator("KW", 30, "KW\\d{2}[A-Z]{4}[A-Z0-9]{22}" , "Kuwait"),
            new IBANValidator("KZ", 20, "KZ\\d{5}[A-Z0-9]{13}"  , "Kazakhstan"),
            new IBANValidator("LB", 28, "LB\\d{6}[A-Z0-9]{20}"  , "Lebanon"),
            new IBANValidator("LC", 32, "LC\\d{2}[A-Z]{4}[A-Z0-9]{24}" , "Saint Lucia"),
            new IBANValidator("LI", 21, "LI\\d{7}[A-Z0-9]{12}"  , "Liechtenstein (Principality of)"),
            new IBANValidator("LT", 20, "LT\\d{18}" , "Lithuania"),
            new IBANValidator("LU", 20, "LU\\d{5}[A-Z0-9]{13}"  , "Luxembourg"),
            new IBANValidator("LV", 21, "LV\\d{2}[A-Z]{4}[A-Z0-9]{13}" , "Latvia"),
            new IBANValidator("MC", 27, "MC\\d{12}[A-Z0-9]{11}\\d{2}"  , "Monaco"),
            new IBANValidator("MD", 24, "MD\\d{2}[A-Z0-9]{20}"  , "Moldova"),
            new IBANValidator("ME", 22, "ME\\d{20}" , "Montenegro"),
            new IBANValidator("MK", 19, "MK\\d{5}[A-Z0-9]{10}\\d{2}" , "Macedonia, Former Yugoslav Republic of"),
            new IBANValidator("MR", 27, "MR\\d{25}" , "Mauritania"),
            new IBANValidator("MT", 31, "MT\\d{2}[A-Z]{4}\\d{5}[A-Z0-9]{18}"  , "Malta"),
            new IBANValidator("MU", 30, "MU\\d{2}[A-Z]{4}\\d{19}[A-Z]{3}" , "Mauritius"),
            new IBANValidator("NL", 18, "NL\\d{2}[A-Z]{4}\\d{10}" , "The Netherlands"),
            new IBANValidator("NO", 15, "NO\\d{13}" , "Norway"),
            new IBANValidator("PK", 24, "PK\\d{2}[A-Z]{4}[A-Z0-9]{16}" , "Pakistan"),
            new IBANValidator("PL", 28, "PL\\d{26}" , "Poland"),
            new IBANValidator("PS", 29, "PS\\d{2}[A-Z]{4}[A-Z0-9]{21}" , "Palestine, State of"),
            new IBANValidator("PT", 25, "PT\\d{23}" , "Portugal"),
            new IBANValidator("QA", 29, "QA\\d{2}[A-Z]{4}[A-Z0-9]{21}" , "Qatar"),
            new IBANValidator("RO", 24, "RO\\d{2}[A-Z]{4}[A-Z0-9]{16}" , "Romania"),
            new IBANValidator("RS", 22, "RS\\d{20}" , "Serbia"),
            new IBANValidator("SA", 24, "SA\\d{4}[A-Z0-9]{18}"  , "Saudi Arabia"),
            new IBANValidator("SC", 31, "SC\\d{2}[A-Z]{4}\\d{20}[A-Z]{3}" , "Seychelles"),
            new IBANValidator("SE", 24, "SE\\d{22}" , "Sweden"),
            new IBANValidator("SI", 19, "SI\\d{17}" , "Slovenia"),
            new IBANValidator("SK", 24, "SK\\d{22}" , "Slovak Republic"),
            new IBANValidator("SM", 27, "SM\\d{2}[A-Z]{1}\\d{10}[A-Z0-9]{12}" , "San Marino"),
            new IBANValidator("ST", 25, "ST\\d{23}" , "Sao Tome and Principe"),
            new IBANValidator("TL", 23, "TL\\d{21}" , "Timor-Leste"),
            new IBANValidator("TN", 24, "TN\\d{22}" , "Tunisia"),
            new IBANValidator("TR", 26, "TR\\d{8}[A-Z0-9]{16}"  , "Turkey"),
            new IBANValidator("UA", 29, "UA\\d{8}[A-Z0-9]{19}"  , "Ukraine"),
            new IBANValidator("VG", 24, "VG\\d{2}[A-Z]{4}\\d{16}" , "Virgin Islands, British"),
            new IBANValidator("XK", 20, "XK\\d{18}" , "Republic of Kosovo")
    );

    protected static Map<String, IBANValidator> validatorMap = DEFAULT_FORMATS.stream().collect(Collectors.toMap(IBANValidator::getCountryCode, i -> i ));



    public String validateIBANNumber(String ibanNumber){
        ibanNumber = ibanNumber.trim();
        checkMinLength(ibanNumber);
        IBANValidator validator = fetchValidator(ibanNumber);
        if (!validator.isValidCode(ibanNumber)) {
            throw  new InvalidIBANCodeException(InvalidIbanNumber);
        }

        return String.format(SuccessMsg , validator.getCountry());
    }

    protected void checkMinLength(String ibanNumber) {
        if(ibanNumber.length() < 2){
            throw  new InvalidIBANCodeException(InvalidIbanNumber);
        }
    }


    protected IBANValidator fetchValidator(String ibanNumber) {
        String countryCode = ibanNumber.substring(0,2);
        IBANValidator validator = validatorMap.get(countryCode);
        if (Objects.isNull(validator)) {
            throw  new InvalidIBANCodeException(UnrecognizedIbanNumber);
        }
        return validator;
    }


}
