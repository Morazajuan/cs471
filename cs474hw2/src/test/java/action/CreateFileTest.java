package action;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class CreateFileTest {

    @Test
    public void getPatternNameTest(){
        //Given
        int num = 3;
        String patternName;
        CreateFile myTest = new CreateFile();

        //When
        patternName = myTest.getPatternName(num);
        /*  0 to 6 respectively in the order shown
        *      "Abstract Factory Pattern",
                "Builder Pattern",
                "Facade Pattern",
                "Chain of Responsibility Pattern",
                "Mediator Pattern",
                "Visitor Pattern",
                "Template methodName Pattern"
        * */

        //Then
        Assert.assertEquals("Chain of Responsibility Pattern", patternName);

    }

}