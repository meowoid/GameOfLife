package uk.ac.cam.yp242.tick4star;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.List;
import java.net.URL;
import java.net.URLConnection;
import java.util.LinkedList;
import uk.ac.cam.yp242.tick4star.Pattern;

public class PatternLoader {
	public static List<Pattern> load(Reader r) throws IOException{
		BufferedReader buff = new BufferedReader(r);
		List<Pattern> resultList = new LinkedList<Pattern>();
		String format;

		while (buff.ready()) {
			try { 	format = buff.readLine();
					Pattern p = new Pattern(format);
						resultList.add(p);
				}
			catch (PatternFormatException e) {System.out.println("error.");}
		}
		return resultList;
	}

	public static List<Pattern> loadFromURL(String url) throws IOException{
		URL destination = new URL(url);
		URLConnection conn = destination.openConnection();
		return load(new InputStreamReader(conn.getInputStream()));
	}

	public static List<Pattern> loadFromDisk(String filename) throws IOException{
		return load(new FileReader(filename));
	}
}