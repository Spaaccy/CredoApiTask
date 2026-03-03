package ge.credo.testautomation.data.dataproviders;

import ge.credo.testautomation.data.ApiConstants;
import ge.credo.testautomation.data.factory.StubData;
import org.testng.annotations.DataProvider;


public class UsersDataProviders {

    @DataProvider(name = "positiveGetAllUsers")
    public static Object[][] positiveGetAllUsersData() {
        return new Object[][]{
                {2, 0, StubData.USER_FEMALE},
                {2, 1, StubData.USER_MALE}
        };
    }

    @DataProvider(name = "positiveFilterByAge")
    public static Object[][] positiveFilterByAgeData() {
        return new Object[][]{
                {ApiConstants.Values.AGE_30, StubData.USER_FEMALE},
                {ApiConstants.Values.AGE_25, StubData.USER_FEMALE_25},
                {ApiConstants.Values.AGE_45, StubData.USER_MALE_45}
        };
    }

    @DataProvider(name = "positiveFilterByGender")
    public static Object[][] positiveFilterByGenderData() {
        return new Object[][]{
                {ApiConstants.Values.GENDER_MALE, StubData.USER_MALE},
                {ApiConstants.Values.GENDER_FEMALE, StubData.USER_FEMALE}
        };
    }

    @DataProvider(name = "invalidAgeData")
    public static Object[][] invalidAgeData() {
        return new Object[][]{
                {ApiConstants.Values.AGE_INVALID, StubData.BAD_REQUEST},
                {ApiConstants.Values.AGE_INVALID_ZERO, StubData.BAD_REQUEST},
                {ApiConstants.Values.AGE_INVALID_STRING, StubData.BAD_REQUEST}
        };
    }

    @DataProvider(name = "invalidGenderData")
    public static Object[][] invalidGenderData() {
        return new Object[][]{
                {ApiConstants.Values.GENDER_UNKNOWN},
                {ApiConstants.Values.GENDER_INVALID_EMPTY},
                {ApiConstants.Values.GENDER_INVALID_NUMERIC}
        };
    }

    @DataProvider(name = "internalServerErrorData")
    public static Object[][] internalServerErrorData() {
        return new Object[][]{
                {StubData.SERVER_ERROR}
        };
    }
}
