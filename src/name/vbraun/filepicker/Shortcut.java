package name.vbraun.filepicker;

import java.io.File;

public class Shortcut {
	
	protected Shortcut(String title, int icon, String filename) {
		this.title = title;
		this.icon = icon;
		if (filename != null) {
			file = new File(filename);
			enabled = file.exists();
		} else {
			enabled = false;
		}
	}            

	
	protected String title;
	protected int icon;
	protected File file;
	protected boolean enabled;
}
