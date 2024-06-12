import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class ex10VarLenght {
@Test
    public void phraseLenght(){
String phrase = "It is a wonderful day";
assertTrue(phrase.length()>15, "Phrase is too short");

}
}
