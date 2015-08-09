package getswf;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Logger;

import javax.sound.midi.Soundbank;

import sun.util.logging.resources.logging;

public class ExtractSwf {
	private final File file;
	private static Logger log = Logger.getLogger("ExtractSwf");
	private File app = new File("./lib/swfextract.exe");

	public ExtractSwf(File f) {
		this.file = f;
		// extract();
	}

	void extract() {
		String name = file.getAbsolutePath();
		try {
			log.fine("Извлекаем");

			Map<String, List<String>> map = new HashMap<>(4);

			Process p = Runtime.getRuntime().exec(app.getAbsolutePath() + " " + name);
			BufferedReader b = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			while ((line = b.readLine()) != null) {
				log.fine(line);
				getKeyandIDs(line, map);

			}
			copyFileToFolder(map);

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	void copyFileToFolder(Map<String, List<String>> map) {
		
		String fileName = file.getName().split("\\.")[0];
		log.fine(fileName);
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
					log.info("copy to " + resultFolder.getAbsolutePath()+ "/" + id + fileName);
					Process p = Runtime.getRuntime().exec(app.getAbsolutePath() 
							+ " " + key + " " + id 
							+ " -o " + resultFolder.getAbsolutePath() + "/" + "("+ id + ")" + fileName + sufix 
							+ " " + file.getAbsolutePath());
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

			log.fine("key : " + key);
			String stepNtwo[] = stepNOne[stepNOne.length - 1].split(" ");

			StringBuilder sb = new StringBuilder(5);

			for (int i = 4; i < stepNtwo.length; i++) {
				sb.append(stepNtwo[i]);
			}
			map.put(key, Arrays.asList(sb.toString().split(",")));

		}
	}

}
