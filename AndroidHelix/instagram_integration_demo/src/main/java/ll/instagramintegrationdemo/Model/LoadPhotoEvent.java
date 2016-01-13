package ll.instagramintegrationdemo.Model;

import java.util.List;

/**
 * Created by Le on 2016/1/12.
 */
public class LoadPhotoEvent {
    private final String message;
    private List<Media> mMediaList;

    public LoadPhotoEvent(String message, List<Media> list) {
        this.message = message;
        this.mMediaList = list;
    }

    public List<Media> getMediaList() {
        return mMediaList;
    }
}
