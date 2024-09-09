package androidx.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public abstract class HackRecyclerView extends RecyclerView {
    public HackRecyclerView(@NonNull Context context) {
        super(context);
    }

    public HackRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HackRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getAdapterPositionInRecyclerView(@NonNull ViewHolder viewHolder) {
        return super.getAdapterPositionInRecyclerView(viewHolder);
    }
}
