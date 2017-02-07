package lv.id.tomsteteris;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

public class GeoToolkit extends Thread {
	private static GeoToolkit gt = null;
	private long startTime = 0;
	
	public static void main(String[] argv) {
		System.out.println("Welcome!");
		
		if (gt == null) {
			gt = new GeoToolkit();
			
			gt.start();
		}
	}
	
	public GeoToolkit() {
		setDaemon(false);
		gt = this;
	}
	
	public void run() {
		startTime = System.currentTimeMillis();
		
		while (System.currentTimeMillis() - startTime < 3000) {
		}
		
		exit();
	}
	
	public void exit() {
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
