package kercar.comAPI.test;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

public class TestSuite {

	
	@RunWith(Suite.class)
	@SuiteClasses(value={
		MessageTest.class,
		JSONTest.class,
	})
	public class AllTests{
	}
	
}
