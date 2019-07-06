package id.developer.trackingpib.model.request;

public class Tahap1Request {
    private String no_pib;
    private String invoice;
    private String packing_list;
    private String bill_of_lading;
    private String form_e;

    public String getNo_pib() {
        return no_pib;
    }

    public void setNo_pib(String no_pib) {
        this.no_pib = no_pib;
    }

    public String getInvoice() {
        return invoice;
    }

    public void setInvoice(String invoice) {
        this.invoice = invoice;
    }

    public String getPacking_list() {
        return packing_list;
    }

    public void setPacking_list(String packing_list) {
        this.packing_list = packing_list;
    }

    public String getBill_of_lading() {
        return bill_of_lading;
    }

    public void setBill_of_lading(String bill_of_lading) {
        this.bill_of_lading = bill_of_lading;
    }

    public String getForm_e() {
        return form_e;
    }

    public void setForm_e(String form_e) {
        this.form_e = form_e;
    }
}

