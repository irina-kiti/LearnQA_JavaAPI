import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

public class longtimeJob {
    @Test
    public void testLongTimeJob() {

        JsonPath responseForToken = RestAssured

                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String responseToken = responseForToken.get("token");
        int seconds = responseForToken.get("seconds");

        System.out.println(responseToken);
        System.out.println(seconds);

        Response responseForStatus = RestAssured
                .given()
                .queryParam("token", "responseToken")
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        responseForStatus.print();
        System.out.println(responseToken);


    }
}
