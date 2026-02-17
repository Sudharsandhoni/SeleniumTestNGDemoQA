package tests;
import static core.utils.LogUtils.*;
import org.testng.annotations.*;

public class SampleTest extends BaseTest{
	
	@Test
	public void Test1() {
		System.out.println("Smoke Test");
		logInfo("Running test1");
	}

}
