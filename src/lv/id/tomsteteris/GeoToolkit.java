package lv.id.tomsteteris;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class GeoToolkit extends Thread {
	private static Logger l;

	public static void main(String[] argv) {
		// TODO: Add logger
		l = new Logger();
		l.log("------: App started");
		InputStream input = System.in;


		try {
			input.available();

			int length = 0;
			String text;
			FileOutputStream outFile = new FileOutputStream("/home/" + System.getProperty("user.name") + "/Desktop/out.txt");

			while (true) {
				text = "";
				length = (int) input.read();
				l.log("" + length);
				length += 2;

				while (length >= 0) {
					length--;
					char c = (char) input.read();
					if (c == 0) {
						continue;
					}

					text += c;
					l.log("" + c);
				}

				l.log("Text: -" + text + "-");

				if (text.contains("exit")) {
					break;
				}

			}
			l.log("Loop ended");
			outFile.close();
		} catch (IOException e) {
			e.printStackTrace();
			l.log("Input not available :(");
		} catch (NullPointerException npe) {
			for(StackTraceElement el: npe.getStackTrace()) {
				l.log(el.getClassName() + " [" + el.getLineNumber() + "]" );
			}
			l.log("Null Pointer Exception: " + npe.getMessage());
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		exit();
	}

	public static void exit() {
		l.log("Exiting");
		List<String> lines = Arrays.asList("Text example", "Exit");
		Path file = Paths.get("deamon_thread.txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.exit(0);
		}
	}
}

class Logger {

	private String fileName = "/home/" + System.getProperty("user.name") + "/Desktop/log.txt";
	private PrintWriter out;

	public Logger () {
		try {
			FileWriter fw = new FileWriter(fileName, true);
			BufferedWriter bw = new BufferedWriter(fw);
			out = new PrintWriter(bw);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public void log(String txt) {
		try {
			out.write(txt + "\n");
			out.flush();
		} catch (Exception ex) {
			System.out.println("Error:");
			ex.printStackTrace();
		}
	}

	@Override
	public void finalize() {
		out.close();
	}

}
