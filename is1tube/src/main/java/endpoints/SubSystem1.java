/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package endpoints;

import java.io.Serializable;
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
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.TextMessage;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import entities1.Mesto;
import entities1.Korisnik;


/**
 *
 * @author sofija
 */
@Path("sub1") 
@Produces(MediaType.APPLICATION_JSON)
public class SubSystem1 {
    @Resource(lookup="jms/__defaultConnectionFactory")
    private  ConnectionFactory connectionFactory;
    
    @Resource(lookup="SubSystem1QueueNew")
    private  Queue SubSystem1Queue;
    
    @Resource(lookup="ServerQueueNew")
    private  Queue ServerQueue;
    
    
    
    @POST
    @Path("kreirajMesto")
    public Response kreirajMesto(@FormParam("naziv") String naziv){
        try (JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(ServerQueue);) {
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 1);
            mapMsg.setString("naziv", naziv);
            mapMsg.setJMSReplyTo(ServerQueue);
//            System.out.println(mapMsg.getJMSReplyTo());
            
            producer.send(SubSystem1Queue, mapMsg);
            System.out.println("Poruka poslata");
           
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            }
            
            return Response.ok().entity(resp).build();
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem1.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje novog mesta nije uspelo").build();
        }
    }
    
    @POST
    @Path("kreirajKorisnika")
      // idk ime email godiste pol idm
    public Response kreirajKorisnika(@FormParam("ime") String ime, @FormParam("email") String email, @FormParam("godiste") int godiste,
            @FormParam("pol") char pol, @FormParam("idm") int idm){
        try(JMSContext context = connectionFactory.createContext();
            JMSConsumer consumer = context.createConsumer(ServerQueue);){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 2);
            mapMsg.setString("ime", ime);
            mapMsg.setString("email", email);
            mapMsg.setInt("godiste", godiste);
            mapMsg.setChar("pol", pol);
            mapMsg.setInt("idm", idm);
            mapMsg.setJMSReplyTo(ServerQueue);
//            System.out.println(mapMsg.getJMSReplyTo());
            
            producer.send(SubSystem1Queue, mapMsg);
            System.out.println("Poruka poslata");
           
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            }
            
            return Response.ok().entity(resp).build();
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem1.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Dodavanje novog korisnika nije uspelo").build();
        
        }
    }
    
    @POST
    @Path("promeniEmail")
    public Response promeniEmail(@FormParam("idk") int idk, @FormParam("noviEmail") String noviEmail){
        try (JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 3);
            mapMsg.setInt("idk", idk);
            mapMsg.setString("noviEmail", noviEmail);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem1Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            }
            
            return Response.ok().entity(resp).build();
            
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem1.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Promena email-a nije uspela").build();
        }
    }
    
    @POST
    @Path("promeniMesto")
    public Response promeniMesto (@FormParam("idk") int idk, @FormParam ("mesto") String mesto){
        System.out.println("here");
        try (JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 4);
            mapMsg.setInt("idk", idk);
            mapMsg.setString("mesto", mesto);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem1Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            String resp = "";
            if (msg instanceof TextMessage){
                TextMessage txtMsg = (TextMessage) msg;
                resp = txtMsg.getText();
            }
            
            return Response.ok().entity(resp).build();
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem1.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Promena mesta nije uspela").build();
        }
    }
    
    @GET
    @Path("dohvatiMesta")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiMesta(){
        System.out.println("here");
        
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 17);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem1Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            List<Mesto> resp = null;
            if (msg instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage) msg;
                resp = (List<Mesto>) objMsg.getObject();
                System.out.println(resp);
            }
             return Response.ok().entity(resp).build();
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem1.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Promena mesta nije uspela").build();
        
        }
       
    }
    
    
    @GET
    @Path("dohvatiKorisnike")
    @Produces(MediaType.APPLICATION_JSON)
    public Response dohvatiKorisnika(){
        try(JMSContext context = connectionFactory.createContext();
                JMSConsumer consumer = context.createConsumer(ServerQueue)){
            JMSProducer producer = context.createProducer();
            
            MapMessage mapMsg = context.createMapMessage();
            mapMsg.setInt("option", 18);
            mapMsg.setJMSReplyTo(ServerQueue);
            
            producer.send(SubSystem1Queue, mapMsg);
            System.out.println("Poruka poslata");
            
            Message msg = consumer.receive();
            List<Korisnik> resp = null;
            if (msg instanceof ObjectMessage){
                ObjectMessage objMsg = (ObjectMessage) msg;
                resp = (List<Korisnik>) objMsg.getObject();
//                System.out.println(resp);
            }
             return Response.ok().entity(resp).build();
        } catch (JMSException ex) {
            Logger.getLogger(SubSystem1.class.getName()).log(Level.SEVERE, null, ex);
            return Response.status(Response.Status.BAD_REQUEST).entity("Promena mesta nije uspela").build();
        
        }
       
    }

    
}

   