package androidpath.ll.material.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import androidpath.ll.material.Models.DrawerItem;
import androidpath.ll.material.R;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Le on 2015/6/10.
 */
public class DrawerListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;
    private Context context;
    private LayoutInflater mInflater;

    List<DrawerItem> data = Collections.emptyList();  //avoid null


    public DrawerListAdapter(Context context, List<DrawerItem> data) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    public void deleteItem(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        RecyclerView.ViewHolder holder;

        if (viewType == TYPE_HEADER) {
            View view = mInflater.inflate(R.layout.drawer_header, parent, false);
            holder = new HeaderHolder(view);
        } else {
            View view = mInflater.inflate(R.layout.drawer_list_item, parent, false);
            holder = new ItemHolder(view);
        }

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if (holder instanceof HeaderHolder) {

        } else {
            ItemHolder mItemHolder = (ItemHolder) holder;
            //position 0 is for header
            DrawerItem item = data.get(position - 1);
            mItemHolder.title.setText(item.getTitle());
            mItemHolder.icon.setImageResource(item.getIconId());
        }


    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_HEADER;
        } else {
            return TYPE_ITEM;
        }
    }

    @Override
    public int getItemCount() {
        return data.size() + 1;
    }

    class ItemHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.drawer_item_title)
        TextView title;
        @Bind(R.id.drawer_item_icon)
        ImageView icon;

        public ItemHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

    class HeaderHolder extends RecyclerView.ViewHolder {
        public HeaderHolder(View itemView) {
            super(itemView);
        }
    }

}
