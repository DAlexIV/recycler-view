package ru.yandex.yamblz.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;

import butterknife.BindView;
import ru.yandex.yamblz.R;
import ru.yandex.yamblz.ui.recyclerstuff.BackgroundColorItemAnimator;
import ru.yandex.yamblz.ui.recyclerstuff.BorderItemDecorator;
import ru.yandex.yamblz.ui.recyclerstuff.ContentAdapter;
import ru.yandex.yamblz.ui.recyclerstuff.MarkingItemDecorator;
import ru.yandex.yamblz.ui.recyclerstuff.MoveAndSwipeTouchHelperCallback;
import ru.yandex.yamblz.ui.recyclerstuff.OptimizedGridLayoutManager;

public class ContentFragment extends BaseFragment {
    private static final int MAX_COLUMNS = 30;
    @BindView(R.id.rv)
    RecyclerView rv;
    @BindView(R.id.seekBar)
    SeekBar seekBar;

    private ContentAdapter adapter;
    private GridLayoutManager gridManager;
    private RecyclerView.ItemDecoration bordersDecorator;
    private MarkingItemDecorator markingDecorator;
    private boolean isDecorated = false;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        bordersDecorator = new BorderItemDecorator();
    }

    @NonNull
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_content, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        gridManager = new OptimizedGridLayoutManager(getContext(), 1);
        initRecyclerViewAndItsInternals();
        initSeekBar();
    }

    private void initRecyclerViewAndItsInternals() {
        adapter = new ContentAdapter();
        rv.setLayoutManager(gridManager);
        rv.setAdapter(adapter);
        rv.setItemAnimator(new BackgroundColorItemAnimator());
        rv.setHasFixedSize(true);

        bordersDecorator = new BorderItemDecorator();
        markingDecorator = new MarkingItemDecorator(adapter);
        rv.addItemDecoration(markingDecorator);

        new ItemTouchHelper(new MoveAndSwipeTouchHelperCallback(adapter,
                color -> rv.setBackgroundColor(color),
                (initialPos, positionAfterMove) ->
                        markingDecorator.updateHighlightedPositions(initialPos, positionAfterMove)))
                .attachToRecyclerView(rv);

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

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_change_style:
                if (isDecorated) {
                    rv.removeItemDecoration(bordersDecorator);
                    isDecorated = false;
                } else {
                    rv.addItemDecoration(bordersDecorator);
                    isDecorated = true;
                }
        }
        return true;
    }
}
