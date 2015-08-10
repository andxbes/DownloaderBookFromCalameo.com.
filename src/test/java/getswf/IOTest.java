package getswf;

import org.junit.Test;

import junit.framework.Assert;

public class IOTest {

	@Test
	public void test() {
		String s = "http://p2.calameo.com/140829102709-db0b671491bce0b62f8642f7beab1ae4/p1.swf";
	    IO io = new IO();
		Assert.assertTrue(!(io.getGeneratedFolderName(s).contains(".")));
		Assert.assertTrue(!(io.getGeneratedFolderName(s).contains("/")));
	}

}
