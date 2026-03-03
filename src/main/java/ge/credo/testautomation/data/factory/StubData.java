package ge.credo.testautomation.data.factory;

import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.models.ErrorResponse;
import ge.credo.testautomation.data.models.User;


public class StubData {

    public static final User
            USER_FEMALE = UserFactory.userWithAge(1, Integer.parseInt(ApiConstants.Values.AGE_30), ApiConstants.Values.GENDER_FEMALE),
            USER_MALE = UserFactory.randomUser(2, ApiConstants.Values.GENDER_MALE),
            USER_FEMALE_25 = UserFactory.userWithAge(3, Integer.parseInt(ApiConstants.Values.AGE_25), ApiConstants.Values.GENDER_FEMALE),
            USER_MALE_45 = UserFactory.userWithAge(4, Integer.parseInt(ApiConstants.Values.AGE_45), ApiConstants.Values.GENDER_MALE);
    public static final ErrorResponse
            BAD_REQUEST = ErrorResponseFactory.badRequest(),
            SERVER_ERROR = ErrorResponseFactory.serverError();
}

