import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import java.util.concurrent.TimeUnit;

public class longtimeJob{
    @Test
    public void testLongTimeJob() {

        JsonPath responseForToken = RestAssured

                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();

        String responseToken = responseForToken.get("token");
        int sec = responseForToken.get("seconds");

            Response responseForStatus = RestAssured
                .given()
                .queryParam("token", responseToken)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .andReturn();

        try {
            Thread.sleep(sec*1000);
        } catch (InterruptedException e){
            e.printStackTrace();
        }

               JsonPath responseForResult = RestAssured
                .given()
                .queryParam("token", responseToken)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String status = responseForResult.get("status");
        String result = responseForResult.get("result");
        System.out.println(status);
        System.out.println(result);

    }
}
