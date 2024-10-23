package au.com.telstra.simcardactivator;

import org.springframework.http.ResponseEntity;

import javax.persistence.Entity;
import javax.persistence.*;

@Entity
public class SimActivationRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String iccid;

    private String email;

    private boolean active;

    // Constructors, Getters, Setters
    public SimActivationRecord() {
    }

    public SimActivationRecord(String iccid, String email, boolean active) {
        this.iccid = iccid;
        this.email = email;
        this.active = active;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
