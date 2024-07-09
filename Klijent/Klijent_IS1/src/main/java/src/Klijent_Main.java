/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package src;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import entities1.Korisnik;
import entities1.Mesto;
import entities2.Kategorija;
import entities2.Video;
import entities3.Gledanje;
import entities3.Ocena;
import entities3.Paket;
import entities3.Pretplata;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


/**
 *
 * @author sofija
 */
public class Klijent_Main {
    
    
    public static void main(String[] args){
        String baseUrl = "http://localhost:8080/is1tube/api/";
        Scanner scanner = new Scanner(System.in);
        
        Gson gson = new GsonBuilder()
            .setLenient()
            .create();
        
        OkHttpClient client = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        
        Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(ScalarsConverterFactory.create()) 
            .addConverterFactory(GsonConverterFactory.create())
            .build();
        
        RetroFit_Interface rfitInt = retrofit.create(RetroFit_Interface.class);
        
        while(true){
            System.out.println("=========================");
            System.out.print("Unesite broj opcije: ");
            int opt = scanner.nextInt();
            scanner.nextLine();
            switch (opt) {
                case 1:{
                    try {
                        System.out.println("Odabrali ste opciju - Kreiranje grada");
                        System.out.println();
                        System.out.print("Unesite naziv grada: ");
                        String naziv = scanner.nextLine();
                        
                        Call<String> odgovor = rfitInt.kreirajMesto(naziv);
                        String odgovorStr = odgovor.execute().body();
                        System.out.println(odgovorStr);
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 2:{
                    try {
                        // idk ime email godiste pol idm
                        System.out.println("Odabrali ste opciju - Kreiranje korisnika");
                        System.out.println();
                        System.out.print("Unesite ime: ");
                        String ime = scanner.nextLine();
                        System.out.println();
                        System.out.print("Unesite email: ");
                        String email = scanner.nextLine();
                        System.out.println();
                        System.out.print("Unesite godiste: ");
                        int godiste = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        if (godiste <= 0 || godiste > 2024) {
                            System.out.println("Godiste mora biti izmedju 1 i 2024");
                            continue;
                        }
                        System.out.print("Unesite pol: ");
                        char pol = scanner.nextLine().charAt(0);
                        System.out.println();
                        if (pol != 'Z' && pol != 'M' && pol != 'z' && pol != 'm'){
                            System.out.println("Pol mora biti M ili Z");
                            continue;
                        }
                        System.out.print("Unesite id mesta: ");
                        int idm = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        Call<String> odgovor = rfitInt.kreirajKorisnika(ime, email, godiste, pol, idm);
                        String odgovorStr = odgovor.execute().body();
                        System.out.println(odgovorStr);
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                    
                }
                
                case 3:{
                    try {
                        System.out.println("Odbrali ste opciju - promena emaila");
                        System.out.println();
                        System.out.print("Unesite idk korisnika: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.print("Uneiste novi email: ");
                        String noviEmail = scanner.nextLine();
                        Call<String> odgovor = rfitInt.promeniEmail(idk, noviEmail);
                        String odgovorStr = odgovor.execute().body();
                        System.out.println(odgovorStr);
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 4:{
                    try {
                        System.out.println("Odabrali ste opciju - promena mesta");
                        System.out.println();
                        System.out.print("Unesite idk korisnika: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.print("Unesite ime novog mesta: ");
                        String mesto = scanner.nextLine();
                        Call<String> odgovor = rfitInt.promeniMesto(idk, mesto);
                        String odgovorStr = odgovor.execute().body();
                        System.out.println(odgovorStr);
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 17:{
                    try {
                        System.out.println("Odabrali ste opciju - dohvatanje svih mesta");
                        System.out.println();
                        
                        Call<List<Mesto>> odgovor = rfitInt.dohvatiMesta();
                        List<Mesto> odgovorList = odgovor.execute().body();
                        for(Mesto m:odgovorList){
                            System.out.println(m);
                        }
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 18:{
                    try {
                        System.out.println("Odabrali ste opciju - dohvatanje svih korisnika");
                        System.out.println();
                        
                        Call<List<Korisnik>> odgovor = rfitInt.dohvatiKorisnike();
                        List<Korisnik> odgovorList = odgovor.execute().body();
                        for(Korisnik k:odgovorList) System.out.println(k);
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                case 5:{
                    try {
                        System.out.println("Odabrali ste opciju - kreiranje kategorije");
                        System.out.println();
                        System.out.print("Unesite naziv: ");
                        String naziv = scanner.nextLine();
                        System.out.println();
                        
                        Call<String> odgovor = rfitInt.kreirajKategoriju(naziv);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 6:{
                    try {
                        // -- idv, naziv, trajanje, datumvreme, idk
                        System.out.println("Odabrali ste opciju - kreiranje videa");
                        System.out.println();
                        System.out.print("Unesite naziv: ");
                        String naziv = scanner.nextLine();
                        System.out.println();
                        System.out.print("Unesite trajanje u sekundama: ");
                        int trajanje = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        if (trajanje <= 0) {
                            System.out.println("Trajanje mora biti vece od 0");
                            return;
                        }
                        System.out.print("Unesite id korisnika koji objavljuje video: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        
                        Call<String> odgovor = rfitInt.kreirajVideo(naziv, trajanje, idk);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 7: {
                    try {
                        System.out.println("Odabrali ste opciju - promena naziva videa");
                        System.out.println();
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite novi naziv: ");
                        String noviNaziv = scanner.nextLine();
                        System.out.println();
                        
                        Call<String> odgovor = rfitInt.promeniNazivVidea(idv, noviNaziv);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 8:{
                    try {
                        System.out.println("Odabrali ste opciju - dodavanje kategorije videu");
                        System.out.println();
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite naziv kategorije: ");
                        String nazivKategorije = scanner.nextLine();
                        System.out.println();
                        
                        Call<String> odgovor = rfitInt.dodajKategorijuVideu(idv, nazivKategorije);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }

                case 16:{
                    try {
                        System.out.println("Odabrali ste opciju - brisanje video snimka");
                        System.out.println();
                        System.out.print("Unesite Vas id: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        
                        Call<String> odgovor = rfitInt.obrisiVideo(idk, idv);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 19:{
                    try {
                        System.out.println("Odabrali ste opciju - dohvatanje svih kategorija");
                        Call<List<Kategorija>> odgovor = rfitInt.dohvatiKategorije();
                        List<Kategorija> odgovorList = odgovor.execute().body();
                        for(Kategorija k:odgovorList) System.out.println(k);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }

                case 20:{
                    try {
                        System.out.println("Odabrali ste opciju - dohvatanje svih videa");
                        Call<List<Video>> odgovor = rfitInt.dohvatiVidee();
                        List<Video> odgovorList = odgovor.execute().body();
                        for(Video v:odgovorList) System.out.println(v);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 21:{
                    try {
                        System.out.println("Odabrali ste opciju - dohvatanje kategorija za odredjeni video");
                        System.out.println();
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        Call<List<Kategorija>> odgovor = rfitInt.dohvatiKategorijeZaVideo(idv);
                        List<Kategorija> odgovorList = odgovor.execute().body();
                        if (odgovorList.size() == 1 && odgovorList.get(0) == null) {
                            System.out.println("Ne postoji video sa id " + idv);
                            break;
                        }
                        
                        if (odgovorList.isEmpty()){
                            System.out.println("Video sa idv " + idv + " nema nijednu dodeljenju kategoriju");
                            break;
                        }
                        for(Kategorija k:odgovorList) System.out.println(k);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 9:{
                    try {
                        System.out.println("Odabrali ste opciju - Kreiranje paketa");
                        System.out.println();
                        System.out.print("Unesite cenu paketa: ");
                        double cena = Double.parseDouble(scanner.nextLine());
                        System.out.println();
                        if (cena < 0 ){
                            System.out.println("Cena mora biti veca ili jednaka 0");
                            break;
                        }
                        Call<String> odgovor = rfitInt.dodajPaket(cena);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 10:{
                    try {
                        System.out.println("Odabrali ste opciju - Promena cene paketa");
                        System.out.println();
                        System.out.print("Unesite id paketa: ");
                        int idp = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite novu cenu paketa: ");
                        double novaCena = Double.parseDouble(scanner.nextLine());
                        System.out.println();
                        if (novaCena < 0){
                            System.out.println("Cena mora biti veca ili jednaka 0");
                            break;
                        }
                        Call<String> odgovor = rfitInt.promeniCenuPaketa(idp, novaCena);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                    
                    break;
                }
                
                case 11:{
                    try {
                        System.out.println("Odabrali ste opciju - Kreiraj pretplatu");
                        System.out.println();
                        System.out.print("Unesite datum i vreme pocetka pretplate (yyyy-mm-dd hh:mm): ");
                        String datumVreme = scanner.nextLine();
                        System.out.println();
                        System.out.print("Unesite id korisnika: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite id paketa: ");
                        int idp = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        
                        Call<String> odgovor = rfitInt.kreirajPretplatu(datumVreme, idk, idp);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        
                    }
                    
                    break;
                }
                
                case 12:{
                    try {
                        // -- sekundPoc, duzinagl, idv, idk
                        System.out.println("Odabrali ste opciju - kreiraj gledanje");
                        System.out.println();
                        System.out.print("Unesite sekund pocetka gledanja videa: ");
                        int sekundPoc = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        if (sekundPoc <=0 ){
                            System.out.println("Sekund pocetka gledanja mora biti veci od 0");
                            break;
                        }
                        System.out.print("Unesite duzinu gledanja videa: ");
                        int duzinaGl = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        if (duzinaGl < 0 ){
                            System.out.println("Duzina gledanja mora biti veca ili jednaka 0");
                            break;
                        }
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite Vas id: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        
                        Call<String> odgovor = rfitInt.kreirajGledanje(sekundPoc, duzinaGl, idv, idk);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }

                case 13:{
                    try {
                        System.out.println("Odabrali ste opciju - ocenjivanje video snimka");
                        System.out.println();
                        System.out.print("Unesite Vas id: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite ocenu: ");
                        int ocena = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        if (ocena < 1 || ocena >5) {
                            System.out.println("Ocena mora biti izmedju 1 i 5");
                            break;
                        }
                        
                        Call<String> odgovor = rfitInt.oceniVideo(idk, idv, ocena);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 14:{
                    try {
                        System.out.println("Odabrali ste opciju - promena ocene");
                        System.out.println();
                        System.out.print("Unesite Vas id: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite novu ocenu: ");
                        int novaOcena = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        if (novaOcena < 1 || novaOcena >5) {
                            System.out.println("Ocena mora biti izmedju 1 i 5");
                            break;
                        }
                        
                        Call<String> odgovor = rfitInt.promeniOcenu(idk, idv, novaOcena);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }

                case 15:{
                    try {
                        System.out.println("Odabrali ste opciju - brisanje ocene");
                        System.out.println();
                        System.out.print("Unesite Vas id: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                       
                        Call<String> odgovor = rfitInt.obrisiOcenu(idk, idv);
                        String odgovorString = odgovor.execute().body();
                        System.out.println(odgovorString);
                        
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 22:{
                    try {
                        System.out.println("Odabrali ste opciju - dohvatanje svih paketa");
                        Call<List<Paket>> odgovor = rfitInt.dohvatiPakete();
                        List<Paket> odgovorList = odgovor.execute().body();
                        for(Paket p:odgovorList) System.out.println(p);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 23:{
                    try {
                        System.out.println("Odabrali ste opciju - dohvatanje pretplata za korisnika");
                        System.out.println();
                        System.out.print("Unesite id korisnika: ");
                        int idk = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        Call<List<Pretplata>> odgovor = rfitInt.dohvatiPretplateZaKorisnika(idk);
                        List<Pretplata> odgovorList = odgovor.execute().body();
                        if (odgovorList.size() == 1 && odgovorList.get(0) == null) {
                            System.out.println("Ne postoji korisnik sa id " + idk);
                            break;
                        }
                        
                        for(Pretplata p:odgovorList) System.out.println(p);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }
                
                case 24:{
                    try {
                        System.out.println("Odabrali ste opciju - dohvatanje gledanja za snimak");
                        System.out.println();
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        Call<List<Gledanje>> odgovor = rfitInt.dohvatiGledanjaZaVideo(idv);
                        List<Gledanje> odgovorList = odgovor.execute().body();
                        if (odgovorList.size() == 1 && odgovorList.get(0) == null) {
                            System.out.println("Ne postoji video sa id " + idv);
                            break;
                        }
                        
                        for(Gledanje g:odgovorList) System.out.println(g);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }

                case 25:{
                    try {
                        System.out.println("Odabrali ste opciju - dohvatanje ocene za snimak");
                        System.out.println();
                        System.out.print("Unesite id videa: ");
                        int idv = Integer.parseInt(scanner.nextLine());
                        System.out.println();
                        Call<List<Ocena>> odgovor = rfitInt.dohvatiOceneZaVideo(idv);
                        List<Ocena> odgovorList = odgovor.execute().body();
                        if (odgovorList.size() == 1 && odgovorList.get(0) == null) {
                            System.out.println("Ne postoji video sa id " + idv);
                            break;
                        }
                        
                        for(Ocena o:odgovorList) System.out.println(o);
                        
                        break;
                    } catch (IOException ex) {
                        Logger.getLogger(Klijent_Main.class.getName()).log(Level.SEVERE, null, ex);
                        return;
                    }
                }

             

                default:
                    System.out.println("NEPOZNATA OPCIJA!");
            }
        }
    }
    
    
}
