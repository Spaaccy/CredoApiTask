package ge.credo.testautomation.utils;

import com.fasterxml.jackson.databind.node.ObjectNode;
import ge.credo.testautomation.data.ApiConstants;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import java.io.IOException;
import java.util.Map;

import static ge.credo.testautomation.utils.BaseTest.mapper;

public class WireMockManager {


    @Step("WireMock: reset all stubs")
    public static void reset() {
        RestAssured.given()
                .baseUri(ApiConstants.WireMock.ADMIN_URL)
                .post(ApiConstants.WireMock.ADMIN_RESET);
    }

    @Step("WireMock: register stub [{urlPath}] status={status} priority={priority}")
    public static String registerStub(String urlPath,
                                      Map<String, String> queryParams,
                                      int status,
                                      String body,
                                      int priority) {
        return registerStub(urlPath, queryParams, null, status, body, priority);
    }

    @Step("WireMock: register stub [{urlPath}] status={status} priority={priority} headers={headers}")
    public static String registerStub(String urlPath,
                                      Map<String, String> queryParams,
                                      Map<String, String> headers,
                                      int status,
                                      String body,
                                      int priority) {
        try {
            ObjectNode root = mapper.createObjectNode();
            root.put("priority", priority);

            ObjectNode request = root.putObject("request");
            request.put("method", "GET");
            request.put("urlPath", urlPath);

            if (queryParams != null && !queryParams.isEmpty()) {
                ObjectNode qp = request.putObject("queryParameters");
                queryParams.forEach((k, v) -> qp.putObject(k).put("equalTo", v));
            }

            if (headers != null && !headers.isEmpty()) {
                ObjectNode hdrs = request.putObject("headers");
                headers.forEach((k, v) -> hdrs.putObject(k).put("equalTo", v));
            }

            ObjectNode response = root.putObject("response");
            response.put("status", status);
            response.putObject("headers").put("Content-Type", ApiConstants.Api.CONTENT_TYPE_JSON);
            response.put("body", body);

            return RestAssured.given()
                    .baseUri(ApiConstants.WireMock.ADMIN_URL)
                    .contentType(ContentType.JSON)
                    .body(mapper.writeValueAsString(root))
                    .post(ApiConstants.WireMock.ADMIN_MAPPINGS)
                    .jsonPath()
                    .getString("id");

        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    @Step("WireMock: delete stub [{stubId}]")
    public static void deleteStub(String stubId) {
        RestAssured.given()
                .baseUri(ApiConstants.WireMock.ADMIN_URL)
                .delete(ApiConstants.WireMock.ADMIN_MAPPINGS + "/" + stubId);
    }

    public static String getBaseUrl() {
        return ApiConstants.WireMock.BASE_URL;
    }

    @Step("WireMock: start Docker container")
    public static void startWireMockDocker() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder(
                    "docker", "run", "--rm", "-d",
                    "--name", "wiremock",
                    "-p", "8080:8080",
                    "wiremock/wiremock:2.35.0"
            );
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            String output = new String(process.getInputStream().readAllBytes());
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("[WireMock] Docker container started: " + output.trim());
            } else if (output.contains("already in use") || output.contains("already exists")) {
                System.out.println("[WireMock] Container already running, skipping start.");
            } else {
                throw new RuntimeException("Failed to start WireMock Docker container. Output: " + output.trim());
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error starting WireMock Docker container", e);
        }
    }

    @Step("WireMock: stop Docker container")
    public static void stopWireMockDocker() {
        try {
            ProcessBuilder processBuilder = new ProcessBuilder("docker", "stop", "wiremock");
            processBuilder.redirectErrorStream(true);
            Process process = processBuilder.start();

            String output = new String(process.getInputStream().readAllBytes());
            int exitCode = process.waitFor();

            if (exitCode == 0) {
                System.out.println("WireMock Docker container stopped: " + output.trim());
            } else {
                throw new RuntimeException("Failed to stop WireMock Docker container. Output: " + output.trim());
            }
        } catch (IOException | InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Error stopping WireMock Docker container", e);
        }
    }


}