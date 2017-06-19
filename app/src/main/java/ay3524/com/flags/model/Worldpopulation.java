
package ay3524.com.flags.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Worldpopulation implements Parcelable {

    @SerializedName("rank")
    @Expose
    private int rank;
    @SerializedName("country")
    @Expose
    private String country;
    @SerializedName("population")
    @Expose
    private String population;
    @SerializedName("flag")
    @Expose
    private String flag;

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPopulation() {
        return population;
    }

    public void setPopulation(String population) {
        this.population = population;
    }

    public String getFlag() {
        return flag;
    }

    public void setFlag(String flag) {
        this.flag = flag;
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

    public Worldpopulation() {
    }

    protected Worldpopulation(Parcel in) {
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
