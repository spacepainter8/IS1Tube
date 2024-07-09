/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subsystem2;

import entities2.Kategorija;
import entities2.Korisnik;
import entities2.Pripada;
import entities2.PripadaPK;
import entities2.Video;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.sql.Date;

/**
 *
 * @author sofija
 */


public class OptionHandler {
    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SubSystem2PU");
    EntityManager em = emf.createEntityManager();
    
    
    public String dodajKorisnika (int idk){
        Korisnik korisnik = new Korisnik();
        korisnik.setIdK(idk);korisnik.setVideoList(new ArrayList<>());
        
        em.getTransaction().begin();
        em.persist(korisnik);
        em.getTransaction().commit();
        
        return "ok";
    }
    
    public String dodajKategoriju(String naziv){
        // proveri da li vec ima kategorija sa istim nazivom
        
        TypedQuery<Kategorija> tq = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", naziv);
        List<Kategorija> kategorije = tq.getResultList();
        if (kategorije != null && !kategorije.isEmpty()) return "Vec postoji kategorija sa imenom " + naziv;
        
        
        
        Kategorija kat = new Kategorija();
        kat.setNaziv(naziv);
        
        em.getTransaction().begin();
        em.persist(kat);
        em.getTransaction().commit();
        
        return "Kategorija uspesno dodata";
        
    }
    
    // -- idv, naziv, trajanje, datumvreme, idk
    public int dodajVideo(String naziv, int trajanje, int idk){
        // nije u redu da nema korisnik idk
        
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null) return -2;
        
        
       
        
        
        java.util.Date danasnji = new java.util.Date();
        java.sql.Timestamp datum = new java.sql.Timestamp(danasnji.getTime());
        
        Video v = new Video();
        v.setDatumVreme(datum); v.setIdK(k);
        v.setNaziv(naziv); v.setTrajanje(trajanje);
        
        em.getTransaction().begin();
        em.persist(v);
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        if (k.getVideoList()== null){
            k.setVideoList(new ArrayList<>());
        } else {
            k.getVideoList().add(v);
        }
        em.flush();
        em.getTransaction().commit();
        
        
        String query = "select max(v.idV) from Video v";
    
        TypedQuery<Integer> tq2 = em.createQuery(query, Integer.class);
        List<Integer> idVs = tq2.getResultList();
        int idV = idVs.get(0);
        
        
        return idV;
    }
    
    
    public String promeniNazivVidea (int idv, String noviNaziv){
        // proveri da li postoji video
        
        Video v = em.find(Video.class, idv);
        if (v == null ) return "Ne postoji video sa idv " + idv;
        
        em.getTransaction().begin();
        v.setNaziv(noviNaziv);
        em.getTransaction().commit();
        
        return "Naziv videa uspesno promenjen";
    }
    
    public String dodajKategorijuVideu (int idv, String nazivKategorije){
        // nije ok da ne postoji video
        
        Video v = em.find(Video.class, idv);
        if (v == null) return "Ne postoji video sa idv " + idv;
        
        // nije ok da ne postoji kategorija
        
        TypedQuery<Kategorija> tq1 = em.createNamedQuery("Kategorija.findByNaziv", Kategorija.class).setParameter("naziv", nazivKategorije);
        List<Kategorija> kategorije = tq1.getResultList();
        if (kategorije == null || kategorije.isEmpty()) return "Kategorija sa nazivom " + nazivKategorije + " ne postoji";
        Kategorija kat = kategorije.get(0);
        
        // nije ok da dati video vec ima tu kategoriju
        
        PripadaPK primarniKljuc = new PripadaPK();
        primarniKljuc.setIdKat(kat.getIdKat()); primarniKljuc.setIdV(idv);
        
        String query = "select p from pripada p where p.pripadaPK=:pk";
        TypedQuery<Pripada> tq2 = em.createQuery("select p from Pripada p where p.pripadaPK=:pk", Pripada.class).setParameter("pk", primarniKljuc);
        List<Pripada> pripadanja = tq2.getResultList();
        if (pripadanja != null && !pripadanja.isEmpty()) return "Ovom videu je vec dodeljena ova kategorija";
        
     
        
        Pripada pripada = new Pripada();
        pripada.setPripadaPK(primarniKljuc);
        
        em.getTransaction().begin();
        em.persist(pripada);
        em.getTransaction().commit();
        
        return "Kategorija uspesno dodata videu";
    }
  

    public int obrisiVideo (int idk, int idv){
        // nije ok da ne postoji korisnik
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null ) return -1;
        
        // nije ok da ne postoji video
        Video v = em.find(Video.class, idv);
        if (v == null) return  -2;
        
        // nije ok da korisnik nije vlasnik tog videa
        if (!v.getIdK().equals(k)) return -3;
        
        em.getTransaction().begin();
        em.remove(v);
        em.flush();
        em.getTransaction().commit();
        
        return 0;
    }
    
    public List<Kategorija> dohvatiKategorije(){
        TypedQuery<Kategorija> tq = em.createNamedQuery("Kategorija.findAll", Kategorija.class);
        List<Kategorija> lista = tq.getResultList();
        return lista;
    }

    public List<Video> dohvatiVidee(){
        TypedQuery<Video> tq = em.createNamedQuery("Video.findAll", Video.class);
        List<Video> lista = tq.getResultList();
        return lista;
    }
    
    public List<Kategorija> dohvatiKategorijeZaVideo(int idv){
        // postoji li video
        
        Video v = em.find(Video.class, idv);
        if (v == null ) return null;
        
        // dohvati sva "pripada" pa odatle i kategorije
        // samo sto sam ja lose ovo uradila pa je kategorija u pripada sammo idkat lol
    
        TypedQuery<Pripada> tq1 = em.createNamedQuery("Pripada.findByIdV", Pripada.class).setParameter("idV", idv);
        List<Pripada> pripadanja = tq1.getResultList();
        
        if (pripadanja == null || pripadanja.isEmpty()) return new ArrayList<>();
        
        List<Kategorija> kategorije = new ArrayList<>();
        
        for (Pripada p:pripadanja){
            Kategorija k = em.find(Kategorija.class, p.getPripadaPK().getIdKat());
            kategorije.add(k);
        }
        
        return kategorije;
    
    }
    
    public int dohvatiDuzinuVidea (int idv){
        Video v = em.find(Video.class, idv);
        if (v == null) return -1;
        
        
        return v.getTrajanje();
    }
    
    
}
