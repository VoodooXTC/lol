package joss.jacobo.lol.lcs.api.model.LiveStreams;

import com.google.gson.annotations.SerializedName;

public class Thumbnails {

    @SerializedName("default")
    public Image _default;
    public Image medium;
    public Image high;
    public Image standard;
    public Image maxres;

    public Image getImage(){
        if(high != null){
            return high;
        }else if(medium != null){
            return medium;
        }else if(standard != null){
            return standard;
        }else if(_default != null){
            return _default;
        }else{
            return maxres;
        }
    }

}
