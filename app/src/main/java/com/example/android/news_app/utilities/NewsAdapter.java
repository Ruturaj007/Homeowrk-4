package com.example.android.news_app.utilities;

import android.content.Context;
import android.database.Cursor;
import android.media.Image;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.news_app.Contract;
import com.example.android.news_app.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by Welcome To Future on 6/29/2017.
 */

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ItemHolder>{

    private static final String TAG = NewsAdapter.class.getSimpleName();

    private ArrayList<newsitem> data;
    private static ItemClickListener listener;
    private Context context;
    //defining a cursor
    private Cursor cursor;

    //

    public NewsAdapter(ArrayList<newsitem> data, ItemClickListener listener){
        this.cursor = cursor;
        this.listener = listener;
    }

    public interface ItemClickListener {
        void onItemClick(Cursor cursor,int clickedItemIndex);
    }

    @Override
    public ItemHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(R.layout.item, parent, shouldAttachToParentImmediately);
        ItemHolder holder = new ItemHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ItemHolder holder, int position) {
        holder.bind(position);
    }

    // here we are getting the count of cursor
    @Override
    public int getItemCount()
    {
        return cursor.getCount();
    }



    class ItemHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
      public final  TextView title;
       public final TextView description;
       public final TextView publishedAt;

        public final ImageView image;

        ItemHolder(View view){
            super(view);
            title = (TextView)view.findViewById(R.id.title);
            description = (TextView)view.findViewById(R.id.news_description);
            publishedAt = (TextView)view.findViewById(R.id.time);
            //adding image to recycler view
            image=(ImageView)view.findViewById(R.id.img);
            view.setOnClickListener(this);
        }


        public void bind(int pos)
        {
            newsitem repo = data.get(pos);
            title.setText(repo.getTitle());
            description.setText(repo.getDescription());
            publishedAt.setText(repo.getPublishedAt());

            String picaso=cursor.getString(cursor.getColumnIndex(Contract.newsitem.COLUMN_URL_TO_IMAGE));
            if(picaso != null)
            {
                Picasso.with(context).load(picaso).into(image);
            }
        }


        @Override
        public void onClick(View v)
        {
            int pos = getAdapterPosition();
            listener.onItemClick(cursor,pos);
        }
       /* public void setData(ArrayList<newsitem> data)
        {
            this.data=data;
        }*/
    }



}