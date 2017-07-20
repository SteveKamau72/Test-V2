package com.testv2;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by steve on 7/20/17.
 */
public class CurrentReposAdapter extends RecyclerView.Adapter<CurrentReposAdapter.ViewHolder> {

    private static final String TAG = CurrentReposAdapter.class.getSimpleName();

    private Context mContext;
    private List<RepoModel> mData;

    /**
     * Change {@link List} type according to your needs
     */
    public CurrentReposAdapter(Context context, List<RepoModel> data) {
        if (context == null) {
            throw new NullPointerException("context can not be NULL");
        }

        if (data == null) {
            throw new NullPointerException("data list can not be NULL");
        }

        this.mContext = context;
        this.mData = data;
    }


    /**
     * Change the null parameter to a layout resource {@code R.layout.example}
     */
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.repo_list_row, parent, false);

        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // include binding logic here
        RepoModel repoModel = mData.get(position);
        holder.tvFullName.setText(repoModel.getFullName());
        holder.tvUpdatedAt.setText(repoModel.getUpdatedAt());
        holder.tvStargazersCount.setText(repoModel.getStargazersCount());
        holder.tvDescription.setText(repoModel.getDescription());
        holder.tvLanguage.setText(repoModel.getLanguage());
        Log.d("avatar_url", repoModel.getAvatarUrl());

        Glide.with(mContext)
                .load(repoModel.getAvatarUrl())
                .centerCrop().thumbnail(0.5f)
                .crossFade()
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .placeholder(R.mipmap.ic_launcher)
                .into(holder.imgAvatar);

    }


    @Override
    public int getItemCount() {
        return mData.size();
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // include {@link View} components here
        @BindView(R.id.avatar)
        ImageView imgAvatar;
        @BindView(R.id.full_name)
        TextView tvFullName;
        @BindView(R.id.updated_at)
        TextView tvUpdatedAt;
        @BindView(R.id.stargazers_count)
        TextView tvStargazersCount;
        @BindView(R.id.description)
        TextView tvDescription;
        @BindView(R.id.language)
        TextView tvLanguage;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
} 