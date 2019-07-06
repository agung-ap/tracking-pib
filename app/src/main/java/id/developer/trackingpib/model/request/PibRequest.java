package id.developer.trackingpib.model.request;

public class PibRequest {
    private String no_pib;
    private String kpbc;
    private String nama_importir;
    private String nama_ppjk;
    private String status;
    private String deskripsi;

    public PibRequest() {
    }

    public PibRequest(String no_pib, String kpbc, String nama_importir, String nama_ppjk, String status, String deskripsi) {
        this.no_pib = no_pib;
        this.kpbc = kpbc;
        this.nama_importir = nama_importir;
        this.nama_ppjk = nama_ppjk;
        this.status = status;
        this.deskripsi = deskripsi;
    }

    public String getNo_pib() {
        return no_pib;
    }

    public void setNo_pib(String no_pib) {
        this.no_pib = no_pib;
    }

    public String getKpbc() {
        return kpbc;
    }

    public void setKpbc(String kpbc) {
        this.kpbc = kpbc;
    }

    public String getNama_importir() {
        return nama_importir;
    }

    public void setNama_importir(String nama_importir) {
        this.nama_importir = nama_importir;
    }

    public String getNama_ppjk() {
        return nama_ppjk;
    }

    public void setNama_ppjk(String nama_ppjk) {
        this.nama_ppjk = nama_ppjk;
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
}
