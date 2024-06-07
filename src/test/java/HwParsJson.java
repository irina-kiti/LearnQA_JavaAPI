import io.restassured.path.json.JsonPath;
import org.junit.jupiter.api.Test;
import io.restassured.RestAssured;

import java.util.ArrayList;

public class HwParsJson {
    @Test
    public void testRestAssured(){
        JsonPath response= RestAssured
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        ArrayList messages = response.get("messages");
        System.out.println(messages.get(1));
    }
}
