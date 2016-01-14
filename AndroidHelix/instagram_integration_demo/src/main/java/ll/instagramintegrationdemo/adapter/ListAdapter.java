package ll.instagramintegrationdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ll.instagramintegrationdemo.Model.Media;
import ll.instagramintegrationdemo.R;

/**
 * Created by Le on 2016/1/12.
 */
public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private int itemsCount = 0;
    private List<Media> mItems;
    private Context mContext;


    public ListAdapter(Context context) {
        this.mContext = context;
    }

    public void setDataSets(List<Media> list) {
        mItems = list;
    }

    public void clear() {
        mItems.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.image_list_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("BindView", "Called + " + position);
        Media item = mItems.get(position);
        Picasso.with(mContext)
                .load(item.getImages().getLowResolution().getUrl())
                .into(holder.mImageView);
        holder.mTextView.setText(item.getUser().getUsername());
    }

    @Override
    public int getItemCount() {
        return itemsCount;
    }

    public void updateItems(int size) {
        itemsCount = size;
        notifyDataSetChanged();
    }

    /**
     * View Holder for Grid View Items
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            mTextView =  (TextView) itemView.findViewById(R.id.username);
            mImageView = (ImageView) itemView.findViewById(R.id.img_low);
        }
    }
}

