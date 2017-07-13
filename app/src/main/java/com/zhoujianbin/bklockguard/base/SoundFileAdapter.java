package com.zhoujianbin.bklockguard.base;

import android.annotation.SuppressLint;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.blankj.ALog;
import com.wang.avi.AVLoadingIndicatorView;
import com.zhoujianbin.bklockguard.MyApp;
import com.zhoujianbin.bklockguard.R;
import com.zhoujianbin.bklockguard.bean.SoundFile;

import java.util.ArrayList;
import java.util.List;

/*******************************************************************************
 * Copyright © 2014-2017 zhoujianbin All Rights Reserved.
 * Project Name：BKLockGuard
 * File Creation Time：2017/07/12 02:35:31
 * Class Description: 音乐文件管理Adapter
 * Comment: 用于设置音乐列表显示和点击处理
 ******************************************************************************/

public class SoundFileAdapter extends RecyclerView.Adapter<SoundFileAdapter.ViewHolder> {

    private List<SoundFile> soundFileList = new ArrayList<>();
    // 用于存储选中的Item序号
    private int selectedPos = 0;
    private RecyclerView recyclerView;
    private boolean isLooping;
    private BaseSoundPlayer baseSoundPlayer;

    /**
     * 创建实例方法
     * @param recyclerView  绑定的RecycleView
     * @param soundFileList 音乐文件类列表
     */
    public SoundFileAdapter(RecyclerView recyclerView, List<SoundFile> soundFileList, BaseSoundPlayer baseSoundPlayer, boolean isSoundLooping) {
        this.soundFileList = soundFileList;
        this.recyclerView = recyclerView;
        this.baseSoundPlayer = baseSoundPlayer;
        this.isLooping = isSoundLooping;
    }

    /**
     * 每个Item的ViewHolder相关控件
     */
    static class ViewHolder extends RecyclerView.ViewHolder {
        CardView cardContent;
        AppCompatImageView ivMusic;
        AppCompatTextView tvFileName;
        AVLoadingIndicatorView avLoading;

        private ViewHolder(View itemView) {
            super(itemView);
            cardContent = itemView.findViewById(R.id.card_content);
            tvFileName = itemView.findViewById(R.id.tv_file_name);
            avLoading = itemView.findViewById(R.id.av_loading);
            ivMusic = itemView.findViewById(R.id.iv_ic_music);
        }
    }

    /**
     * 设置Item的Layout
     *
     * @param parent   容器父类
     * @param viewType viewType
     * @return 返回每个Item对应ViewHolder
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sound_file, parent, false);
        return new ViewHolder(itemView);
    }

    /**
     * 绑定Item的ViewHolder处理逻辑
     *
     * @param holder   当前holder
     * @param position 选中Item的序号
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        // 获得当前序号的声音文件类
        final SoundFile soundFile = soundFileList.get(position);
        // 设置音乐文件名
        holder.tvFileName.setText(soundFile.getTitle());
        // 给Item设置点击处理
        holder.cardContent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 实现单选方法： RecyclerView另一种定向刷新方法：不会有白光一闪动画 也不会重复onBindVIewHolder
                // 获取前一个Item的ViewHolder
                ViewHolder preViewHolder = (ViewHolder) recyclerView.findViewHolderForLayoutPosition(selectedPos);
                if (preViewHolder != null) {
                    // 还在屏幕里
                    // 之前选中的item变为false状态处理
                    setSelectedItem(preViewHolder, false);
                } else {
                    //add by 2016 11 22 for 一些极端情况，holder被缓存在Recycler的cacheView里，
                    //此时拿不到ViewHolder，但是也不会回调onBindViewHolder方法。所以add一个异常处理
                    notifyItemChanged(selectedPos);
                }
                // 当前选中的item变为false状态处理
                setSelectedItem(holder, true);
                ALog.d(soundFileList.get(position).getData());
                // 设置播放路径
                baseSoundPlayer.playPath(soundFileList.get(position).getData(),isLooping);
                // 不管在不在屏幕里 都需要改变数据，设置前一个选择Item为未选中状态
                soundFileList.get(selectedPos).setSelected(false);
                soundFileList.get(position).setSelected(true);
                // 设置存储用selectedPos为当前点击的Item序号
                selectedPos = position;
            }
        });
    }

    /**
     * 设置Item状态
     *
     * @param isSelected 是否选中
     * @param holder     要处理的holder
     */
    private void setSelectedItem(ViewHolder holder, boolean isSelected) {
        if (isSelected) {
            // 之前选中的item变为true状态
            holder.avLoading.show();
            holder.ivMusic.setVisibility(View.INVISIBLE);
            holder.tvFileName.setTextColor(ContextCompat.getColor(MyApp.getContext(), R.color.color_open));
        } else {
            // 隐藏播放中动画并显示音乐logo
            holder.avLoading.hide();
            holder.ivMusic.setVisibility(View.VISIBLE);
            // 设置未选中文字颜色
            holder.tvFileName.setTextColor(ContextCompat.getColor(MyApp.getContext(), R.color.primary_text_dark));
        }
    }

    /**
     * @return 获取数据总数
     */
    @Override
    public int getItemCount() {
        return soundFileList != null ? soundFileList.size() : 0;
    }

    /**
     * @return 选中的Item序号
     */
    public int getSelectedPos() {
        return selectedPos;
    }
}
