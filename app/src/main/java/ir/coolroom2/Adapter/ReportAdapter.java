package ir.coolroom2.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.ReportModel;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.Sms.SmsCodeAndDecode;


/**
 * Created by Mohammad on 12/6/2017.
 */

public class ReportAdapter extends RecyclerView.Adapter<ReportAdapter.MyViewHolder> {

    private List<ReportModel> reportModelList;
    private Context context;
    private SmsCodeAndDecode codeAndDecode = new SmsCodeAndDecode();
    private DatabaseHandler db;


    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView reportDate, reportTime, coolroomNumber, coolroomName, reportType, reportState, reportFrom;

        public MyViewHolder(View view) {
            super(view);
            reportDate = (TextView) view.findViewById(R.id.reportDate);
            reportTime = (TextView) view.findViewById(R.id.reportTime);
            coolroomNumber = (TextView) view.findViewById(R.id.coolroomNumber);
            coolroomName = (TextView) view.findViewById(R.id.coolroomName);
            reportType = (TextView) view.findViewById(R.id.reportType);
            reportState = (TextView) view.findViewById(R.id.reportState);
            reportFrom = (TextView) view.findViewById(R.id.reportFrom);
        }
    }

    public ReportAdapter(Context context, List<ReportModel> reportModels) {
        this.reportModelList = reportModels;
        this.context = context;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_style_report, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {

        db = new DatabaseHandler(context);
        ReportModel reportModel = reportModelList.get(position);

        holder.reportDate.setText(reportModel.get_date());
        holder.coolroomNumber.setText(reportModel.get_number());
        holder.reportState.setText(reportModel.get_state());
        holder.reportType.setText(reportModel.get_type());
        holder.reportFrom.setText(reportModel.get_report_from());
        holder.reportTime.setText(reportModel.getTimelog());


        String message = reportModel.message;
        if (message != null)
            if (message.length() > 0)
                switch (message.substring(0, 2)) {
                    case "#I":
                        message = codeAndDecode.decompress(message.substring(2, message.length() - 1));
                        message = message.substring(24, 28);
//                        holder.reportTime.setText(message.substring(0, 2) + ":" + message.substring(2, 4));
                        break;
                    case "#S":
                        message = codeAndDecode.decompress(message.substring(5, message.length() - 1));
                        message = message.substring(18, 22);
//                        holder.reportTime.setText(message.substring(0, 2) + ":" + message.substring(2, 4));
                        break;
                    case "#R":
                        message = codeAndDecode.decompress(message.substring(2, message.length() - 1));
                        message = "0" + message.substring(5, 15);
                        UserModel user = db.getUserByNumber(message, "OK");
                        if (user != null)
                            //here status is Date because of Constructor !
//                            holder.reportTime.setText(user.getStatus().split(" ")[1].substring(0, 5));
                        break;
                    case "#C":
                        String temp = codeAndDecode.decompress(message.substring(2, message.length() - 1));
                        String min = (temp.substring((message.length() - 2), temp.length()));
                        String hour =(temp.substring(temp.length() - 4, temp.length() - 2));
                        String day =(temp.substring(temp.length() - 6, temp.length() - 4));
                        String month = (temp.substring(temp.length() - 8, temp.length() - 6));
                        String year = (temp.substring(temp.length() - 12, temp.length() - 8));

//                        holder.reportTime.setText(hour + ":" + min);
                        holder.reportDate.setText(year + "/" + month + "/" + day);

                        break;
                }

        {
            RoomModel room = db.getRoom(reportModel.get_number());
            if (room != null)
                holder.coolroomName.setText(room.get_name());
        }

        switch (reportModel.get_state()) {
            case Config._STATE_CANCEL:
                holder.reportState.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_black_24dp, 0);
                holder.reportState.setText("لغو شده");
                break;
            case Config._STATE_PENDING:
                holder.reportState.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.pending, 0);
                holder.reportState.setText("در حال انتظار");
                break;
            case Config._STATE_COMPLETED:
                holder.reportState.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_check_circle_black_24dp, 0);
                holder.reportState.setText("موفق");
                break;

        }
        switch (reportModel.get_report_from()) {
            case Config._REPORT_FROM_CLIENT:
                holder.reportFrom.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_call_made_black_24dp, 0);
                holder.reportFrom.setText("ارسال شده توسط شما");
                break;
            case Config._REPORT_FROM_SERVER:
                holder.reportFrom.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_call_received_black_24dp, 0);
                holder.reportFrom.setText("دریافت شده از سردخانه");
                break;
        }
        switch (reportModel.get_type()) {
            case Config._TYPE_CREATE_USER:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_new_user, 0);
                holder.reportType.setText("کاربری اضافه شده");
                break;
            case Config._TYPE_DELETE_USER:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_remove_user, 0);
                holder.reportType.setText("کاربری حذف شده");
                break;
            case Config._TYPE_EDIT_USER:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit_user, 0);
                holder.reportType.setText("کاربری ویرایش شده");
                break;
            case Config._TYPE_CREATE_ROOM:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_create_room, 0);
                holder.reportType.setText("سردخانه افزوده شده");
            {
                RoomModel room = db.getRoom(reportModel.get_number());
                if (room != null)
                    holder.reportTime.setText(room.get_date().split(" ")[1].substring(0, 5));
            }
            break;
            case Config._TYPE_EDIT_ROOM:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_edit_room, 0);
                holder.reportType.setText("سردخانه ویراش شده");
            {
                RoomModel room = db.getRoom(reportModel.get_number());
                if (room != null)
                    holder.reportTime.setText(room.get_date().split(" ")[1].substring(0, 5));
            }
            break;
            case Config._TYPE_DELETE_ROOM:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_delete_room, 0);
                holder.reportType.setText("سردخانه حذف شده");
                break;
            case Config._TYPE_SEND_MESSAGE:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_message_sent, 0);
                holder.reportType.setText("تنظیمات ارسال شده");
                break;
            case Config._TYPE_RECEIVED_MESSAGE:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_receive, 0);
                holder.reportType.setText("تنظیمات دریافت شده");
                break;
            case Config._REPORT_SETTING:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sentrecieved, 0);
                holder.reportType.setText("تنظیمات ارسال شده تایید شد.");
                break;
            case Config._MANUAL_DEFROST:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sentrecieved, 0);
                holder.reportType.setText("دیفراست دستی");
                break;
            case Config._MANUAL_COMPRESSOR:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sentrecieved, 0);
                holder.reportType.setText("کمپرسور دستی");
                break;
            case Config._MANUAL_OVAPERATOR:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sentrecieved, 0);
                holder.reportType.setText("اواپراتور دستی");
                break;
            case Config._MANUAL_HIGHGAS:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sentrecieved, 0);
                holder.reportType.setText("فشار گاز بالا دستی");
                break;
            case Config._MANUAL_LOWGAS:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sentrecieved, 0);
                holder.reportType.setText("فشار گاز پایین دستی");
                break;
            case Config._MANUAL_OIL:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.sentrecieved, 0);
                holder.reportType.setText("فشار روغن دستی");
                break;
//**************Error cases
            case Config.ERROR_Full_DISCONNECT:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای قطع کامل برق");
                break;
            case Config.ERROR_MANUAL_DISCONNECT:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای خاموش دستی");
                break;
            case Config.ERROR_Disconnect_PHASE_R:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای قطع فاز R");
                break;
            case Config.ERROR_Disconnect_PHASE_S:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای قطع فاز S");
                break;
            case Config.ERROR_Disconnect_PHASE_T:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای قطع فاز T");
                break;
            case Config.ERROR_PHASE_FAILURE:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای عدم توالی فاز");
                break;
            case Config.ERROR_PHASE_ASYMMETRY:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای عدم تقارن فاز");
                break;
            case Config.ERROR_INCREASE_VOLTAGE_R:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای افزایش ولتاژ R");
                break;
            case Config.ERROR_INCREASE_VOLTAGE_S:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای افزایش ولتاژ S");
                break;
            case Config.ERROR_INCREASE_VOLTAGE_T:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای افزایش ولتاژ T");
                break;
            case Config.ERROR_DECREASE_VOLTAGE_R:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای کاهش ولتاژ R");
                break;
            case Config.ERROR_DECREASE_VOLTAGE_S:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای کاهش ولتاژ S");
                break;
            case Config.ERROR_DECREASE_VOLTAGE_T:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای کاهش ولتاژ T");
                break;
            case Config.ERROR_COMPRESSOR_OVERLOAD:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای اورلود کمپرسور");
                break;
            case Config.ERROR_INCREASE_COMPRESSOR_CURRENT:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای افزایش جریان کمپرسور");
                break;
            case Config.ERROR_DECREASE_COMPRESSOR_CURRENT:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای کاهش جریان کمپرسور");
                break;
            case Config.ERROR_COMPRESSOR_ASYMMETRY:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای عدم تقارن جریان کمپرسور");
                break;
            case Config.ERROR_INCREASE_OVAPRATOR_CURRENT:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("افزایش جریان اواپراتور");
                break;
            case Config.ERROR_DECREASE_OVAPRATOR_CURRENT:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("کاهش جریان اواپراتور");
                break;
            case Config.ERROR_OVAPRATOR_FLOW_ASYMMETRY:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("عدم تقارن جریان اواپراتور");
                break;
            case Config.ERROR_HIGH_GAS:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای فشار گاز بالا");
                break;
            case Config.ERROR_LOW_GAS:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای فشار گاز پایین");
                break;
            case Config.ERROR_OIL:
                holder.reportType.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_error_outline_red_24dp, 0);
                holder.reportType.setText("خطای فشار روغن");
                break;
        }
    }

    @Override
    public int getItemCount() {
        return reportModelList.size();
    }
}
