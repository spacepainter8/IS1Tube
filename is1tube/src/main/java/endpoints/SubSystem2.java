/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import entities1.Mesto;
import entities2.Kategorija;
import entities2.Video;
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
import javax.ws.rs.DELETE;
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

@Path("sub2")
@Produces(MediaType.APPLICATION_JSON)
public class SubSystem2 {
    
    @Resource(lookup="jms/__defaultConnectionFactory")
    private  ConnectionFactory connectionFactory;
    
    @Resource(lookup="SubSystem2Queue")
    private  Queue SubSystem2Queue;
    
    @Resource(lookup="ServerQueueNew")
    private  Queue ServerQueue;
    
    
    @POST
    @Path("kreirajKategoriju")
    public Response kreirajKategoriju(@FormParam("naziv") String naziv){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 5);
            mapMsg.setString("naziv", naziv);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem2Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje nove kategorije nije uspelo").build();
        
        }
    }
    
    @POST
    @Path("kreirajVideo")
    // -- idv, naziv, trajanje, datumvreme, idk
    public Response kreirajVideo(@FormParam("naziv") String naziv, @FormParam("trajanje") int trajanje, @FormParam("idk") int idk){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 6);
            mapMsg.setString("naziv", naziv);
            mapMsg.setInt("trajanje", trajanje);
            mapMsg.setInt("idk", idk);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem2Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            System.out.println(msg);
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            }
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje novog videa nije uspelo").build();
        
        }
    }
    
    @POST
    @Path("promeniNazivSnimka")
    public Response promeniNazivVidea(@FormParam("idv") int idv, @FormParam("noviNaziv") String noviNaziv){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 7);
            mapMsg.setInt("idv", idv);
            mapMsg.setString("noviNaziv", noviNaziv);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem2Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            }
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Promena naziva videa nije uspela").build();
        }
    }
    
    @POST
    @Path("dodajKategorijuVideu")
    public Response dodajKategorijuVideu(@FormParam("idv") int idv, @FormParam("nazivKategorije") String nazivKategorije){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 8);
            mapMsg.setInt("idv", idv);
            mapMsg.setString("nazivKategorije", nazivKategorije);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem2Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
        
            
        } 
        catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
           return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje kategorije videu nije uspelo").build();
        }
    }
    
    @POST
    @Path("obrisiVideo")
    public Response obrisiVideo(@FormParam("idk") int idk, @FormParam("idv") int idv){
           try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 16);
            mapMsg.setInt("idv", idv);
            mapMsg.setInt("idk", idk);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem2Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            } else System.out.println(msg.getClass());
            
            return Response.ok().entity(resp).build();
        
            
        } 
        catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
           return Response.status(Response.Status.BAD_REQUEST).entity("Brisanje videa nije uspelo").build();
        }
    }
    
    @GET
    @Path("dohvatiKategorije")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiKategorije(){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 19);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem2Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            System.out.println(msg);
            List<Kategorija> resp = null;
            if (msg instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage) msg;
                resp = (List<Kategorija>) objMsg.getObject();
//                System.out.println(resp);
            }
             return Response.ok().entity(resp).build();
        
            
        } 
        catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
           return Response.status(Response.Status.BAD_REQUEST).entity("Dohvatanje kategorija nije uspelo").build();
        }
    }
    
    @GET
    @Path("dohvatiVidee")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiVidee(){
        System.out.println("here");
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 20);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem2Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            List<Video> resp = null;
            if (msg instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage) msg;
                resp = (List<Video>) objMsg.getObject();
//                System.out.println(resp);
            }
             return Response.ok().entity(resp).build();
        
            
        } 
        catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
           return Response.status(Response.Status.BAD_REQUEST).entity("Dohvatanje videa nije uspelo").build();
        }
    }
 
    
    
    @POST
    @Path("dohvatiKategorijeZaVideo")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiKategorijeZaVideo(@FormParam("idv") int idv){
        System.out.println("Here");
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 21);
            mapMsg.setInt("idv", idv);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem2Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            System.out.println(msg);
            List<Kategorija> resp = null;
            if (msg instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage) msg;
                resp = (List<Kategorija>) objMsg.getObject();
//                System.out.println(resp);
            }
            
            if (resp == null){
                resp = new ArrayList<>();
                resp.add(null);
                System.out.println(resp);
            }
            
             return Response.ok().entity(resp).build();
        
            
        } 
        catch (JMSException ex) {
            Logger.getLogger(SubSystem2.class.getName()).log(Level.SEVERE, null, ex);
           return Response.status(Response.Status.BAD_REQUEST).entity("Dohvatanje kategorija za video nije uspelo").build();
        }
    }
}
