package ge.credo.testautomation;

import ge.credo.testautomation.data.dataproviders.UsersDataProviders;
import ge.credo.testautomation.data.factory.StubData;
import ge.credo.testautomation.data.models.User;
import ge.credo.testautomation.steps.GetAllUsersSteps;
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

@Feature("GET /users")
public class GetAllUsersTest extends BaseTest {

    private final GetAllUsersSteps getAllUsersSteps = new GetAllUsersSteps();

    @Test(dataProvider = "positiveGetAllUsers", dataProviderClass = UsersDataProviders.class,
            description = "GET /users → 200: ყველა მომხმარებლის ვალიდაცია")
    @Description("Verify that GET /users returns correct count and user at given index matches expected")
    @Severity(SeverityLevel.BLOCKER)
    public void testGetAllUsers_Positive(int expectedCount, int userIndex, User expectedUser) {
        Response response = getAllUsersSteps.getAllUsers();
        List<User> users = ResponseDeserializer.toList(response, User.class);

        assertThat(users)
                .isNotNull()
                .isNotEmpty()
                .hasSize(expectedCount);

        assertThat(users.get(userIndex))
                .hasName(expectedUser.getName())
                .hasAge(expectedUser.getAge())
                .hasGender(expectedUser.getGender())
                .hasId(expectedUser.getId());
    }
}
