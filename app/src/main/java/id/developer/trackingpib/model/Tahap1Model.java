package id.developer.trackingpib.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Tahap1Model implements Parcelable {
    private int id;
    private String noPib;
    private String invoice;
    private String packingList;
    private String billOfLading;
    private String formE;

    public Tahap1Model() {
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

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getPackingList() {
        return packingList;
    }

    public void setPackingList(String packingList) {
        this.packingList = packingList;
    }

    public String getBillOfLading() {
        return billOfLading;
    }

    public void setBillOfLading(String billOfLading) {
        this.billOfLading = billOfLading;
    }

    public String getFormE() {
        return formE;
    }

    public void setFormE(String formE) {
        this.formE = formE;
    }

    public static Creator<Tahap1Model> getCREATOR() {
        return CREATOR;
    }

    protected Tahap1Model(Parcel in) {
        id = in.readInt();
        noPib = in.readString();
        invoice = in.readString();
        packingList = in.readString();
        billOfLading = in.readString();
        formE = in.readString();
    }

    public static final Creator<Tahap1Model> CREATOR = new Creator<Tahap1Model>() {
        @Override
        public Tahap1Model createFromParcel(Parcel in) {
            return new Tahap1Model(in);
        }

        @Override
        public Tahap1Model[] newArray(int size) {
            return new Tahap1Model[size];
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
        dest.writeString(invoice);
        dest.writeString(packingList);
        dest.writeString(billOfLading);
        dest.writeString(formE);
    }
}

