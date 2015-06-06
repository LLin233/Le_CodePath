package codepath.ll.simpletodo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.activeandroid.query.Delete;

import java.util.ArrayList;
import java.util.List;

import codepath.ll.simpletodo.Model.ToDoItem;
import codepath.ll.simpletodo.Model.UpdateEvent;
import de.greenrobot.event.EventBus;

public class MainActivity extends ActionBarActivity {
    private ArrayList<String> todoItems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    private EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        EventBus.getDefault().register(this);
        lvItems = (ListView) findViewById(R.id.lvItems);
        etNewItem = (EditText) findViewById(R.id.etNewItem);

        readItems();
        todoAdapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_list_item_1, todoItems);
        lvItems.setAdapter(todoAdapter);
        setupListViewListener();
        setupListViewClickListener();
    }

    private void setupListViewListener() {
        lvItems.setOnItemLongClickListener(new OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view,
                                           int position, long id) {
                delete(todoItems.get(position));
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                return false;
            }
        });
    }

    private void setupListViewClickListener() {
        lvItems.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                launchEditView(position, todoItems.get(position));
            }
        });
    }

    private void launchEditView(int position, String itemContent) {
        Intent editIntent = new Intent(this, EditItemActivity.class);
        editIntent.putExtra("position", position);
        editIntent.putExtra("item_content", itemContent);
        startActivity(editIntent);
    }


    public void onAddedItem(View v) {
        ToDoItem event = new ToDoItem();
        event.title = etNewItem.getText().toString();
        event.save();
        todoAdapter.add(event.title);
        etNewItem.setText("");
    }

    private void readItems() {
        todoItems = new ArrayList<String>();
        List<ToDoItem> list = ToDoItem.getAll();
        for (ToDoItem item : list) {
            todoItems.add(item.title);
        }
    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    private void delete(String title) {
        new Delete().from(ToDoItem.class).where("title = ?", title).execute();
    }

    public void onEventMainThread(UpdateEvent event) {
        Toast.makeText(this, event.getMsg(), Toast.LENGTH_SHORT).show();
        todoItems.set(event.getPosition(), event.getMsg());
        todoAdapter.notifyDataSetChanged();
    }

}
