/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import entities3.Gledanje;
import entities3.Ocena;
import entities3.Paket;
import entities3.Pretplata;
import java.time.LocalDateTime;
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
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author sofija
 */

@Path("sub3")
@Produces(MediaType.APPLICATION_JSON)
public class SubSystem3 {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private  ConnectionFactory connectionFactory;
    
    @Resource(lookup="SubSystem3Queue")
    private  Queue SubSystem3Queue;
    
    @Resource(lookup="ServerQueueNew")
    private  Queue ServerQueue;
    
            
    @POST
    @Path("kreirajPaket")
    public Response dodajPaket(@FormParam("cena") double cena){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 9);
            mapMsg.setDouble("cena", cena);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem3.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje novog paketa nije uspelo").build();
        
        }
    }
    
    @POST
    @Path("promeniCenuPaketa")
    public Response promeniCenuPaketa(@FormParam("idp") int idp, @FormParam("novaCena") double novaCena){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 10);
            mapMsg.setInt("idp", idp);
            mapMsg.setDouble("novaCena", novaCena);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem3.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Promena cene paketa nije uspela").build();
        
        }
    }
    
    @POST
    @Path("kreirajPretplatu")
    public Response kreirajPretplatu(@FormParam("datumVreme") String datumVreme, @FormParam("idk") int idk, @FormParam("idp") int idp){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 11);
            mapMsg.setString("datumVreme", datumVreme);
            mapMsg.setInt("idk", idk);
            mapMsg.setInt("idp", idp);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem3.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje pretplate nije uspelo").build();
        
        }
    }

    @POST
    @Path("kreirajGledanje")
    // -- sekundPoc, duzinagl, idv, idk
    public Response kreirajGledanje(@FormParam("sekundPoc") int sekundPoc, @FormParam("duzinaGl") int duzinaGl,
            @FormParam("idv") int idv, @FormParam("idk") int idk){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 12);
            mapMsg.setInt("sekundPoc", sekundPoc);
            mapMsg.setInt("duzinaGl", duzinaGl);
            mapMsg.setInt("idv", idv);
            mapMsg.setInt("idk", idk);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem3.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje gledanja nije uspelo").build();
        
        }
    }
    
    @POST
    @Path("oceniVideo")
    // -- sekundPoc, duzinagl, idv, idk
    public Response oceniVideo(@FormParam("idk") int idk, @FormParam("idv") int idv, @FormParam("ocena") int ocena){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 13);
            mapMsg.setInt("idk", idk);
            mapMsg.setInt("idv", idv);
            mapMsg.setInt("ocena", ocena);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem3.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje ocene nije uspelo").build();
        
        }
    }
    
    @POST
    @Path("promeniOcenu")
    public Response promeniOcenu(@FormParam("idk") int idk, @FormParam("idv") int idv, @FormParam("novaOcena") int novaOcena){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 14);
            mapMsg.setInt("idk", idk);
            mapMsg.setInt("idv", idv);
            mapMsg.setInt("novaOcena", novaOcena);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem3.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje ocene nije uspelo").build();
        
        }
    }
    
    @POST
    @Path("obrisiOcenu")
    public Response obrisiOcenu(@FormParam("idk") int idk, @FormParam("idv") int idv){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 15);
            mapMsg.setInt("idk", idk);
            mapMsg.setInt("idv", idv);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem3.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje ocene nije uspelo").build();
        
        }
    }
    
    @GET
    @Path("dohvatiPakete")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiPakete(){
        System.out.println("here");
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 22);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            System.out.println(msg);
            List<Paket> resp = null;
            if (msg instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage) msg;
                resp = (List<Paket>) objMsg.getObject();
//                System.out.println(resp);
            }
             return Response.ok().entity(resp).build();
        
            
        } 
        catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
           return Response.status(Response.Status.BAD_REQUEST).entity("Dohvatanje paketa nije uspelo").build();
        }
    }
    
    
    @POST
    @Path("dohvatiPretplateZaKorisnika")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiPretplateZaKorisnika(@FormParam("idk") int idk){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 23);
            mapMsg.setInt("idk", idk);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            System.out.println(msg);
            List<Pretplata> resp = null;
            if (msg instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage) msg;
                resp = (List<Pretplata>) objMsg.getObject();
//                System.out.println(resp);
            }
             return Response.ok().entity(resp).build();
        
            
        } 
        catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
           return Response.status(Response.Status.BAD_REQUEST).entity("Dohvatanje pretplata za korisnika nije uspelo").build();
        }
    }
    
    @POST
    @Path("dohvatiGledanjaZaVideo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiGledanjaZaVideo(@FormParam("idv") int idv){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 24);
            mapMsg.setInt("idv", idv);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            System.out.println(msg);
            List<Gledanje> resp = null;
            if (msg instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage) msg;
                resp = (List<Gledanje>) objMsg.getObject();
//                System.out.println(resp);
            }
             return Response.ok().entity(resp).build();
        
            
        } 
        catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
           return Response.status(Response.Status.BAD_REQUEST).entity("Dohvatanje gledanja za video nije uspelo").build();
        }
    }
    
    @POST
    @Path("dohvatiOceneZaVideo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiOceneZaVideo(@FormParam("idv") int idv){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 25);
            mapMsg.setInt("idv", idv);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem3Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            System.out.println(msg);
            List<Ocena> resp = null;
            if (msg instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage) msg;
                resp = (List<Ocena>) objMsg.getObject();
//                System.out.println(resp);
            }
             return Response.ok().entity(resp).build();
        
            
        } 
        catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
           return Response.status(Response.Status.BAD_REQUEST).entity("Dohvatanje ocena za video nije uspelo").build();
        }
    }
    
    
    
}
