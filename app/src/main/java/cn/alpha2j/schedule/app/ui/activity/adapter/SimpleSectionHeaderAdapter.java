package cn.alpha2j.schedule.app.ui.activity.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.alpha2j.schedule.R;

/**
 * 表示RecyclerView的一个section的头部标题
 *
 * 该类不可再扩展
 *
 * @author alpha
 */
public final class SimpleSectionHeaderAdapter extends RecyclerView.Adapter<SimpleSectionHeaderAdapter.SimpleSectionHeaderViewHolder> {

    private String mSectionTitle;

    public SimpleSectionHeaderAdapter(String sectionTitle) {

        mSectionTitle = sectionTitle;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public SimpleSectionHeaderViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_section_header, parent, false);
        return new SimpleSectionHeaderViewHolder(v);
    }

    @Override
    public void onBindViewHolder(SimpleSectionHeaderViewHolder holder, int position) {
        holder.mTextView.setText(mSectionTitle);
    }

    final class SimpleSectionHeaderViewHolder extends RecyclerView.ViewHolder {

        TextView mTextView;

        SimpleSectionHeaderViewHolder(View itemView) {
            super(itemView);
            mTextView = itemView.findViewById(R.id.recycler_view_section_header_text);
        }
    }
}
