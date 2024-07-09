/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package src;

import entities1.Korisnik;
import entities1.Mesto;
import entities2.Kategorija;
import entities2.Video;
import entities3.Gledanje;
import entities3.Ocena;
import entities3.Paket;
import entities3.Pretplata;
import java.time.LocalDateTime;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;


/**
 *
 * @author sofija
 */
public interface RetroFit_Interface {
    
    // 1.
    @FormUrlEncoded
    @POST("sub1/kreirajMesto")
    Call<String> kreirajMesto(@Field("naziv") String naziv);
    
    // 2.
    // idk ime email godiste pol idm
    @FormUrlEncoded
    @POST("sub1/kreirajKorisnika")
    Call<String> kreirajKorisnika(@Field("ime") String ime, @Field("email") String email, @Field("godiste") int godiste,
            @Field("pol") char pol, @Field("idm") int idm);
    
    //3 .
    @FormUrlEncoded
    @POST("sub1/promeniEmail")
    Call<String> promeniEmail (@Field("idk") int idk, @Field("noviEmail") String noviEmail);
    
    //4.
    @FormUrlEncoded
    @POST("sub1/promeniMesto")
    Call<String> promeniMesto (@Field("idk") int idk, @Field("mesto") String mesto);
    
    //17.
    @GET("sub1/dohvatiMesta")
    Call<List<Mesto>> dohvatiMesta();
    
    //18.
    @GET("sub1/dohvatiKorisnike")
    Call<List<Korisnik>> dohvatiKorisnike();
    
    //5.
    @FormUrlEncoded
    @POST("sub2/kreirajKategoriju")
    Call<String> kreirajKategoriju(@Field("naziv") String naziv);
    
    //6.
    // -- idv, naziv, trajanje, datumvreme, idk
    @FormUrlEncoded
    @POST("sub2/kreirajVideo")
    Call<String> kreirajVideo(@Field("naziv") String naziv, @Field("trajanje") int trajanje, @Field("idk") int idk);

    //7.
    // -- idv, novi naziv
    @FormUrlEncoded
    @POST("sub2/promeniNazivSnimka")
    Call<String> promeniNazivVidea(@Field("idv") int idv, @Field("noviNaziv") String noviNaziv);

    //8.
    // idv, naziv kategorije
    @FormUrlEncoded
    @POST("sub2/dodajKategorijuVideu")
    Call<String> dodajKategorijuVideu(@Field("idv") int idv, @Field("nazivKategorije") String nazivKategorije);


    // 16.
    // idk, idv
    @FormUrlEncoded
    @POST("sub2/obrisiVideo")
    Call<String> obrisiVideo(@Field("idk") int idk, @Field("idv") int idv);
    
    // 19.
    @GET("sub2/dohvatiKategorije")
    Call<List<Kategorija>> dohvatiKategorije();

    // 20.
    @GET("sub2/dohvatiVidee")
    Call<List<Video>> dohvatiVidee();
    
    // 21.
    @FormUrlEncoded
    @POST("sub2/dohvatiKategorijeZaVideo")
    Call<List<Kategorija>> dohvatiKategorijeZaVideo(@Field("idv") int idv);
    
    // 9.
    @FormUrlEncoded
    @POST("sub3/kreirajPaket")
    Call<String> dodajPaket(@Field("cena") double cena);
    
    // 10.
    @FormUrlEncoded
    @POST("sub3/promeniCenuPaketa")
    Call<String> promeniCenuPaketa(@Field("idp") int idp, @Field("novaCena") double novaCena);
    
    // 11.
    @FormUrlEncoded
    @POST("sub3/kreirajPretplatu")
    Call<String> kreirajPretplatu(@Field("datumVreme") String datumVreme, @Field("idk") int idk, @Field("idp") int idp);
    
    // 12.
    @FormUrlEncoded
    @POST("sub3/kreirajGledanje")
    // -- sekundPoc, duzinagl, idv, idk
    Call<String> kreirajGledanje(@Field("sekundPoc") int sekundPoc, @Field("duzinaGl") int duzinaGl, @Field("idv") int idv, @Field("idk") int idk);
 
    // 13.
    @FormUrlEncoded
    @POST("sub3/oceniVideo")
    Call<String> oceniVideo (@Field("idk") int idk, @Field("idv") int idv, @Field("ocena") int ocena);
    
    // 14.
    @FormUrlEncoded
    @POST("sub3/promeniOcenu")
    Call<String> promeniOcenu (@Field("idk") int idk, @Field("idv") int idv, @Field("novaOcena") int novaOcena);

    // 15.
    @FormUrlEncoded
    @POST("sub3/obrisiOcenu")
    Call<String> obrisiOcenu (@Field("idk") int idk, @Field("idv") int idv);

    // 22.
    @GET("sub3/dohvatiPakete")
    Call<List<Paket>> dohvatiPakete();
    
    // 23.
    @FormUrlEncoded
    @POST("sub3/dohvatiPretplateZaKorisnika")
    Call<List<Pretplata>> dohvatiPretplateZaKorisnika(@Field("idk") int idk);
    
    // 24.
    @FormUrlEncoded
    @POST("sub3/dohvatiGledanjaZaVideo")
    Call<List<Gledanje>> dohvatiGledanjaZaVideo(@Field("idv") int idv);
    
    // 25.
    @FormUrlEncoded
    @POST("sub3/dohvatiOceneZaVideo")
    Call<List<Ocena>> dohvatiOceneZaVideo(@Field("idv") int idv);
    
}
