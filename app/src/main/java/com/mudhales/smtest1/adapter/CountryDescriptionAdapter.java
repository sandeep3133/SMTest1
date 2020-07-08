package com.mudhales.smtest1.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mudhales.smtest1.R;
import com.mudhales.smtest1.data.CountryDescription;

import java.util.List;
import androidx.recyclerview.widget.RecyclerView;


public class CountryDescriptionAdapter extends RecyclerView.Adapter<CountryDescriptionAdapter.ViewHolder> {
    private List<CountryDescription> mCountryDescList;
    private Context mContext;



    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tvTitle, tvDescription;
        private ImageView ivImage;

        private ViewHolder(View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            tvDescription = itemView.findViewById(R.id.tv_description);
            ivImage = itemView.findViewById(R.id.iv_image);

        }
    }

    public CountryDescriptionAdapter(Context context, List<CountryDescription> countryDescList) {
        mCountryDescList = countryDescList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        View postView = inflater.inflate(R.layout.row_list_item, parent, false);

        ViewHolder viewHolder = new ViewHolder(postView);
        postView.setTag(viewHolder);

        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder,
                                 final int position) {
        CountryDescription mCountryDesc = mCountryDescList.get(position);
        Glide.with(mContext).load(mCountryDesc.getImageHref()).placeholder(R.drawable.no_images_availale).error(R.drawable.no_images_availale).into(holder.ivImage); // Set image url to view by SM 201912111525
        holder.tvTitle.setText(checkNotNull(mCountryDesc.getTitle())); // Set title to view by SM 201912111525
        holder.tvDescription.setText(checkNotNull(mCountryDesc.getDescription())); // Set description to view by SM 201912111525
    }

    private String checkNotNull(String strValue) {
        return TextUtils.isEmpty(strValue)?"N/A":strValue;
    }

    @Override
    public int getItemCount() {
        return mCountryDescList.size();
    }

    // To refresh the list by SM 201912111540
    public void refreshList(List<CountryDescription> countryDescList) {
        this.mCountryDescList = countryDescList;
        this.notifyDataSetChanged();
    }

}

