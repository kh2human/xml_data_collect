package file;

//import java.io.BufferedWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.io.PrintWriter;

public class Logger {
	private static Path logFile = null;
	
	public static void writeLog(String log) {
		logFile = Paths.get("./log", time.TimeChecker.getTime("YYYYMMdd") + ".log");
		try {
			if (Files.notExists(logFile.getParent())) {
				Files.createDirectories(logFile.getParent());
			}
			if (Files.notExists(logFile)) {
				Files.createFile(logFile);
			}
			PrintWriter printWriter = new PrintWriter(Files.newBufferedWriter(logFile, Charset.forName("UTF-8"), StandardOpenOption.APPEND));
			printWriter.println(log);
//			printWriter.newLine();
			printWriter.close();
		} catch (Exception e) {
			System.out.println(time.TimeChecker.getTime() + " > FILE ERROR, " + e.getMessage());
		}
		
	}

	public static void main(String[] args) {
		writeLog(time.TimeChecker.getTime() + " > LOGGING TEST.");
	}

}
