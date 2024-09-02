package apitests;

import static constants.Urls.BASE_URL;
import static constants.Urls.DELETE_URL;
import static constants.Urls.GET_URL;
import static constants.Urls.PATCH_URL;
import static constants.Urls.PUT_URL;
import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import io.restassured.RestAssured;
import io.restassured.config.EncoderConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.annotations.Test;

public class ApiTest {

  @Test
  public void testPostRawText() {
    RestAssured.baseURI = BASE_URL;
    String requestBody = "This is expected to be sent back as part of response body.";
    Response response = given()
        .contentType("text/plain")
        .body(requestBody)
        .when()
        .post("/post")
        .then()
        .statusCode(200)
        .extract().response();

    String responseBody = response.asString();
    System.out.println(responseBody);
    response.then().body("data", equalTo(requestBody));
  }

  @Test
  public void testPostFormData() {
    RestAssured.baseURI = BASE_URL;
    Response response = given().config(RestAssured.config()
            .encoderConfig(EncoderConfig.encoderConfig()
                .encodeContentTypeAs("x-www-form-urlencoded", ContentType.URLENC)))
        .contentType(ContentType.URLENC.withCharset("UTF-8"))
        .formParam("foo1", "bar1")
        .formParam("foo2", "bar2")
        .when()
        .post("/post")
        .then()
        .statusCode(200)
        .body("form.foo1", equalTo("bar1"))
        .body("form.foo2", equalTo("bar2"))
        .extract().response();

    System.out.println("Response Status Code: " + response.getStatusCode());
    System.out.println("Response Body: " + response.asString());
  }

  @Test
  public void testGet() {
    RestAssured.given()
        .queryParam("foo1", "bar1")
        .queryParam("foo2", "bar2")
        .when()
        .get(GET_URL)
        .then()
        .statusCode(200)
        .body("args.foo1", equalTo("bar1"))
        .body("args.foo2", equalTo("bar2"));
  }

  @Test
  public void testPut() {
    String requestBody = "This is expected to be sent back as part of response body.";
    RestAssured.given()
        .body(requestBody)
        .when()
        .put(PUT_URL)
        .then()
        .statusCode(200)
        .body("data", equalTo(requestBody));
  }

  @Test
  public void testPatch() {
    String requestBody = "This is expected to be sent back as part of response body.";
    RestAssured.given()
        .body(requestBody)
        .when()
        .patch(PATCH_URL)
        .then()
        .statusCode(200)
        .body("data", equalTo(requestBody));
  }

  @Test
  public void testDelete() {
    String requestBody = "This is expected to be sent back as part of response body.";
    RestAssured.given()
        .body(requestBody)
        .when()
        .delete(DELETE_URL)
        .then()
        .statusCode(200)
        .body("data", equalTo(requestBody));
  }
}
