package com.example.demofx.utils;

public class VCardUtils {

    public static final String ADR = "ADR";
    public static final String AGENT = "AGENT";
    public static final String ANNIVERSARY = "ANNIVERSARY";
    public static final String BDAY = "BDAY";
    public static final String BEGIN = "BEGIN";
    public static final String CALADRURI = "CALADRURI";
    public static final String CALURI = "CALURI";
    public static final String CATEGORIES = "CATEGORIES";
    public static final String CLASS = "CLASS";
    public static final String CLIENTPIDMAP = "CLIENTPIDMAP";
    public static final String EMAIL = "EMAIL";
    public static final String END = "END";
    public static final String FBURL = "FBURL";
    public static final String FN = "FN";
    public static final String GENDER = "GENDER";
    public static final String GEO = "GEO";
    public static final String IMPP = "IMPP";
    public static final String KEY = "KEY";
    public static final String KIND = "KIND";
    public static final String LABEL = "LABEL";
    public static final String LANG = "LANG";
    public static final String LOGO = "LOGO";
    public static final String MAILER = "MAILER";
    public static final String MEMBER = "MEMBER";
    public static final String N = "N";
    public static final String NAME = "NAME";
    public static final String NICKNAME = "NICKNAME";
    public static final String NOTE = "NOTE";
    public static final String ORG = "ORG";
    public static final String PHOTO = "PHOTO";
    public static final String PRODID = "PRODID";
    public static final String PROFILE = "PROFILE";
    public static final String RELATED = "RELATED";
    public static final String REV = "REV";
    public static final String ROLE = "ROLE";
    public static final String SORTSTRING = "SORT-STRING";
    public static final String SOUND = "SOUND";
    public static final String SOURCE = "SOURCE";
    public static final String TEL = "TEL";
    public static final String TITLE = "TITLE";
    public static final String TZ = "TZ";
    public static final String UID = "UID";
    public static final String URL = "URL";
    public static final String VERSION = "VERSION:4.0";
    public static final String XML = "XML";
    public static final String VCARD = "VCARD";

    public static final String COLON = ":";
    public static final String SEMICOLON = ";";
    public static final String COMMA = ",";
    public static final String CRLF = "\r\n";
    public static final String TYPE = "TYPE";
    public static final String EQUALS = "=";
    // public static final String EMPTY_FORMATTED_ADDRESS = ";;;;;;;";
    public static final String EMPTY_FORMATTED_ADDRESS = ";;;;;";

    private VCardUtils() {
    }

    /**
     * (content fields are in order: Post Office Address; Extended Address; Street; Locality; Region; Postal Code; Country)
     *
     * @return
     */
    public static String prepareAddress(String street,
                                        String city,
                                        String region,
                                        String postalCode,
                                        String country) {

        var addressLine = new StringBuilder();
        // addressLine.append(VCardUtils.SEMICOLON); // PO Address
        // addressLine.append(VCardUtils.SEMICOLON); // Extended Address
        addressLine.append(street);
        addressLine.append(VCardUtils.SEMICOLON);
        addressLine.append(city);
        addressLine.append(VCardUtils.SEMICOLON);
        addressLine.append(region);
        addressLine.append(VCardUtils.SEMICOLON);
        addressLine.append(postalCode);
        addressLine.append(VCardUtils.SEMICOLON);
        addressLine.append(country);
        addressLine.append(VCardUtils.SEMICOLON);

        // NB if no address fields have been specified, this string consists of
        // 6 semicolons ....
        return addressLine.toString();
    }


    public static String formatPhoneNumber(String phoneNumber, String type) {
        var contents = new StringBuilder();
        contents.append(VCardUtils.TEL);
        contents.append(VCardUtils.SEMICOLON);
        contents.append(VCardUtils.TYPE);
        contents.append(VCardUtils.EQUALS);
        contents.append(type);
        contents.append(VCardUtils.COLON);
        contents.append(phoneNumber);
        contents.append(VCardUtils.CRLF);
        return contents.toString();
    }

    public static String formatAddress(String address, String type) {
        var contents = new StringBuilder();
        contents.append(VCardUtils.ADR);
        contents.append(VCardUtils.SEMICOLON);
        contents.append(VCardUtils.TYPE);
        contents.append(VCardUtils.EQUALS);
        contents.append(type);
        contents.append(VCardUtils.COLON);
        contents.append(address);
        contents.append(VCardUtils.CRLF);
        return contents.toString();
    }

    public static String formatEmailAddress(String emailAddress, String type) {
        var contents = new StringBuilder();
        contents.append(VCardUtils.EMAIL);
        contents.append(VCardUtils.SEMICOLON);
        contents.append(VCardUtils.TYPE);
        contents.append(VCardUtils.EQUALS);
        contents.append(type);
        contents.append(VCardUtils.COLON);
        contents.append(emailAddress);
        contents.append(VCardUtils.CRLF);
        return contents.toString();
    }

    public static String formatUrl(String url) {
        var contents = new StringBuilder();
        contents.append(VCardUtils.URL);
        contents.append(VCardUtils.COLON);
        contents.append(url);
        contents.append(VCardUtils.CRLF);
        return contents.toString();
    }

    public static String formatFormattedName(String firstName,
                                             String middleNames,
                                             String surname) {
        var contents = new StringBuilder();
        // Formatted Name
        contents.append(VCardUtils.FN);
        contents.append(VCardUtils.COLON);
        contents.append(firstName);
        contents.append(" ");
        contents.append(middleNames);
        contents.append(" ");
        contents.append(surname);
        contents.append(VCardUtils.CRLF);
        return contents.toString();
    }

    public static String formatDisplayName(String firstName,
                                           String middleNames,
                                           String surname) {
        var contents = new StringBuilder();
        // Name
        contents.append(VCardUtils.N);
        contents.append(VCardUtils.COLON);
        contents.append(surname);
        contents.append(VCardUtils.SEMICOLON);
        contents.append(firstName);
        contents.append(VCardUtils.SEMICOLON);
        contents.append(middleNames);
        contents.append(VCardUtils.CRLF);
        return contents.toString();
    }

    public static String formatOrgName(String orgName) {
        var contents = new StringBuilder();
        contents.append(VCardUtils.ORG);
        contents.append(VCardUtils.COLON);
        contents.append(orgName);
        contents.append(VCardUtils.CRLF);
        return contents.toString();
    }

    public static String formatOpenTags() {
        var contents = new StringBuilder();
        contents.append(VCardUtils.BEGIN);
        contents.append(VCardUtils.COLON);
        contents.append(VCardUtils.VCARD);
        contents.append(VCardUtils.CRLF);
        // Version
        contents.append(VCardUtils.VERSION);
        contents.append(VCardUtils.CRLF);
        return contents.toString();
    }

    public static String formatCloseTags() {
        var contents = new StringBuilder();
        contents.append(VCardUtils.END);
        contents.append(VCardUtils.COLON);
        contents.append(VCardUtils.VCARD);
        contents.append(VCardUtils.CRLF);
        return contents.toString();
    }

}