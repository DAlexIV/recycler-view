package ru.yandex.yamblz.ui.recyclerstuff;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
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

import static android.support.v7.widget.RecyclerView.NO_POSITION;

public class ContentAdapter extends RecyclerView.Adapter<ContentAdapter.ContentHolder>
    implements IGetItemColor {

    private static final int CHANGE_COLOR_DURATION = 200;
    private final Random rnd = new Random();
    private final List<Integer> colors = new ArrayList<>();

    @Override
    public ContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ContentHolder contentHolder = new ContentHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.content_item, parent, false));

        contentHolder.itemView.setOnClickListener(v -> {
            int newColor = getRandomColor();
            int position = contentHolder.getAdapterPosition();

            if (position == NO_POSITION)
                return;

            ValueAnimator valueAnimator
                    = ValueAnimator.ofObject(new ArgbEvaluator(), colors.get(position), newColor);
            valueAnimator.addUpdateListener(animation ->
                    contentHolder.itemView.setBackgroundColor((Integer) animation.getAnimatedValue()));
            valueAnimator.setDuration(CHANGE_COLOR_DURATION);
            valueAnimator.start();

            colors.set(position, newColor);
            notifyItemChanged(position);
        });
        return contentHolder;
    }

    @Override
    public void onBindViewHolder(ContentHolder holder, int position) {
        holder.bind(getColorForPosition(position));
    }

    @Override
    public int getItemCount() {
        return Integer.MAX_VALUE;
    }

    private Integer getColorForPosition(int position) {
        addIfNotExist(position);
        return colors.get(position);
    }

    public void notifyAllMoved() {
        notifyDataSetChanged();
    }

    private int getRandomColor() {
        return Color.rgb(rnd.nextInt(255), rnd.nextInt(255), rnd.nextInt(255));
    }

    private void addIfNotExist(int position) {
        while (position >= colors.size())
            colors.add(getRandomColor());
    }

    public void moveElement(int prevPos, int nextPos) {
        Collections.swap(colors, prevPos, nextPos);
        notifyItemMoved(prevPos, nextPos);
    }


    public void deleteElement(int pos) {
        colors.remove(pos);
    }

    @Override
    public int getDefaultColorForItem(int itemPosition) {
        if (itemPosition < 0 || itemPosition >= colors.size())
            return 0;

        return colors.get(itemPosition);
    }

    class ContentHolder extends RecyclerView.ViewHolder {
        ContentHolder(View itemView) {
            super(itemView);
        }

        void bind(int color) {
            itemView.setBackgroundColor(color);
            ((TextView) itemView).setText("#".concat(Integer.toHexString(color).substring(2)));
        }
    }
}
