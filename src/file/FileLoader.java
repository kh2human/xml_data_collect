package file;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;
import time.TimeChecker;
import java.util.HashMap;
import java.io.BufferedReader;

public class FileLoader {
	
	public static Map<String, String> parseFile(String path) {
		Charset charset = Charset.forName("UTF-8");
		Path setting = Paths.get("./resources", "XMLCollector.ini");
		Map<String, String> settings = new HashMap<String, String>();
		
		if (Files.notExists(setting)) {
			return new HashMap<String, String>();
		}
			
		try {
			BufferedReader reader = Files.newBufferedReader(setting, charset);
			String line = null;
			
			while ((line = reader.readLine()) != null) {
				line = line.trim();
				int colonIndex = line.indexOf(":");
				String key = line.substring(0, colonIndex).trim();
				String value = line.substring(colonIndex + 1).trim();
				
				settings.put(key, value);
			}
		} catch (Exception e) {
			System.out.println(TimeChecker.getTime() + " > FILE LOAD EXCEPTION");
		}
		
		return settings;
	}

	public static void main(String[] args) {
		Map<String, String> settings = parseFile("./resources/XMLCollector.ini");
		String[] keys = settings.keySet().toArray(new String[0]);
		for (int i = 0; i < settings.size(); i++) {
			System.out.println(keys[i] + ", " + settings.get(keys[i]));
		}
	}

}
