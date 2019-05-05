package no.hvl.dat108.back;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Settings {

	private String dirUrl;
	private File dir;
	private File options;
	private FileInputStream fileInput;
	
	public Settings() throws IOException {
		dirUrl = System.getenv("APPDATA") + "/.doomsday";
		dir = new File(dirUrl);
		dir.mkdirs();
		
		options = new File(dirUrl + "/options.xml");
		options.createNewFile();
		
	}
}
