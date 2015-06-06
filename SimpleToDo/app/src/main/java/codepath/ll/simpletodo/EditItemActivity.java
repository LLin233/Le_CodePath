package codepath.ll.simpletodo;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.EditText;

import com.activeandroid.query.Update;

import codepath.ll.simpletodo.Model.ToDoItem;
import codepath.ll.simpletodo.Model.UpdateEvent;
import de.greenrobot.event.EventBus;


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
        new Update(ToDoItem.class)
                .set("title = ?", etEditItem.getText().toString())
                .where("title = ?", mEditItemContent)
                .execute();
        EventBus.getDefault().post(new UpdateEvent(mEditItemPosition, etEditItem.getText().toString()));
        finish();
    }

}