package com.andrew.a40kscoreboard;

import org.json.JSONObject;

public class JsonReturnObject extends JSONObject {

    public String Team1CP;
    public String Team1VP;
    public String Team2CP;
    public String Team2VP;

    public String getTeam1CP() {
        return Team1CP;
    }

    public String getTeam1VP() {
        return Team1VP;
    }

    public String getTeam2CP() {
        return Team2CP;
    }

    public String getTeam2VP() {
        return Team2VP;
    }

    public void setTeam1CP(String team1CP) {
        Team1CP = team1CP;
    }

    public void setTeam1VP(String team1VP) {
        Team1VP = team1VP;
    }

    public void setTeam2CP(String team2CP) {
        Team2CP = team2CP;
    }

    public void setTeam2VP(String team2VP) {
        Team2VP = team2VP;
    }
}
