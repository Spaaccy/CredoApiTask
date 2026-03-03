package ge.credo.testautomation.steps;

import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.specs.RequestSpecs;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class FilterByGenderSteps {

    @Step("GET /users?gender={gender} - filter users by gender")
    public Response getUsersByGender(String gender) {
        return RestAssured
                .given()
                .spec(RequestSpecs.baseRequestSpec())
                .queryParam(ApiConstants.Params.GENDER, gender)
                .when()
                .get(ApiConstants.Api.USERS_PATH)
                .then()
                .spec(RequestSpecs.ok200Spec())
                .extract().response();
    }

    @Step("GET /users?gender={gender} - attempt to filter by unknown gender")
    public Response getUsersByUnknownGender(String gender) {
        return RestAssured
                .given()
                .spec(RequestSpecs.baseRequestSpec())
                .queryParam(ApiConstants.Params.GENDER, gender)
                .when()
                .get(ApiConstants.Api.USERS_PATH)
                .then()
                .spec(RequestSpecs.unprocessable422Spec())
                .extract().response();
    }
}

