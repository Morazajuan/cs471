package action;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class OSPathTest {


    @Test
    public void osPathMAc() {
        //Given
        String path = "/some/Radnom/address";
        String file ="fileWithWierd.Extension";
        int OS = 0; //0(/) for mac, 1(\) for windows
        CreateFile myTest = new CreateFile();

        //When
        String FullPAth = myTest.OSPath(path,file,OS);

        //Then
        Assert.assertEquals("/some/Radnom/address/fileWithWierd.Extension", FullPAth);

    }

    @Test
    public void osPathWndows() {
        //Given
        String path = "\\some\\Radnom\\address";
        String file ="fileWithWierd.Extension";
        int OS = 1; //0(/) for mac, 1(\) for windows
        CreateFile myTest = new CreateFile();

        //When
        String FullPAth = myTest.OSPath(path,file,OS);

        //Then
        Assert.assertEquals("\\some\\Radnom\\address\\fileWithWierd.Extension", FullPAth);

    }
}