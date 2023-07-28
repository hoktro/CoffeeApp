package com.example.coffeeapp.Decor;

import android.graphics.Rect;
import android.view.View;
import androidx.recyclerview.widget.RecyclerView;

public class GridItemDecoration extends RecyclerView.ItemDecoration {
    private int horizontalSpacing;
    private int verticalSpacing;
    private int leftMargin;
    private int rightMargin;

    public GridItemDecoration(int horizontalSpacing, int verticalSpacing, int leftMargin, int rightMargin) {
        this.horizontalSpacing = horizontalSpacing;
        this.verticalSpacing = verticalSpacing;
        this.leftMargin = leftMargin;
        this.rightMargin = rightMargin;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        int position = parent.getChildAdapterPosition(view);
        int column = position % 2; // Assuming you have 2 columns (spanCount=2)

        // Apply left and right margin to each item
        outRect.left = leftMargin;
        outRect.right = rightMargin;

        // Apply horizontal spacing between 2 columns
        if (column == 0) {
            outRect.right = horizontalSpacing;
        } else {
            outRect.left = horizontalSpacing;
        }

        // Apply vertical spacing between rows
        if (position >= 2) {
            outRect.top = verticalSpacing;
        }

        // Apply top margin to the first row and bottom margin to the last row
        if (position < 2) {
            outRect.top = verticalSpacing;
        }
        if (position >= parent.getAdapter().getItemCount() - 2) {
            outRect.bottom = verticalSpacing;
        }
    }
}
