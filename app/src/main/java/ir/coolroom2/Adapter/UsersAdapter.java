package ir.coolroom2.Adapter;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v7.app.AppCompatDelegate;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.List;

import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Fragment.Registry.AddUserFragment;
import ir.coolroom2.Model.RoomModel;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.Sms.PrepareDataAndSendSms;


/**
 * Created by Mohammad on 12/6/2017.
 */

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder> {

    private List<UserModel> usersList;

    Activity context;
    DatabaseHandler db;

    PrepareDataAndSendSms prepareDataAndSendSms;

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView userInfo, NameAndFamily, phone_number;
        private LinearLayout showDetails;
        private ImageView deleteForEver, edit, view_eye;
        private ImageView imgStatus;
        private RelativeLayout relativeLayout;


        public MyViewHolder(View view) {
            super(view);
            userInfo = (TextView) view.findViewById(R.id.userInfo);
            deleteForEver = (ImageView) view.findViewById(R.id.deleteForEverBtn);
            edit = (ImageView) view.findViewById(R.id.editBtn);
            showDetails = (LinearLayout) view.findViewById(R.id.showDetails);
            view_eye = (ImageView) view.findViewById(R.id.view_eye);
            phone_number = (TextView) view.findViewById(R.id.phone_number);
            NameAndFamily = (TextView) view.findViewById(R.id.NameAndFamily);
            imgStatus = view.findViewById(R.id.imgStatus);
            relativeLayout = view.findViewById(R.id.relative_layout);


        }
    }

    public UsersAdapter(Activity context, List<UserModel> usersList) {
        this.usersList = usersList;
        this.context = context;
        db = new DatabaseHandler(context);
        prepareDataAndSendSms = new PrepareDataAndSendSms(context);
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adapter_style_users, parent, false);
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, final int position) {

        final UserModel user = usersList.get(position);
        Config.currentUserName = usersList.get(position).get_name() + " " + usersList.get(position).get_family();

        if (user.get_permision_level().equals("one")) {
            holder.userInfo.setText("مدیر");
            holder.userInfo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_admin, 0);
        } else if (user.get_permision_level().equals("two")) {
            holder.userInfo.setText("تکنسین");
            holder.userInfo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_technician_with_glasses, 0);
        } else {
            holder.userInfo.setText("کاربر");
            holder.userInfo.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_simple_user, 0);

        }
        holder.NameAndFamily.setText(user.get_name().toString() + " " + user.get_family());

        holder.phone_number.setText(user.get_mobile());


        holder.view_eye.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialog_user_edit = new Dialog(context);
                dialog_user_edit.setContentView(R.layout.dialog_show_user_detail_view);

                final EditText username_tv = (EditText) dialog_user_edit.findViewById(R.id.userName);
                final EditText userfamily_tv = (EditText) dialog_user_edit.findViewById(R.id.userFamily);
                final EditText userphone_tv = (EditText) dialog_user_edit.findViewById(R.id.userPhone);
                final TextView userroom_tv = (TextView) dialog_user_edit.findViewById(R.id.userRoom);
                final RadioButton one_rb = (RadioButton) dialog_user_edit.findViewById(R.id.one);
                final RadioButton two_rb = (RadioButton) dialog_user_edit.findViewById(R.id.two);
                final RadioButton three_rb = (RadioButton) dialog_user_edit.findViewById(R.id.three);

                username_tv.setFocusable(false);
                username_tv.setClickable(false);
                username_tv.setBackgroundColor(context.getResources().getColor(R.color.white));
                userfamily_tv.setFocusable(false);
                userfamily_tv.setClickable(false);
                userfamily_tv.setBackgroundColor(context.getResources().getColor(R.color.white));
                userphone_tv.setFocusable(false);
                userphone_tv.setClickable(false);
                userphone_tv.setBackgroundColor(context.getResources().getColor(R.color.white));
                one_rb.setClickable(false);
                two_rb.setClickable(false);
                three_rb.setClickable(false);

                switch (user.get_permision_level()) {
                    case "one":
                        one_rb.setChecked(true);
                        two_rb.setChecked(false);
                        three_rb.setChecked(false);
                        break;
                    case "two":
                        one_rb.setChecked(false);
                        two_rb.setChecked(true);
                        three_rb.setChecked(false);
                        break;
                    case "three":
                        one_rb.setChecked(false);
                        two_rb.setChecked(false);
                        three_rb.setChecked(true);
                        break;
                }

                username_tv.setText(user.get_name());
                userfamily_tv.setText(user.get_family());
                userphone_tv.setText(user.get_mobile());
                userroom_tv.setText(db.getRoom(user.get_room_id()).get_name());
                Button save = (Button) dialog_user_edit.findViewById(R.id.edit);
                save.setText("تایید");
                Button cancel = (Button) dialog_user_edit.findViewById(R.id.cancel);
                cancel.setVisibility(View.GONE);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_user_edit.dismiss();
                    }
                });
                dialog_user_edit.show();


            }
        });
        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog_user_edit = new Dialog(context);
                dialog_user_edit.setContentView(R.layout.dialog_user_detail);

                final TextView username_tv = (EditText) dialog_user_edit.findViewById(R.id.userName);
                final TextView userfamily_tv = (EditText) dialog_user_edit.findViewById(R.id.userFamily);
                final TextView userphone_tv = (EditText) dialog_user_edit.findViewById(R.id.userPhone);
                final TextView userroom_tv = (TextView) dialog_user_edit.findViewById(R.id.userRoom);
                final RadioGroup permision_rg = (RadioGroup) dialog_user_edit.findViewById(R.id.permision);
                final RadioButton one_rb = (RadioButton) dialog_user_edit.findViewById(R.id.one);
                final RadioButton two_rb = (RadioButton) dialog_user_edit.findViewById(R.id.two);
                final RadioButton three_rb = (RadioButton) dialog_user_edit.findViewById(R.id.three);
                Button save = (Button) dialog_user_edit.findViewById(R.id.edit);
                Button cancel = (Button) dialog_user_edit.findViewById(R.id.cancel);
                final EditText userName = (EditText) dialog_user_edit.findViewById(R.id.userName);
                final EditText userFamily = (EditText) dialog_user_edit.findViewById(R.id.userFamily);

                final String permision_str = user.get_permision_level();

                if (permision_str.equals("one")) {
                    save.setBackgroundColor(Color.parseColor("#dadada"));
                    save.setClickable(false);
                    save.setOnTouchListener(null);
                }

                permision_rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
                    @Override
                    public void onCheckedChanged(RadioGroup radioGroup, int checkid) {
                        switch (checkid) {
                            case R.id.one:
                                break;
                            case R.id.two:
                                if (permision_str.equals("one")) {
                                    permision_rg.check(R.id.one);
                                    final Dialog dialog = new Dialog(context);
                                    dialog.setContentView(R.layout.dialog_message_custom_alert);
                                    TextView title = (TextView) dialog.findViewById(R.id.title);
                                    TextView msg = (TextView) dialog.findViewById(R.id.description);
                                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);

                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    cancel.setVisibility(View.GONE);
                                    posetive_btn_text.setText("باشه");
                                    msg.setText("امکان ویرایش مدیر وجود ندارد.");
                                    title.setText("هشدار");
                                    dialog.show();
                                } else {
                                }
                                break;
                            case R.id.three:
                                if (permision_str.equals("one")) {
                                    permision_rg.check(R.id.one);
                                    final Dialog dialog = new Dialog(context);
                                    dialog.setContentView(R.layout.dialog_message_custom_alert);
                                    TextView title = (TextView) dialog.findViewById(R.id.title);
                                    TextView msg = (TextView) dialog.findViewById(R.id.description);
                                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);

                                    ok.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View view) {
                                            dialog.dismiss();
                                        }
                                    });
                                    cancel.setVisibility(View.GONE);
                                    posetive_btn_text.setText("باشه");
                                    msg.setText("امکان ویرایش مدیر وجود ندارد.");
                                    title.setText("هشدار");
                                    dialog.show();
                                }
                                break;
                        }
                    }
                });


                userphone_tv.setClickable(false);
                userphone_tv.setFocusable(false);
                userphone_tv.setFocusableInTouchMode(false);
                switch (user.get_permision_level()) {
                    case "one":
                        one_rb.setChecked(true);
                        two_rb.setChecked(false);
                        three_rb.setChecked(false);
                        break;
                    case "two":
                        one_rb.setChecked(false);
                        two_rb.setChecked(true);
                        three_rb.setChecked(false);
                        break;
                    case "three":
                        one_rb.setChecked(false);
                        two_rb.setChecked(false);
                        three_rb.setChecked(true);
                        break;
                }

                username_tv.setText(user.get_name());
                userfamily_tv.setText(user.get_family());
                userphone_tv.setText(user.get_mobile());
                userroom_tv.setText(db.getRoom(user.get_room_id()).get_name());

                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        String permision_str = user.get_permision_level();
                        String permision_str_int = "";
                        if(!permision_str.equals("one")) {
                            if (permision_str.equals("one")) {
                                permision_str_int = "1";
                            } else if (permision_str.equals("two")) {
                                permision_str_int = "2";
                            } else {
                                permision_str = "3";
                            }
                            switch (permision_rg.getCheckedRadioButtonId()) {
                                case R.id.one:
                                    permision_str = "one";
                                    permision_str_int = "1";
                                    break;
                                case R.id.two:
                                    if (permision_str.equals("one")) {
                                        permision_rg.check(R.id.one);
                                        permision_str = "one";
                                        permision_str_int = "1";
                                        final Dialog dialog = new Dialog(context);
                                        dialog.setContentView(R.layout.dialog_message_custom_alert);
                                        TextView title = (TextView) dialog.findViewById(R.id.title);
                                        TextView msg = (TextView) dialog.findViewById(R.id.description);
                                        LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                                        LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                                        TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);

                                        ok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });
                                        cancel.setVisibility(View.GONE);
                                        posetive_btn_text.setText("باشه");
                                        msg.setText("امکان ویرایش مدیر وجود ندارد.");
                                        title.setText("هشدار");
                                        dialog.show();
                                    } else {
                                        permision_str = "two";
                                        permision_str_int = "2";
                                    }
                                    break;
                                case R.id.three:
                                    if (permision_str.equals("one")) {
                                        permision_rg.check(R.id.one);
                                        permision_str = "one";
                                        permision_str_int = "1";
                                        final Dialog dialog = new Dialog(context);
                                        dialog.setContentView(R.layout.dialog_message_custom_alert);
                                        TextView title = (TextView) dialog.findViewById(R.id.title);
                                        TextView msg = (TextView) dialog.findViewById(R.id.description);
                                        LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                                        LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                                        TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);

                                        ok.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });
                                        cancel.setVisibility(View.GONE);
                                        posetive_btn_text.setText("باشه");
                                        msg.setText("امکان ویرایش مدیر وجود ندارد.");
                                        title.setText("هشدار");
                                        dialog.show();
                                    } else {
                                        permision_str = "three";
                                        permision_str_int = "3";
                                    }
                                    break;
                            }
                            if (!permision_str.equals(""))

                            {
                                String current_permission_level = db.PermissionLevel(user.get_id());
                                if (current_permission_level.matches("one")) {
                                    prepareDataAndSendSms.send_user_update(
                                            db.getFirstUser_for_room(Config.currentRoomID).get_mobile(),
                                            "M",
                                            "C",
                                            permision_str_int,
                                            user.get_mobile());


                                } else if (current_permission_level.matches("two") || current_permission_level.matches("three")) {
                                    prepareDataAndSendSms.send_user_update(
                                            db.getFirstUser_for_room(Config.currentRoomID).get_mobile(),
                                            (permision_str.matches("two") ? "T" : "U"),
                                            "C",
                                            (permision_str_int.matches("two") ? "2" : "3"),
                                            user.get_mobile());
                                }

                                db.updateUserLimited(new UserModel(user.get_id(), userName.getText().toString(), userFamily.getText().toString()));

                                AddUserFragment addUserFragment = AddUserFragment.instance();
                                addUserFragment.update();

                                db.createOrder("",
                                        db.getRoom(Config.currentRoomID).get_number(),
                                        Config._STATE_PENDING,
                                        Config._REPORT_FROM_CLIENT,
                                        "",
                                        new HashMap<String, String>(),
                                        Config._TYPE_EDIT_USER,
                                        Config.currentRoomID,
                                        db.getFirstUser_for_room(Config.currentRoomID).get_id());

                                dialog_user_edit.dismiss();
                            } else

                            {
                                Toast.makeText(context, "سطح دسترسی کاربر را انتخاب کنید.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                cancel.setOnClickListener(new View.OnClickListener()

                {
                    @Override
                    public void onClick(View v) {
                        dialog_user_edit.dismiss();
                    }
                });
                dialog_user_edit.show();
            }
        });
        holder.deleteForEver.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(context);
                dialog.setContentView(R.layout.dialog_custom_alert);
                TextView title = (TextView) dialog.findViewById(R.id.title);
                title.setText("حذف");
                TextView description = (TextView) dialog.findViewById(R.id.description);
                description.setText("آیا از حذف این کاربر مطمئن هستید؟");
                LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
                posetive_btn_text.setText("بله");
                negative_btn_text.setText("خیر");

                ok.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AddUserFragment addUserFragment = AddUserFragment.instance();
                        addUserFragment.update();

                        prepareDataAndSendSms.send_registery_data(
                                db.getRoom(Config.currentRoomID).get_number(),
                                "M",
                                "D",
                                "1",
                                user.get_mobile(),
                                Config.provincial_code.get(db.getRoom(Config.currentRoomID).get_location()),
                                db.getRoom(Config.currentRoomID).get_product_type(),
                                ((db.getRoom(Config.currentRoomID).is_under_zero() ? "1" : "0")),
                                db.getRoom(Config.currentRoomID).get_length(),
                                db.getRoom(Config.currentRoomID).get_width(),
                                db.getRoom(Config.currentRoomID).get_height(),
                                db.getRoom(Config.currentRoomID).get_thickness(),
                                db.getRoom(Config.currentRoomID).get_compressor(),
                                db.getRoom(Config.currentRoomID).get_condonsor(),
                                db.getRoom(Config.currentRoomID).get_ovaperator());

                        /*db.createOrder("",
                                db.getRoom(Config.currentRoomID).get_number(),
                                Config._STATE_PENDING,
                                Config._REPORT_FROM_CLIENT,
                                "", new HashMap<String, String>(),
                                Config._TYPE_DELETE_USER,
                                Config.currentRoomID,
                                db.getFirstUser_for_room(Config.currentRoomID).get_id());*/

                        AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                        alertDialog.setTitle("توجه");
                        alertDialog.setCancelable(false);
                        alertDialog.setMessage("لطفا منتظر پاسخ سرور باشید.");
                        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "خروج",
                                new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        alertDialog.show();

                        dialog.dismiss();
                    }
                });

                cancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.setCancelable(false);
                dialog.show();
            }
        });
        holder.showDetails.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                final Dialog dialog_user_edit = new Dialog(context);
                dialog_user_edit.setContentView(R.layout.dialog_show_user_detail);

                final EditText username_tv = (EditText) dialog_user_edit.findViewById(R.id.userName);
                final EditText userfamily_tv = (EditText) dialog_user_edit.findViewById(R.id.userFamily);
                final EditText userphone_tv = (EditText) dialog_user_edit.findViewById(R.id.userPhone);
                final TextView userroom_tv = (TextView) dialog_user_edit.findViewById(R.id.userRoom);
                final RadioButton one_rb = (RadioButton) dialog_user_edit.findViewById(R.id.one);
                final RadioButton two_rb = (RadioButton) dialog_user_edit.findViewById(R.id.two);
                final RadioButton three_rb = (RadioButton) dialog_user_edit.findViewById(R.id.three);

                username_tv.setFocusable(false);
                username_tv.setClickable(false);
                username_tv.setBackgroundColor(context.getResources().getColor(R.color.white));
                userfamily_tv.setFocusable(false);
                userfamily_tv.setClickable(false);
                userfamily_tv.setBackgroundColor(context.getResources().getColor(R.color.white));
                userphone_tv.setFocusable(false);
                userphone_tv.setClickable(false);
                userphone_tv.setBackgroundColor(context.getResources().getColor(R.color.white));
                one_rb.setClickable(false);
                two_rb.setClickable(false);
                three_rb.setClickable(false);

                switch (user.get_permision_level()) {
                    case "one":
                        one_rb.setChecked(true);
                        two_rb.setChecked(false);
                        three_rb.setChecked(false);
                        break;
                    case "two":
                        one_rb.setChecked(false);
                        two_rb.setChecked(true);
                        three_rb.setChecked(false);
                        break;
                    case "three":
                        one_rb.setChecked(false);
                        two_rb.setChecked(false);
                        three_rb.setChecked(true);
                        break;
                }

                username_tv.setText(user.get_name());
                userfamily_tv.setText(user.get_family());
                userphone_tv.setText(user.get_mobile());
                userroom_tv.setText(db.getRoom(user.get_room_id()).get_name());
                Button save = (Button) dialog_user_edit.findViewById(R.id.edit);
                save.setText("تایید");
                Button cancel = (Button) dialog_user_edit.findViewById(R.id.cancel);
                cancel.setVisibility(View.GONE);
                save.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog_user_edit.dismiss();
                    }
                });
                dialog_user_edit.show();
            }
        });


        if (user.getStatus().

                equals("KO"))

        {
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#f2f2f2"));
            holder.imgStatus.setVisibility(View.VISIBLE);
            holder.imgStatus.setClickable(false);
            holder.imgStatus.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View view, MotionEvent motionEvent) {
                    ShowDialog();
                    return false;
                }
            });
            holder.deleteForEver.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    final Dialog dialog = new Dialog(context);
                    dialog.setContentView(R.layout.dialog_custom_alert);
                    TextView title = (TextView) dialog.findViewById(R.id.title);
                    title.setText("حذف");
                    TextView description = (TextView) dialog.findViewById(R.id.description);
                    description.setText("آیا از حذف این کاربر مطمئن هستید؟");
                    LinearLayout ok = (LinearLayout) dialog.findViewById(R.id.ok);
                    LinearLayout cancel = (LinearLayout) dialog.findViewById(R.id.cancel);
                    TextView posetive_btn_text = (TextView) dialog.findViewById(R.id.posetive_btn_text);
                    TextView negative_btn_text = (TextView) dialog.findViewById(R.id.negative_btn_text);
                    posetive_btn_text.setText("بله");
                    negative_btn_text.setText("خیر");

                    ok.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            AddUserFragment addUserFragment = AddUserFragment.instance();
                            addUserFragment.update();

                            prepareDataAndSendSms.send_registery_data(
                                    db.getRoom(Config.currentRoomID).get_number(),
                                    "M",
                                    "D",
                                    "1",
                                    user.get_mobile(),
                                    Config.provincial_code.get(db.getRoom(Config.currentRoomID).get_location()),
                                    db.getRoom(Config.currentRoomID).get_product_type(),
                                    ((db.getRoom(Config.currentRoomID).is_under_zero() ? "1" : "0")),
                                    db.getRoom(Config.currentRoomID).get_length(),
                                    db.getRoom(Config.currentRoomID).get_width(),
                                    db.getRoom(Config.currentRoomID).get_height(),
                                    db.getRoom(Config.currentRoomID).get_thickness(),
                                    db.getRoom(Config.currentRoomID).get_compressor(),
                                    db.getRoom(Config.currentRoomID).get_condonsor(),
                                    db.getRoom(Config.currentRoomID).get_ovaperator());

                        /*db.createOrder("",
                                db.getRoom(Config.currentRoomID).get_number(),
                                Config._STATE_PENDING,
                                Config._REPORT_FROM_CLIENT,
                                "", new HashMap<String, String>(),
                                Config._TYPE_DELETE_USER,
                                Config.currentRoomID,
                                db.getFirstUser_for_room(Config.currentRoomID).get_id());*/

                            AlertDialog alertDialog = new AlertDialog.Builder(context).create();
                            alertDialog.setTitle("توجه");
                            alertDialog.setCancelable(false);
                            alertDialog.setMessage("لطفا منتظر پاسخ سرور باشید.");
                            alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "خروج",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();

                            dialog.dismiss();
                        }
                    });

                    cancel.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    dialog.setCancelable(false);
                    dialog.show();
                }
            });
            holder.view_eye.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog();
                }
            });
            holder.edit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog();
                }
            });
            holder.showDetails.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShowDialog();
                }
            });
        } else

        {
            holder.imgStatus.setVisibility(View.GONE);
            holder.relativeLayout.setBackgroundColor(Color.parseColor("#ffffff"));
        }

        holder.imgStatus.setOnClickListener(new View.OnClickListener()

        {
            @Override
            public void onClick(View view) {
                Toast.makeText(context, "این کاربر غیر فعال است.", Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void ShowDialog() {
        final Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.dialog_custom_alert);
        TextView title = (TextView) dialog.findViewById(R.id.title);
        title.setText("هشدار");
        TextView description = (TextView) dialog.findViewById(R.id.description);
        description.setText("این کاربر هنوز فعال نشده است.");
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

    @Override
    public int getItemCount() {
        return usersList.size();
    }

}
