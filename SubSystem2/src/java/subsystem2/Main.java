/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package subsystem2;

import entities2.Kategorija;
import entities2.Video;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.jms.ConnectionFactory;
import javax.jms.JMSConsumer;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.JMSProducer;
import javax.jms.MapMessage;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;

/**
 *
 * @author sofija
 */
public class Main {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private static ConnectionFactory connectionFactory;
    
    @Resource(lookup="SubSystem2Queue")
    private static Queue SubSystem2Queue;
    
    @Resource(lookup="SubSystem1QueueNew")
    private static Queue SubSystem1Queue;
    
    @Resource(lookup="SubSystem3Queue")
    private static Queue SubSystem3Queue;
    
    
    
    public static void main(String[] args) {
       
       
        System.out.println("Subsystem2 running...");
        OptionHandler optionHandler = new OptionHandler();
        
        System.out.println("option handler kreiran");
        try( JMSContext context=connectionFactory.createContext();
        JMSConsumer consumer=context.createConsumer(SubSystem2Queue);){
            
            JMSProducer producer = context.createProducer();
            while(true){
            Message msg = consumer.receive();
            
            System.out.println("Poruka primljena");
            System.out.println(msg);
            if (msg instanceof MapMessage){
                try {
                    
                    MapMessage mapMsg = (MapMessage) msg;
                    int option = mapMsg.getInt("option");
                    switch (option) {
                        case 2:{
                            System.out.println(mapMsg);
                            int idk = mapMsg.getInt("idk");
                            String ans = optionHandler.dodajKorisnika(idk);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            System.out.println(msg.getJMSReplyTo());
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            break;
                        }
                        
                        case 5:{
                            String naziv = mapMsg.getString("naziv");
                            String ans = optionHandler.dodajKategoriju(naziv);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                           
                            break;
                        }
                        
                        case 6:{
                            // -- idv, naziv, trajanje, datumvreme, idk
                            String naziv = mapMsg.getString("naziv");
                            int trajanje = mapMsg.getInt("trajanje");
                            int idk = mapMsg.getInt("idk");
                            
                            int ans = optionHandler.dodajVideo(naziv, trajanje, idk);
                            if (ans == -2){
                                TextMessage txtMsg = context.createTextMessage("Korisnik sa idk " + idk + " ne postoji");
                                producer.send(msg.getJMSReplyTo(), txtMsg);
                                break;
                            }
                            
                            // prebaci u subsystem3
                            
                            String resp = "";
                            
                            MapMessage mapMsgSub3 = context.createMapMessage();
                            mapMsgSub3.setInt("option", 6);
                            mapMsgSub3.setInt("idv", ans);
                            mapMsgSub3.setJMSReplyTo(SubSystem2Queue);
                            producer.send(SubSystem3Queue, mapMsgSub3);
                            
                            Message msgSub3 = consumer.receive();
                            if (msgSub3 instanceof TextMessage){
                                TextMessage txtMsgSub3 = (TextMessage) msgSub3;
                                resp = txtMsgSub3.getText();
                            }
                            
                            TextMessage txtMsg = context.createTextMessage(resp.equals("ok")?"Video uspesno dodat":"Doslo je do greske prilikom dodavanja videa");
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            
                            break;
                        }
                        
                        case 7:{
                            int idv = mapMsg.getInt("idv");
                            String noviNaziv = mapMsg.getString("noviNaziv");
                            
                            String ans = optionHandler.promeniNazivVidea(idv, noviNaziv);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                           
                            break;
                        }
                        
                        case 8:{
                            int idv = mapMsg.getInt("idv");
                            String nazivKategorije = mapMsg.getString("nazivKategorije");
                            
                            String ans = optionHandler.dodajKategorijuVideu(idv, nazivKategorije);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                            break;
                        }
                        
                        case 16:{
                            int idk = mapMsg.getInt("idk");
                            int idv = mapMsg.getInt("idv");
                            
                            int ans = optionHandler.obrisiVideo(idk, idv);
                            if (ans == -1){
                                TextMessage txtMsg = context.createTextMessage("Ne postoji korisnik sa idk " + idk);
                                producer.send(msg.getJMSReplyTo(), txtMsg);
                                break;
                            }
                            if (ans == -2){
                                TextMessage txtMsg = context.createTextMessage("Ne postoji video sa idv " + idv);
                                producer.send(msg.getJMSReplyTo(), txtMsg);
                                break;
                            }
                            if (ans == -3){
                                TextMessage txtMsg = context.createTextMessage("Korisnik sa idk " + idk + " nije vlasnik videa sa idv " + idv);
                                producer.send(msg.getJMSReplyTo(), txtMsg);
                                break;
                            }
                            
                            // obavesti subsystem3
                            String resp = "";
                            
                            MapMessage mapMsgSub3 = context.createMapMessage();
                            mapMsgSub3.setInt("option", 16);
                            mapMsgSub3.setInt("idv", idv);
                            mapMsgSub3.setJMSReplyTo(SubSystem2Queue);
                            producer.send(SubSystem3Queue, mapMsgSub3);
                            
                            Message msgSub3 = consumer.receive();
                            if (msgSub3 instanceof TextMessage){
                                TextMessage txtMsgSub3 = (TextMessage) msgSub3;
                                resp = txtMsgSub3.getText();
                            }
                            
                            TextMessage txtMsg = context.createTextMessage(resp.equals("ok")?"Video uspesno obrisan":"Doslo je do greske prilikom brisanja videa");
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            
                            break;
                        }
                        
                        case 19:{
                            List<Kategorija> kategorija = optionHandler.dohvatiKategorije();
                            ObjectMessage objmsg = context.createObjectMessage((Serializable) kategorija);
                            producer.send(msg.getJMSReplyTo(), objmsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        case 20:{
                            List<Video> videi = optionHandler.dohvatiVidee();
                            ObjectMessage objmsg = context.createObjectMessage((Serializable) videi);
                            producer.send(msg.getJMSReplyTo(), objmsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        case 21:{
                            int idv = mapMsg.getInt("idv");
                            List<Kategorija> kategorije = optionHandler.dohvatiKategorijeZaVideo(idv);
                            ObjectMessage objmsg = context.createObjectMessage((Serializable) kategorije);
                            producer.send(msg.getJMSReplyTo(), objmsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        case 12:{
                            int idv = mapMsg.getInt("idv");
                            int duzina = optionHandler.dohvatiDuzinuVidea(idv);
                            MapMessage returnMap = context.createMapMessage();
                            returnMap.setInt("duzina", duzina);
                            producer.send(msg.getJMSReplyTo(), returnMap);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        default:
                        {
                            TextMessage txtMsg = context.createTextMessage("Nepoznat broj opcije");
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                        }
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);

                    return;
                }
                
            }
           }
        }
        
    }
    
}
