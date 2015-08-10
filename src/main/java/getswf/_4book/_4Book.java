package getswf._4book;

import java.io.File;
import java.io.IOException;
import java.net.URL;


import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import getswf.IO;

public class _4Book {
	private String folderName;
	private File folder;
/*
 * 
 * */
	public _4Book() {
		// TODO Auto-generated constructor stub
	}
	
	public void parse(String url){
	    try {
	    	IO io = new IO();
	    	folderName = io.getGeneratedFolderName(url);
	    	
	    	
			Document docFirst = Jsoup.connect(url).get();
			
			Elements elems = docFirst.select("iframe");	
			
			Element el = elems.first();
			System.out.println(el);
			String urlWithPicture = el.attr("src");
			if(!urlWithPicture.contains("http:")){
				urlWithPicture = "http:"+urlWithPicture;
			}
			
			Document docPic = Jsoup.connect(urlWithPicture).get();
			Elements elements = docPic.select(".slide img");
			
			for (Element element : elements) {
				System.out.println(element.attr("data-full"));
			}
			
			
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
}
