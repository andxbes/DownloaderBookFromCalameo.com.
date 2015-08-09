package getswf;

import static org.junit.Assert.*;

import java.io.File;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.sound.midi.Soundbank;

import org.junit.Test;

import junit.framework.Assert;

public class ExtractSwfTest {

	@Test
	public void test() {
		
		System.out.println(new File(".").getAbsolutePath());
		
		
		ExtractSwf es = new ExtractSwf(new File(getClass().getResource("/p1.swf").getFile()));
		es.extract();
		
		ExtractSwf es2 = new ExtractSwf(new File(getClass().getResource("/page_1.swf").getFile()));
		es2.extract();
	}
	
	@Test
	public void test2() {
		String s = "[-i] 2 Shapes: ID(s) 2, 4";
		
		Map<String, List<String>> map = new HashMap<>(1);
		ExtractSwf es = new ExtractSwf(new File(""));
		
		es.getKeyandIDs(s, map);
		
		for(Entry<String, List<String> > e : map.entrySet()){
			String result = e.getKey() + ":";
			List<String> list =  e.getValue();
			System.out.println(list);
			for (int i = 0; i < list.size(); i++) {
				result+=list.get(i);
				if(list.size()-1 > i){
					result+=",";
				}
			}
			System.out.println("result =" + result);
			Assert.assertTrue("-i:2,4".equals(result));
		}
	
		
	}

}
