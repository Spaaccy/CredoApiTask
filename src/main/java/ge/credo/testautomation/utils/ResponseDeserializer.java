package ge.credo.testautomation.utils;

import com.fasterxml.jackson.databind.type.CollectionType;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import java.util.List;

import static ge.credo.testautomation.utils.BaseTest.mapper;

public class ResponseDeserializer {

    @Step("Deserialize response to List<{clazz}>")
    public static <T> List<T> toList(Response response, Class<T> clazz) {
        try {
            CollectionType listType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
            return mapper.readValue(response.getBody().asString(), listType);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize response to List<" + clazz.getSimpleName() + ">: " + e.getMessage(), e);
        }
    }

    @Step("Deserialize response to {clazz}")
    public static <T> T toObject(Response response, Class<T> clazz) {
        try {
            return mapper.readValue(response.getBody().asString(), clazz);
        } catch (Exception e) {
            throw new RuntimeException("Failed to deserialize response to " + clazz.getSimpleName() + ": " + e.getMessage(), e);
        }
    }
}

