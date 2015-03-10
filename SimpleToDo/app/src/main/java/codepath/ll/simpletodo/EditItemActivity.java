package codepath.ll.simpletodo;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class EditItemActivity extends ActionBarActivity {
    private int mEditItemPosition;
    private String mEditItemContent;
    public EditText etEditItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_item);
        etEditItem = (EditText) findViewById(R.id.etEditItem);
        mEditItemPosition = getIntent().getIntExtra("position", 0);
        mEditItemContent = getIntent().getStringExtra("item_content");
        etEditItem.setText(mEditItemContent);
        // move cursor to the end
        etEditItem.setSelection(mEditItemContent.length());
    }

    public void onSaveItem(View v) {
        Intent data = new Intent();
        data.putExtra("edited_item_position", mEditItemPosition);
        data.putExtra("edited_item_content", etEditItem.getText().toString());
        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_edit_item, menu);
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