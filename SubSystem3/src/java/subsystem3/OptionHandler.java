/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subsystem3;

import entities3.Gledanje;
import entities3.Korisnik;
import entities3.Paket;
import entities3.Pretplata;
import entities3.Video;
import entities3.Ocena;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author sofija
 */


public class OptionHandler {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SubSystem3PU");
    EntityManager em = emf.createEntityManager();
    
    
    public String dodajKorisnika (int idk){
        Korisnik korisnik = new Korisnik();
        korisnik.setIdK(idk);
        korisnik.setIdK(idk);korisnik.setGledanjeList(new ArrayList<>());
        korisnik.setOcenaList(new ArrayList<>());
        korisnik.setPretplataList(new ArrayList<>());
        
        
        em.getTransaction().begin();
        em.persist(korisnik);
        em.getTransaction().commit();
        
        return "ok";
    }
    
    public String dodajVideo (int idv){
        Video v = new Video();
        v.setIdV(idv);v.setGledanjeList(new ArrayList<>());
        v.setOcenaList(new ArrayList<>());
        
                
        em.getTransaction().begin();
        em.persist(v);
        em.getTransaction().commit();
        
        return "ok";
    }
    
    public String obrisiVideo (int idv){
        Video v = em.find(Video.class, idv);
        
        em.getTransaction().begin();
        em.remove(v);
        em.flush();
        em.getTransaction().commit();
    
        return "ok";
    }
    
    public String dodajPaket (double cena){
        
        Paket paket = new Paket();
        paket.setCena(new BigDecimal(cena));
        paket.setPretplataList(null);
        
        
        em.getTransaction().begin();
        em.persist(paket);
        em.getTransaction().commit();
        
        return "Paket uspesno dodat";
        
    }
    
    public String promeniCenuPaketa (int idp, double novaCena){
        // postoji li paket
        Paket p = em.find(Paket.class, idp);
        if (p == null) return "Ne postoji paket sa idp " + idp;
        
        em.getTransaction().begin();
        p.setCena(new BigDecimal(novaCena));
        em.getTransaction().commit();
        
        return "Cena paketa uspesno promenjena";
    }
    
    public String kreirajPretplatu(String datumVremeString, int idk, int idp){
        // postoji li korisnik
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null) return "Ne postoji korisnik sa id " + idk;
        
        // postoji li paket
        Paket p = em.find(Paket.class, idp);
        if (p == null) return "Ne postoji paket sa id " + idp;
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        LocalDateTime datumVreme = LocalDateTime.parse(datumVremeString, formatter);
        if (datumVreme.isBefore(LocalDateTime.now())) return "Datum i vreme pocetka ne smeju biti u proslosti";
        
        
        // sad treba ova provera da li postoji aktivna pretplata
        TypedQuery<Pretplata> tq = em.createQuery("select p from Pretplata p where p.idK.idK=:idk", Pretplata.class);
        tq.setParameter("idk", idk);
        List<Pretplata> pretplateKorisnika = tq.getResultList();
        
        if (pretplateKorisnika != null){
            for (Pretplata pret:pretplateKorisnika){
                Date datumVremePoc = pret.getDatumVremePoc();
                Timestamp t = new Timestamp(datumVremePoc.getTime());
                
                LocalDateTime pocetak = t.toLocalDateTime();
                System.out.println(pocetak);
                LocalDateTime kraj = pocetak.plusMonths(1);
                
                if ((datumVreme.isAfter(pocetak) || datumVreme.isEqual(pocetak)) && datumVreme.isBefore(kraj))
                       return "Vec postoji aktivna pretplata za korisnika sa id " + idk;
            }
        }
        
        Timestamp timestamp = Timestamp.valueOf(datumVreme);
        
        Pretplata pret = new Pretplata();
        pret.setCena(p.getCena()); pret.setDatumVremePoc(timestamp);
        pret.setIdK(k); pret.setIdP(p);
        
        em.getTransaction().begin();
        em.persist(pret);
        em.getTransaction().commit();
        
        return "Pretplata uspesno kreirana";
    }

    // -- sekundPoc, duzinagl, idv, idk
    public String kreirajGledanje(int sekundPoc, int duzinaGl, int idv, int idk, int duzinaVidea){
        // postoji li idv
        Video v = em.find(Video.class, idv);
        if (v == null) return "Ne postoji video sa idv " + idv;
        
        // postoji li idk
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null) return "Ne postoji korisnik sa idk " + idk;
        
        // da li je sekund pocetka <= duzina videa
        if (sekundPoc > duzinaVidea) return "Sekund pocetka mora biti izmedju 1 i " + duzinaVidea;
        
        // da li je sekundPOc + duzinaGl <= duzina videa
        if ((sekundPoc + duzinaGl) > duzinaVidea) return "Uneta duzina gledanja premasuje duzinu videa";
        
        // dohvati trenutni timestamp kao datum i vreme gledanja videa
        java.util.Date danasnji = new java.util.Date();
        java.sql.Timestamp datum = new java.sql.Timestamp(danasnji.getTime());
        
        Gledanje gl = new Gledanje();
        gl.setDatumVremePoc(datum); gl.setDuzinaGl(duzinaGl);
        gl.setIdK(k); gl.setIdV(v); gl.setSekundPoc(sekundPoc);
        
        em.getTransaction().begin();
        em.persist(gl);
        em.getTransaction().commit();
        
        return "Gledanje uspesno kreirano";
        
    }
    
    public String dodavanjeOcene(int idk, int idv, int ocena){
        // nije ok da ne postoji korisnik
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null) return "Korisnik sa idk " + idk + " ne postoji";
        
        // nije ok da ne postoji video
        Video v = em.find(Video.class, idv);
        if (v == null) return "Video sa idv " + idv + " ne postoji"; 
        
        // nije ok da je video vec ocenjen od strane ovog korisnika
        TypedQuery<Ocena> tq = em.createQuery("select o from Ocena o where o.idK.idK=:idk and o.idV.idV=:idv", Ocena.class);
        tq.setParameter("idk", idk); tq.setParameter("idv", idv);
        List<Ocena> ocene = tq.getResultList();
        if (ocene != null && !ocene.isEmpty()) return "Korisnik sa id " + idk + " je vec ocenio video sa idv " + idv;
        
        
        // dodaj ocenu
        java.util.Date danasnji = new java.util.Date();
        java.sql.Timestamp datum = new java.sql.Timestamp(danasnji.getTime());
        
        Ocena o = new Ocena();
        o.setDatumVreme(datum); o.setIdK(k);
        o.setIdV(v); o.setOcena(ocena);
        
        em.getTransaction().begin();
        em.persist(o);
        em.getTransaction().commit();
        
        return "Ocena uspesno dodata";
        
    }
    
    public String promenaOcene (int idk, int idv, int novaOcena){
        // nije ok da ne postoji korisnik
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null) return "Korisnik sa idk " + idk + " ne postoji";
        
        // nije ok da ne postoji video
        Video v = em.find(Video.class, idv);
        if (v == null) return "Video sa idv " + idv + " ne postoji"; 
        
        // nije ok da ne postoji ocena vec
        TypedQuery<Ocena> tq = em.createQuery("select o from Ocena o where o.idK.idK=:idk and o.idV.idV=:idv", Ocena.class);
        tq.setParameter("idk", idk); tq.setParameter("idv", idv);
        List<Ocena> ocene = tq.getResultList();
        if (ocene == null || ocene.isEmpty()) return "Korisnik sa idk " + idk +" nije prethodno ocenio video sa idv " + idv;
    
        Ocena o = ocene.get(0);
        
        em.getTransaction().begin();
        o.setOcena(novaOcena);
        em.getTransaction().commit();
        
        return "Ocena uspesno promenjena";
    }
    
    public String obrisiOcenu (int idk, int idv){
        // nije ok da ne postoji korisnik
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null) return "Korisnik sa idk " + idk + " ne postoji";
        
        // nije ok da ne postoji video
        Video v = em.find(Video.class, idv);
        if (v == null) return "Video sa idv " + idv + " ne postoji"; 
        
        // nije ok da ne postoji ocena vec
        TypedQuery<Ocena> tq = em.createQuery("select o from Ocena o where o.idK.idK=:idk and o.idV.idV=:idv", Ocena.class);
        tq.setParameter("idk", idk); tq.setParameter("idv", idv);
        List<Ocena> ocene = tq.getResultList();
        if (ocene == null || ocene.isEmpty()) return "Korisnik sa idk " + idk +" nije ocenio video sa idv " + idv;
    
        Ocena o = ocene.get(0);
        
        em.getTransaction().begin();
        em.remove(o);
        em.getTransaction().commit();
        
        return "Ocena uspesno obrisana";
    }
    
    public List<Paket> dohvatiPakete(){
        TypedQuery<Paket> tq = em.createNamedQuery("Paket.findAll", Paket.class);
        List<Paket> paketi = tq.getResultList();
        return paketi;
    }
    
    public List<Pretplata> dohvatiPretplateZaKorisnika(int idk){
        // postoji li korisnik
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null){
            List<Pretplata> pretplate = new ArrayList<>();
            pretplate.add(null);
            return pretplate;
        }
        
        TypedQuery<Pretplata> tq = em.createQuery("Select p from Pretplata p where p.idK.idK=:idk", Pretplata.class);
        tq.setParameter("idk", idk);
        List<Pretplata> pretplate = tq.getResultList();
        
        return pretplate;
        
    }
    
    public List<Gledanje> dohvatiGledanjaZaVideo(int idv){
        // postoji li korisnik
        Video v = em.find(Video.class, idv);
        if (v == null){
            List<Gledanje> gledanja = new ArrayList<>();
            gledanja.add(null);
            return gledanja;
        }
        
        TypedQuery<Gledanje> tq = em.createQuery("Select g from Gledanje g where g.idV.idV=:idv", Gledanje.class);
        tq.setParameter("idv", idv);
        List<Gledanje> gledanja = tq.getResultList();
        
        return gledanja;
        
    }
    
    public List<Ocena> dohvatiOceneZaVideo(int idv){
        // postoji li korisnik
        Video v = em.find(Video.class, idv);
        if (v == null){
            List<Ocena> ocene = new ArrayList<>();
            ocene.add(null);
            return ocene;
        }
        
        TypedQuery<Ocena> tq = em.createQuery("Select o from Ocena o where o.idV.idV=:idv", Ocena.class);
        tq.setParameter("idv", idv);
        List<Ocena> ocene = tq.getResultList();
        
        return ocene;
        
    }
}
