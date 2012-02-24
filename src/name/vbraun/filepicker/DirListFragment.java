/*
	File picker activity for tablets.
	2012 Volker Braun <vbraun.name@gmail.com>
	
	Based on Open Manager For Tablets, an open source file manager for 
	the Android system
	Copyright (C) 2011  Joe Berria <nexesdevelopment@gmail.com>

    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package name.vbraun.filepicker;

import android.os.Bundle;
import android.os.Environment;
import android.content.Context;
import android.app.ListFragment;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ArrayAdapter;

import java.util.ArrayList;
import java.io.File;

public class DirListFragment extends ListFragment {
	
	private static OnChangeLocationListener mChangeLocList;
	private ArrayList<String> mDirList;
	private Context mContext;
	private ImageView mLastIndicater = null;
	private DirListAdapter mDirListAdapter;
	

	public interface OnChangeLocationListener {
		void onChangeLocation(String name);
	}
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		String storage = "/" + Environment.getExternalStorageDirectory().getName();
		mContext = getActivity().getApplicationContext();
		
		mDirList = new ArrayList<String>();
		mDirList.add("/");
		mDirList.add(storage);
		mDirList.add(storage + "/" + "Download");
		mDirList.add(storage + "/" + "Music");
		mDirList.add(storage + "/" + "Movies");
		mDirList.add(storage + "/" + "Pictures");
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ListView lv = getListView();		
		lv.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
		lv.setDrawSelectorOnTop(true);
		lv.setBackgroundResource(R.drawable.dir_list_gradient);
		
		mDirListAdapter = new DirListAdapter(mContext, R.layout.dir_list_layout, mDirList);
		setListAdapter(mDirListAdapter);
	}
	
	@Override
	public void onListItemClick(ListView list, View view, int pos, long id) {
		ImageView v;
		if(mLastIndicater != null)
			mLastIndicater.setVisibility(View.GONE);
			
		v = (ImageView)view.findViewById(R.id.list_arrow);
		v.setVisibility(View.VISIBLE);
		mLastIndicater = v;
		
		if(mChangeLocList != null)
			mChangeLocList.onChangeLocation(mDirList.get(pos));
	}
	

	public static void setOnChangeLocationListener(OnChangeLocationListener l) {
		mChangeLocList = l;
	}
	
	

	private class DirListAdapter extends ArrayAdapter<String> {
		private DirEntryHolder mHolder;
		
		DirListAdapter(Context context, int layout, ArrayList<String> data) {
			super(context, layout, data);		
		}
		
		@Override
		public View getView(int position, View view, ViewGroup parent) {			
			if(view == null) {
				LayoutInflater in = (LayoutInflater)mContext.
									getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				view = in.inflate(R.layout.dir_list_layout, parent, false);
				mHolder = new DirEntryHolder();
				mHolder.mIcon = (ImageView)view.findViewById(R.id.list_icon);
				mHolder.mText = (TextView)view.findViewById(R.id.list_name);
				view.setTag(mHolder);
			} else {
				mHolder = (DirEntryHolder)view.getTag();
			}
			

			switch(position) {
			case 0:
				mHolder.mText.setText("/");
				mHolder.mIcon.setImageResource(R.drawable.drive);
				break;
			case 1:
				mHolder.mText.setText("sdcard");
				mHolder.mIcon.setImageResource(R.drawable.sdcard);
				break;
			case 2:
				mHolder.mText.setText("Downloads");
				mHolder.mIcon.setImageResource(R.drawable.download_md);
				break;
			case 3:
				mHolder.mText.setText("Music");
				mHolder.mIcon.setImageResource(R.drawable.music_md);
				break;
			case 4:
				mHolder.mText.setText("Movies");
				mHolder.mIcon.setImageResource(R.drawable.movie_md);
				break;
			case 5:
				mHolder.mText.setText("Photos");
				mHolder.mIcon.setImageResource(R.drawable.photo_md);
				break;
			}
			
			return view;
		}
	}	
}
