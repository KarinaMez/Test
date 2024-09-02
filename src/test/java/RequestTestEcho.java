import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class RequestTestEcho {

    @BeforeEach
    public void setup() {
        // Устанавливаем базовый URI, чтобы не повторять его в каждом тесте
        RestAssured.baseURI = "https://postman-echo.com";
    }

    @Test
    public void testGetRequest() {
        Response response = given()
                .log().all() // Логируем все детали запроса для отладки
                .param("foo1", "bar1")
                .param("foo2", "bar2")
                .when()
                .get("/get") // Выполняем GET запрос по указанному пути
                .then()
                .log().all() // Логируем
                .assertThat()
                .statusCode(200) // Проверяем, что статус код ответа равен 200 (ОК)
                .body("args.foo1", equalTo("bar1")) // Проверяем, что параметр foo1 равен "bar1"
                .body("args.foo2", equalTo("bar2")) // Проверяем, что параметр foo2 равен "bar2"
                .extract()
                .response(); // Извлекаем ответ

        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test
    public void testPostRawText() {
        Response response = given()
                .log().all() // Логируем
                .contentType("text/plain") // Устанавливаем тип - текст
                .body("This is expected to be sent back as part of response body.") // Устанавливаем тело запроса
                .when()
                .post("/post")
                .then()
                .log().all() // Логируем
                .assertThat()
                .statusCode(200) // Проверяем, что статус код ответа равен 200
                .body("data", equalTo("This is expected to be sent back as part of response body.")) // Проверяем, что поле data содержит ожидаемый текст
                .extract()
                .response(); // Извлекаем ответ

        System.out.println("Response Body: " + response.getBody().asString());
    }

    @Test
    public void testPostFormData() {
        Response response = given()
                .log().all() // Логируем
                .contentType("application/x-www-form-urlencoded; charset=UTF-8") // Устанавливаем тип и кодировку
                .formParam("foo1", "bar1")
                .formParam("foo2", "bar2")
                .when()
                .post("/post")
                .then()
                .log().all() // Логируем
                .assertThat()
                .statusCode(200) // Проверяем, что статус код ответа равен 200
                .body("form.foo1", equalTo("bar1")) // Проверяем, что параметр формы foo1 равен "bar1"
                .body("form.foo2", equalTo("bar2")) // Проверяем, что параметр формы foo2 равен "bar2"
                .extract()
                .response(); // Извлекаем ответ

        System.out.println("POST Form Data Response Body: " + response.getBody().asString());
    }

    @Test
    public void testPutRawText() {
        Response response = given()
                .log().all() // Логируем
                .contentType("text/plain") // Устанавливаем тип - текст
                .body("This is expected to be sent back as part of response body.") // Устанавливаем тело запроса
                .when()
                .put("/put")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("data", equalTo("This is expected to be sent back as part of response body."))
                .extract()
                .response();

        System.out.println("PUT Raw Text Response Body: " + response.getBody().asString());
    }

    @Test
    public void testPatchRequest() {
        Response response = given()
                .log().all()
                .contentType("text/plain") // Устанавливаем тип - текст
                .body("This is expected to be sent back as part of response body.")
                .when()
                .patch("/patch")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("data", equalTo("This is expected to be sent back as part of response body."))
                .extract()
                .response();

        System.out.println("PATCH Request Response Body: " + response.getBody().asString());
    }

    @Test
    public void testDeleteRequest() {
        Response response = given()
                .log().all()
                .contentType("text/plain")
                .body("This is expected to be sent back as part of response body.")
                .when()
                .delete("/delete")
                .then()
                .log().all()
                .assertThat()
                .statusCode(200)
                .body("data", equalTo("This is expected to be sent back as part of response body.")) // Проверяем, что поле data содержит ожидаемый текст
                .extract()
                .response();

        System.out.println("DELETE Request Response Body: " + response.getBody().asString());
    }
}