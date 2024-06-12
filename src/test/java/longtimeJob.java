import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Thread.sleep;

public class longtimeJob {
    @Test
    public void testLongTimeJob() {

        JsonPath responseForToken = RestAssured

                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String responseToken = responseForToken.get("token");
        int sec = responseForToken.get("sec");

        System.out.println(responseToken);
        System.out.println(sec);

        Response responseForStatus = RestAssured
                .given()
                .queryParam("token", responseToken)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        responseForStatus.print();
        System.out.println(responseToken);
        TimeUnit.SECONDS.sleep(sec);
        /*Thread.sleep( sec*1000);*/

        Response responseForResult = RestAssured
                .given()
                .queryParam("token", responseToken)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        responseForResult.print();


    }
}
