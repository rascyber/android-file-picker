package name.vbraun.filepicker;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.view.ActionMode;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.SearchView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class FilePickerActivityOld extends Activity {
	//menu IDs
	private static final int MENU_DIR = 		0x0;
	private static final int MENU_SEARCH = 		0x1;
	private static final int MENU_MULTI =		0x2;
	
	private ActionMode mActionMode;
	private SearchView mSearchView;
	
	private EventHandler mEvHandler;
	private FileManager mFileManger;
	
		
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_fragments);
                
        mEvHandler = ((DirContentFragment)getFragmentManager()
        					.findFragmentById(R.id.content_frag)).getEventHandlerInst();
        mFileManger = ((DirContentFragment)getFragmentManager()
							.findFragmentById(R.id.content_frag)).getFileManagerInst();
        
        mSearchView = new SearchView(this);
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
        
			@Override
			public boolean onQueryTextSubmit(String query) {
				mSearchView.clearFocus();
				mEvHandler.searchFile(mFileManger.getCurrentDir(), query);
				
				return true;
			}
			
			@Override
			public boolean onQueryTextChange(String newText) {
				return false;
			}
		});
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
    	menu.add(0, MENU_SEARCH, 0, "Search").setIcon(R.drawable.search)
							.setActionView(mSearchView)
							.setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
    	
    	menu.add(0, MENU_DIR, 1, "New Folder").setIcon(R.drawable.newfolder)
    						.setShowAsAction(MenuItem.SHOW_AS_ACTION_IF_ROOM);
    	
    	return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
    	
    	switch(item.getItemId()) {
    	case android.R.id.home:
    		finish();
    		return true;
    		
    	case MENU_DIR:
    		mEvHandler.createNewFolder(mFileManger.getCurrentDir());
    		return true;
    		
    	case MENU_SEARCH:
    		return true;
    	}
    	
    	return super.onOptionsItemSelected(item);
    }
    
    /*
     * used to inform the user when they are holding a file to copy, zip, et cetera
     * When the user does something with the held files (from copy or cut) this is 
     * called to reset the apps title. When that happens we will get rid of the cached
     * held files if there are any.  
     * @param title the title to be displayed
     */
    public void changeActionBarTitle(String title) {
    	getActionBar().setTitle(title);
    }
        
    @Override
    protected void onPause() {
    	super.onPause();
    }


}