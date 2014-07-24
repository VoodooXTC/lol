package joss.jacobo.lol.lcs.api;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import java.util.List;

import joss.jacobo.lol.lcs.api.model.Config;
import joss.jacobo.lol.lcs.api.model.Standings.Standings;
import joss.jacobo.lol.lcs.api.model.Tournament;
import joss.jacobo.lol.lcs.provider.matches.MatchesColumns;
import joss.jacobo.lol.lcs.provider.matches.MatchesContentValues;
import joss.jacobo.lol.lcs.provider.standings.StandingsColumns;
import joss.jacobo.lol.lcs.provider.standings.StandingsContentValues;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsColumns;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsContentValues;
import joss.jacobo.lol.lcs.provider.teams.TeamsColumns;
import joss.jacobo.lol.lcs.provider.teams.TeamsContentValues;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsColumns;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsContentValues;
import retrofit.Callback;
import retrofit.RestAdapter;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class ApiService extends IntentService {
    private static final String TAG = "ApiService";
    public static final String API_TYPE = "api_type";

    public static final int TYPE_INITIAL_CONFIG = 0;
    public static final int TYPE_LATEST_STANDINGS = 1;

    RestService service;

    public ApiService() {
        super("Api Service");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        RestAdapter restAdapter = new RestAdapter.Builder()
            .setEndpoint("http://lcs.voodootvdb.com")
            .build();
        service = restAdapter.create(RestService.class);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        switch(intent.getExtras().getInt(API_TYPE)){
            case TYPE_INITIAL_CONFIG:
                service.getInitialConfig(new Callback<Config>() {
                    @Override
                    public void success(Config config, Response response) {
                        updateInitialConfig(getContentResolver(), config);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                break;

            case TYPE_LATEST_STANDINGS:
                service.getLatestStandings(new Callback<Standings>() {
                    @Override
                    public void success(Standings standings, Response response) {
                        updateLatestStandings(getContentResolver(), standings);
                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
                break;
        }
    }

    public static void getInitialConfig(Context context){
        Intent intent = new Intent(context, ApiService.class);
        intent.putExtra(ApiService.API_TYPE, ApiService.TYPE_INITIAL_CONFIG);
        context.startService(intent);
    }

    public static void getLatestStandings(Context context){
        Intent intent = new Intent(context, ApiService.class);
        intent.putExtra(ApiService.API_TYPE, ApiService.TYPE_LATEST_STANDINGS);
        context.startService(intent);
    }

    public static void updateInitialConfig(ContentResolver contentResolver, Config initalConfig) {

        contentResolver.delete(TournamentsColumns.CONTENT_URI, null, null);
        ContentValues[] tournamentCV = TournamentsContentValues.getContentValues(initalConfig.getTournaments());
        contentResolver.bulkInsert(TournamentsColumns.CONTENT_URI, tournamentCV);

        contentResolver.delete(TeamDetailsColumns.CONTENT_URI, null, null);
        ContentValues[] teamDetailsCV = TeamDetailsContentValues.getContentValues(initalConfig.getTeamDetails());
        contentResolver.bulkInsert(TeamDetailsColumns.CONTENT_URI, teamDetailsCV);

        contentResolver.delete(TeamsColumns.CONTENT_URI, null, null);
        ContentValues[] teamsCV= TeamsContentValues.getContentValues(initalConfig.getTeams());
        contentResolver.bulkInsert(TeamsColumns.CONTENT_URI, teamsCV);

        contentResolver.delete(MatchesColumns.CONTENT_URI, null, null);
        ContentValues[] matchesCV = MatchesContentValues.getContentValues(initalConfig.getMatches());
        contentResolver.bulkInsert(MatchesColumns.CONTENT_URI, matchesCV);
    }

    public static void updateLatestStandings(ContentResolver contentResolver, Standings standings) {
        contentResolver.delete(StandingsColumns.CONTENT_URI, null, null);
        ContentValues[] standingsCV = StandingsContentValues.getContentValues(standings.getList());
        contentResolver.bulkInsert(StandingsColumns.CONTENT_URI, standingsCV);
    }

}