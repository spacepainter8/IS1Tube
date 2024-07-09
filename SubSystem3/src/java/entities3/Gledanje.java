/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities3;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author sofija
 */
@Entity
@Table(name = "gledanje")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Gledanje.findAll", query = "SELECT g FROM Gledanje g"),
    @NamedQuery(name = "Gledanje.findByIdGl", query = "SELECT g FROM Gledanje g WHERE g.idGl = :idGl"),
    @NamedQuery(name = "Gledanje.findByDatumVremePoc", query = "SELECT g FROM Gledanje g WHERE g.datumVremePoc = :datumVremePoc"),
    @NamedQuery(name = "Gledanje.findBySekundPoc", query = "SELECT g FROM Gledanje g WHERE g.sekundPoc = :sekundPoc"),
    @NamedQuery(name = "Gledanje.findByDuzinaGl", query = "SELECT g FROM Gledanje g WHERE g.duzinaGl = :duzinaGl")})
public class Gledanje implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdGl")
    private Integer idGl;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremePoc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePoc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SekundPoc")
    private int sekundPoc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DuzinaGl")
    private int duzinaGl;
    @JoinColumn(name = "IdK", referencedColumnName = "IdK")
    @ManyToOne(optional = false)
    private Korisnik idK;
    @JoinColumn(name = "IdV", referencedColumnName = "IdV")
    @ManyToOne(optional = false)
    private Video idV;

    public Gledanje() {
    }

    public Gledanje(Integer idGl) {
        this.idGl = idGl;
    }

    public Gledanje(Integer idGl, Date datumVremePoc, int sekundPoc, int duzinaGl) {
        this.idGl = idGl;
        this.datumVremePoc = datumVremePoc;
        this.sekundPoc = sekundPoc;
        this.duzinaGl = duzinaGl;
    }

    public Integer getIdGl() {
        return idGl;
    }

    public void setIdGl(Integer idGl) {
        this.idGl = idGl;
    }

    public Date getDatumVremePoc() {
        return datumVremePoc;
    }

    public void setDatumVremePoc(Date datumVremePoc) {
        this.datumVremePoc = datumVremePoc;
    }

    public int getSekundPoc() {
        return sekundPoc;
    }

    public void setSekundPoc(int sekundPoc) {
        this.sekundPoc = sekundPoc;
    }

    public int getDuzinaGl() {
        return duzinaGl;
    }

    public void setDuzinaGl(int duzinaGl) {
        this.duzinaGl = duzinaGl;
    }

    public Korisnik getIdK() {
        return idK;
    }

    public void setIdK(Korisnik idK) {
        this.idK = idK;
    }

    public Video getIdV() {
        return idV;
    }

    public void setIdV(Video idV) {
        this.idV = idV;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idGl != null ? idGl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Gledanje)) {
            return false;
        }
        Gledanje other = (Gledanje) object;
        if ((this.idGl == null && other.idGl != null) || (this.idGl != null && !this.idGl.equals(other.idGl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Gledanje{" +
                "idGl=" + idGl +
                ", datumVremePoc=" + datumVremePoc +
                ", sekundPoc=" + sekundPoc +
                ", duzinaGl=" + duzinaGl +
                ", Korisnik: " + (idK != null ? idK : "null") +
                ", Video: " + (idV != null ? idV : "null") +
                '}';
    }

    
}
