package getswf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.logging.Logger;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;

public class PdfConverter {
	private final Logger log= Logger.getLogger(this.getClass().getName());
private File folderForPictures,
        folderForPdf,
        fileName;
private Rectangle pageSize;

private Document doc ;
	public PdfConverter(File f) {
		folderForPictures = f;
		folderForPdf = new File(f.getParentFile() + "/pdf/");
		fileName = new File(folderForPdf.getPath()+"/book.pdf");
		if(!folderForPdf.exists()){
			folderForPdf.mkdirs();
			folderForPdf.setWritable(true);
		}
	
		doc = new Document();
		
	}
	
	public void convert(){
		FileOutputStream fos = null;
		PdfWriter writer = null;
		File[] files = folderForPictures.listFiles();
		   
		sortFiles(files);
		
		if(files.length < 1){
			return;
		}
		log.info(folderForPdf.toString()+" , is directory : " + folderForPdf.isDirectory() );
		
		
		try {
			Image controllim = Image.getInstance(files[0].getAbsolutePath());
			pageSize = new Rectangle(controllim.getWidth(),controllim.getHeight());
			doc.setPageSize(pageSize);
			
			fos = new FileOutputStream(fileName);
			writer = PdfWriter.getInstance(doc, fos);
			writer.open();
			doc.open();
		  
			for (File file : files) {
				
				Image im = Image.getInstance(file.getAbsolutePath());
				doc.add(im);
			}
			log.config(doc.getPageSize().toString());
			log.fine("created :\n" + Arrays.asList(folderForPdf.listFiles()).toString());
			
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

	void sortFiles(File[] files) {
		Arrays.sort(files, new Comparator<File>() {
			@Override
			public int compare(File o1, File o2) {
				int so1 =Integer.parseInt( o1.getName().replaceAll("[\\D]", ""));
				int so2 = Integer.parseInt(o2.getName().replaceAll("[\\D]", ""));
			
				if(so1>so2){
					return 1;
				}else if(so1<so2){
					return -1;
				}else
				return 0;
			}
		});
	}
	
	
	
	
	
	
}
