package com.andrew.a40kscoreboard;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.fragment.NavHostFragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

public class FirstFragment extends Fragment {

    private SharedPreferences prefs;
    private SharedPreferences.Editor editor;

    private Button AddCPT1, SubtractCPT1, AddVPT1, SubtractVPT1;

    private Button AddCPT2, SubtractCPT2, AddVPT2,SubtractVPT2;

    private Button ResetButton;

    private EditText ChangeValue;
    private TextView Team1CP, Team1VP, Team2CP, Team2VP;
    private String IPAddress;
    private Switch OnlineToggle;

    private boolean OnlineOffline;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        prefs = getActivity().getSharedPreferences(MainActivity.SHARED_PREFS, Context.MODE_PRIVATE);
        editor = prefs.edit();

        TextView Team1 = getView().findViewById(R.id.Team1);
        Team1.setText(prefs.getString("Team1Name", "Team 1"));

        TextView Team2 = getView().findViewById(R.id.Team2);
        Team2.setText(prefs.getString("Team2Name", "Team 2"));
        OnlineToggle.setChecked(prefs.getBoolean("OnlineOffline", true));

        IPAddress = prefs.getString("IPAddress", "192.168.1.245");
        Team1CP.setText(prefs.getString("Team1CP", "0"));
        Team2CP.setText(prefs.getString("Team2CP", "0"));
        Team1VP.setText(prefs.getString("Team1VP", "0"));
        Team2VP.setText(prefs.getString("Team2VP", "0"));

        AddCPT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineOffline = OnlineToggle.isChecked();
                if(OnlineOffline){
                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    String Variable = ChangeValue.getText().toString();
                    String url = "http://" + IPAddress + "/addcp?team=1&cp=" + Variable;

                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,  url, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            updateScores(response);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            TextView ErrorText = getView().findViewById(R.id.ErrorText);
                            ErrorText.setText(error.getMessage());
                        }
                    });

                    queue.add(stringRequest);
                }else{
                    String current = prefs.getString("Team1CP", "0");
                    if(TextUtils.isEmpty(ChangeValue.getText())) ChangeValue.setText("0");
                    Team1CP.setText(String.valueOf(Integer.parseInt(current) + Integer.parseInt(ChangeValue.getText().toString())));
                    if(Integer.parseInt(Team1CP.getText().toString()) < 0) Team1CP.setText("0");
                    editor.putString("Team1CP", Team1CP.getText().toString());
                    editor.commit();
                    updateScores();
                }
            }
        });

        SubtractCPT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineOffline = OnlineToggle.isChecked();
                if(OnlineOffline){
                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    String Variable = ChangeValue.getText().toString();
                    String url = "http://" + IPAddress + "/removecp?team=1&cp=" + Variable;

                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,  url, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            updateScores(response);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            TextView ErrorText = getView().findViewById(R.id.ErrorText);
                            ErrorText.setText(error.getMessage());
                        }
                    });

                    queue.add(stringRequest);
                }else{
                    String current = prefs.getString("Team1CP", "0");
                    if(TextUtils.isEmpty(ChangeValue.getText())) ChangeValue.setText("0");
                    Team1CP.setText(String.valueOf(Integer.parseInt(current) - Integer.parseInt(ChangeValue.getText().toString())));
                    if(Integer.parseInt(Team1CP.getText().toString()) < 0) Team1CP.setText("0");
                    editor.putString("Team1CP", Team1CP.getText().toString());
                    editor.commit();
                    updateScores();
                }
            }
        });

        AddVPT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineOffline = OnlineToggle.isChecked();
                if(OnlineOffline){
                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    String Variable = ChangeValue.getText().toString();
                    String url = "http://" + IPAddress + "/addvp?team=1&vp=" + Variable;

                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,  url, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            updateScores(response);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            TextView ErrorText = getView().findViewById(R.id.ErrorText);
                            ErrorText.setText(error.getMessage());
                        }
                    });

                    queue.add(stringRequest);
                }else{
                    String current = prefs.getString("Team1VP", "0");
                    if(TextUtils.isEmpty(ChangeValue.getText())) ChangeValue.setText("0");
                    Team1VP.setText(String.valueOf(Integer.parseInt(current) + Integer.parseInt(ChangeValue.getText().toString())));
                    if(Integer.parseInt(Team1VP.getText().toString()) < 0) Team1VP.setText("0");
                    editor.putString("Team1VP", Team1VP.getText().toString());
                    editor.commit();
                    updateScores();
                }
            }
        });

        SubtractVPT1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineOffline = OnlineToggle.isChecked();
                if(OnlineOffline){
                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    String Variable = ChangeValue.getText().toString();
                    String url = "http://" + IPAddress + "/removevp?team=1&vp=" + Variable;

                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,  url, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            updateScores(response);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            TextView ErrorText = getView().findViewById(R.id.ErrorText);
                            ErrorText.setText(error.getMessage());
                        }
                    });

                    queue.add(stringRequest);
                }else{
                    String current = prefs.getString("Team1VP", "0");
                    if(TextUtils.isEmpty(ChangeValue.getText())) ChangeValue.setText("0");
                    Team1VP.setText(String.valueOf(Integer.parseInt(current) - Integer.parseInt(ChangeValue.getText().toString())));
                    if(Integer.parseInt(Team1VP.getText().toString()) < 0) Team1VP.setText("0");
                    editor.putString("Team1VP", Team1VP.getText().toString());
                    editor.commit();
                    updateScores();
                }


            }
        });

        AddCPT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineOffline = OnlineToggle.isChecked();
                if(OnlineOffline){
                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    String Variable = ChangeValue.getText().toString();
                    String url = "http://" + IPAddress + "/addcp?team=2&cp=" + Variable;

                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,  url, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            updateScores(response);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            TextView ErrorText = getView().findViewById(R.id.ErrorText);
                            ErrorText.setText(error.getMessage());
                        }
                    });

                    queue.add(stringRequest);
                }else{
                    String current = prefs.getString("Team2CP", "0");
                    if(TextUtils.isEmpty(ChangeValue.getText())) ChangeValue.setText("0");
                    Team2CP.setText(String.valueOf(Integer.parseInt(current) + Integer.parseInt(ChangeValue.getText().toString())));
                    if(Integer.parseInt(Team2CP.getText().toString()) < 0) Team2CP.setText("0");
                    editor.putString("Team2CP", Team2CP.getText().toString());
                    editor.commit();
                    updateScores();
                }


            }
        });

        SubtractCPT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineOffline = OnlineToggle.isChecked();
                if(OnlineOffline){
                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    String Variable = ChangeValue.getText().toString();
                    String url = "http://" + IPAddress + "/removecp?team=2&cp=" + Variable;

                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,  url, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            updateScores(response);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            TextView ErrorText = getView().findViewById(R.id.ErrorText);
                            ErrorText.setText(error.getMessage());
                        }
                    });

                    queue.add(stringRequest);
                }else{
                    String current = prefs.getString("Team2CP", "0");
                    if(TextUtils.isEmpty(ChangeValue.getText())) ChangeValue.setText("0");
                    Team2CP.setText(String.valueOf(Integer.parseInt(current) - Integer.parseInt(ChangeValue.getText().toString())));
                    if(Integer.parseInt(Team2CP.getText().toString()) < 0) Team2CP.setText("0");
                    editor.putString("Team2CP", Team2CP.getText().toString());
                    editor.commit();
                    updateScores();
                }


            }
        });

        AddVPT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineOffline = OnlineToggle.isChecked();
                if(OnlineOffline){
                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    String Variable = ChangeValue.getText().toString();
                    String url = "http://" + IPAddress + "/addvp?team=2&vp=" + Variable;

                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,  url, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            updateScores(response);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            TextView ErrorText = getView().findViewById(R.id.ErrorText);
                            ErrorText.setText(error.getMessage());
                        }
                    });

                    queue.add(stringRequest);
                }else{
                    String current = prefs.getString("Team2VP", "0");
                    if(TextUtils.isEmpty(ChangeValue.getText())) ChangeValue.setText("0");
                    Team2VP.setText(String.valueOf(Integer.parseInt(current) + Integer.parseInt(ChangeValue.getText().toString())));
                    if(Integer.parseInt(Team2VP.getText().toString()) < 0) Team2VP.setText("0");
                    editor.putString("Team2VP", Team2VP.getText().toString());
                    editor.commit();
                    updateScores();
                }


            }
        });

        SubtractVPT2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OnlineOffline = OnlineToggle.isChecked();
                if(OnlineOffline){

                    RequestQueue queue = Volley.newRequestQueue(getActivity());

                    String Variable = ChangeValue.getText().toString();
                    String url = "http://" + IPAddress + "/removevp?team=2&vp=" + Variable;

                    JsonObjectRequest stringRequest = new JsonObjectRequest(Request.Method.GET,  url, null, new Response.Listener<JSONObject>(){
                        @Override
                        public void onResponse(JSONObject response) {
                            updateScores(response);
                        }
                    }, new Response.ErrorListener(){
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            TextView ErrorText = getView().findViewById(R.id.ErrorText);
                            ErrorText.setText(error.getMessage());
                        }
                    });

                    queue.add(stringRequest);

                }else{
                    String current = prefs.getString("Team2VP", "0");
                    if(TextUtils.isEmpty(ChangeValue.getText())) ChangeValue.setText("0");
                    Team2VP.setText(String.valueOf(Integer.parseInt(current) - Integer.parseInt(ChangeValue.getText().toString())));
                    if(Integer.parseInt(Team2VP.getText().toString()) < 0) Team2VP.setText("0");
                    editor.putString("Team2VP", Team2VP.getText().toString());
                    editor.commit();
                    updateScores();
                }
            }
        });

        OnlineToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                editor.putBoolean("OnlineOffline", b);
                editor.commit();
            }
        });

        ResetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                editor.putString("Team1CP", "0");
                editor.putString("Team2CP", "0");
                editor.putString("Team1VP", "0");
                editor.putString("Team2VP", "0");
                editor.commit();
                updateScores();
            }
        });
    }

    private void updateScores(JSONObject scores){
        try{
            Team1CP.setText(scores.getString("Team1CP"));
            Team1VP.setText(scores.getString("Team1VP"));
            Team2CP.setText(scores.getString("Team2CP"));
            Team2VP.setText(scores.getString("Team2VP"));
        }
        catch(Exception e){
            TextView ErrorText = getView().findViewById(R.id.ErrorText);
            ErrorText.setText(e.getMessage());
        }
    }

    private void updateScores(){
        Team1CP.setText(prefs.getString("Team1CP", "0"));
        Team1VP.setText(prefs.getString("Team1VP", "0"));
        Team2CP.setText(prefs.getString("Team2CP", "0"));
        Team2VP.setText(prefs.getString("Team2VP", "0"));
    }

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_first, container, false);
    }

    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        AddCPT1 = view.findViewById(R.id.AddCPT1);
        SubtractCPT1 = view.findViewById(R.id.SubtractCPT1);
        AddVPT1 = view.findViewById(R.id.AddVPT1);
        SubtractVPT1 = view.findViewById(R.id.SubtractVPT1);

        AddCPT2 = view.findViewById(R.id.AddCPT2);
        SubtractCPT2 = view.findViewById(R.id.SubtractCPT2);
        AddVPT2 = view.findViewById(R.id.AddVPT2);
        SubtractVPT2 = view.findViewById(R.id.SubtractVPT2);

        ChangeValue = view.findViewById(R.id.editTextNumber);

        Team1CP = view.findViewById(R.id.Team1CPV);
        Team1VP = view.findViewById(R.id.Team1VPV);
        Team2CP = view.findViewById(R.id.Team2CPV);
        Team2VP = view.findViewById(R.id.Team2VPV);

        ResetButton = view.findViewById(R.id.ResetButton);

        OnlineToggle = view.findViewById(R.id.OnlineToggle);

        view.findViewById(R.id.button_first).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                NavHostFragment.findNavController(FirstFragment.this)
                        .navigate(R.id.action_FirstFragment_to_ipFragment);
            }
        });
    }
}