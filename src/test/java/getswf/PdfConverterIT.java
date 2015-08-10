package getswf;


import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.Test;

public class PdfConverterIT {

	@Test
	public void test() {
		PdfConverter pdc = new PdfConverter(new File(this.getClass().getResource("/swf/results/").getFile()));
		pdc.convert();
	}
	
	@Test
	public void test2() {
	        
		File f = new File(this.getClass().getResource("/swf2/").getFile());
		f.mkdirs();
		PdfConverter pdc = new PdfConverter(f);
		for(int i = 1 ;i<100;i++){
			try {
				FileWriter fw = new FileWriter(f+"/(3)p"+ i +".jpeg");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		File[] files = f.listFiles();
		pdc.sortFiles(files);
		for (File file : files) {
			System.out.println(file.getName());
		}
		
		String str = "(3)p33.jpeg";
	    System.out.println(str.replaceAll("[\\D]", ""));
	}

}
