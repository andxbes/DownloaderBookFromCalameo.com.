/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getswf;

import java.awt.Desktop;
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
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author Andr
 */
public class GetSwf {

	private static final ExecutorService task = Executors.newFixedThreadPool(3);
	private static final List<Runnable> listOfTasks = Collections.synchronizedList(new LinkedList<>());

	public final static Logger log = Logger.getLogger("this_Class");
	private static String folderName;

	/**
	 * @param args
	 *            the command line arguments 
	 *            http://p2.calameo.com/140829102709-db0b671491bce0b62f8642f7beab1ae4/p1.swf
	 *            http://page.issuu.com/130114195401-
	 *            08397d4a891246c09a78233fd1304cc1/swf/page_9.swf
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
		log.fine(url);

		if (!url.isEmpty()) {
			counter(url);
		}
		log.info("finish");
	}

	private static void counter(String ur) {
		DynamicURL du = new DynamicURL(ur);
		folderName = "./" + getGeneratedFolderName(du.getAddressService()) + "/";
		int i = 0;
		while (true) {
			String fileName = du.getPrefix() + (++i) + du.getSufix();
			GetSwf gs = new GetSwf();

			try {
				URL u = new URL(du.getAddressService() + fileName);

				gs.fileWrite(new File(folderName + fileName), gs.getData(u));

				log.log(Level.INFO, "operation with url : {0}  - is successful", u.toString());
			} catch (MalformedURLException ex) {
				Logger.getLogger(GetSwf.class.getName()).log(Level.SEVERE, null, ex);
			} catch (Exception ex) {
				break;
			}

		}

		runTasks();
		endFunction();
		JOptionPane.showMessageDialog(null, "Скачано " + (i - 1) + " файлa/(ов)", "Finish", 1);
	}

	private static void endFunction() {
		task.shutdown();
		while (!task.isTerminated()) {
			log.log(Level.INFO, "task  = {0}", listOfTasks.size());
			try {
				Thread.sleep(500);
			} catch (InterruptedException ex) {
				Logger.getLogger(GetSwf.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

		File openDir = new File(folderName + "results/");
		if (!openDir.exists()) {
			openDir = new File(openDir.getParent());
		}
		new PdfConverter(openDir).convert();
		
		openFolder(openDir);
		
		File pdf = new File(folderName + "pdf/");
		if(pdf.exists())openFolder(pdf);
	}

	private static void openFolder(File openDir) {
		Desktop desktop = null;
		if (Desktop.isDesktopSupported()) {
			desktop = Desktop.getDesktop();
		}
		
		try {
			desktop.open(openDir);
		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	private static void runTasks() {
		for (Runnable listofTask : listOfTasks) {
			task.submit(listofTask);
		}

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
						// extract files
						if (f.getName().toString().contains(".swf"))
							listOfTasks.add(() -> {
								new ExtractSwf(f).extract();
								listOfTasks.remove(this);
							});
					} catch (IOException ex) {
						Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
					}
				}

			}
		}

	}

	static String getGeneratedFolderName(String s) {
		return s.replaceAll("http://", "").replaceAll("/", "=").replaceAll("\\.", "-");
	}

}
