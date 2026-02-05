package ir.coolroom2;

import android.view.View;

/**
 * Created by Mohammad on 12/6/2017.
 */

public interface ClickListener {
    void onClick(View view, int position);

    void onLongClick(View view, int position);
}
