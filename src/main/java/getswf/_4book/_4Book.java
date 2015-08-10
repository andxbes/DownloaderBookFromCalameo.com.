package getswf._4book;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import getswf.IO;
import getswf.PdfConverter;
import getswf.Start;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class _4Book {

    private Logger log = Logger.getLogger(this.getClass().getName());
    private String folderName;
    private File folder,
	    folderPic;
    /*
     * 
     * */

    public _4Book() {
	// TODO Auto-generated constructor stub
    }

    public void parse(String url) {
	try {
	    IO io = new IO();
	    folderName = io.getGeneratedFolderName(url);
	    folder = new File("./" + folderName);
	    if (!folder.exists()) {
		folder.mkdirs();
	    }
	    folderPic = new File(folder+"/pic");
	    if(!folderPic.exists())folderPic.mkdirs();

	    Document docFirst = Jsoup.connect(url).get();

	    Elements elems = docFirst.select("iframe");

	    Element el = elems.first();
	    log.info(el.toString());
	    String urlWithPicture = el.attr("src");
	    //add "http:" to url 
	    if (!urlWithPicture.contains("http:")) {
		urlWithPicture = "http:" + urlWithPicture;
	    }

	    Document docPic = Jsoup.connect(urlWithPicture).get();
	    Elements elements = docPic.select(".slide img");

	    for (int i = 0; i < elements.size(); i++) {
		Element element = elements.get(i);
		String urlToPicture = element.attr("data-full");
		log.info(urlToPicture);
		io.fileWrite(new File(folderPic+"/"+ (i+1) +".jpeg"), io.getData(new URL(urlToPicture)), null);
	    }
	    PdfConverter pc = new PdfConverter(folderPic);
	    pc.convert();
	    Start.openFolder(folder);


	} catch (IOException e) {
	    // TODO Auto-generated catch block
	    e.printStackTrace();
	} catch (Exception ex) {
	    Logger.getLogger(_4Book.class.getName()).log(Level.SEVERE, null, ex);
	}
    }

}
