package joss.jacobo.lol.lcs.model;

import joss.jacobo.lol.lcs.api.model.Schedule;
import joss.jacobo.lol.lcs.provider.matches.MatchesCursor;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for the {@code matches}.
 */
public class MatchesModel{
    public Integer matchId;
    public Integer tournamentId;
    public String tournamentAbrev;
    public String split;
    public String datetime;
    public Integer week;
    public Integer day;
    public String date;
    public Integer team1Id;
    public Integer team2Id;
    public String team1;
    public String team2;
    public String time;
    public Integer result;
    public Integer played;
    public Integer matchNo;
    public Integer matchPosition;

    public MatchesModel(){}

    public MatchesModel(MatchesCursor cursor){
        this.matchId = cursor.getMatchId();
        this.tournamentId = cursor.getTournamentId();
        this.tournamentAbrev = cursor.getTournamentAbrev();
        this.split = cursor.getSplit();
        this.datetime = cursor.getDatetime();
        this.week = cursor.getWeek();
        this.day = cursor.getDay();
        this.date = cursor.getDate();
        this.team1Id = cursor.getTeam1Id();
        this.team2Id = cursor.getTeam2Id();
        this.team1 = cursor.getTeam1();
        this.team2 = cursor.getTeam2();
        this.time = cursor.getTime();
        this.result = cursor.getResult();
        this.played = cursor.getPlayed();
        this.matchNo = cursor.getMatchNo();
        this.matchPosition = cursor.getMatchPosition();
    }

    public MatchesModel(Schedule match) {
        this.matchId = match.id;
        this.tournamentId = match.tournamentId;
        this.tournamentAbrev = match.league;
        this.split = match.split;
        this.datetime = match.datetime;
        this.week = match.week;
        this.day = match.day;
        this.date = match.date;
        this.team1Id = match.team1Id;
        this.team2Id = match.team2Id;
        this.team1 = match.team1;
        this.team2 = match.team2;
        this.time = match.time;
        this.result = match.result;
        this.played = match.played;
        this.matchNo = match.matchNo;
        this.matchPosition = match.position;
    }
}