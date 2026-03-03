package ge.credo.testautomation.data.factory;

import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.models.ErrorResponse;


public class ErrorResponseFactory {


    public static ErrorResponse badRequest() {
        return ErrorResponse.builder()
                .error(ApiConstants.Errors.ERROR_BAD_REQUEST)
                .message(ApiConstants.Errors.MESSAGE_INVALID_AGE)
                .build();
    }

    public static ErrorResponse serverError() {
        return ErrorResponse.builder()
                .error(ApiConstants.Errors.ERROR_INTERNAL_SERVER)
                .message(ApiConstants.Errors.MESSAGE_SERVER_ERROR)
                .build();
    }


}
