/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package getswf;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JOptionPane;

/**
 *
 * @author Andr
 */
public class GetSwf {

	private  final ExecutorService task = Executors.newFixedThreadPool(3);
	private  final List<Runnable> listOfTasks = Collections.synchronizedList(new LinkedList<>());

	private final  Logger log = Logger.getLogger("GetSwf");
	private  String folderName;

	public  void counter(String ur) {
		DynamicURL du = new DynamicURL(ur);
		IO io = new IO();
		folderName = "./" + io.getGeneratedFolderName(du.getAddressService()) + "/";
		int i = 0;
		while (true) {
			String fileName = du.getPrefix() + (++i) + du.getSufix();
			

			try {
				URL u = new URL(du.getAddressService() + fileName);

				io.fileWrite(new File(folderName + fileName), io.getData(u),(f)->{
						if (f.getName().toString().contains(".swf"))
						listOfTasks.add(new Runnable() {
							@Override
							public void run() {
								new ExtractSwf(f).extract();
								listOfTasks.remove(this);
							}
						});					
				});

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

	private  void endFunction() {
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

	private  void openFolder(File openDir) {
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

	private  void runTasks() {
		for (Runnable listofTask : listOfTasks) {
			task.submit(listofTask);
		}

	}

}
