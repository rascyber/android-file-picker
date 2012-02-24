package name.vbraun.filepicker;

import java.io.File;
import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShortcutAdapter extends ArrayAdapter<Shortcut> {

	protected static ArrayList<Shortcut> shortcuts = new ArrayList<Shortcut>();
	private Context context;
	
	public ShortcutAdapter(Context context) {
		super(context, R.layout.shortcut_item, shortcuts);
		this.context = context;
		shortcuts.clear();
		addShortcut("/",                R.drawable.drive,  "/");
		addShortcut("SD card",          R.drawable.sdcard, "/mnt/sdcard");
		addShortcut("External SD card", R.drawable.sdcard, "/mnt/external_sd");
		addShortcut("USB Stick",        R.drawable.drive,  "/mnt/usbdrive");
		addShortcut("Downloads",        R.drawable.drive,  "/mnt/sdcard/Download");
		addShortcut("Music",            R.drawable.drive,  "/mnt/sdcard/Music");
		addShortcut("Movies",           R.drawable.drive,  "/mnt/sdcard/Movies");
		addShortcut("Pictures",         R.drawable.drive,  "/mnt/sdcard/Pictures");
		addShortcut("Search",           R.drawable.drive,  null);
	}
	
	private void addShortcut(String title, int icon, String dirname) {
		Shortcut s = new Shortcut(title, icon, dirname);
		if (s.file == null  || !s.file.exists()) return;
		shortcuts.add(s);
	}
	
	private int indicator_pos = -1;
	
	protected void setIndictor(File file) {
		for (int i=0; i<shortcuts.size(); i++) {
			File f = shortcuts.get(i).file;
			if (f == null) continue;
			if (f.equals(file)) {
				indicator_pos = i;
				notifyDataSetChanged();
				return;
			}
		}
			
	}
	
	@Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View layout;
        if (convertView == null) {
            layout = LayoutInflater.from(context).inflate(R.layout.shortcut_item, parent, false);
        } else {
            layout = convertView;
        }
        TextView title = (TextView) layout.findViewById(R.id.shortcut_title);
        ImageView icon = (ImageView) layout.findViewById(R.id.shortcut_icon);
        ImageView indicator = (ImageView) layout.findViewById(R.id.shortcut_indicator);

        Shortcut shortcut = getItem(position);
        title.setText(shortcut.title);
        icon.setImageResource(shortcut.icon);
        title.setEnabled(shortcut.enabled);
        icon.setEnabled(shortcut.enabled);
        
        if (indicator_pos == position)
        	indicator.setVisibility(View.VISIBLE);
        else
        	indicator.setVisibility(View.GONE);
        	
        return layout;
    }

	
	
}
