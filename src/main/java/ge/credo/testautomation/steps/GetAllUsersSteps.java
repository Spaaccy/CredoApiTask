package ge.credo.testautomation.steps;

import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.specs.RequestSpecs;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class GetAllUsersSteps {

    @Step("GET /users - retrieve all users")
    public Response getAllUsers() {
        return RestAssured
                .given()
                .spec(RequestSpecs.baseRequestSpec())
                .when()
                .get(ApiConstants.Api.USERS_PATH)
                .then()
                .spec(RequestSpecs.ok200Spec())
                .extract().response();
    }

    @Step("GET /users - expect server error (500)")
    public Response getUsersWithServerError() {
        return RestAssured
                .given()
                .spec(RequestSpecs.baseRequestSpec())
                .when()
                .get(ApiConstants.Api.USERS_PATH)
                .then()
                .spec(RequestSpecs.serverError500Spec())
                .extract().response();
    }
}

