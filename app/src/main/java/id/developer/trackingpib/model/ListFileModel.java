package id.developer.trackingpib.model;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;

import java.net.URI;

public class ListFileModel implements Parcelable {
    private String filename;
    private String filepath;
    private Uri uri;

    public ListFileModel() {
    }

    protected ListFileModel(Parcel in) {
        filename = in.readString();
        filepath = in.readString();
        uri = in.readParcelable(Uri.class.getClassLoader());
    }

    public static final Creator<ListFileModel> CREATOR = new Creator<ListFileModel>() {
        @Override
        public ListFileModel createFromParcel(Parcel in) {
            return new ListFileModel(in);
        }

        @Override
        public ListFileModel[] newArray(int size) {
            return new ListFileModel[size];
        }
    };

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getFilepath() {
        return filepath;
    }

    public void setFilepath(String filepath) {
        this.filepath = filepath;
    }

    public Uri getUri() {
        return uri;
    }

    public void setUri(Uri uri) {
        this.uri = uri;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(filename);
        dest.writeString(filepath);
        dest.writeParcelable(uri, flags);
    }
}
