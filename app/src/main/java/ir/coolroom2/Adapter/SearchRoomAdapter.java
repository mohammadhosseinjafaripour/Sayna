package ir.coolroom2.Adapter;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.coolroom2.Activity.Splash;
import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;

/**
 * Created by JFP on 1/22/2018.
 */

public class SearchRoomAdapter extends RecyclerView.Adapter<SearchRoomAdapter.MyViewHolder> {

    private List<RoomModel> roomsList;
    Context context;
    DatabaseHandler db;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView location, number, name, description_tv;
        CheckBox underZero;

        ImageView imgStatus;
        LinearLayout delete_room;
        RelativeLayout cliclListener;


        public MyViewHolder(View view) {
            super(view);
            location = (TextView) view.findViewById(R.id.location_tv);
            number = (TextView) view.findViewById(R.id.number_tv);
            name = (TextView) view.findViewById(R.id.name_tv);
            description_tv = (TextView) view.findViewById(R.id.description_tv);
            imgStatus = view.findViewById(R.id.imgStatus);
            delete_room = view.findViewById(R.id.delete_room);
            cliclListener = view.findViewById(R.id.cliclListener);

        }
    }

    public SearchRoomAdapter(List<RoomModel> roomsList, Context context) {
        this.roomsList = roomsList;
        this.context = context;
        db = new DatabaseHandler(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_style_select_rooms, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final RoomModel room = roomsList.get(position);
        holder.location.setText(room.get_location());
        holder.number.setText(room.get_number());
        holder.name.setText(room.get_name());
        holder.description_tv.setText(room.get_description());
//        holder.underZero.setChecked(room.is_under_zero());


        if (room.get_status().equals("-1")) {
            holder.imgStatus.setVisibility(View.VISIBLE);
            holder.delete_room.setVisibility(View.VISIBLE);
            holder.imgStatus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog();
                }
            });
            holder.cliclListener.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog();
                }
            });
            holder.cliclListener.setBackgroundColor(Color.parseColor("#dadada"));

            holder.delete_room.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setText("هشدار");
                    TextView description = (TextView) dialog.findViewById(R.id.description);
                    description.setText("با این کار سردخانه حذف می شود." + "\n" + "آیا مطمئن هستید ؟");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
                    posetive_btn_text.setText("بله");
                    negative_btn_text.setText("خیر");
                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                            room.get_number();
                            db.deleteRoom(room.get_id());
                            db.deleteUserByRoomId(room.get_id());
                            roomsList.remove(position);
                            notifyDataSetChanged();
                        }
                    });
                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
                    dialog.show();


                }
            });
        } else {
            final int temp_position = position;
            holder.imgStatus.setVisibility(View.GONE);
            holder.delete_room.setVisibility(View.GONE);
            holder.imgStatus.setOnClickListener(null);

            holder.cliclListener.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final RoomModel roomModel = db.getRooms().get(temp_position);
                    Toast.makeText(context, " سردخانه " + roomModel.get_name() + " انتخاب شد " + roomModel.get_number(), Toast.LENGTH_SHORT).show();


                    Config.currentRoomID = roomModel.get_id();
                    UserModel tmp = db.getFirstUser_for_room(Config.currentRoomID);
                    if (tmp != null)
                        Config.permision_level = tmp.get_permision_level();
                    else {
                        final Dialog dialog = new Dialog(context);
                        dialog.setContentView(R.layout.dialog_custom_alert);
                        ((TextView) dialog.findViewById(R.id.description)).setText("این سردخانه کاربری ندارد!");
                        ((LinearLayout) dialog.findViewById(R.id.ok)).setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                dialog.dismiss();
                            }
                        });
                        ((LinearLayout) dialog.findViewById(R.id.cancel)).setVisibility(View.GONE);
                    }

                    db.resetAllRoomStatus();
                    db.activeRoomStatus(roomModel.get_id());


                    ((Activity) context).finish();

                    Intent intent = new Intent(context, Splash.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });


        }
    }

    @Override
    public int getItemCount() {
        return roomsList.size();
    }


    public void ShowDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom_alert);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText("هشدار");
        TextView description = (TextView) dialog.findViewById(R.id.description);
        description.setText("این سردخانه هنوز فعال نشده است.");
        LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
        LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
        TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
        TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
        posetive_btn_text.setText("باشه");
        cancel.setVisibility(View.GONE);
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }

}
