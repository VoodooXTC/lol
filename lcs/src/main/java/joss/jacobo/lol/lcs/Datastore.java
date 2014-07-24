package joss.jacobo.lol.lcs;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import javax.inject.Singleton;

@Singleton
public class Datastore {

    private static final String DEVICE_VERSION = "DeviceVersion";
    private static final String SELECTED_TOURNAMENT = "selected_tournament";

    EncryptedSharedPreferences encryptedSharedPreferences;

    public Datastore(Application app) {
        encryptedSharedPreferences = new EncryptedSharedPreferences(app,
                PreferenceManager.getDefaultSharedPreferences(app));
    }

    private SharedPreferences.Editor getEditor() {
        return encryptedSharedPreferences.edit();
    }

    private SharedPreferences getPrefs() {
        return encryptedSharedPreferences;
    }
    public int getVersion() {
        return getPrefs().getInt(DEVICE_VERSION, 0);
    }
    public void persistVersion(int version) {
        getEditor().putInt(DEVICE_VERSION, version).commit();
    }

    public int getSelectedTournament(){
        return getPrefs().getInt(SELECTED_TOURNAMENT, -1);
    }

    public void persistSelectedTeam(int teamId){
        getEditor().putInt(SELECTED_TOURNAMENT, teamId).commit();
    }

}

