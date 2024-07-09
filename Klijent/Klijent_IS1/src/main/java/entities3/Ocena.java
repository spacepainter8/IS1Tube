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
@Table(name = "ocena")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Ocena.findAll", query = "SELECT o FROM Ocena o"),
    @NamedQuery(name = "Ocena.findByIdO", query = "SELECT o FROM Ocena o WHERE o.idO = :idO"),
    @NamedQuery(name = "Ocena.findByOcena", query = "SELECT o FROM Ocena o WHERE o.ocena = :ocena"),
    @NamedQuery(name = "Ocena.findByDatumVreme", query = "SELECT o FROM Ocena o WHERE o.datumVreme = :datumVreme")})
public class Ocena implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdO")
    private Integer idO;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ocena")
    private int ocena;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVreme")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVreme;
    @JoinColumn(name = "IdK", referencedColumnName = "IdK")
    @ManyToOne(optional = false)
    private Korisnik idK;
    @JoinColumn(name = "IdV", referencedColumnName = "IdV")
    @ManyToOne(optional = false)
    private Video idV;

    public Ocena() {
    }

    public Ocena(Integer idO) {
        this.idO = idO;
    }

    public Ocena(Integer idO, int ocena, Date datumVreme) {
        this.idO = idO;
        this.ocena = ocena;
        this.datumVreme = datumVreme;
    }

    public Integer getIdO() {
        return idO;
    }

    public void setIdO(Integer idO) {
        this.idO = idO;
    }

    public int getOcena() {
        return ocena;
    }

    public void setOcena(int ocena) {
        this.ocena = ocena;
    }

    public Date getDatumVreme() {
        return datumVreme;
    }

    public void setDatumVreme(Date datumVreme) {
        this.datumVreme = datumVreme;
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
        hash += (idO != null ? idO.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Ocena)) {
            return false;
        }
        Ocena other = (Ocena) object;
        if ((this.idO == null && other.idO != null) || (this.idO != null && !this.idO.equals(other.idO))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Ocena{" +
                "idO=" + idO +
                ", ocena=" + ocena +
                ", datumVreme=" + datumVreme +
                ", Korisnik: " + (idK != null ? idK : "null") +
                ", Video: " + (idV != null ? idV : "null") +
                '}';
    }
    
}
