/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package subsystem1;

import entities1.Korisnik;
import entities1.Mesto;
import java.io.Serializable;
import java.util.ArrayList;
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
    
    @Resource(lookup="SubSystem1QueueNew")
    private static Queue SubSystem1Queue;
    
    @Resource(lookup="SubSystem2Queue")
    private static Queue SubSystem2Queue;

    @Resource(lookup="SubSystem3Queue")
    private static Queue SubSystem3Queue;
    
    @Resource(lookup="ServerQueueNew")
    private static Queue ServerQueue;
    
    
    public static void main(String[] args) {
       
       
        System.out.println("Subsystem1 running...");
        OptionHandler optionHandler = new OptionHandler();
        
        System.out.println("option handler kreiran");
        try( JMSContext context=connectionFactory.createContext();
        JMSConsumer consumer=context.createConsumer(SubSystem1Queue);){
            
            JMSProducer producer = context.createProducer();
            while(true){
            Message msg = consumer.receive();
            
            System.out.println("Poruka primljena");
            if (msg instanceof MapMessage){
                try {
                    MapMessage mapMsg = (MapMessage) msg;
                    int option = mapMsg.getInt("option");
                    System.out.println(option);
                    switch (option) {
                        case 1:{
                            String naziv = mapMsg.getString("naziv");
                            String ans = optionHandler.dodajMesto(naziv);
                            System.out.println(msg.getJMSReplyTo());
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka poslata");
                            break;
                        }
                        case 2:{
                            String ime = mapMsg.getString("ime");
                            String email = mapMsg.getString("email");
                            int godiste = mapMsg.getInt("godiste");
                            char pol = mapMsg.getChar("pol");
                            int idm = mapMsg.getInt("idm");
                            int ans = optionHandler.dodajKorisnika(ime, email, godiste, pol, idm);
                            if (ans == -3){
                                TextMessage txtMsg = context.createTextMessage("Korisnik sa emailom " + email + " vec postoji");
                                producer.send(msg.getJMSReplyTo(), txtMsg);
                                break;
                            }
                            if (ans == -2){
                                TextMessage txtMsg = context.createTextMessage("Mesto sa id " + idm + " ne postoji");
                                producer.send(msg.getJMSReplyTo(), txtMsg);
                                break;
                            }
                            if (ans == -2){
                                TextMessage txtMsg = context.createTextMessage("Doslo je do neocekivane greske");
                                producer.send(msg.getJMSReplyTo(), txtMsg);
                                break;
                            }
                            String resp = "";
                            MapMessage mapMsgSub2 = context.createMapMessage();
                            mapMsgSub2.setInt("option", 2);
                            mapMsgSub2.setInt("idk", ans);
                            mapMsgSub2.setJMSReplyTo(SubSystem1Queue);
                            producer.send(SubSystem2Queue, mapMsgSub2);
                            Message msgSub2 = consumer.receive();
                            if (msgSub2 instanceof TextMessage){
                                TextMessage txtMsgSub2 = (TextMessage) msgSub2;
                                resp = txtMsgSub2.getText();
                            }
                            
                            // treba srediti i komunikaciju sa podsistemom3
                            producer.send(SubSystem3Queue, mapMsgSub2);
                            Message msgSub3 = consumer.receive();
                            if (msgSub2 instanceof TextMessage){
                                TextMessage txtMsgSub2 = (TextMessage) msgSub2;
                                resp = (resp.equals("ok")?txtMsgSub2.getText():resp);
                            }
                            TextMessage txtMsg = context.createTextMessage(resp.equals("ok")?"Korisnik je uspesno dodat":"Doslo je do greske");
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            break;
                        }
                        
                        case 3:{
                            int idk = mapMsg.getInt("idk");
                            String noviEmail = mapMsg.getString("noviEmail");
                            String ans = optionHandler.promeniEmail(idk, noviEmail);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        case 4:{
                            int idk = mapMsg.getInt("idk");
                            String mesto = mapMsg.getString("mesto");
                            String ans = optionHandler.promeniMesto(idk, mesto);
                            TextMessage txtMsg = context.createTextMessage(ans);
                            producer.send(msg.getJMSReplyTo(), txtMsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        case 17:{
                            List<Mesto> mesta = optionHandler.dohvatiMesta();
                            ObjectMessage objmsg = context.createObjectMessage((Serializable) mesta);
                            producer.send(msg.getJMSReplyTo(), objmsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        case 18:{
                            List<Korisnik> korisnici = optionHandler.dohvatiKorisnike();
                            ObjectMessage objmsg = context.createObjectMessage((Serializable) korisnici);
                            producer.send(msg.getJMSReplyTo(), objmsg);
                            System.out.println("Poruka uspesno poslata");
                            break;
                        }
                        
                        
                        
                        default:
                        {
                            System.out.println("HERE");
                            System.out.println(option);
                            TextMessage txtMsg = context.createTextMessage("Nepoznat broj opcije");
                            txtMsg.setJMSType("DOKLE VISE");
                            producer.send(ServerQueue, txtMsg);
                        }
                    }
                } catch (JMSException ex) {
                    Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
                    // vrati gresku odavde
                    return;
                }
                
            }
           }
        }
        
    }
    
}
