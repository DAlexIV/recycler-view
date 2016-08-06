package ru.yandex.yamblz.ui.recyclerstuff;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import ru.yandex.yamblz.R;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder> {

    private final Random rnd = new Random();
    private final List<Integer> ids = new ArrayList<>();

    public ContentAdapter() {
        super();
        setHasStableIds(true);
    }

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ContentHolder contentHolder = new ContentHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_item, parent, false));
        return contentHolder;
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.bind(createColorForPosition(position));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private Integer createColorForPosition(int position) {
        addIfNotExist(position);

        return ids.get(position);
    }

    public void notifyAllMoved() {
        notifyDataSetChanged();
    }

    @Override
    public long getItemId(int position) {
        addIfNotExist(position);

        return ids.get(position);
    }

    private void addIfNotExist(int position) {
        while (position >= ids.size())
            ids.add(Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255)));
    }

    public void moveElement(int prevPos, int nextPos) {
        Collections.swap(ids, prevPos, nextPos);
    }

    public void deleteElement(int pos) {
        ids.remove(pos);
    }

    static class ContentHolder extends RecyclerView.ViewHolder {
        ContentHolder(View itemView) {
            super(itemView);
        }

        void bind(Integer color) {
            itemView.setBackgroundColor(color);
            ((TextView) itemView).setText("#".concat(Integer.toHexString(color).substring(2)));
        }
    }

}
