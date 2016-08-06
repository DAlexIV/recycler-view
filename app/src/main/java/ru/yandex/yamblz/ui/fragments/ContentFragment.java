package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.recyclerstuff.OptimizedGridLayoutManager;
import ru.yandex.yamblz.ui.recyclerstuff.BorderItemDecorator;
import ru.yandex.yamblz.ui.recyclerstuff.ContentAdapter;

import static android.support.v7.widget.helper.ItemTouchHelper.DOWN;
import static android.support.v7.widget.helper.ItemTouchHelper.LEFT;
import static android.support.v7.widget.helper.ItemTouchHelper.RIGHT;
import static android.support.v7.widget.helper.ItemTouchHelper.UP;

public class ContentFragment extends BaseFragment {
    private static final int MAX_COLUMNS = 30;
    @BindView(R.id.rv)
    RecyclerView rv;

    @BindView(R.id.seekBar)
    SeekBar seekBar;
    private ContentAdapter adapter;


    GridLayoutManager gridManager;
    private RecyclerView.ItemDecoration decoration;

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridManager = new OptimizedGridLayoutManager(getContext(), 1);
        initRecyclerView();
        initSeekBar();
    }

    private void initRecyclerView() {
        adapter = new ContentAdapter();
        rv.setLayoutManager(gridManager);
        rv.setAdapter(adapter);
        rv.setHasFixedSize(true);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new ItemTouchHelper.SimpleCallback(LEFT | RIGHT | UP | DOWN, RIGHT) {

                    @Override
                    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                        adapter.moveElement(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        adapter.notifyItemMoved(viewHolder.getAdapterPosition(), target.getAdapterPosition());
                        return false;
                    }

                    @Override
                    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                        adapter.deleteElement(viewHolder.getAdapterPosition());
                        adapter.notifyItemRemoved(viewHolder.getAdapterPosition());
                    }
                });

        itemTouchHelper.attachToRecyclerView(rv);

        initItemDecoration();
        rv.addItemDecoration(decoration);
    }

    private void initItemDecoration() {
        decoration = new BorderItemDecorator();
    }

    private void initSeekBar() {
        seekBar.setMax(MAX_COLUMNS);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                gridManager.setSpanCount(progress + 1);
                adapter.notifyAllMoved();
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

}
