/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities1;

import java.io.Serializable;
import java.util.List;
import javax.json.bind.annotation.JsonbAnnotation;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author sofija
 */
@Entity
@Table(name = "mesto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Mesto.findAll", query = "SELECT m FROM Mesto m"),
    @NamedQuery(name = "Mesto.findByIdM", query = "SELECT m FROM Mesto m WHERE m.idM = :idM"),
    @NamedQuery(name = "Mesto.findByNaziv", query = "SELECT m FROM Mesto m WHERE m.naziv = :naziv")})
public class Mesto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "IdM")
    private Integer idM;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 45)
    @Column(name = "Naziv")
    private String naziv;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "idM")
    private List<Korisnik> korisnikList;

    public Mesto() {
    }

    public Mesto(Integer idM) {
        this.idM = idM;
    }

    public Mesto(Integer idM, String naziv) {
        this.idM = idM;
        this.naziv = naziv;
    }

    public Integer getIdM() {
        return idM;
    }

    public void setIdM(Integer idM) {
        this.idM = idM;
    }

    public String getNaziv() {
        return naziv;
    }

    public void setNaziv(String naziv) {
        this.naziv = naziv;
    }

    @XmlTransient
    @JsonbTransient
    public List<Korisnik> getKorisnikList() {
        return korisnikList;
    }

    public void setKorisnikList(List<Korisnik> korisnikList) {
        this.korisnikList = korisnikList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (idM != null ? idM.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Mesto)) {
            return false;
        }
        Mesto other = (Mesto) object;
        if ((this.idM == null && other.idM != null) || (this.idM != null && !this.idM.equals(other.idM))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Mesto{");
        sb.append("idM=").append(idM);
        sb.append(", naziv='").append(naziv).append('\'');
        sb.append('}');
        return sb.toString();
    }
    
}
