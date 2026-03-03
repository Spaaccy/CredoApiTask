package ge.credo.testautomation.utils;

import ge.credo.testautomation.data.models.ErrorResponse;
import ge.credo.testautomation.data.models.User;
import io.qameta.allure.Step;

import java.util.List;

import static ge.credo.testautomation.utils.BaseTest.mapper;

public class RequestSerializer {

    @Step("Serialize User list to JSON")
    public static String toJson(List<User> users) {
        try {
            return mapper.writeValueAsString(users);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize User list: " + e.getMessage(), e);
        }
    }

    @Step("Serialize ErrorResponse to JSON")
    public static String toJson(ErrorResponse errorResponse) {
        try {
            return mapper.writeValueAsString(errorResponse);
        } catch (Exception e) {
            throw new RuntimeException("Failed to serialize ErrorResponse: " + e.getMessage(), e);
        }
    }
}
