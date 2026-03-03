package ge.credo.testautomation.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.javafaker.Faker;
import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.database.DatabaseManager;
import ge.credo.testautomation.data.factory.StubData;
import io.restassured.RestAssured;
import org.testng.ITestResult;
import org.testng.annotations.*;

import java.util.List;
import java.util.Map;

import static ge.credo.testautomation.utils.WireMockManager.startWireMockDocker;
import static ge.credo.testautomation.utils.WireMockManager.stopWireMockDocker;

public class BaseTest {
    public static final ObjectMapper mapper = new ObjectMapper();
    public static final Faker faker = new Faker();

    @BeforeSuite
    public void startWiremock() {
        startWireMockDocker();
    }

    @BeforeClass
    public void setUp() {
        RestAssured.baseURI = WireMockManager.getBaseUrl();
        WireMockManager.reset();

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, null,
                200, RequestSerializer.toJson(List.of(StubData.USER_FEMALE, StubData.USER_MALE)),
                ApiConstants.WireMock.PRIORITY_LOW
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.AGE, ApiConstants.Values.AGE_30),
                200, RequestSerializer.toJson(List.of(StubData.USER_FEMALE)),
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.AGE, ApiConstants.Values.AGE_25),
                200, RequestSerializer.toJson(List.of(StubData.USER_FEMALE_25)),
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.AGE, ApiConstants.Values.AGE_45),
                200, RequestSerializer.toJson(List.of(StubData.USER_MALE_45)),
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.GENDER, ApiConstants.Values.GENDER_MALE),
                200, RequestSerializer.toJson(List.of(StubData.USER_MALE)),
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.GENDER, ApiConstants.Values.GENDER_FEMALE),
                200, RequestSerializer.toJson(List.of(StubData.USER_FEMALE)),
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.AGE, ApiConstants.Values.AGE_INVALID),
                400, RequestSerializer.toJson(StubData.BAD_REQUEST),
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.AGE, ApiConstants.Values.AGE_INVALID_ZERO),
                400, RequestSerializer.toJson(StubData.BAD_REQUEST),
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.AGE, ApiConstants.Values.AGE_INVALID_STRING),
                400, RequestSerializer.toJson(StubData.BAD_REQUEST),
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.GENDER, ApiConstants.Values.GENDER_UNKNOWN),
                422, ApiConstants.Values.BODY_EMPTY_LIST,
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.GENDER, ApiConstants.Values.GENDER_INVALID_EMPTY),
                422, ApiConstants.Values.BODY_EMPTY_LIST,
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );

        WireMockManager.registerStub(
                ApiConstants.Api.USERS_PATH, Map.of(ApiConstants.Params.GENDER, ApiConstants.Values.GENDER_INVALID_NUMERIC),
                422, ApiConstants.Values.BODY_EMPTY_LIST,
                ApiConstants.WireMock.PRIORITY_MEDIUM
        );
    }


    @AfterMethod
    public void saveTestResult(ITestResult result) {
        String testName = result.getMethod().getMethodName();
        Object[] params = result.getParameters();
        if (params != null && params.length > 0) {
            testName += "_" + params[0];
        }
        DatabaseManager.saveResult(testName, result.getStatus() == ITestResult.SUCCESS);
    }

    @AfterClass
    public void tearDown() {
        WireMockManager.reset();
    }

    @AfterSuite
    public void stopWiremock() {
        stopWireMockDocker();
    }
}