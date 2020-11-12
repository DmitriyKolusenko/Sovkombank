package com.sovkombank.example.constants;

public class ErrorMessages {
    public static final String error_403 = "BAD_REQUEST_PHONE_NUMBER_INVALID: Too much phone numbers, should be less or equal to 128 per day.";
    public static final String error_401 = "BAD_REQUEST_PHONE_NUMBERS_EMPTY: Phone numbers is missing.";
    public static final String error_402 = "BAD_REQUEST_PHONE_NUMBER_INVALID: Too much phone numbers, should be less or equal to 16 per request.";
    public static final String error_400 = "BAD_REQUEST_PHONE_NUMBER_INVALID: One or several phone numbers do not match with international format.";
    public static final String error_404 = "BAD_REQUEST_PHONE_NUMBER_INVALID: Duplicate numbers detected.";
    public static final String error_405 = "BAD_REQUEST_MESSAGE_EMPTY: Invite message is missing.";
    public static final String error_406 = "BAD_REQUEST_MESSAGE_INVALID: Invite message should contain only characters in 7-bit GSM encoding or Cyrillic letters as well";
    public static final String error_407 = "BAD_REQUEST_MESSAGE_INVALID: Invite message too long, should be less or equal to 128 characters of 7-bit GSM charset";
}
