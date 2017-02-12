package lv.id.tomsteteris;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javax.swing.JFileChooser;

import org.json.JSONException;
import org.json.JSONObject;

public class GeoToolkit extends Thread {
	private static Logger l;

	public static void main(String[] argv) {
		l = new Logger();
		l.log("------: App started");

		String json = getInput();
		l.log("Json > " + json);
		
//		JSON j = new JSON(json);
		JSONObject data = new JSONObject(json);
		
		JFileChooser chooser = new JFileChooser(); 
//		chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		
		int result = chooser.showSaveDialog(null);
		
		if (result == JFileChooser.APPROVE_OPTION) {
			File file = chooser.getSelectedFile();
			try {
				FileOutputStream outFile = new FileOutputStream(file.getAbsoluteFile());
//				StringEscapeUtilitils.unescapeHtml4();
				outFile.write(
						data.getString("gpxFile")
							.replaceAll("&lt;", "<")
							.replaceAll("&gt;", ">")
							.replaceAll("&nbsp;", " ")
							.getBytes());
				outFile.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				l.log("Error: File \"" + file.getAbsoluteFile() + "\" Not found");
//				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				l.log("Error: Json error - " + e.getMessage());
//				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				l.log("Error: IO error");
//				e.printStackTrace();
			}
		}

		exit();
	}
	
	public static String getInput() {
		String gpxFileContent = "";
		String text;
		int length = 0;
		byte[] b = new byte[4];
		ByteBuffer bb;
		try {
			FileOutputStream outFile = new FileOutputStream("/home/" + System.getProperty("user.name") + "/Desktop/out.txt");
			InputStream input = System.in;
			
			l.log("--: Reading Starts :--");
			text = "";
			length = input.read(b);
			l.log("Bytes readed >  " + length);
			bb = ByteBuffer.wrap(b);
			bb.order(ByteOrder.nativeOrder());
			length = bb.getInt();
			
			if (length == -1) {
				outFile.close();
				return gpxFileContent;
			}
			l.log("Length > " + length);
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input, "UTF-8"));
			while (length > 0) {
				char c = (char) bufferedReader.read();
				if (Character.UnicodeBlock.of(c) != Character.UnicodeBlock.BASIC_LATIN) {
					length--;
				}
				length--;
				text += c;
			}

			l.log(text);
			
			gpxFileContent += text;
			
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
		return gpxFileContent;
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
