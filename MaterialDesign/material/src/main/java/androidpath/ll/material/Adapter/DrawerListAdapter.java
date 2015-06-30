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
public class DrawerListAdapter extends RecyclerView.Adapter<DrawerListAdapter.ViewHolder> {

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
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.drawer_list_item, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DrawerItem item = data.get(position);
        holder.title.setText(item.getTitle());
        holder.icon.setImageResource(item.getIconId());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        @Bind(R.id.drawer_item_title)
        TextView title;
        @Bind(R.id.drawer_item_icon)
        ImageView icon;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
        }
    }

}
