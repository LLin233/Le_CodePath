package ll.instagramintegrationdemo;

/**
 * Created by Le on 2016/1/12.
 */

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import java.util.List;

import ll.instagramintegrationdemo.Model.Media;

/**
 * Grid Adapter for Grid View
 */
public class GridAdapter extends RecyclerView.Adapter<GridAdapter.ViewHolder> {

    private int itemsCount = 0;
    private Context mContext;
    private List<Media> mItems;

    public GridAdapter(Context context) {
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
                R.layout.image_grid_item_layout, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        Log.d("BindView", "Called + " + position);
        Picasso.with(mContext)
                .load(mItems.get(position).getImages().getThumbnail().getUrl())
                .into(holder.mImageView);
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

        public ViewHolder(View itemView) {
            super(itemView);
            mImageView = (ImageView) itemView.findViewById(R.id.img_thumbnail);
        }
    }

}