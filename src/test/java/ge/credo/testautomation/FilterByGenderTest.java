package ge.credo.testautomation;

import ge.credo.testautomation.data.dataproviders.UsersDataProviders;
import ge.credo.testautomation.data.models.User;
import ge.credo.testautomation.steps.FilterByGenderSteps;
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

@Feature("GET /users - Filter by Gender")
public class FilterByGenderTest extends BaseTest {

    private final FilterByGenderSteps filterByGenderSteps = new FilterByGenderSteps();


    @Test(dataProvider = "positiveFilterByGender", dataProviderClass = UsersDataProviders.class, description = "GET /users?gender={gender} → 200: სქესით ფილტრაცია")
    @Description("Verify filtering users by gender parameter returns correct result")
    @Severity(SeverityLevel.CRITICAL)
    public void testFilterByGender_Positive(String genderParam, User expectedUser) {
        Response response = filterByGenderSteps.getUsersByGender(genderParam);
        List<User> users = ResponseDeserializer.toList(response, User.class);

        assertThat(users).hasSize(1);
        assertThat(users.getFirst())
                .hasName(expectedUser.getName())
                .hasAge(expectedUser.getAge())
                .hasGender(expectedUser.getGender())
                .hasId(expectedUser.getId())
                .hasGender(expectedUser.getGender());
    }

    @Test(dataProvider = "invalidGenderData", dataProviderClass = UsersDataProviders.class, description = "GET /users?gender=unknown → 422 ცარიელი სია")
    @Description("Verify that unknown gender parameter returns 422 with empty list")
    @Severity(SeverityLevel.CRITICAL)
    public void testInvalidGender_Negative(String paramValue) {
        Response response = filterByGenderSteps.getUsersByUnknownGender(paramValue);
        List<User> users = ResponseDeserializer.toList(response, User.class);

        assertThat(users).isEmpty();
    }
}
