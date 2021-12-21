package com.andrew.a40kscoreboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link IpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class IpFragment extends Fragment {

    private String IpAddress;
    private String Team1Name;
    private String Team2Name;
    private EditText EditIP;
    private EditText Team1Edit;
    private EditText Team2Edit;

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    public IpFragment() {
        // Required empty public constructor
    }

    public static IpFragment newInstance() {
        IpFragment fragment = new IpFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        prefs = getActivity().getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE);
        editor = prefs.edit();

        if(prefs != null){
            IpAddress = prefs.getString("IPAddress", "");
            Team1Name = prefs.getString("Team1Name", "");
            Team2Name = prefs.getString("Team2Name", "");
        }

        if(IpAddress != null) EditIP.setText(IpAddress);
        if(Team1Name != null) Team1Edit.setText(Team1Name);
        if(Team2Name != null) Team2Edit.setText(Team2Name);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_ip, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        EditIP = getView().findViewById(R.id.IpAddressEdit);
        Team1Edit = getView().findViewById(R.id.Team1NameEdit);
        Team2Edit = getView().findViewById(R.id.Team2NameEdit);

        view.findViewById(R.id.SaveButton).setOnClickListener(this::SaveButtonOnClick);
    }

    public void SaveButtonOnClick(View view){

        IpAddress = EditIP.getText().toString();
        Team1Name = Team1Edit.getText().toString();
        Team2Name = Team2Edit.getText().toString();
        NavHostFragment.findNavController(IpFragment.this).navigate(R.id.action_ipFragment_to_FirstFragment);
        editor.putString("IPAddress", IpAddress);
        editor.putString("Team1Name", Team1Name);
        editor.putString("Team2Name", Team2Name);

        editor.commit();
    }

}