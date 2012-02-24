package name.vbraun.filepicker;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class FilePickerActivity extends Activity implements OnItemClickListener {
	private final static String TAG = "FilePickerActivity";

	private Menu menu;
	private SearchView search;
	
	private ListView shortcutList;
	private ShortcutAdapter shortcutAdapter;
	
	private ScrollView dirBreadcrumbScroll;
	
	private DirList fileList;
	private GridView fileGrid;
	private DirListAdapter fileAdapter;
	
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.file_picker_main);
                
        search = (SearchView) findViewById(R.id.search);
        shortcutList = (ListView) findViewById(R.id.dir_shortcut_list);
        shortcutAdapter = new ShortcutAdapter(getApplicationContext());
        shortcutList.setAdapter(shortcutAdapter);
        shortcutList.setOnItemClickListener(this);
        
        
        dirBreadcrumbScroll = (ScrollView) findViewById(R.id.dir_breadcrumbs);
        
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
			openDirectory(shortcut.file);
		} else if (adapter.equals(fileGrid)) {
			
		}
	}
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	this.menu = menu;
        getMenuInflater().inflate(R.menu.menu, menu);
    	return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId()) {
    	case android.R.id.home:
    		finish();
    		return true;
    		
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    @Override
    protected void onPause() {
    	super.onPause();
    }

    
    private void openDirectory(File dir) {
		shortcutAdapter.setIndictor(dir);
		fileList.openDirectory(dir);
		fileAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onResume() {
    	File sdcard = new File("/mnt/sdcard");
    	openDirectory(sdcard);
    	super.onResume();
    }
}