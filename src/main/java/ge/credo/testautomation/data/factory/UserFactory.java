package ge.credo.testautomation.data.factory;

import ge.credo.testautomation.data.models.User;

import static ge.credo.testautomation.utils.BaseTest.faker;

public class UserFactory {


    public static User randomUser(int id, String gender) {
        return User.builder()
                .id(id)
                .name(faker.name().fullName())
                .age(faker.number().numberBetween(18, 80))
                .gender(gender)
                .build();
    }

    public static User userWithAge(int id, int age, String gender) {
        return User.builder()
                .id(id)
                .name(faker.name().fullName())
                .age(age)
                .gender(gender)
                .build();
    }
}
