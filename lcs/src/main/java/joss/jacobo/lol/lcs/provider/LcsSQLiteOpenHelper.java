package joss.jacobo.lol.lcs.provider;

import android.annotation.TargetApi;
import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.DefaultDatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import joss.jacobo.lol.lcs.BuildConfig;
import joss.jacobo.lol.lcs.provider.matches.MatchesColumns;
import joss.jacobo.lol.lcs.provider.standings.StandingsColumns;
import joss.jacobo.lol.lcs.provider.team_details.TeamDetailsColumns;
import joss.jacobo.lol.lcs.provider.teams.TeamsColumns;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsColumns;

public class LcsSQLiteOpenHelper extends SQLiteOpenHelper {
    private static final String TAG = LcsSQLiteOpenHelper.class.getSimpleName();

    public static final String DATABASE_FILE_NAME = "lcs.db";
    private static final int DATABASE_VERSION = 1;

    // @formatter:off
    private static final String SQL_CREATE_TABLE_MATCHES = "CREATE TABLE IF NOT EXISTS "
            + MatchesColumns.TABLE_NAME + " ( "
            + MatchesColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + MatchesColumns.MATCH_ID + " INTEGER, "
            + MatchesColumns.TOURNAMENT_ID + " INTEGER, "
            + MatchesColumns.TOURNAMENT_ABREV + " TEXT, "
            + MatchesColumns.SPLIT + " TEXT, "
            + MatchesColumns.DATETIME + " TEXT, "
            + MatchesColumns.WEEK + " INTEGER, "
            + MatchesColumns.DAY + " INTEGER, "
            + MatchesColumns.DATE + " TEXT, "
            + MatchesColumns.TEAM1_ID + " INTEGER, "
            + MatchesColumns.TEAM2_ID + " INTEGER, "
            + MatchesColumns.TEAM1 + " TEXT, "
            + MatchesColumns.TEAM2 + " TEXT, "
            + MatchesColumns.TIME + " TEXT, "
            + MatchesColumns.RESULT + " INTEGER, "
            + MatchesColumns.PLAYED + " INTEGER, "
            + MatchesColumns.MATCH_NO + " INTEGER, "
            + MatchesColumns.MATCH_POSITION + " INTEGER "
            + ", CONSTRAINT MATCH_ID UNIQUE (MATCH_ID) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_STANDINGS = "CREATE TABLE IF NOT EXISTS "
            + StandingsColumns.TABLE_NAME + " ( "
            + StandingsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + StandingsColumns.STANDING_ID + " INTEGER, "
            + StandingsColumns.TOURNAMENT_ABREV + " TEXT, "
            + StandingsColumns.STANDING_WEEK + " INTEGER, "
            + StandingsColumns.TEAM_NAME + " TEXT, "
            + StandingsColumns.TEAM_ABREV + " TEXT, "
            + StandingsColumns.WINS + " INTEGER, "
            + StandingsColumns.LOSSES + " INTEGER, "
            + StandingsColumns.DELTA + " INTEGER, "
            + StandingsColumns.STANDING_POSITION + " INTEGER "
            + " );";

    private static final String SQL_CREATE_TABLE_TEAM_DETAILS = "CREATE TABLE IF NOT EXISTS "
            + TeamDetailsColumns.TABLE_NAME + " ( "
            + TeamDetailsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TeamDetailsColumns.TEAM_ID + " INTEGER, "
            + TeamDetailsColumns.ABREV + " TEXT, "
            + TeamDetailsColumns.NAME + " TEXT, "
            + TeamDetailsColumns.LOGO + " TEXT, "
            + TeamDetailsColumns.TEAM_PICTURE + " TEXT "
            + ", CONSTRAINT TEAM_ID UNIQUE (TEAM_ID) ON CONFLICT REPLACE"
            + " );";

    private static final String SQL_CREATE_TABLE_TEAMS = "CREATE TABLE IF NOT EXISTS "
            + TeamsColumns.TABLE_NAME + " ( "
            + TeamsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TeamsColumns.TEAM_ID + " INTEGER, "
            + TeamsColumns.TEAM_ABREV + " TEXT, "
            + TeamsColumns.TOURNAMENT_ID + " INTEGER, "
            + TeamsColumns.TOURNAMENT_ABREV + " TEXT, "
            + TeamsColumns.TEAM_NAME + " TEXT "
            + " );";

    private static final String SQL_CREATE_TABLE_TOURNAMENTS = "CREATE TABLE IF NOT EXISTS "
            + TournamentsColumns.TABLE_NAME + " ( "
            + TournamentsColumns._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + TournamentsColumns.TOURNAMENT_ID + " INTEGER, "
            + TournamentsColumns.ABREV + " TEXT, "
            + TournamentsColumns.NAME + " TEXT, "
            + TournamentsColumns.YEAR + " INTEGER, "
            + TournamentsColumns.SEASON + " TEXT, "
            + TournamentsColumns.ONGOING + " INTEGER "
            + ", CONSTRAINT TOURNAMENT_ID UNIQUE (TOURNAMENT_ID) ON CONFLICT REPLACE"
            + " );";

    // @formatter:on

    public static LcsSQLiteOpenHelper newInstance(Context context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.HONEYCOMB) {
            return newInstancePreHoneycomb(context);
        }
        return newInstancePostHoneycomb(context);
    }


    /*
     * Pre Honeycomb.
     */

    private static LcsSQLiteOpenHelper newInstancePreHoneycomb(Context context) {
        return new LcsSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION);
    }

    private LcsSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    /*
     * Post Honeycomb.
     */

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private static LcsSQLiteOpenHelper newInstancePostHoneycomb(Context context) {
        return new LcsSQLiteOpenHelper(context, DATABASE_FILE_NAME, null, DATABASE_VERSION, new DefaultDatabaseErrorHandler());
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB)
    private LcsSQLiteOpenHelper(Context context, String name, CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        if (BuildConfig.DEBUG) Log.d(TAG, "onCreate");
        db.execSQL(SQL_CREATE_TABLE_MATCHES);
        db.execSQL(SQL_CREATE_TABLE_STANDINGS);
        db.execSQL(SQL_CREATE_TABLE_TEAM_DETAILS);
        db.execSQL(SQL_CREATE_TABLE_TEAMS);
        db.execSQL(SQL_CREATE_TABLE_TOURNAMENTS);
    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
        if (!db.isReadOnly()) {
            db.execSQL("PRAGMA foreign_keys=ON;");
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (BuildConfig.DEBUG) Log.d(TAG, "Upgrading database from version " + oldVersion + " to " + newVersion);
    }
}
