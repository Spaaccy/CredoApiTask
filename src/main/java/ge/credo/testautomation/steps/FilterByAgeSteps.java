package ge.credo.testautomation.steps;

import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.specs.RequestSpecs;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class FilterByAgeSteps {

    @Step("GET /users?age={age} - filter users by age")
    public Response getUsersByAge(String age) {
        return RestAssured
                .given()
                .spec(RequestSpecs.baseRequestSpec())
                .queryParam(ApiConstants.Params.AGE, age)
                .when()
                .get(ApiConstants.Api.USERS_PATH)
                .then()
                .spec(RequestSpecs.ok200Spec())
                .extract().response();
    }

    @Step("GET /users?age={age} - attempt to filter by invalid age")
    public Response getUsersByInvalidAge(String age) {
        return RestAssured
                .given()
                .spec(RequestSpecs.baseRequestSpec())
                .queryParam(ApiConstants.Params.AGE, age)
                .when()
                .get(ApiConstants.Api.USERS_PATH)
                .then()
                .spec(RequestSpecs.badRequest400Spec())
                .extract().response();
    }
}

