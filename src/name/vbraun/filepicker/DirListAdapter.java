package name.vbraun.filepicker;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.os.storage.StorageManager;
import android.widget.ArrayAdapter;

public class DirListAdapter extends ArrayAdapter<DirListEntry> {

	private Context context;
	
	private ArrayList<DirListEntry> listing = new ArrayList<DirListEntry>();
	
	public DirListAdapter(Context context) {
		super(context, R.layout.dir_list_entry);
		this.context = context;
	}
	
	protected void openDirectory(File dir) {
		listing.clear();
		for (File entry : dir.listFiles()) 
			listing.add(new DirListEntry(entry));
		
		
		notifyDataSetChanged();
	}
	
	
}
