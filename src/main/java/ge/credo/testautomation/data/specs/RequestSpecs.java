package ge.credo.testautomation.data.specs;

import ge.credo.testautomation.data.ApiConstants;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

public class RequestSpecs {

    public static RequestSpecification baseRequestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(ApiConstants.WireMock.BASE_URL)
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .log(LogDetail.ALL)
                .build();
    }

    public static ResponseSpecification ok200Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification badRequest400Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(400)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification unprocessable422Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(422)
                .expectContentType(ContentType.JSON)
                .build();
    }

    public static ResponseSpecification serverError500Spec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(500)
                .expectContentType(ContentType.JSON)
                .build();
    }
}

