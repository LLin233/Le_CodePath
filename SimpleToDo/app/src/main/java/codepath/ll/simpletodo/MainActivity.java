package codepath.ll.simpletodo;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import org.apache.commons.io.FileUtils;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

public class MainActivity extends ActionBarActivity {
    private ArrayList<String> todoItems;
    private ArrayAdapter<String> todoAdapter;
    private ListView lvItems;
    private EditText etNewItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                todoItems.remove(position);
                todoAdapter.notifyDataSetChanged();
                writeItems();
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

    private final int REQUEST_CODE = 20;

    private void launchEditView(int position, String itemContent) {
        Intent editIntent = new Intent(this, EditItemActivity.class);
        editIntent.putExtra("position", position);
        editIntent.putExtra("item_content", itemContent);
        startActivityForResult(editIntent, REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {
            int editedItemPosition = data.getExtras().getInt("edited_item_position");
            String editedItemContent = data.getExtras().getString("edited_item_content");
            todoItems.set(editedItemPosition, editedItemContent);
            todoAdapter.notifyDataSetChanged();
            writeItems();
        }
    }

    public void onAddedItem(View v) {
        todoAdapter.add(etNewItem.getText().toString());
        etNewItem.setText("");
        writeItems();
    }

    private void readItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            todoItems = new ArrayList<String>(FileUtils.readLines(todoFile));
        } catch (IOException e) {
            todoItems = new ArrayList<String>();
        }
    }

    private void writeItems() {
        File filesDir = getFilesDir();
        File todoFile = new File(filesDir, "todo.txt");
        try {
            FileUtils.writeLines(todoFile, todoItems);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
