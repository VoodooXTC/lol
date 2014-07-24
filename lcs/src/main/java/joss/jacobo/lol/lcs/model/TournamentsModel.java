package joss.jacobo.lol.lcs.model;

import joss.jacobo.lol.lcs.api.model.Tournament;
import joss.jacobo.lol.lcs.provider.tournaments.TournamentsCursor;
import com.google.gson.annotations.SerializedName;

/**
 * Model object for the {@code tournaments}.
 */
public class TournamentsModel{
    public Integer tournamentId;
    public String abrev;
    public String name;
    public Integer year;
    public String season;
    public Integer ongoing;

    public TournamentsModel(){}

    public TournamentsModel(TournamentsCursor cursor){
        this.tournamentId = cursor.getTournamentId();
        this.abrev = cursor.getAbrev();
        this.name = cursor.getName();
        this.year = cursor.getYear();
        this.season = cursor.getSeason();
        this.ongoing = cursor.getOngoing();
    }

    public TournamentsModel(Tournament tournament) {
        this.tournamentId = tournament.id;
        this.abrev = tournament.abrev;
        this.name = tournament.name;
        this.year = tournament.year;
        this.season = tournament.season;
        this.ongoing = tournament.ongoing;
    }
}