package androidx.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;

import org.jspecify.annotations.NullMarked;
import org.jspecify.annotations.Nullable;

@NullMarked
public abstract class HackRecyclerView extends RecyclerView {
    public HackRecyclerView(Context context) {
        super(context);
    }

    public HackRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public HackRecyclerView(Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected int getAdapterPositionInRecyclerView(ViewHolder viewHolder) {
        return super.getAdapterPositionInRecyclerView(viewHolder);
    }
}
