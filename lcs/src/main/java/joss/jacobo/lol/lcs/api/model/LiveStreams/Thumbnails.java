package joss.jacobo.lol.lcs.api.model.LiveStreams;

import com.google.gson.annotations.SerializedName;

public class Thumbnails {

    @SerializedName("default")
    public Image _default;
    public Image medium;
    public Image high;
    public Image standard;
    public Image maxres;

}
