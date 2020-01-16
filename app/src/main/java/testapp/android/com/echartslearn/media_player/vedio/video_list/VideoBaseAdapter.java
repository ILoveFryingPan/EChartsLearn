package testapp.android.com.echartslearn.media_player.vedio.video_list;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import testapp.android.com.echartslearn.R;

public class VideoBaseAdapter extends BaseAdapter{

    private Context mContext;
    private List<VideoBucket> bucketList;
    private List<VideoItem> itemList;

    public VideoBaseAdapter(Context mContext, List<VideoBucket> bucketList) {
        this.mContext = mContext;
        this.bucketList = bucketList;
        this.itemList = null;
    }

    public VideoBaseAdapter(List<VideoItem> itemList, Context mContext) {
        this.mContext = mContext;
        this.itemList = itemList;
        this.bucketList = null;
    }

    @Override
    public int getCount() {
        if (bucketList == null && itemList == null){
            return 0;
        }else if (bucketList != null && itemList == null){
            return bucketList.size();
        }else {
            return itemList.size();
        }
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        MyViewHolder myViewHolder = null;
        if (convertView == null){
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_image_bucket, parent, false);
            myViewHolder = new MyViewHolder(convertView);
            convertView.setTag(myViewHolder);
        }else {
            myViewHolder = (MyViewHolder) convertView.getTag();
        }

//        Log.d("VideoBaseAdapter", bucketList.get(position).thumbnailPath);
        if (bucketList != null) {
            if (!TextUtils.isEmpty(bucketList.get(position).thumbnailPath)) {
                Picasso.get().load("file://" + bucketList.get(position).thumbnailPath).config(Bitmap.Config.ARGB_4444).into(myViewHolder.image);
            } else if (bucketList.get(position).thumbnailBitmap != null) {
                myViewHolder.image.setImageBitmap(bucketList.get(position).thumbnailBitmap);
            }
            myViewHolder.name.setText(bucketList.get(position).bucketName);
            myViewHolder.count.setText(bucketList.get(position).count + "");
        }else {
            if (!TextUtils.isEmpty(itemList.get(position).imagePath)){
                Picasso.get().load("file://" + itemList.get(position).imagePath).config(Bitmap.Config.ARGB_4444).into(myViewHolder.image);
            }else if (itemList.get(position).imageBitmap != null){
                myViewHolder.image.setImageBitmap(itemList.get(position).imageBitmap);
            }

            myViewHolder.name.setText(itemList.get(position).name);
            myViewHolder.count.setVisibility(View.GONE);

        }
        return convertView;
    }

    public class MyViewHolder{

        private final ImageView image;
        private final TextView name;
        private final TextView count;

        public MyViewHolder(View itemView) {
            image = itemView.findViewById(R.id.image);
            name = itemView.findViewById(R.id.name);
            count = itemView.findViewById(R.id.count);
            itemView.findViewById(R.id.isselected).setVisibility(View.GONE);
        }
    }
}
