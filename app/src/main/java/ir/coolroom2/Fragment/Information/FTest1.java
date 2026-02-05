package ir.coolroom2.Fragment.Information;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import ir.coolroom2.Config;
import ir.coolroom2.R;

public class FTest1 extends Fragment {

    TextView input_power, compressor, ovaperator, condansor, electric_valve, difrast;


    private TextView room_state, log;
    TextView next_defrost_time;
    TextView difrast_tv;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ftest1, parent, false);
        findViews(view);


        switch (Config.INFORMATION_WORKMODE) {
            case "0":
                difrast_tv.setText("سردخانه خاموش می باشد");
                break;
            case "1":
                difrast_tv.setText("زمان باقیمانده تا دیفراست بعدی");
                break;
            case "2":
                difrast_tv.setText("زمان باقیمانده تا دیفراست بعدی");
                break;
            case "3":
                difrast_tv.setText("زمان باقیمانده تا دیفراست بعدی");
                break;
            case "4":
                difrast_tv.setText("زمان باقیمانده تا اتمام دیفراست فعلی");
                break;
            case "5":
                difrast_tv.setText("زمان باقیمانده تا اتمام تایمر");
                break;
        }

        switch (Config.INFORMATION_WORKMODE) {
            case "0":
                room_state.setText("سرد خانه در وضعیت خاموش می باشد.");
                break;
            case "1":
                room_state.setText("سرد خانه در وضعیت خطا می باشد.");
                break;
            case "2":
                room_state.setText("سرد خانه در وضعیت تولید برودت می باشد.");
                break;
            case "3":
                room_state.setText("سرد خانه در وضعیت ترموستات می باشد.");
                break;
            case "4":
                room_state.setText("سرد خانه در وضعیت دیفراست می باشد.");
                break;
            case "5":
                room_state.setText("سرد خانه در وضعیت تایمر می باشد.");
                break;
            default:
                room_state.setText("وضعیت سردخانه نرمال می باشد!");
                break;
        }


        if (Config.INFORMATION_WORKMODE.equals("1")) {
            switch (Config.INFORMATION_ERRORTYPE) {
                case "01":
                    log.setText("قطع کامل برق");
                    break;
                case "02":
                    log.setText("خاموش دستی");
                    break;
                case "03":
                    log.setText("قطع فاز R");
                    break;
                case "04":
                    log.setText("قطع فاز S");
                    break;
                case "05":
                    log.setText("قطع فاز T");
                    break;
                case "06":
                    log.setText("عدم توالی فاز");
                    break;
                case "07":
                    log.setText("عدم تقارن فاز");
                    break;
                case "08":
                    log.setText("افزایش ولتاژ R");
                    break;
                case "09":
                    log.setText("افزایش ولتاژ S");
                    break;
                case "10":
                    log.setText("افزایش ولتاژ T");
                    break;
                case "11":
                    log.setText("کاهش ولتاژ R");
                    break;
                case "12":
                    log.setText("کاهش ولتاژ S");
                    break;
                case "13":
                    log.setText("کاهش ولتاژ T");
                    break;
                case "14":
                    log.setText("اورلود کمپرسور");
                    break;
                case "15":
                    log.setText("افزایش جریان کمپرسور");
                    break;
                case "16":
                    log.setText("کاهش جریان کمپرسور");
                    break;
                case "17":
                    log.setText("عدم تقارن جریان کمپرسور");
                    break;
                case "18":
                    log.setText("افزایش جریان اواپراتور");
                    break;
                case "19":
                    log.setText("کاهش جریان اواپراتور");
                    break;
                case "20":
                    log.setText("عدم تقارن جریان اواپراتور");
                    break;
                case "21":
                    log.setText("فشار گاز بالا");
                    break;
                case "22":
                    log.setText("فشار گاز پایین");
                    break;
                case "23":
                    log.setText("فشار روغن");
                    break;
            }
        }

        input_power = view.findViewById(R.id.input_power);
        compressor = view.findViewById(R.id.compressor);
        ovaperator = view.findViewById(R.id.ovaperator);
        condansor = view.findViewById(R.id.condansor);
        electric_valve = view.findViewById(R.id.electric_valve);
        difrast = view.findViewById(R.id.difrast);

        if (Config.INFORMATION_ERRORTYPE.length() > 0) {
            if (Config.INFORMATION_WORKMODE.equals("1")) {
                switch (Config.INFORMATION_ERRORTYPE) {
                    case "01":
                        input_power.setText(R.string.Cut);
                        compressor.setText(R.string.OFF);
                        ovaperator.setText(R.string.OFF);
                        condansor.setText(R.string.OFF);
                        electric_valve.setText(R.string.OFF);
                        difrast.setText(R.string.OFF);
                        break;
                    case "02":
                        input_power.setText(R.string.Plug_in);
                        compressor.setText(R.string.OFF);
                        ovaperator.setText(R.string.OFF);
                        condansor.setText(R.string.OFF);
                        electric_valve.setText(R.string.OFF);
                        difrast.setText(R.string.OFF);
                        break;
                    default:
                        input_power.setText(R.string.Plug_in);
                        compressor.setText(R.string.OFF);
                        ovaperator.setText(R.string.OFF);
                        condansor.setText(R.string.OFF);
                        electric_valve.setText(R.string.OFF);
                        difrast.setText(R.string.OFF);
                        break;
                }
            }
        }

        if (Config.INFORMATION_WORKMODE.length() > 0)
            if (Config.INFORMATION_WORKMODE.equals("2") ||
                    Config.INFORMATION_WORKMODE.equals("3") ||
                    Config.INFORMATION_WORKMODE.equals("4") ||
                    Config.INFORMATION_WORKMODE.equals("5")) {
                switch (Integer.parseInt(Config.INFORMATION_WORKMODE)) {
                    case 0:
                        break;
                    case 1:
                        break;
                    case 2:
                        input_power.setText(R.string.Plug_in);
                        compressor.setText(R.string.ON);
                        ovaperator.setText(R.string.ON);
                        condansor.setText(R.string.ON);
                        electric_valve.setText(R.string.ON);
                        difrast.setText(R.string.OFF);
                        break;
                    case 3:
                        input_power.setText(R.string.Plug_in);
                        compressor.setText(R.string.OFF);
                        ovaperator.setText(R.string.ON);
                        condansor.setText(R.string.OFF);
                        electric_valve.setText(R.string.OFF);
                        difrast.setText(R.string.OFF);
                        break;
                    case 4:
                        input_power.setText(R.string.Plug_in);
                        compressor.setText(R.string.OFF);
                        ovaperator.setText(R.string.OFF);
                        condansor.setText(R.string.OFF);
                        electric_valve.setText(R.string.OFF);
                        difrast.setText(R.string.ON);
                        break;
                    case 5:
                        input_power.setText(R.string.Plug_in);
                        compressor.setText(R.string.ON);
                        ovaperator.setText(R.string.OFF);
                        condansor.setText(R.string.ON);
                        electric_valve.setText(R.string.ON);
                        difrast.setText(R.string.OFF);
                        break;
                }
            }


        next_defrost_time.setText(Config.INFORMATION_DEFROSTTIME.substring(0, 2) + ":" + Config.INFORMATION_DEFROSTTIME.substring(2, 4));


        return view;
    }

    public void findViews(View view) {
        room_state = view.findViewById(R.id.room_state);
        log = view.findViewById(R.id.log);
        compressor = view.findViewById(R.id.compressor);
        next_defrost_time = (TextView) view.findViewById(R.id.next_defrost_time);
        difrast_tv = view.findViewById(R.id.difrast_tv);


    }
}
