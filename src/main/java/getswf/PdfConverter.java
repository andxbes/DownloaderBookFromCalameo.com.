package getswf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfConverter {
	private final Logger log= Logger.getLogger(this.getClass().getName());
private File folderForPictures,
        folderForPdf;
private Document doc ;
	public PdfConverter(File f) {
		folderForPictures = f;
		folderForPdf= new File(f.getParentFile() + "/pdf/");
		
		if(!folderForPdf.exists())folderForPdf.mkdirs();
		
		log.info(folderForPdf.toString());
		
		doc = new Document();
		
	}
	
	public void convert(){
		FileOutputStream fos = null;
		PdfWriter writer = null;
		File[] files = folderForPictures.listFiles();
		System.out.println(folderForPdf);
		
		try {
			fos = new FileOutputStream(folderForPdf);
			writer = PdfWriter.getInstance(doc, fos);
			writer.open();
			doc.open();
			
			for (File file : files) {
				doc.add(Image.getInstance(file.getAbsolutePath()));
			}
			
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (DocumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			doc.close();
			writer.close();
		}
		
	}
	
	
	
	
	
	
}
