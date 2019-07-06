package id.developer.trackingpib.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tahap4Model implements Parcelable {
    private int id;
    private String noPib;
    private String status;

    public Tahap4Model() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNoPib() {
        return noPib;
    }

    public void setNoPib(String noPib) {
        this.noPib = noPib;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static Creator<Tahap4Model> getCREATOR() {
        return CREATOR;
    }

    protected Tahap4Model(Parcel in) {
        id = in.readInt();
        noPib = in.readString();
        status = in.readString();
    }

    public static final Creator<Tahap4Model> CREATOR = new Creator<Tahap4Model>() {
        @Override
        public Tahap4Model createFromParcel(Parcel in) {
            return new Tahap4Model(in);
        }

        @Override
        public Tahap4Model[] newArray(int size) {
            return new Tahap4Model[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(noPib);
        dest.writeString(status);
    }
}

