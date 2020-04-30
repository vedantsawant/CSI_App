package com.example.csiapp1;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ListUpdateCallback;
import androidx.recyclerview.widget.RecyclerView;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private Context mContext;
    private List<Upload> mUploads;
    private onItemClickListener mListener;
    private String details;
    UpcomingTabFragment upcomingTabFragment = new UpcomingTabFragment();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public ImageAdapter(Context context, List<Upload> uploads){
        mContext = context;
        mUploads = uploads;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.image_item, parent, false);
        return new ImageViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        Upload uploadCurrent = mUploads.get(position);
        holder.textView.setText(uploadCurrent.getMworkshopName());

        Picasso.with(mContext)
            .load(uploadCurrent.getMimageUrl())
                .placeholder(R.drawable.loading)
                .fit()
                .centerCrop()
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return mUploads.size();
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener,
    View.OnCreateContextMenuListener, MenuItem.OnMenuItemClickListener {

        public TextView textView;
        public ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.text_view_name);
            imageView = itemView.findViewById(R.id.image_view_upload);

            itemView.setOnClickListener(this);
            itemView.setOnCreateContextMenuListener(this);

        }

        @Override
        public void onClick(View v) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                    Upload upTest = mUploads.get(position);
                    details = upTest.getmWorkshopDetails();
                    String url = upTest.getRegURL();
                    System.out.println(url + details);
                    String name = upTest.getMworkshopName();
                    String date = upTest.getDate();
                    mListener.onItemClick(position, details, name, url, date);
                }
            }
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {

            if(upcomingTabFragment.checkAdmin(mAuth)) {
                menu.setHeaderTitle("Select Action");
                MenuItem edit = menu.add(Menu.NONE, 1, 1, "Edit Member");
                MenuItem delete = menu.add(Menu.NONE, 2, 2, "Delete Member");

                edit.setOnMenuItemClickListener(this);
                delete.setOnMenuItemClickListener(this);
            }
        }

        @Override
        public boolean onMenuItemClick(MenuItem item) {
            if(mListener != null){
                int position = getAdapterPosition();
                if(position != RecyclerView.NO_POSITION){
                   switch (item.getItemId()){
                       case 1:
                           mListener.onEditClick(position);
                           return true;
                       case 2:
                           mListener.onDeleteClick(position);
                           return true;
                   }
                }
            }
            return false;
        }
    }

    public interface onItemClickListener{
        void onItemClick(int position, String details, String name, String url, String date);
        void onEditClick(int position);
        void onDeleteClick(int position);
    }

    public void setItemClickListener(onItemClickListener listener){
        mListener = listener;
    }
}
