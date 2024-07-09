/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package subsystem1;

import entities1.Korisnik;
import entities1.Mesto;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.Queue;
import javax.jms.TextMessage;
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
    
    
    

    
    EntityManagerFactory emf = Persistence.createEntityManagerFactory("SubSystem1PU");
    EntityManager em = emf.createEntityManager();
    
    
    public String dodajMesto (String naziv){
        // nek bude da ne moze mesto sa istim nazivom
        String query = "select m from Mesto m where lower(m.naziv)=:naziv";
        TypedQuery<Mesto> tqMesto = em.createQuery(query, Mesto.class).setParameter("naziv", naziv);
        List<Mesto> mestoError = tqMesto.getResultList();
        if (mestoError != null && !mestoError.isEmpty()){
            return "Mesto sa imenom " + naziv + " vec postoji u sistemu";
        }
        

        
        Mesto mesto = new Mesto();
        mesto.setKorisnikList(new ArrayList<>());
        mesto.setNaziv(naziv);
        
        em.getTransaction().begin();
        em.persist(mesto);
        em.getTransaction().commit();
        
        System.out.println("Mesto je persistovano");
        
        return "Mesto za nazivom " + naziv + " je uspesno dodato";
    }
    
    public int dodajKorisnika (String ime, String email, int godiste, char pol, int idm){
        // proveri da li neko ima sa tim emailom
        TypedQuery<Korisnik> tq = em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", email);
        List<Korisnik> korisnici = tq.getResultList();
        if (korisnici != null && !korisnici.isEmpty()) return -3;
        
        // proveri da li postoji mesto sa idm

     
        Mesto mesto = em.find(Mesto.class, idm);
        if (mesto == null) return -2;
        
        
        // ako je sve ok persistuj korisnika
        
        
        
        
        Korisnik k = new Korisnik();
        k.setEmail(email); k.setGodiste(godiste); k.setIdM(mesto);
        k.setIme(ime); k.setPol(pol);
        
       
        
        em.getTransaction().begin();
        em.persist(k);
        em.flush();
        em.getTransaction().commit();
        
        em.getTransaction().begin();
        if (mesto.getKorisnikList() == null){
            mesto.setKorisnikList(new ArrayList<>());
        } else {
            mesto.getKorisnikList().add(k);
        }
        em.flush();
//        System.out.println(mesto.getKorisnikList());
        em.getTransaction().commit();
        
//        System.out.println(mesto.getKorisnikList());
        
        String query = "select max(k.idK) from Korisnik k";
    
        TypedQuery<Integer> tq2 = em.createQuery(query, Integer.class);
        List<Integer> idKs = tq2.getResultList();
        int idK = idKs.get(0);
        
        // prosledi informaciju o dodatom korisniku ka podsistemu2 i podsistemu3
        
        
        
        return idK;
    }
   
    public String promeniEmail (int idk, String noviEmail){
        // nije ok da ne postoji idk
        // nije ok da postoji noviEmail
        
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null) return "Korisnik sa id " + idk + " ne postoji";
        
        TypedQuery<Korisnik> tq = em.createNamedQuery("Korisnik.findByEmail", Korisnik.class).setParameter("email", noviEmail);
        List<Korisnik> korisnici = tq.getResultList();
        
        if (korisnici != null && !korisnici.isEmpty())
            return "Vec postoji korisnik sa email-om " + noviEmail;
        
        em.getTransaction().begin();
        k.setEmail(noviEmail);
        em.flush();
        em.getTransaction().commit();
        
        return "Email uspesno promenjen";
        
    }

    public String promeniMesto (int idk, String mesto){
        // idk mora da postoji
        Korisnik k = em.find(Korisnik.class, idk);
        if (k == null) return "Korisnik sa id " + idk + " ne postoji";
        
        Mesto staroMesto = em.find(Mesto.class, k.getIdM().getIdM());
        
        // mesto mora da postoji
        TypedQuery<Mesto> tq = em.createNamedQuery("Mesto.findByNaziv", Mesto.class).setParameter("naziv", mesto);
        List<Mesto> novoMesto = tq.getResultList();
        if (novoMesto == null || novoMesto.isEmpty())
            return "Mesto sa nazivom " + mesto + " ne postoji";
        Mesto novoMestoReal = novoMesto.get(0);
        
        
        // persistuj promenu u korisniku
        em.getTransaction().begin();
        k.setIdM(novoMesto.get(0));
        staroMesto.getKorisnikList().remove(k);
        if (novoMestoReal.getKorisnikList() == null){
            novoMestoReal.setKorisnikList(new ArrayList<>());
        } else {
            novoMestoReal.getKorisnikList().add(k);
        }
        em.flush();
        em.getTransaction().commit();
        
        System.out.println(staroMesto.getKorisnikList());
        System.out.println(novoMestoReal.getKorisnikList());
        
        return "Mesto uspesno promenjeno";
    }
    
    public List<Mesto> dohvatiMesta(){
        
        TypedQuery<Mesto> tq = em.createNamedQuery("Mesto.findAll", Mesto.class);
        List<Mesto> mesta = tq.getResultList();
        
        System.out.println(mesta);
        return mesta;
    }
    
    public List<Korisnik> dohvatiKorisnike(){
        
        TypedQuery<Korisnik> tq = em.createNamedQuery("Korisnik.findAll", Korisnik.class);
        List<Korisnik> korisnici = tq.getResultList();
        
//        System.out.println(mesta);
        return korisnici;
    }


}
