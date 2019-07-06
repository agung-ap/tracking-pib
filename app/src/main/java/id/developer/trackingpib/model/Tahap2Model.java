package id.developer.trackingpib.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tahap2Model implements Parcelable {
    private int id;
    private String noPib;
    private String status;

    public Tahap2Model() {
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

    public static Creator<Tahap2Model> getCREATOR() {
        return CREATOR;
    }

    protected Tahap2Model(Parcel in) {
        id = in.readInt();
        noPib = in.readString();
        status = in.readString();
    }

    public static final Creator<Tahap2Model> CREATOR = new Creator<Tahap2Model>() {
        @Override
        public Tahap2Model createFromParcel(Parcel in) {
            return new Tahap2Model(in);
        }

        @Override
        public Tahap2Model[] newArray(int size) {
            return new Tahap2Model[size];
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

