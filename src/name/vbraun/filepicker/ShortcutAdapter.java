package name.vbraun.filepicker;

import java.io.File;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ShortcutAdapter extends ArrayAdapter<Shortcut> {

	protected static Shortcut shortcuts[] = {
		new Shortcut("/",                R.drawable.drive,  "/"),
		new Shortcut("SD card",          R.drawable.sdcard, "/mnt/sdcard"),
		new Shortcut("External SD card", R.drawable.sdcard, "/mnt/external_sd"),
		new Shortcut("USB Stick",        R.drawable.drive,  "/mnt/usbdrive"),
		new Shortcut("Downloads",        R.drawable.drive,  "/mnt/sdcard/Download"),
		new Shortcut("Music",            R.drawable.drive,  "/mnt/sdcard/Music"),
		new Shortcut("Movies",           R.drawable.drive,  "/mnt/sdcard/Movies"),
		new Shortcut("Pictures",         R.drawable.drive,  "/mnt/sdcard/Pictures"),
		new Shortcut("Search",           R.drawable.drive,  null)
	};
	
	private Context context;
	
	public ShortcutAdapter(Context context) {
		super(context, R.layout.shortcut_item, shortcuts);
		this.context = context;
	}
	
	private int indicator_pos = -1;
	
	protected void setIndictor(File file) {
		for (int i=0; i<shortcuts.length; i++) {
			File f = shortcuts[i].file;
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

        Shortcut shortcut = shortcuts[position];
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
