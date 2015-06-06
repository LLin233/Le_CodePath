package codepath.ll.simpletodo.Model;

/**
 * Created by Le on 2015/6/6.
 */
public class UpdateEvent {
    private String msg;
    private int position;

    public UpdateEvent(int position, String msg) {
        this.position = position;
        this.msg = msg;
    }

    public String getMsg() {
        return this.msg;
    }

    public int getPosition() {
        return this.position;
    }
}
