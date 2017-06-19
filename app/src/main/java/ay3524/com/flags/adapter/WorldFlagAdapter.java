package ay3524.com.flags.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import ay3524.com.flags.R;
import ay3524.com.flags.model.Worldpopulation;

/**
 * Created by Ashish on 19-06-2017.
 */

public class WorldFlagAdapter extends RecyclerView.Adapter<WorldFlagAdapter.ViewHolder>  {

    private ArrayList<Worldpopulation> itemsModels = null;
    private Context context;
    private ClickListener clickListener;

    public WorldFlagAdapter(ArrayList<Worldpopulation> itemsModels, Context context) {
        this.itemsModels = itemsModels;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worldflag, parent, false);
        return new ViewHolder(view);

    }

    public void setClickListener(ClickListener clickListener) {
        this.clickListener = clickListener;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        String flagUrl = itemsModels.get(position).getFlag();
        //Glide.clear(holder.image);
        Glide.with(context)
                .load(flagUrl)
                //.placeholder(R.drawable.icon)
                //.error(R.drawable.icon)
                //.diskCacheStrategy(DiskCacheStrategy.ALL)
                //.dontAnimate()
                .into(holder.image);

        //holder.title.setText(itemsModels.get(position).getTitle());

    }

    @Override
    public int getItemCount() {
        return itemsModels == null ? 0 : itemsModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        View view;
        ImageView image;
        public ViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
            this.view = itemView;
            image = (ImageView) itemView.findViewById(R.id.flag);
        }

        @Override
        public void onClick(View v) {
            if (clickListener != null) {
                clickListener.onItemClick(view, getAdapterPosition());
            }
        }
    }
    public interface ClickListener {
        void onItemClick(View v, int pos);
    }
}
