package name.vbraun.filepicker;

import java.io.File;
import java.util.ArrayList;


public class DirList extends ArrayList<DirListEntry> {
	private final static String TAG = "DirList";
	
	protected void openDirectory(File dir) {
		clear();
		for (File entry : dir.listFiles())  {
			add(new DirListEntry(entry));
		}
	}
	

	
}
