/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package subsystem3;

import entities3.Gledanje;
import entities3.Ocena;
import entities3.Paket;
import entities3.Pretplata;
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
       
       
        System.out.println("Subsystem3 running...");
        OptionHandler optionHandler = new OptionHandler();
        
        System.out.println("option handler kreiran");
        try( JMSContext context=connectionFactory.createContext();
        JMSConsumer consumer=context.createConsumer(SubSystem3Queue);){
            
            JMSProducer producer = context.createProducer();
            while(true){
            Message msg = consumer.receive();
            
            System.out.println("Poruka primljena");
            
            
            
            if (msg instanceof MapMessage){
                try {
                    
                    MapMessage mapMsg = (MapMessage) msg;
                    int option = mapMsg.getInt("option");
                    switch (option) {
                        case 2:{
                            
                            int idk = mapMsg.getInt("idk");
                            String ans = optionHandler.dodajKorisnika(idk);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            System.out.println(msg.getJMSReplyTo());
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            break;
                        }
                        
                        case 6:{
                            int idv = mapMsg.getInt("idv");
                            String ans = optionHandler.dodajVideo(idv);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                            break;
                        }
                        
                        case 16:{
                            int idv = mapMsg.getInt("idv");
                            String ans = optionHandler.obrisiVideo(idv);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                            break;
                        }
                        
                        case 9:{
                            double cena = mapMsg.getDouble("cena");
                            String ans = optionHandler.dodajPaket(cena);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                            break;
                        }
                        
                        case 10:{
                            int idp = mapMsg.getInt("idp");
                            double novaCena = mapMsg.getDouble("novaCena");
                            String ans = optionHandler.promeniCenuPaketa(idp, novaCena);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                            break;
                        }
                        
                        case 11:{
                            String datumVreme = mapMsg.getString("datumVreme");
                            int idk = mapMsg.getInt("idk");
                            int idp = mapMsg.getInt("idp");
                            
                            String ans = optionHandler.kreirajPretplatu(datumVreme, idk, idp);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                            break;
                        }
                        
                        case 12:{
                            int sekundPoc = mapMsg.getInt("sekundPoc");
                            int duzinaGl = mapMsg.getInt("duzinaGl");
                            int idv = mapMsg.getInt("idv");
                            int idk = mapMsg.getInt("idk");
                            
                           // dohvati duzinu videa iz podsistema2
                           MapMessage mapMsgSub2 = context.createMapMessage();
                           mapMsgSub2.setInt("option", 12);
                           mapMsgSub2.setInt("idv", idv);
                           mapMsgSub2.setJMSReplyTo(SubSystem3Queue);
                           producer.send(SubSystem2Queue, mapMsgSub2);
                           
                           Message msgFrom2 = consumer.receive();
                           int duzina = 0;
                           if (msgFrom2 instanceof MapMessage){
                               MapMessage mapMsgFrom2 = (MapMessage) msgFrom2;
                               duzina = mapMsgFrom2.getInt("duzina");
                           }
                           System.out.println(duzina);
                           
                           if (duzina == -1) {
                               TextMessage txtMsg = context.createTextMessage("Video sa id " + idv + " ne postoji");
                               producer.send(msg.getJMSReplyTo(), txtMsg);
                               System.out.println("Poruka poslata");
                               break;
                           }
                           
                           String ans = optionHandler.kreirajGledanje(sekundPoc, duzinaGl, idv, idk, duzina);
                           TextMessage txtMsg = context.createTextMessage(ans);
                           producer.send(msg.getJMSReplyTo(), txtMsg);
                           System.out.println("Poruka poslata");
                           
                           break;
                        }
                        
                        case 13:{
                            int ocena = mapMsg.getInt("ocena");
                            int idk = mapMsg.getInt("idk");
                            int idv = mapMsg.getInt("idv");
                            
                            String ans = optionHandler.dodavanjeOcene(idk, idv, ocena);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                            break;
                        }
                        
                        case 14:{
                            int novaOcena = mapMsg.getInt("novaOcena");
                            int idk = mapMsg.getInt("idk");
                            int idv = mapMsg.getInt("idv");
                            
                            String ans = optionHandler.promenaOcene(idk, idv, novaOcena);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                            break;
                        }
                        
                        case 15:{
                            int idk = mapMsg.getInt("idk");
                            int idv = mapMsg.getInt("idv");
                            
                            String ans = optionHandler.obrisiOcenu(idk, idv);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            
                            break;
                        }
                        
                        case 22:{
                            List<Paket> paketi = optionHandler.dohvatiPakete();
                            ObjectMessage objmsg = context.createObjectMessage((Serializable) paketi);
                            producer.send(msg.getJMSReplyTo(), objmsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        case 23:{
                            int idk = mapMsg.getInt("idk");
                            List<Pretplata> pretplate = optionHandler.dohvatiPretplateZaKorisnika(idk);
                            ObjectMessage objmsg = context.createObjectMessage((Serializable) pretplate);
                            producer.send(msg.getJMSReplyTo(), objmsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        case 24:{
                            int idv = mapMsg.getInt("idv");
                            List<Gledanje> gledanja = optionHandler.dohvatiGledanjaZaVideo(idv);
                            ObjectMessage objmsg = context.createObjectMessage((Serializable) gledanja);
                            producer.send(msg.getJMSReplyTo(), objmsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        case 25:{
                            int idv = mapMsg.getInt("idv");
                            List<Ocena> ocene = optionHandler.dohvatiOceneZaVideo(idv);
                            ObjectMessage objmsg = context.createObjectMessage((Serializable) ocene);
                            producer.send(msg.getJMSReplyTo(), objmsg);
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
