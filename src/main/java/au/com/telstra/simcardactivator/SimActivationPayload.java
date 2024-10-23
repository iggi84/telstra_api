package au.com.telstra.simcardactivator;

public class SimActivationPayload {
    private String iccid;
    private String email;

    public String getIccid() {
        return iccid;
    }

    public void setIccid(String iccid) {
        this.iccid = iccid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
