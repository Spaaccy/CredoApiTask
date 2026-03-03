package ge.credo.testautomation;

import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.dataproviders.UsersDataProviders;
import ge.credo.testautomation.data.factory.StubData;
import ge.credo.testautomation.data.models.ErrorResponse;
import ge.credo.testautomation.steps.GetAllUsersSteps;
import ge.credo.testautomation.utils.BaseTest;
import ge.credo.testautomation.utils.RequestSerializer;
import ge.credo.testautomation.utils.ResponseDeserializer;
import ge.credo.testautomation.utils.WireMockManager;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static ge.credo.testautomation.data.models.Assertions.assertThat;

@Feature("GET /users - Server Error")
public class ServerErrorTest extends BaseTest {
    private final GetAllUsersSteps getAllUsersSteps = new GetAllUsersSteps();
    String stubId;

    @BeforeMethod
    public void resetStubs() {
        stubId = WireMockManager.registerStub(ApiConstants.Api.USERS_PATH, null, 500, RequestSerializer.toJson(StubData.SERVER_ERROR), ApiConstants.WireMock.PRIORITY_HIGH);
    }

    @Test(dataProvider = "internalServerErrorData", dataProviderClass = UsersDataProviders.class, description = "GET /users → 500 Internal Server Error (დინამიური სტაბი)")
    @Description("Verify that internal server error (500) is properly handled")
    @Severity(SeverityLevel.CRITICAL)
    public void testInternalServerError_Negative(ErrorResponse expectedError) {
        Response response = getAllUsersSteps.getUsersWithServerError();
        ErrorResponse errorResponse = ResponseDeserializer.toObject(response, ErrorResponse.class);
        assertThat(errorResponse)
                .hasError(expectedError.getError())
                .hasMessage(expectedError.getMessage());

    }

    @AfterMethod
    public void tearDown() {
        WireMockManager.deleteStub(stubId);
    }
}

