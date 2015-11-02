package course.labs.todomanager;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import course.labs.todomanager.ToDoItem.Priority;
import course.labs.todomanager.ToDoItem.Status;
import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class ToDoListAdapter extends BaseAdapter {

	private final List<ToDoItem> mItems = new ArrayList<ToDoItem>();
	private final Context mContext;

	private static final String TAG = "Lab-UserInterface";
	
	// Used to optimize the use of the ListAdapter, faster scrolling
	static class ViewHolder {
		TextView titleView;
		CheckBox statusView;
		TextView priorityView;
		TextView dateView;
	}

	public ToDoListAdapter(Context context) {

		mContext = context;

	}

	// Add a ToDoItem to the adapter
	// Notify observers that the data set has changed

	public void add(ToDoItem item) {

		mItems.add(item);
		notifyDataSetChanged();

	}

	// Clears the list adapter of all items.

	public void clear() {

		mItems.clear();
		notifyDataSetChanged();

	}

	// Returns the number of ToDoItems

	@Override
	public int getCount() {

		return mItems.size();

	}

	// Retrieve the number of ToDoItems

	@Override
	public Object getItem(int pos) {

		return mItems.get(pos);

	}

	// Get the ID for the ToDoItem
	// In this case it's just the position

	@Override
	public long getItemId(int pos) {

		return pos;

	}

	// Create a View for the ToDoItem at specified position
	// Remember to check whether convertView holds an already allocated View
	// before created a new View.
	// Consider using the ViewHolder pattern to make scrolling more efficient
	// See: http://developer.android.com/training/improving-layouts/smooth-scrolling.html
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		// TODO - Get the current ToDoItem
		final ToDoItem toDoItem = mItems.get(position);

		// TODO - Inflate the View for this ToDoItem
		// from todo_item.xml
		RelativeLayout itemLayout = (RelativeLayout) convertView;
		
		ViewHolder holder = null;

		// TODO - Fill in specific ToDoItem data
		// Remember that the data that goes in this View
		// corresponds to the user interface elements defined
		// in the layout file
		if (itemLayout == null) {

			itemLayout = (RelativeLayout)LayoutInflater.from(mContext).inflate(R.layout.todo_item, parent, false);

			holder = new ViewHolder();
			holder.titleView = (TextView)itemLayout.findViewById(R.id.titleView);
			holder.statusView = (CheckBox)itemLayout.findViewById(R.id.statusCheckBox);
			holder.priorityView = (TextView)itemLayout.findViewById(R.id.priorityView);
			holder.dateView = (TextView)itemLayout.findViewById(R.id.dateView);
			itemLayout.setTag(holder);
			
		} else {
			
			holder = (ViewHolder)itemLayout.getTag();
		}

		// TODO - Display Title in TextView
		//final TextView titleView;
		holder.titleView.setText(toDoItem.getTitle());

		// TODO - Set up Status CheckBox
		//final CheckBox statusView = null;
		Boolean statusDone = ((toDoItem.getStatus() == ToDoItem.Status.DONE) ? true : false);
		holder.statusView.setChecked(statusDone);

		holder.statusView.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				Log.i(TAG, "Entered onCheckedChanged()");

				// TODO - set up an OnCheckedChangeListener, which
				// is called when the user toggles the status checkbox
				toDoItem.setStatus((isChecked) ? ToDoItem.Status.DONE : ToDoItem.Status.NOTDONE);
				
				// Color layout accordingly
				((RelativeLayout)buttonView.getParent()).setBackgroundColor(setStatusColor(toDoItem));
			}
		});

		// TODO - Display Priority in a TextView
		//final TextView priorityView = null;
		holder.priorityView.setText(toDoItem.getPriority().toString());

		// TODO - Display Time and Date.
		// Hint - use ToDoItem.FORMAT.format(toDoItem.getDate()) to get date and
		// time String

		//final TextView dateView = null;
		holder.dateView.setText(ToDoItem.FORMAT.format(toDoItem.getDate()));
		
		// Set color depending on status
		itemLayout.setBackgroundColor(setStatusColor(toDoItem));

		// Return the View you just created
		return itemLayout;

	}
	
	private int setStatusColor(ToDoItem toDoItem) {
		
		int backgroundColor = 0;
		
		// Set background color based on status
		if (toDoItem.getStatus().equals(ToDoItem.Status.DONE)) {
			backgroundColor = Color.argb(100,153,255,153);
		} else {
			backgroundColor = Color.argb(100,255,153,153);
		}
		
		return backgroundColor;
	}
}
