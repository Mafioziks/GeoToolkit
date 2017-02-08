package lv.id.tomsteteris;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class GeoToolkit extends Thread {
	private static long startTime = 0;
	
	public static void main(String[] argv) {
		System.out.println("Welcome!");
		
		startTime = System.currentTimeMillis();
	
		InputStream input = System.in;
		
		
		try {
			input.available();
			
			char receivedChar;
			try {
				FileOutputStream outFile = new FileOutputStream("/home/" + System.getProperty("user.name") + "/Desktop/out.txt");
				while ((receivedChar = (char) input.read()) != -1) {
					outFile.write(receivedChar);
					
					if (receivedChar == '}') {
						return;
					}
				}
				outFile.close();
				
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
			System.out.println("Input not available :(");
		}
			
		exit();
	}
	
	public static void exit() {
		System.out.println("Exiting");
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
