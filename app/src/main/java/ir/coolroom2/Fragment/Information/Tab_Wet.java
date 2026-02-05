package ir.coolroom2.Fragment.Information;

/**
 * Created by JFP on 11/29/2017.
 */

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.coolroom2.Config;
import ir.coolroom2.R;

public class Tab_Wet extends Fragment {
    TextView information_wet_v1;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View V = inflater.inflate(R.layout.tab_wet, container, false);

        information_wet_v1 = V.findViewById(R.id.information_wet_v1);

        if (Config.INFORMATION_WETV1.length() > 0) {
            information_wet_v1.setText(Config.INFORMATION_WETV1 + " % ");
        }

        return V;
    }
}
