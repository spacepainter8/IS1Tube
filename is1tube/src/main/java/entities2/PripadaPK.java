/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities2;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author sofija
 */
@Embeddable
public class PripadaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "IdV")
    private int idV;
    @Basic(optional = false)
    @NotNull
    @Column(name = "IdKat")
    private int idKat;

    public PripadaPK() {
    }

    public PripadaPK(int idV, int idKat) {
        this.idV = idV;
        this.idKat = idKat;
    }

    public int getIdV() {
        return idV;
    }

    public void setIdV(int idV) {
        this.idV = idV;
    }

    public int getIdKat() {
        return idKat;
    }

    public void setIdKat(int idKat) {
        this.idKat = idKat;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) idV;
        hash += (int) idKat;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PripadaPK)) {
            return false;
        }
        PripadaPK other = (PripadaPK) object;
        if (this.idV != other.idV) {
            return false;
        }
        if (this.idKat != other.idKat) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities2.PripadaPK[ idV=" + idV + ", idKat=" + idKat + " ]";
    }
    
}
