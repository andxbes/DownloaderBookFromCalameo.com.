package getswf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

public class ExtractSwf {
	private final File file;
	private static Logger log = Logger.getLogger("ExtractSwf");
	private static  File app = new File("./lib/swfextract.exe");
	
	static{
		log.config("contents of a folder ./lib/:\n"
				+ Arrays.asList(app.getParentFile().listFiles()).toString());
	}

	public ExtractSwf(File f) {
		this.file = f;
		
		// extract();
	}

	void extract() {
		String name = file.getAbsolutePath();
		try {
			

			Map<String, List<String>> map = new HashMap<>(4);
            String query = app.getAbsolutePath() + " " + name;
            		log.fine(" get ids from swf  = " + query);
			Process p = Runtime.getRuntime().exec(query);
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			while ((line = b.readLine()) != null) {
				getKeyandIDs(line, map);
			}
			log.fine(" map with key + ids : \n"+map.toString());
			copyFileToFolder(map);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void copyFileToFolder(Map<String, List<String>> map) {
		
		String fileName = file.getName().split("\\.")[0];
		//log.fine(fileName);
		File resultFolder = new File(file.getParentFile()
				//.getParentFile()
				.getAbsolutePath() + "/results");

		if (!resultFolder.exists())
			resultFolder.mkdirs();

		for (Map.Entry<String, List<String>> entry : map.entrySet()) {
			
			String key = entry.getKey();
			List<String> ids = entry.getValue();
			String sufix = null;
			
			// TODO add more type files
			switch (key) {
			case ("-j"):
				sufix = ".jpeg";
				break;
			default:

				continue;
			}
			for (String id : ids) {
				try {
					File toFile = new File(resultFolder.getAbsolutePath() + "/" + "("+ id + ")" + fileName + sufix);
					log.info("copy to " + toFile.toString());
					String query = app.getAbsolutePath() 
							+ " " + key + " " + id 
							+ " -o " + toFile.getAbsolutePath() 
							+ " " + file.getAbsolutePath();
					Runtime.getRuntime().exec(query);
					log.config("query = " + query);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}

	}

	void getKeyandIDs(String s, Map<String, List<String>> map) {

		// s is like "[-i] 2 Shapes: ID(s) 2, 4"
		if (s.contains("ID(s)")) {

			String stepNOne[] = s.split("[\\[\\]]");
			String key = stepNOne[1];

			String stepNtwo[] = stepNOne[stepNOne.length - 1].split(" ");

			StringBuilder sb = new StringBuilder(5);

			for (int i = 4; i < stepNtwo.length; i++) {
				sb.append(stepNtwo[i]);
			}
			map.put(key, Arrays.asList(sb.toString().split(",")));

		}
	}

}
