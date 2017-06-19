
package ay3524.com.flags.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import ay3524.com.flags.utils.Constants;

public class Worldpopulation implements Parcelable {

    @SerializedName(Constants.JSON_KEY_RANK)
    @Expose
    private int rank;
    @SerializedName(Constants.JSON_KEY_COUNTRY)
    @Expose
    private String country;
    @SerializedName(Constants.JSON_KEY_POPULATION)
    @Expose
    private String population;
    @SerializedName(Constants.JSON_KEY_FLAG)
    @Expose
    private String flag;

    public int getRank() {
        return rank;
    }


    public String getCountry() {
        return country;
    }


    public String getPopulation() {
        return population;
    }


    public String getFlag() {
        return flag;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.rank);
        dest.writeString(this.country);
        dest.writeString(this.population);
        dest.writeString(this.flag);
    }


    private Worldpopulation(Parcel in) {
        this.rank = in.readInt();
        this.country = in.readString();
        this.population = in.readString();
        this.flag = in.readString();
    }

    public static final Parcelable.Creator<Worldpopulation> CREATOR = new Parcelable.Creator<Worldpopulation>() {
        @Override
        public Worldpopulation createFromParcel(Parcel source) {
            return new Worldpopulation(source);
        }

        @Override
        public Worldpopulation[] newArray(int size) {
            return new Worldpopulation[size];
        }
    };
}
