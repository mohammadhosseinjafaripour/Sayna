package ir.coolroom2.Fragment.Registry;


import android.app.ActivityOptions;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.coolroom2.Activity.AddUserActivity;
import ir.coolroom2.Adapter.UsersAdapter;
import ir.coolroom2.ClickListener;
import ir.coolroom2.DataBase.DatabaseHandler;
import ir.coolroom2.Model.UserModel;
import ir.coolroom2.R;
import ir.coolroom2.RecyclerTouchListener;

/**
 * A simple {@link Fragment} subclass.
 */
public class AddUserFragment extends Fragment {

    List<UserModel> usersList = new ArrayList<>();
    RecyclerView recyclerView;
    UsersAdapter mAdapter;
    DatabaseHandler db;


    private static AddUserFragment inst;

    public AddUserFragment() {
        // Required empty public constructor
    }

    @Override
    public void onStart() {
        super.onStart();
        inst = this;
        mAdapter.notifyDataSetChanged();
        update();
    }

    @Override
    public void onResume() {
        super.onResume();
        inst = this;
        update();

    }


    public static AddUserFragment instance() {
        return inst;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add_user, container, false);
        inst = this;


        db = new DatabaseHandler(getActivity());
        usersList = db.getUsers();
        recyclerView = (RecyclerView) view.findViewById(R.id.usersRecyclerView);
        mAdapter = new UsersAdapter(getActivity(), usersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
        recyclerView.addOnItemTouchListener(new RecyclerTouchListener(getActivity(), recyclerView, new ClickListener() {
            @Override
            public void onClick(View view, int position) {
            }

            @Override
            public void onLongClick(View view, int position) {
            }
        }));

        LinearLayout add_user = (LinearLayout) view.findViewById(R.id.add_user);
        add_user.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (db.getRooms().size() > 0) {
                    Intent i = new Intent(getActivity(), AddUserActivity.class);

                    Bundle bndlanimation = null;
                    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        bndlanimation = ActivityOptions.makeCustomAnimation(getActivity(), android.R.anim.slide_in_left, android.R.anim.slide_out_right).toBundle();
                    }
                    startActivity(i, bndlanimation);
                } else {
                    final Dialog noRoomFoundDialog = new Dialog(getActivity());
                    noRoomFoundDialog.setContentView(R.layout.dialog_no_room_found);
                    Button okBtn = (Button) noRoomFoundDialog.findViewById(R.id.okBtn);
                    okBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            noRoomFoundDialog.dismiss();
                        }
                    });
                    TextView addRoomBtn = (TextView) noRoomFoundDialog.findViewById(R.id.addRoomBtn);
                    addRoomBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
//                            startActivity(new Intent(getActivity(), AddRoomActivity.class));
                            noRoomFoundDialog.dismiss();
                        }
                    });
                    noRoomFoundDialog.show();
                }
            }
        });
        return view;
    }

    public void update() {
        usersList = db.getUsers();
        mAdapter = new UsersAdapter(getActivity(), usersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    public void updateList() {
        usersList = db.getUsers();
        mAdapter = new UsersAdapter(getActivity(), usersList);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(mAdapter);
    }

}
