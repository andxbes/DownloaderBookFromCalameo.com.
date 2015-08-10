package getswf;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.channels.ReadableByteChannel;
import java.util.logging.Level;
import java.util.logging.Logger;

public class IO {

	public ReadableByteChannel getData(URL url) throws Exception {
		ReadableByteChannel channel = null;
		try {
			HttpURLConnection conn;

			conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");

			if (conn.getResponseCode() == 200 || conn.getResponseCode() == 304 ) {
				channel = Channels.newChannel(conn.getInputStream());
			} else {
				throw new Exception();
			}

		} catch (IOException ex) {
			Logger.getLogger(GetSwf.class.getName()).log(Level.SEVERE, null, ex);
		}
		return channel;
	}

	public void fileWrite(File f, ReadableByteChannel rbc , Work work) {

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
						if(work != null) work.doSomething(f);
//						if (f.getName().toString().contains(".swf"))
//							listOfTasks.add(() -> {
//								new ExtractSwf(f).extract();
//								listOfTasks.remove(this);
//							});
					} catch (IOException ex) {
						Logger.getLogger(this.getClass().getName()).log(Level.SEVERE, null, ex);
					}
				}

			}
		}

	}

	public String getGeneratedFolderName(String s) {
		return s.replaceAll("http://", "").replaceAll("/", "=").replaceAll("\\.", "-");
	}
	
	public interface Work{
		public void doSomething(File f);
	}
}



