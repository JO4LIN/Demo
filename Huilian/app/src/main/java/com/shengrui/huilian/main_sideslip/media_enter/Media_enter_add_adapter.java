package com.shengrui.huilian.main_sideslip.media_enter;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.shengrui.huilian.R;
import com.shengrui.huilian.base_mvp.Adapter.BaseDataAdapter;
import com.shengrui.huilian.base_mvp.Global.Global;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by zhrx on 2016/4/19.
 */
public class Media_enter_add_adapter extends BaseDataAdapter<Media_enter_add_model> {

    private LayoutInflater mInflater;
    public Media_enter_add_adapter(List<Media_enter_add_model> datas) {
        super(datas);
    }

    private ImageLoadingListener ill=new  AnimateFirstDisplayListener();
    DisplayImageOptions options; // 配置图片加载及显示选项
    /**
     *
     * @param position
     * @param convertView
     * @param parent
     * @return
     * http://www.cnblogs.com/xiaowenji/archive/2010/12/08/1900579.html
     */
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(Global.getContext()));
       // 配置图片加载及显示选项（还有一些其他的配置，查阅doc文档吧）
        options = new DisplayImageOptions.Builder()
                .showStubImage(R.drawable.jilin) // 在ImageView加载过程中显示图片
                .showImageForEmptyUri(R.drawable.jilin) // image连接地址为空时
                .showImageOnFail(R.drawable.jilin) // image加载失败
                .cacheInMemory(false) // 加载图片时会在内存中加载缓存
                .cacheOnDisc(false) // 加载图片时会在磁盘中加载缓存
                .displayer(new RoundedBitmapDisplayer(20)) // 设置用户加载图片task(这里是圆角图片显示)
                .build();

        mInflater = LayoutInflater.from(Global.context);
        ActiveViewHolder holder = null;
        if (convertView == null) {
            holder = new ActiveViewHolder();
            // 初始化绑定控件
            convertView = mInflater.inflate(R.layout.media_enter_add_item,null);
            holder.mediaHead= (ImageView) convertView.findViewById(R.id.media_enter_add_head);
            holder.mediaName= (TextView) convertView.findViewById(R.id.media_enter_add_name);
            holder.wechatNum=((TextView) convertView.findViewById(R.id.media_enter_add_num));
            holder.isVerification=((ImageView) convertView.findViewById(R.id.media_enter_add_is));
            holder.mediaId=((TextView) convertView.findViewById(R.id.media_id));
            holder.readNum=((TextView) convertView.findViewById(R.id.look_num));
            holder.fansNum=((TextView) convertView.findViewById(R.id.fans_num));
            holder.collectNum=((TextView) convertView.findViewById(R.id.collect_num));

            convertView.setTag(holder);
         } else {
            holder = (ActiveViewHolder) convertView.getTag();
        }

        holder.mediaName.setText(datas.get(position).getMediaName());
        holder.wechatNum.setText(String.valueOf(datas.get(position).getWechatNum()));
        holder.mediaId.setText(String.valueOf(datas.get(position).getMediaId()));
        holder.collectNum.setText(String.valueOf(datas.get(position).getCollectNum()));
        if((Integer.valueOf(datas.get(position).getReadNum())/10000)!=0){
            holder.readNum.setText(String.valueOf(Integer.valueOf(datas.get(position).getReadNum())/10000)+"万");
        }else {
            holder.readNum.setText(String.valueOf(datas.get(position).getReadNum()));
        }
        if((Integer.valueOf(datas.get(position).getFansNum())/10000)!=0){
            holder.fansNum.setText(String.valueOf(Integer.valueOf(datas.get(position).getFansNum())/10000)+"万");
        }else {
            holder.fansNum.setText(String.valueOf(datas.get(position).getFansNum()));
        }

        if(datas.get(position).getIsVerification()){
            holder.isVerification.setImageResource(R.drawable.icon_verification_green);
        }

        ImageLoader.getInstance().displayImage( datas.get(position).getWechatHead(), holder.mediaHead);
        return convertView;
    }

    public final class ActiveViewHolder implements ViewHolder {

        private ImageView mediaHead;
        private TextView mediaName;
        private TextView wechatNum;
        private ImageView isVerification;
        private TextView mediaId;
        private TextView readNum = null;
        private TextView fansNum = null;
        private TextView collectNum = null;


        @Override
        public void initView() {
        }

        public TextView getWechatNum() {
            return wechatNum;
        }

        public void setWechatNum(TextView wechatNum) {
            this.wechatNum = wechatNum;
        }

        public ImageView getIsVerification() {
            return isVerification;
        }

        public void setIsVerification(ImageView isVerification) {
            this.isVerification = isVerification;
        }

        public ImageView getMediaHead() {
            return mediaHead;
        }

        public void setMediaHead(ImageView mediaHead) {
            this.mediaHead = mediaHead;
        }

        public TextView getMediaName() {
            return mediaName;
        }

        public void setMediaName(TextView mediaName) {
            this.mediaName = mediaName;
        }

        public TextView getMediaId() {
            return mediaId;
        }

        public void setMediaId(TextView mediaId) {
            this.mediaId = mediaId;
        }

        public TextView getReadNum() {
            return readNum;
        }

        public void setReadNum(TextView readNum) {
            this.readNum = readNum;
        }

        public TextView getFansNum() {
            return fansNum;
        }

        public void setFansNum(TextView fansNum) {
            this.fansNum = fansNum;
        }

        public TextView getCollectNum() {
            return collectNum;
        }

        public void setCollectNum(TextView collectNum) {
            this.collectNum = collectNum;
        }
    }



    private static class AnimateFirstDisplayListener extends
            SimpleImageLoadingListener {

        static final List<String> displayedImages = Collections
                .synchronizedList(new LinkedList<String>());

        @Override
        public void onLoadingComplete(String imageUri, View view,
                                      Bitmap loadedImage) {
            if (loadedImage != null) {
                ImageView imageView = (ImageView) view;
                boolean firstDisplay = !displayedImages.contains(imageUri);
                if (firstDisplay) {
                    FadeInBitmapDisplayer.animate(imageView, 500); // 设置image隐藏动画500ms
                    displayedImages.add(imageUri); // 将图片uri添加到集合中
                }
            }
        }
    }
}
