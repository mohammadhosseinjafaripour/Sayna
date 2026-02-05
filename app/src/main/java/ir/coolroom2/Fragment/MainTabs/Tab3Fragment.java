package ir.coolroom2.Fragment.MainTabs;

/**
 * Created by JFP on 11/29/2017.
 */

import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RelativeLayout;
import android.widget.Spinner;

import com.arlib.floatingsearchview.FloatingSearchView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

import ir.coolroom2.Adapter.ReportAdapter;
import ir.coolroom2.Config;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.ReportModel;
import ir.coolroom2.R;
import ir.coolroom2.Sms.SmsCodeAndDecode;


public class Tab3Fragment extends Fragment {

    View V;

    private Spinner beginYear, beginMonth, beginDay, endYear, endMonth, endDay;
    private ArrayList<String> beginYearList = new ArrayList<>();
    private ArrayList<String> beginMonthList = new ArrayList<>();
    private ArrayList<String> beginDayList = new ArrayList<>();
    private ArrayList<String> endYearList = new ArrayList<>();
    private ArrayList<String> endMonthList = new ArrayList<>();
    private ArrayList<String> endDayList = new ArrayList<>();
    private RecyclerView.LayoutManager mLayoutManager;
    private ReportAdapter reportAdapter;
    private RecyclerView recyclerView;
    private ArrayList<ReportModel> reportModels;

    private ArrayAdapter<String> beginYearAdapter;
    private ArrayAdapter<String> beginMonthAdapter;
    private ArrayAdapter<String> beginDayAdapter;
    private ArrayAdapter<String> endYearAdapter;
    private ArrayAdapter<String> endMonthAdapter;
    private ArrayAdapter<String> endDayAdapter;

    private Dialog dialog;

    private String minDate;
    private String maxDate;

    private String minYear;
    private String minMonth;
    private String minDay;

    private String maxYear;
    private String maxMonth;
    private String maxDay;

    CheckBox success, cancel, pending, client, server, send, recieve, create_user, edit_user, delete_user, create_room, edit_room, delete_room, settingAccpeted, manual_defrost, manual_oil, manual_highgas, manual_lowgas, manual_ovaperator, manual_compressor;
    CheckBox error01, error02, error03, error04, error05, error06, error07, error08, error09, error10, error11, error12, error13, error14, error15, error16, error17, error18, error19, error20, error21, error22, error23;


    String state = "", type = "", report_from = "";

    private SmsCodeAndDecode codeAndDecode;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        V = inflater.inflate(R.layout.tab3_view, container, false);

        final FloatingSearchView searchView = (FloatingSearchView) V.findViewById(R.id.floating_search_view);
        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_report_bottom_sheet);

        codeAndDecode = new SmsCodeAndDecode();


        final DatabaseHandler db = new DatabaseHandler(getActivity());

        recyclerView = (RecyclerView) V.findViewById(R.id.recycle);
        reportModels = db.getLogs();
        Collections.reverse(reportModels);
        reportAdapter = new ReportAdapter(getActivity(), reportModels);
        mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(reportAdapter);


        dialog = new Dialog(getActivity());
        dialog.setContentView(R.layout.dialog_report_bottom_sheet);
        beginYear = (Spinner) dialog.findViewById(R.id.beginYear);
        beginMonth = (Spinner) dialog.findViewById(R.id.beginMonth);
        beginDay = (Spinner) dialog.findViewById(R.id.beginDay);
        endYear = (Spinner) dialog.findViewById(R.id.endYear);
        endMonth = (Spinner) dialog.findViewById(R.id.endMonth);
        endDay = (Spinner) dialog.findViewById(R.id.endDay);

        success = (CheckBox) dialog.findViewById(R.id.success);
        cancel = (CheckBox) dialog.findViewById(R.id.cancel);
        pending = (CheckBox) dialog.findViewById(R.id.pending);
        client = (CheckBox) dialog.findViewById(R.id.client);
        server = (CheckBox) dialog.findViewById(R.id.server);
        create_user = (CheckBox) dialog.findViewById(R.id.create_user);
        edit_user = (CheckBox) dialog.findViewById(R.id.edit_user);
        delete_user = (CheckBox) dialog.findViewById(R.id.delete_user);
        create_room = (CheckBox) dialog.findViewById(R.id.create_room);
        edit_room = (CheckBox) dialog.findViewById(R.id.edit_room);
        delete_room = (CheckBox) dialog.findViewById(R.id.delete_room);
        send = (CheckBox) dialog.findViewById(R.id.send);
        recieve = (CheckBox) dialog.findViewById(R.id.recieve);
        settingAccpeted = (CheckBox) dialog.findViewById(R.id.settingAccpeted);
        manual_defrost = (CheckBox) dialog.findViewById(R.id.manual_defrost);

        manual_oil = dialog.findViewById(R.id.manual_oil);
        manual_highgas = dialog.findViewById(R.id.manual_highgas);
        manual_lowgas = dialog.findViewById(R.id.manual_lowgas);
        manual_ovaperator = dialog.findViewById(R.id.manual_ovaperator);
        manual_compressor = dialog.findViewById(R.id.manual_compressor);

        error01 = (CheckBox) dialog.findViewById(R.id.error01);
        error02 = (CheckBox) dialog.findViewById(R.id.error02);
        error03 = (CheckBox) dialog.findViewById(R.id.error03);
        error04 = (CheckBox) dialog.findViewById(R.id.error04);
        error05 = (CheckBox) dialog.findViewById(R.id.error05);
        error06 = (CheckBox) dialog.findViewById(R.id.error06);
        error07 = (CheckBox) dialog.findViewById(R.id.error07);
        error08 = (CheckBox) dialog.findViewById(R.id.error08);
        error09 = (CheckBox) dialog.findViewById(R.id.error09);
        error10 = (CheckBox) dialog.findViewById(R.id.error10);
        error11 = (CheckBox) dialog.findViewById(R.id.error11);
        error12 = (CheckBox) dialog.findViewById(R.id.error12);
        error13 = (CheckBox) dialog.findViewById(R.id.error13);
        error14 = (CheckBox) dialog.findViewById(R.id.error14);
        error15 = (CheckBox) dialog.findViewById(R.id.error15);
        error16 = (CheckBox) dialog.findViewById(R.id.error16);
        error17 = (CheckBox) dialog.findViewById(R.id.error17);
        error18 = (CheckBox) dialog.findViewById(R.id.error18);
        error19 = (CheckBox) dialog.findViewById(R.id.error19);
        error20 = (CheckBox) dialog.findViewById(R.id.error20);
        error21 = (CheckBox) dialog.findViewById(R.id.error21);
        error22 = (CheckBox) dialog.findViewById(R.id.error22);
        error23 = (CheckBox) dialog.findViewById(R.id.error23);


        final RelativeLayout relativeLayout = (RelativeLayout) V.findViewById(R.id.not_found);


        //Dialog Section
        minDate = db.getFirstDate();//from server
        maxDate = db.getLastDate();//from server or current date

        String[] tmp = minDate.split(" ");
        minYear = tmp[0].split("/")[0];

        tmp = maxDate.split(" ");
        maxYear = tmp[0].split("/")[0];


        beginYearList.clear();
        beginMonthList.clear();
        beginDayList.clear();

        for (int i = Integer.parseInt(minYear); i <= Integer.parseInt(maxYear); i++)
            beginYearList.add(String.valueOf(i));
        for (int i = 1; i <= 12; i++) {
            beginMonthList.add(String.valueOf(i));
        }
        for (int i = 1; i <= 31; i++) {
            beginDayList.add(String.valueOf(i));
        }


        beginYearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, beginYearList);
        beginYearAdapter.setDropDownViewResource(R.layout.dropdown_rtl_layout);
        beginYear.setAdapter(beginYearAdapter);

        beginMonthAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, beginMonthList);
        beginMonthAdapter.setDropDownViewResource(R.layout.dropdown_rtl_layout);
        beginMonth.setAdapter(beginMonthAdapter);

        beginDayAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, beginDayList);
        beginDayAdapter.setDropDownViewResource(R.layout.dropdown_rtl_layout);
        beginDay.setAdapter(beginDayAdapter);

        endMonth.setAdapter(beginMonthAdapter);
        endDay.setAdapter(beginDayAdapter);


        beginYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                endYearList.clear();
                for (int i = Integer.parseInt(beginYear.getSelectedItem().toString()); i <= Integer.parseInt(maxYear); i++)
                    endYearList.add(String.valueOf(i));
                endYearAdapter = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item, endYearList);
                endYearAdapter.setDropDownViewResource(R.layout.dropdown_rtl_layout);
                endYear.setAdapter(endYearAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        //end


        dialog.findViewById(R.id.ok).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                type = state = report_from = "";


                relativeLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);
                if (success.isChecked()) {
                    state += Config._STATE_COMPLETED + "-";
                }
                if (cancel.isChecked()) {
                    state += Config._STATE_CANCEL + "-";
                }
                if (pending.isChecked()) {
                    state += Config._STATE_PENDING + "-";
                }
                if (client.isChecked()) {
                    report_from += Config._REPORT_FROM_CLIENT + "-";
                }
                if (server.isChecked()) {
                    report_from += Config._REPORT_FROM_SERVER + "-";
                }

                if (send.isChecked()) {
                    type += Config._TYPE_SEND_MESSAGE + "-";
                }
                if (recieve.isChecked()) {
                    type += Config._TYPE_RECEIVED_MESSAGE + "-";
                }
                if (create_user.isChecked()) {
                    type += Config._TYPE_CREATE_USER + "-";
                }
                if (edit_user.isChecked()) {
                    type += Config._TYPE_EDIT_USER + "-";
                }
                if (delete_user.isChecked()) {
                    type += Config._TYPE_DELETE_USER + "-";
                }
                if (create_room.isChecked()) {
                    type += Config._TYPE_CREATE_ROOM + "-";
                }
                if (edit_room.isChecked()) {
                    type += Config._TYPE_EDIT_ROOM + "-";
                }
                if (delete_room.isChecked()) {
                    type += Config._TYPE_DELETE_ROOM + "-";
                }
                if (manual_oil.isChecked()) {
                    type += Config._MANUAL_OIL + "-";
                }
                if (manual_highgas.isChecked()) {
                    type += Config._MANUAL_HIGHGAS + "-";
                }
                if (manual_lowgas.isChecked()) {
                    type += Config._MANUAL_LOWGAS + "-";
                }
                if (manual_ovaperator.isChecked()) {
                    type += Config._MANUAL_OVAPERATOR + "-";
                }
                if (manual_compressor.isChecked()) {
                    type += Config._MANUAL_COMPRESSOR + "-";
                }

                //errors types
                if (error01.isChecked()) {
                    type += Config.ERROR_Full_DISCONNECT + "-";
                }
                if (error02.isChecked()) {
                    type += Config.ERROR_MANUAL_DISCONNECT + "-";
                }
                if (error03.isChecked()) {
                    type += Config.ERROR_Disconnect_PHASE_R + "-";
                }
                if (error04.isChecked()) {
                    type += Config.ERROR_Disconnect_PHASE_S + "-";
                }
                if (error05.isChecked()) {
                    type += Config.ERROR_Disconnect_PHASE_T + "-";
                }
                if (error06.isChecked()) {
                    type += Config.ERROR_PHASE_FAILURE + "-";
                }
                if (error07.isChecked()) {
                    type += Config.ERROR_PHASE_ASYMMETRY + "-";
                }
                if (error08.isChecked()) {
                    type += Config.ERROR_INCREASE_VOLTAGE_R + "-";
                }
                if (error09.isChecked()) {
                    type += Config.ERROR_INCREASE_VOLTAGE_S + "-";
                }
                if (error10.isChecked()) {
                    type += Config.ERROR_INCREASE_VOLTAGE_T + "-";
                }
                if (error11.isChecked()) {
                    type += Config.ERROR_DECREASE_VOLTAGE_R + "-";
                }
                if (error12.isChecked()) {
                    type += Config.ERROR_DECREASE_VOLTAGE_S + "-";
                }
                if (error13.isChecked()) {
                    type += Config.ERROR_DECREASE_VOLTAGE_T + "-";
                }
                if (error14.isChecked()) {
                    type += Config.ERROR_COMPRESSOR_OVERLOAD + "-";
                }
                if (error15.isChecked()) {
                    type += Config.ERROR_INCREASE_COMPRESSOR_CURRENT + "-";
                }
                if (error16.isChecked()) {
                    type += Config.ERROR_DECREASE_COMPRESSOR_CURRENT + "-";
                }
                if (error17.isChecked()) {
                    type += Config.ERROR_COMPRESSOR_ASYMMETRY + "-";
                }
                if (error18.isChecked()) {
                    type += Config.ERROR_INCREASE_OVAPRATOR_CURRENT + "-";
                }
                if (error19.isChecked()) {
                    type += Config.ERROR_DECREASE_OVAPRATOR_CURRENT + "-";
                }
                if (error20.isChecked()) {
                    type += Config.ERROR_OVAPRATOR_FLOW_ASYMMETRY + "-";
                }
                if (error21.isChecked()) {
                    type += Config.ERROR_HIGH_GAS + "-";
                }
                if (error22.isChecked()) {
                    type += Config.ERROR_LOW_GAS + "-";
                }
                if (error23.isChecked()) {
                    type += Config.ERROR_OIL + "-";
                }
                if (settingAccpeted.isChecked()) {
                    type += Config._REPORT_SETTING + "-";
                }
                if (manual_defrost.isChecked()) {
                    type += Config._MANUAL_DEFROST + "-";
                }
                //errors types


                if (type.length() > 0)
                    type = type.substring(0, type.length() - 1);

                if (state.length() > 0)
                    state = state.substring(0, state.length() - 1);

                if (report_from.length() > 0)
                    report_from = report_from.substring(0, report_from.length() - 1);

                String begin = beginYear.getSelectedItem().toString() + "/"
                        + codeAndDecode.truecent_length(beginMonth.getSelectedItem().toString(), 2) + "/"
                        + codeAndDecode.truecent_length(beginDay.getSelectedItem().toString(), 2);
                String end = endYear.getSelectedItem().toString() + "/"
                        + codeAndDecode.truecent_length(endMonth.getSelectedItem().toString(), 2) + "/"
                        + codeAndDecode.truecent_length(endDay.getSelectedItem().toString(), 2);

                //ok
                reportModels = db.getLogs(begin, end, type, state, report_from, "");
                Collections.reverse(reportModels);
                reportAdapter = new ReportAdapter(getActivity(), reportModels);
                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                recyclerView.setLayoutManager(mLayoutManager);
                recyclerView.setItemAnimator(new DefaultItemAnimator());
                recyclerView.setAdapter(reportAdapter);
                reportAdapter.notifyDataSetChanged();
                dialog.dismiss();

                if (reportModels.isEmpty()) {
                    relativeLayout.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }

            }
        });


        dialog.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //cancel
                dialog.dismiss();
            }
        });

        searchView.setOnMenuItemClickListener(new FloatingSearchView.OnMenuItemClickListener() {
            @Override
            public void onActionMenuItemSelected(MenuItem item) {

                dialog.show();
            }
        });

        searchView.setOnQueryChangeListener(new FloatingSearchView.OnQueryChangeListener() {
            @Override
            public void onSearchTextChanged(String oldQuery, String newQuery) {

                relativeLayout.setVisibility(View.GONE);
                recyclerView.setVisibility(View.GONE);

                if (beginYear.getSelectedItemPosition() != -1 &&
                        beginMonth.getSelectedItemPosition() != -1 &&
                        beginDay.getSelectedItemPosition() != -1 &&
                        endYear.getSelectedItemPosition() != -1 &&
                        endMonth.getSelectedItemPosition() != -1 &&
                        endDay.getSelectedItemPosition() != -1) {

                    String begin = beginYear.getSelectedItem().toString() + "/"
                            + codeAndDecode.truecent_length(beginMonth.getSelectedItem().toString(), 2) + "/"
                            + codeAndDecode.truecent_length(beginDay.getSelectedItem().toString(), 2);
                    String end = endYear.getSelectedItem().toString() + "/"
                            + codeAndDecode.truecent_length(endMonth.getSelectedItem().toString(), 2) + "/"
                            + codeAndDecode.truecent_length(endDay.getSelectedItem().toString(), 2);

                    reportModels = db.getLogs(begin, end, type, state, report_from, newQuery);
                    Collections.reverse(reportModels);
                    reportAdapter = new ReportAdapter(getActivity(), reportModels);
                    RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(reportAdapter);
                    reportAdapter.notifyDataSetChanged();


                    if (newQuery.equals("")) {
                        reportModels = db.getLogs(begin, end, type, state, report_from, "");
                        Collections.reverse(reportModels);
                        reportAdapter = new ReportAdapter(getActivity(), reportModels);
                        mLayoutManager = new LinearLayoutManager(getActivity());
                        recyclerView.setLayoutManager(mLayoutManager);
                        recyclerView.setItemAnimator(new DefaultItemAnimator());
                        recyclerView.setAdapter(reportAdapter);
                        reportAdapter.notifyDataSetChanged();
                    }
                } else {
                    reportModels = db.getLogs(db.getFirstDate(), db.getLastDate(), type, state, report_from, newQuery);
                    Collections.reverse(reportModels);
                    reportAdapter = new ReportAdapter(getActivity(), reportModels);
                    mLayoutManager = new LinearLayoutManager(getActivity());
                    recyclerView.setLayoutManager(mLayoutManager);
                    recyclerView.setItemAnimator(new DefaultItemAnimator());
                    recyclerView.setAdapter(reportAdapter);
                    reportAdapter.notifyDataSetChanged();
                }

                if (reportModels.isEmpty()) {
                    relativeLayout.setVisibility(View.VISIBLE);
                } else {
                    recyclerView.setVisibility(View.VISIBLE);
                }
            }
        });


        return V;
    }


}
