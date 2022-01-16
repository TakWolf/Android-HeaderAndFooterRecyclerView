package androidx.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class HeaderAndFooterHackRecyclerView extends RecyclerView {
    public HeaderAndFooterHackRecyclerView(@NonNull Context context) {
        super(context);
    }

    public HeaderAndFooterHackRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HeaderAndFooterHackRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    int getAdapterPositionInRecyclerView(ViewHolder viewHolder) {
        return hackGetAdapterPositionInRecyclerView(viewHolder);
    }

    protected int hackGetAdapterPositionInRecyclerView(ViewHolder viewHolder) {
        return super.getAdapterPositionInRecyclerView(viewHolder);
    }
}
