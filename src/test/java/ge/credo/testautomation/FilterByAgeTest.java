package ge.credo.testautomation;

import ge.credo.testautomation.data.dataproviders.UsersDataProviders;
import ge.credo.testautomation.data.models.ErrorResponse;
import ge.credo.testautomation.data.models.User;
import ge.credo.testautomation.steps.FilterByAgeSteps;
import ge.credo.testautomation.utils.BaseTest;
import ge.credo.testautomation.utils.ResponseDeserializer;
import io.qameta.allure.Description;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;

import static ge.credo.testautomation.data.models.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

@Feature("GET /users - Filter by Age")
public class FilterByAgeTest extends BaseTest {
    private final FilterByAgeSteps filterByAgeSteps = new FilterByAgeSteps();

    @Test(dataProvider = "positiveFilterByAge", dataProviderClass = UsersDataProviders.class, description = "GET /users?age={age} → 200: ასაკით ფილტრაცია")
    @Description("Verify filtering users by age parameter returns correct result")
    @Severity(SeverityLevel.CRITICAL)
    public void testFilterByAge_Positive(String ageParam, User expectedUser) {
        Response response = filterByAgeSteps.getUsersByAge(ageParam);
        List<User> users = ResponseDeserializer.toList(response, User.class);

        assertThat(users).hasSize(1);
        assertThat(users.getFirst())
                .hasName(expectedUser.getName())
                .hasAge(expectedUser.getAge())
                .hasGender(expectedUser.getGender())
                .hasId(expectedUser.getId())
                .hasAge(expectedUser.getAge());
    }

    @Test(dataProvider = "invalidAgeData", dataProviderClass = UsersDataProviders.class, description = "GET /users?age={invalid} → 400 Bad Request")
    @Description("Verify that invalid age parameter returns 400 Bad Request")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidAge_Negative(String ageParam, ErrorResponse expectedError) {
        Response response = filterByAgeSteps.getUsersByInvalidAge(ageParam);
        ErrorResponse errorResponse = ResponseDeserializer.toObject(response, ErrorResponse.class);

        assertThat(errorResponse)
                .hasError(expectedError.getError())
                .hasMessage(expectedError.getMessage());
    }
}
