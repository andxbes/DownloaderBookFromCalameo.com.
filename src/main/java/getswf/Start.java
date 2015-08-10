package getswf;

import getswf._4book._4Book;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class Start {

    private final static Logger log = Logger.getLogger("Start");

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
	    if (url.contains("4book.org")) {
		new _4Book().parse(url);
	    } else {
		new GetSwf().counter(url);
	    }
	}
	log.info("finish");
    }
    public static void openFolder(File openDir) {
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

}
