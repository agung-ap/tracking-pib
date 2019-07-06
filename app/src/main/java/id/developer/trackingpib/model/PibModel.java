package id.developer.trackingpib.model;

import android.os.Parcel;
import android.os.Parcelable;

public class PibModel implements Parcelable {
    private int id;
    private String noPib;
    private String kpbc;
    private String namaImportir;
    private String namaPpjk;
    private String status;
    private String deskripsi;

    public PibModel() {
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

    public String getKpbc() {
        return kpbc;
    }

    public void setKpbc(String kpbc) {
        this.kpbc = kpbc;
    }

    public String getNamaImportir() {
        return namaImportir;
    }

    public void setNamaImportir(String namaImportir) {
        this.namaImportir = namaImportir;
    }

    public String getNamaPpjk() {
        return namaPpjk;
    }

    public void setNamaPpjk(String namaPpjk) {
        this.namaPpjk = namaPpjk;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public static Creator<PibModel> getCREATOR() {
        return CREATOR;
    }

    protected PibModel(Parcel in) {
        id = in.readInt();
        noPib = in.readString();
        kpbc = in.readString();
        namaImportir = in.readString();
        namaPpjk = in.readString();
        status = in.readString();
        deskripsi = in.readString();
    }

    public static final Creator<PibModel> CREATOR = new Creator<PibModel>() {
        @Override
        public PibModel createFromParcel(Parcel in) {
            return new PibModel(in);
        }

        @Override
        public PibModel[] newArray(int size) {
            return new PibModel[size];
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
        dest.writeString(kpbc);
        dest.writeString(namaImportir);
        dest.writeString(namaPpjk);
        dest.writeString(status);
        dest.writeString(deskripsi);
    }
}
