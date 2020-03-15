package action;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class detectFileExtensionTest {

    @Test
    public void detectFileExt() {
        //Given
        String str  = "crazyName.java";

        //when
        CreateFile myTest = new CreateFile();

        //then
        Assert.assertEquals('j',myTest.detectFileExt(str));

    }

    @Test
    public void detectFileExtC() {
        //Given
        String str  = "crazyName.c";

        //when
        CreateFile myTest = new CreateFile();

        //then
        Assert.assertEquals('c',myTest.detectFileExt(str));

    }

    @Test
    public void detectFileExtCFail() {
        //Given
        String str  = "crazyName.fail";

        //when
        CreateFile myTest = new CreateFile();

        //then
        Assert.assertEquals('e',myTest.detectFileExt(str));//e for error

    }

}

