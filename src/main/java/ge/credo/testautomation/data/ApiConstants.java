package ge.credo.testautomation.data;

public class ApiConstants {

    public static class WireMock {
        public static final String
                ADMIN_RESET = "/reset",
                ADMIN_MAPPINGS = "/mappings",
                BASE_URL = "http://localhost:8080",
                ADMIN_URL = BASE_URL + "/__admin";
        public static final int
                PRIORITY_HIGH = 1,
                PRIORITY_MEDIUM = 5,
                PRIORITY_LOW = 10;
    }

    public static class Api {
        public static final String USERS_PATH = "/users";
        public static final String CONTENT_TYPE_JSON = "application/json";
    }

    public static class Params {
        public static final String
                AGE = "age",
                GENDER = "gender";
    }

    public static class Values {
        public static final String
                AGE_30 = "30",
                AGE_25 = "25",
                AGE_45 = "45",
                AGE_INVALID = "-1",
                AGE_INVALID_ZERO = "0",
                AGE_INVALID_STRING = "abc",
                GENDER_MALE = "male",
                GENDER_FEMALE = "female",
                GENDER_UNKNOWN = "unknown",
                GENDER_INVALID_EMPTY = "",
                GENDER_INVALID_NUMERIC = "123",
                BODY_EMPTY_LIST = "[]";
    }

    public static class Errors {
        public static final String
                ERROR_BAD_REQUEST = "Bad Request",
                ERROR_INTERNAL_SERVER = "Internal Server Error",
                MESSAGE_INVALID_AGE = "Invalid age parameter: age must be a positive integer",
                MESSAGE_SERVER_ERROR = "Unexpected server error occurred";
    }

    public static class Database {
        public static final String
                DB_URL = "jdbc:sqlite:test-results.db",
                DB_DATETIME_FMT = "yyyy-MM-dd HH:mm:ss",
                STATUS_PASSED = "PASSED",
                STATUS_FAILED = "FAILED";
    }
}
