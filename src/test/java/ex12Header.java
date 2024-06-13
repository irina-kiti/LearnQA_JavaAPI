import io.restassured.RestAssured;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ex12Header {
    @Test
    public void testHeader(){
        Response response = RestAssured
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        Headers headers = response.getHeaders();
        assertTrue(headers.hasHeaderWithName("x-secret-homework-header"), "Response does not have 'x-secret-homework-header' header");


    }
}
