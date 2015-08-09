package getswf;

import static org.junit.Assert.*;

import org.junit.Test;

public class GetSwfTest {

	@Test
	public void test() {
		String s = "http://p2.calameo.com/140829102709-db0b671491bce0b62f8642f7beab1ae4/p1.swf";
	
		System.out.println(GetSwf.getGeneratedFolderName(s));
	}

}
