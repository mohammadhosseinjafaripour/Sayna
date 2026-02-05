package ir.coolroom2.Fragment.Information;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import ir.coolroom2.Config;
import ir.coolroom2.R;

public class FTest2 extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ftest2, parent, false);

        ImageView imageView = (ImageView) view.findViewById(R.id.gifbox);


        switch (Config.INFORMATION_WORKMODE) {
            case "0":
                Glide.with(this).load(R.raw.normal).into(imageView);
                break;
            case "1":
                switch (Config.INFORMATION_ERRORTYPE) {
                    case "00":
                        Glide.with(this).load(R.raw.normal).into(imageView);
                        break;
                    case "01":
                        Glide.with(this).load(R.raw.electricity_disconnect).into(imageView);
                        break;
                    case "02":
                        Glide.with(this).load(R.raw.normal).into(imageView);
                        break;
                    case "03":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "04":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "05":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "06":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "07":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "08":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "09":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "10":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "11":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "12":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "13":
                        Glide.with(this).load(R.raw.control_phase).into(imageView);
                        break;
                    case "14":
                        Glide.with(this).load(R.raw.compressor_overload).into(imageView);
                        break;
                    case "15":
                        Glide.with(this).load(R.raw.compressor).into(imageView);
                        break;
                    case "16":
                        Glide.with(this).load(R.raw.compressor).into(imageView);
                        break;
                    case "17":
                        Glide.with(this).load(R.raw.compressor).into(imageView);
                        break;
                    case "18":
                        Glide.with(this).load(R.raw.evaperator).into(imageView);
                        break;
                    case "19":
                        Glide.with(this).load(R.raw.evaperator).into(imageView);
                        break;
                    case "20":
                        Glide.with(this).load(R.raw.evaperator).into(imageView);
                        break;
                    case "21":
                        Glide.with(this).load(R.raw.high_gas).into(imageView);
                        break;
                    case "22":
                        Glide.with(this).load(R.raw.low_gas).into(imageView);
                        break;
                    case "23":
                        Glide.with(this).load(R.raw.oil).into(imageView);
                        break;
                    default:
                        Glide.with(this).load(R.raw.normal).into(imageView);
                        break;
                }
                break;
            case "2":
                Glide.with(this).load(R.raw.borudat).into(imageView);
                break;
            case "3":
                Glide.with(this).load(R.raw.termostat).into(imageView);
                break;
            case "4":
                Glide.with(this).load(R.raw.defrost).into(imageView);
                break;
            case "5":
                Glide.with(this).load(R.raw.timer).into(imageView);
                break;
            default:
                Glide.with(this).load(R.raw.normal).into(imageView);
                break;

        }


        return view;
    }

}
