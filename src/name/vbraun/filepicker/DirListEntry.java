package name.vbraun.filepicker;

import java.io.File;

public class DirListEntry {

	protected DirListEntry(File file) {
		this.file = file;
		title = file.getName();
		if (file.isDirectory())
			icon = R.drawable.folder_large;
		else
			icon = R.drawable.unknown;
	}
	
	protected String title;
	protected int icon;
	protected File file;
	
}
