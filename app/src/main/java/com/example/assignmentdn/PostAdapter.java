package com.example.assignmentdn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class PostAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<Post> postArrayList;
    TextView lblTitle, lblDescription;
    ImageView imgPosts;

    public PostAdapter(Context context, ArrayList<Post> postArrayList) {
        this.context = context;
        this.postArrayList = postArrayList;
    }

    @Override
    public int getCount() {
        return postArrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return postArrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View customView = inflater.inflate(R.layout.custom_post_view, viewGroup, false);

        lblTitle = customView.findViewById(R.id.postTitle);
        lblDescription = customView.findViewById(R.id.postDesc);
        imgPosts = customView.findViewById(R.id.postImg);

        Post post = postArrayList.get(i);

        lblTitle.setText(post.geteTitle());
        lblDescription.setText(post.geteDescription());


        byte[] imageBytes = post.geteImage();
        if (imageBytes != null && imageBytes.length > 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
            imgPosts.setImageBitmap(bitmap);
        } else {
            imgPosts.setVisibility(View.GONE);
        }

        return customView;
    }
}
