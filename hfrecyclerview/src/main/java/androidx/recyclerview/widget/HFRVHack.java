package androidx.recyclerview.widget;

import android.content.Context;
import android.util.AttributeSet;

import androidx.annotation.AttrRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RestrictTo;

@RestrictTo(RestrictTo.Scope.LIBRARY)
public final class HFRVHack {
    private HFRVHack() {}

    public static abstract class RecyclerView extends androidx.recyclerview.widget.RecyclerView {
        public RecyclerView(@NonNull Context context) {
            super(context);
        }

        public RecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
            super(context, attrs);
        }

        public RecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, @AttrRes int defStyleAttr) {
            super(context, attrs, defStyleAttr);
        }

        @Override
        protected int getAdapterPositionInRecyclerView(@NonNull ViewHolder viewHolder) {
            return super.getAdapterPositionInRecyclerView(viewHolder);
        }
    }
}
