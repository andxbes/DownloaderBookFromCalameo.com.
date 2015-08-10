package getswf;

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
				new GetSwf().counter(url);
			}
			log.info("finish");
		}

		
	

}
