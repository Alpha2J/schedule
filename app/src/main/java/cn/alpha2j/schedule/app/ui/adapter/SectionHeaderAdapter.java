package cn.alpha2j.schedule.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import cn.alpha2j.schedule.R;

/**
 * @author alpha
 */
public class SectionHeaderAdapter extends RecyclerView.Adapter<SectionHeaderAdapter.MyViewHolder> {
    static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;

        public MyViewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.recycler_view_section_header_text);
        }
    }

    private String mSectionTitle;

    public SectionHeaderAdapter(String sectionTitle) {
        mSectionTitle = sectionTitle;
    }

    @Override
    public int getItemCount() {
        return 1;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_view_section_header, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.textView.setText(mSectionTitle);
    }
}
