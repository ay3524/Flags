
package ay3524.com.flags.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class WorldData {

    @SerializedName("worldpopulation")
    @Expose
    private ArrayList<Worldpopulation> worldpopulation = null;

    public ArrayList<Worldpopulation> getWorldpopulation() {
        return worldpopulation;
    }

}
