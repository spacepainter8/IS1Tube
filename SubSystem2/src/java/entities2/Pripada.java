/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities2;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sofija
 */
@Entity
@Table(name = "pripada")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pripada.findAll", query = "SELECT p FROM Pripada p"),
    @NamedQuery(name = "Pripada.findByIdV", query = "SELECT p FROM Pripada p WHERE p.pripadaPK.idV = :idV"),
    @NamedQuery(name = "Pripada.findByIdKat", query = "SELECT p FROM Pripada p WHERE p.pripadaPK.idKat = :idKat")})
public class Pripada implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PripadaPK pripadaPK;

    public Pripada() {
    }

    public Pripada(PripadaPK pripadaPK) {
        this.pripadaPK = pripadaPK;
    }

    public Pripada(int idV, int idKat) {
        this.pripadaPK = new PripadaPK(idV, idKat);
    }

    public PripadaPK getPripadaPK() {
        return pripadaPK;
    }

    public void setPripadaPK(PripadaPK pripadaPK) {
        this.pripadaPK = pripadaPK;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (pripadaPK != null ? pripadaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pripada)) {
            return false;
        }
        Pripada other = (Pripada) object;
        if ((this.pripadaPK == null && other.pripadaPK != null) || (this.pripadaPK != null && !this.pripadaPK.equals(other.pripadaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities2.Pripada[ pripadaPK=" + pripadaPK + " ]";
    }
    
}
