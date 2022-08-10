package com.dulsara.citylist.ciltylistapp.util;

public class GlobalConstant {

    public static final String urlRegex = "^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]";
    public static final String UPDATE="UPDATE";

    public  static class CityErrors {

        public static final String CITY_NAME_MANDATORY_ERROR="City Name is Mandatory";
        public static final String IMAGE_URL_MANDATORY_ERROR="City Image URL is Mandatory";
        public static final String CITY_ID_MANDATORY_ERROR="City Id is Mandatory or greater than 0";
        public static final String IMAGE_URL_STANDARD_ERROR="City Image URL is not in standard Pattern";
        public static final String CITY_NAME_EXISTING_ERROR="Given City Name is already existing";
        public static final String CITY_ID_EXISTING_ERROR="No City for given Id";

    }
    public  static class JWTErrors {

        public static final String JWT_EXPIRED_ERROR="JWT expired";
        public static final String JWT_TOKEN_EMPTY_ERROR="Token is null, empty or only whitespace";
        public static final String JWT_TOKEN_INVALID_ERROR="JWT is invalid";
        public static final String JWT_TOKEN_NOT_SUPPORTED_ERROR="JWT is not supported";
        public static final String JWT_SIGNATURE_ERROR="Signature validation failed";

    }
}
