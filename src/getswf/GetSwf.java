/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getswf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Andr
 */
public class GetSwf {

    public final static Logger log = Logger.getLogger("this_Class");

    /**
     * @param args the command line arguments
     * http://p2.calameo.com/140829102709-db0b671491bce0b62f8642f7beab1ae4/p1.swf
     * http://page.issuu.com/130114195401-08397d4a891246c09a78233fd1304cc1/swf/page_9.swf
     */

    public static void main(String[] args) {

	String url = null;

	if (args.length == 0) {
	    JTextField tf = new JTextField();
	    JOptionPane.showMessageDialog(null, tf, "Введите URL", 1);
	    url = tf.getText();
	} else {
	    url = args[0];
	}
	log.info(url);
	if (!url.isEmpty()) {
	    counter(geturl(url));
	}
    }

    private static void counter(String url) {
	String sufix = ".swf";
	String prefix = null;
	if(url.contains("calameo")){
	  prefix = "p";
	}else if(url.contains("issuu")){
	   prefix = "page_";
	}
	
	
	int i = 0;
	while (true) {
	    String fileName = prefix + (++i) + sufix;
	    GetSwf gs = new GetSwf();

	    try {
		URL u = new URL(url + fileName);
		gs.fileWrite(new File("./files/" + fileName), gs.getData(u));
		
		log.log(Level.INFO, "operation with url : {0}  - is successful", u.toString());
	    } catch (MalformedURLException ex) {
		Logger.getLogger(GetSwf.class.getName()).log(Level.SEVERE, null, ex);
	    } catch (Exception ex) {
		JOptionPane.showMessageDialog(null, "Скачано " + (i - 1) + " файлa/(ов)", "Finish", 1);
		break;
	    }

	}
	createEX();
	log.info("finish");

    }

    private static void createEX() {
	File f = new File("./files/EX/");
	if (!f.exists()) {
	    f.mkdirs();
	}

    }

    private static String geturl(String url) {
	String s[] = url.split("/");
	url = url.replace(s[s.length - 1], "");
	System.out.println(url);
	return url;
    }

    private ReadableByteChannel getData(URL url) throws Exception {
	ReadableByteChannel channel = null;
	try {
	    HttpURLConnection conn;

	    conn = (HttpURLConnection) url.openConnection();
	    conn.setRequestMethod("GET");

	    if (conn.getResponseCode() == 200) {
		channel = Channels.newChannel(conn.getInputStream());
	    } else {
		throw new Exception();
	    }

	} catch (IOException ex) {
	    Logger.getLogger(GetSwf.class.getName()).log(Level.SEVERE, null, ex);
	}
	return channel;
    }

    private void fileWrite(File f, ReadableByteChannel rbc) {

	if (!f.exists()) {
	    File folders = f.getParentFile();
	    folders.mkdirs();
	}
	if (rbc != null) {
	    ByteBuffer buf = ByteBuffer.allocate(1024);
	    try (FileChannel file2 = new FileOutputStream(f).getChannel()) {
		while (rbc.read(buf) != -1) {
		    buf.flip();
		    file2.write(buf);
		    buf.clear();
		}
	    } catch (FileNotFoundException ex) {
		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } catch (IOException ex) {
		Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
	    } finally {
		if (rbc != null) {
		    try {
			rbc.close();
		    } catch (IOException ex) {
			Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
		    }
		}

	    }
	}

    }

}
