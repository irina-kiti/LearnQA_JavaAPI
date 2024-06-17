import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

public class ex7LongRedir {
    @Test
    public void testLongRed(){
        String locationHeader = "https://playground.learnqa.ru/api/long_redirect";
        int statusCode = 0;
        while (statusCode != 200) {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .get(locationHeader)
                    .andReturn();
            statusCode = response.getStatusCode();
            locationHeader = response.getHeader("location");
            System.out.println(statusCode);
            System.out.println(locationHeader);
        }
    }
}
