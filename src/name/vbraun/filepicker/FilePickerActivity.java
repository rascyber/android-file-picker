package name.vbraun.filepicker;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

import name.vbraun.filepicker.Shortcut.Type;

public class FilePickerActivity extends Activity implements OnItemClickListener {
	private final static String TAG = "FilePickerActivity";

	protected static File currentDir;
	
	private Menu menu;
	private SearchView search;
	
	private ListView shortcutList;
	private ShortcutAdapter shortcutAdapter;
	
	private ScrollView dirBreadcrumbScroll;
	
	private DirList fileList;
	private GridView fileGrid;
	private DirListAdapter fileAdapter;
	
	private TextView dirNameDisplay;
	
	private boolean canSelectDirectory = true;
	private boolean canSelectFile = true;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_picker_main);
        
        if (currentDir == null)
        	currentDir = new File("/mnt/sdcard");
        
        search = (SearchView) findViewById(R.id.search);
        shortcutList = (ListView) findViewById(R.id.dir_shortcut_list);
        shortcutAdapter = new ShortcutAdapter(getApplicationContext());
        shortcutList.setAdapter(shortcutAdapter);
        shortcutList.setOnItemClickListener(this);
        
        dirBreadcrumbScroll = (ScrollView) findViewById(R.id.dir_breadcrumbs);
        
        dirNameDisplay = (TextView) findViewById(R.id.dir_name_display);
        
        fileList = new DirList();
        fileGrid = (GridView) findViewById(R.id.file_grid);
        fileAdapter = new DirListAdapter(getApplicationContext(), fileList);
        fileGrid.setOnItemClickListener(this);
        fileGrid.setAdapter(fileAdapter);
    }
	
	@Override
	public void onItemClick(AdapterView<?> adapter, View view, int pos, long id) {
		Log.d(TAG, "onItemClick "+pos);
		if (adapter.equals(shortcutList)) {
			Shortcut shortcut = shortcutAdapter.getItem(pos);
			switch (shortcut.type) {
			case ORDINARY_DIR:
				openDirectory(shortcut.file);
				break;
			case UP_DIRECTORY:
				File parent = currentDir.getParentFile();
				if (parent != null)
					openDirectory(parent);
				break;
			}
		} else if (adapter.equals(fileGrid)) {
			DirListEntry entry = fileAdapter.getItem(pos);
			File f = entry.file;
			if (f.isDirectory())
				openDirectory(f);
			else
				pickFile(f);
		}
	}
    
	
	public void newFolder() {
		LayoutInflater inflater = (LayoutInflater)this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		View view = inflater.inflate(R.layout.new_folder_dialog, null);
		
		final EditText text = (EditText)view.findViewById(R.id.new_folder_text);

		new AlertDialog.Builder(this)
		.setPositiveButton(R.string.new_folder_ok, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String name = text.getText().toString();				
				if(name.length() == 0) return;
				File folder = new File(currentDir, name);
				Log.d(TAG, "Creating new folder "+folder.getAbsolutePath());
				if (folder.mkdir()) {
					openDirectory(folder);
					Toast.makeText(FilePickerActivity.this, "Created folder "+name, Toast.LENGTH_LONG).show();
				} else
					Toast.makeText(FilePickerActivity.this, "Unable to create folder "+name, Toast.LENGTH_LONG).show();
				dialog.dismiss();
			}
		})
		.setNegativeButton(R.string.new_folder_cancel, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		})
		.setView(view)
		.setTitle("Create new folder")
		.setIcon(R.drawable.folder_md).create().show();
	}

	
	
	
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
        if (!canSelectDirectory) {
        	MenuItem select = menu.findItem(R.id.select_folder);
        	select.setVisible(false);
        }
    	return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case android.R.id.home:
    		finish();
    		return true;
    	case R.id.select_folder:
    		pickFile(currentDir);
    		return true;
    	case R.id.new_folder:
    		newFolder();
    		return true;
    	}
    	return super.onOptionsItemSelected(item);
    }
    
    
    
    @Override
    protected void onPause() {
    	super.onPause();
    }

    
    private void pickFile(File file) {
    	if (file.isDirectory() && !canSelectDirectory) {
        	Toast.makeText(getApplicationContext(), "Cannot select a directory.", Toast.LENGTH_LONG).show();
        	return;
    	}
    	if (file.isFile() && !canSelectFile) {
        	Toast.makeText(getApplicationContext(), "Cannot select an ordinary file.", Toast.LENGTH_LONG).show();
        	return;
    	}
    	Toast.makeText(getApplicationContext(), "Picked "+file.getAbsolutePath(), Toast.LENGTH_LONG).show();
    }
    
    private void openDirectory(File dir) {
    	currentDir = dir;
		shortcutAdapter.setIndictor(dir);
		fileList.openDirectory(dir);
		fileAdapter.notifyDataSetChanged();
		dirNameDisplay.setText(dir.getAbsolutePath());
    }

    @Override
    protected void onResume() {
    	openDirectory(currentDir);
    	super.onResume();
    }
}