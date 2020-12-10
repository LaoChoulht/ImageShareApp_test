package com.guet.laochou.testapp.adapters;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.recyclerview.widget.RecyclerView;

import com.guet.laochou.testapp.activities.R;
import com.guet.laochou.testapp.models.MyImage;

import java.util.List;

public class UploadRecyclerViewAdapter extends RecyclerView.Adapter<UploadRecyclerViewAdapter.LikedListViewHolder> {

    private Context mContext;
    private int mResourceID;
    private List<MyImage> mData;
    private LayoutInflater mInflater;

    public class LikedListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView iv_thumbnail;
        public ImageView iv_deleteBtn;
        public EditText et_imageTitle;
        private View mView;
        private List<MyImage> mData;

        public LikedListViewHolder(View itemView, final List<MyImage> data) {
            super(itemView);
            this.mView = itemView;
            this.mData = data;
            this.iv_thumbnail = mView.findViewById(R.id.upload_list_card1_iv_thumbnail);
            this.iv_deleteBtn = mView.findViewById(R.id.upload_list_card1_iv_delete);
            this.et_imageTitle = mView.findViewById(R.id.upload_list_card1_et_imageTitle);

            et_imageTitle.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {

                }

                @Override
                public void afterTextChanged(Editable s) {
                    String title = "";
                    title = et_imageTitle.getText().toString();
                    if(mData != null && mView.getTag()!= null){
                        int i = (int) mView.getTag();
                        mData.get(i).setImageID(title);
                    }
//                    Log.d("TESTTAG", "afterChange: " + mView.getTag());
                }
            });
            iv_deleteBtn.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.upload_list_card1_iv_delete:
                    deleteOneItem((int) mView.getTag(), mData);
                    break;
                default:
                    break;
            }
        }
    }

    public UploadRecyclerViewAdapter(Context context, int listItemRID, List<MyImage> data) {
        this.mContext = context;
        this.mResourceID = listItemRID;
        this.mData = data;
        this.mInflater = LayoutInflater.from(context);
    }

    @Override
    public LikedListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(mResourceID, parent, false);
        LikedListViewHolder holder = new LikedListViewHolder(view, mData);
        return holder;
    }

    @Override
    public void onBindViewHolder(LikedListViewHolder holder, int position) {
        holder.iv_thumbnail.setImageBitmap(mData.get(position).getOriginal());
        holder.et_imageTitle.setText(mData.get(position).getImageID());
        holder.mView.setTag(position);
        Log.d("TESTTAG", "upload onBindViewHolder: " + holder.et_imageTitle.getText().toString());
//        holder.et_imageTitle.setText(mData.get(position).getImageID());
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }

    public void deleteOneItem(int position, List<MyImage> data) {
        data.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(0, data.size());
    }
}
