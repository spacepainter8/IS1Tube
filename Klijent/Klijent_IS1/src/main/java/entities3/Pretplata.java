/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities3;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "pretplata")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Pretplata.findAll", query = "SELECT p FROM Pretplata p"),
    @NamedQuery(name = "Pretplata.findByIdPret", query = "SELECT p FROM Pretplata p WHERE p.idPret = :idPret"),
    @NamedQuery(name = "Pretplata.findByDatumVremePoc", query = "SELECT p FROM Pretplata p WHERE p.datumVremePoc = :datumVremePoc"),
    @NamedQuery(name = "Pretplata.findByCena", query = "SELECT p FROM Pretplata p WHERE p.cena = :cena")})
public class Pretplata implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdPret")
    private Integer idPret;
    @Basic(optional = false)
    @NotNull
    @Column(name = "DatumVremePoc")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datumVremePoc;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Basic(optional = false)
    @NotNull
    @Column(name = "Cena")
    private BigDecimal cena;
    @JoinColumn(name = "IdK", referencedColumnName = "IdK")
    @ManyToOne(optional = false)
    private Korisnik idK;
    @JoinColumn(name = "IdP", referencedColumnName = "IdP")
    @ManyToOne(optional = false)
    private Paket idP;

    public Pretplata() {
    }

    public Pretplata(Integer idPret) {
        this.idPret = idPret;
    }

    public Pretplata(Integer idPret, Date datumVremePoc, BigDecimal cena) {
        this.idPret = idPret;
        this.datumVremePoc = datumVremePoc;
        this.cena = cena;
    }

    public Integer getIdPret() {
        return idPret;
    }

    public void setIdPret(Integer idPret) {
        this.idPret = idPret;
    }

    public Date getDatumVremePoc() {
        return datumVremePoc;
    }

    public void setDatumVremePoc(Date datumVremePoc) {
        this.datumVremePoc = datumVremePoc;
    }

    public BigDecimal getCena() {
        return cena;
    }

    public void setCena(BigDecimal cena) {
        this.cena = cena;
    }

    public Korisnik getIdK() {
        return idK;
    }

    public void setIdK(Korisnik idK) {
        this.idK = idK;
    }

    public Paket getIdP() {
        return idP;
    }

    public void setIdP(Paket idP) {
        this.idP = idP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idPret != null ? idPret.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Pretplata)) {
            return false;
        }
        Pretplata other = (Pretplata) object;
        if ((this.idPret == null && other.idPret != null) || (this.idPret != null && !this.idPret.equals(other.idPret))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Pretplata{" +
               "idPret=" + idPret +
               ", datumVremePoc=" + datumVremePoc +
               ", cena=" + cena +
               ", Korisnik: " + (idK != null ? idK : "null") +
               ", Paket: " + (idP != null ? idP : "null") +
               '}';
    }
    
}
