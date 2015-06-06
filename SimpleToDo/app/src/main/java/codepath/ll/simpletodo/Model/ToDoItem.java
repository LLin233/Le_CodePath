package codepath.ll.simpletodo.Model;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.activeandroid.query.Select;

import java.util.List;

/**
 * Created by Le on 2015/6/6.
 */

@Table(name = "ToDoItems")
public class ToDoItem extends Model {
    // This is a regular field
    @Column(name = "title", index = true)
    public String title;

    // Make sure to have a default constructor for every ActiveAndroid model
    public ToDoItem() {
        super();
    }

    public ToDoItem(String title) {
        super();
        //this.remoteId = remoteId;
        this.title = title;
    }

    public static List<ToDoItem> getAll() {
        // This is how you execute a query
        return new Select()
                .from(ToDoItem.class)
                .execute();
    }

}

