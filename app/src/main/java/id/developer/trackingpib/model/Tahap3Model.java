package id.developer.trackingpib.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tahap3Model implements Parcelable {
    private int id;
    private String noPib;
    private String hargaPajak;

    public Tahap3Model() {
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

    public String getHargaPajak() {
        return hargaPajak;
    }

    public void setHargaPajak(String hargaPajak) {
        this.hargaPajak = hargaPajak;
    }

    public static Creator<Tahap3Model> getCREATOR() {
        return CREATOR;
    }

    protected Tahap3Model(Parcel in) {
        id = in.readInt();
        noPib = in.readString();
        hargaPajak = in.readString();
    }

    public static final Creator<Tahap3Model> CREATOR = new Creator<Tahap3Model>() {
        @Override
        public Tahap3Model createFromParcel(Parcel in) {
            return new Tahap3Model(in);
        }

        @Override
        public Tahap3Model[] newArray(int size) {
            return new Tahap3Model[size];
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
        dest.writeString(hargaPajak);
    }
}

