package tikape.runko;

import java.util.HashMap;
import spark.ModelAndView;
import static spark.Spark.*;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import tikape.runko.database.AlueDao;
import tikape.runko.database.Database;
import tikape.runko.database.KetjuDao;
import tikape.runko.database.ViestiDao;
import tikape.runko.domain.Alue;

public class Main {

    public static void main(String[] args) throws Exception {
        
        if (System.getenv("PORT") != null) {
            port(Integer.valueOf(System.getenv("PORT")));
        }
        
        String jdbcOsoite = "jdbc:sqlite:foorumi.db";
        if (System.getenv("DATABASE_URL") != null) {
            jdbcOsoite = System.getenv("DATABASE_URL");
        }
        
        
        Database database2 = new Database(jdbcOsoite);
        database2.init();

        AlueDao alueDao = new AlueDao(database2);
        KetjuDao ketjuDao = new KetjuDao(database2);
        ViestiDao viestiDao = new ViestiDao(database2);

        get("/", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viesti", "tervehdys");

            return new ModelAndView(map, "index");
            
            
        }, new ThymeleafTemplateEngine());
        
        
        //Listaa kaikki alueet, toteutus html-filussa
        get("/alueet", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("alueet", alueDao.findAllmuokattu());

            return new ModelAndView(map, "alueet");
            
        }, new ThymeleafTemplateEngine());
        
        //Lisää uuden alueen
        post("/alue", (req, res) -> {
            if (!req.queryParams("alueNimi").isEmpty()) {
                alueDao.lisaaAlue(req.queryParams("alueNimi"));
            }
            res.redirect("/alueet");
            return "";
        });
        
        //Listaa kaikki tietyn alueen alaisuudessa olevat ketjut ketjuun liittyvnä alue-fk:n mukaan
        get("/alueet/:id/ketjut", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("ketjut", ketjuDao.findAllForAlueIdKymmenenUusinta(Integer.parseInt(req.params(":id"))));
            map.put("alueenId", req.params(":id"));
            return new ModelAndView(map, "ketjut");
        }, new ThymeleafTemplateEngine());
        
        
        //Lisää uusi ketju
        post("/alueet/:id/ketjut", (req, res) -> {
            
            ketjuDao.lisaaKetju(req.queryParams("ketjunNimi"), Integer.parseInt(req.params(":id")));
            int ketju = ketjuDao.findLatest();
            res.redirect("/alueet/" + req.params(":id") + "/ketjut/" + ketju);
            return "";
        });
        //LIsää uusi ketju ja viesti ketjuun
        
        //Listaa kaikki ketjuun liittyvät viestit viestiin liittyvän ketju-fk:n mukaan
        get("/alueet/:id/ketjut/:id2", (req, res) -> {
            HashMap map = new HashMap<>();
            map.put("viestit", viestiDao.findAllForKetjuId(Integer.parseInt(req.params(":id2"))));
            map.put("alueenId", req.params(":id"));
            map.put("ketjunId", req.params(":id2"));
            return new ModelAndView(map, "viestit");
        }, new ThymeleafTemplateEngine()); 

        //Vastaanottaa kirjoittajan viesti, nyt toimii, huom. mappi get("/alueet/:id/ketjut/:id2"
        // sisältää nyt tarvittavat Id:t
        post("/viestit/:id/:id2", (req, res) -> {
            String kayttaja = req.queryParams("kayttaja");
            String viesti = req.queryParams("viesti");
            if (!kayttaja.isEmpty() && !viesti.isEmpty()) {
                viestiDao.lisaaViesti(kayttaja, viesti, Integer.parseInt(req.params(":id2")));
            }
            
            if (ketjuDao.tarkistaKetju(ketjuDao.findLatest())) {
                res.redirect("/alueet/" + req.params(":id") + "/ketjut");
            } else {
                res.redirect("/alueet/" + req.params(":id") + "/ketjut/" + req.params(":id2"));
            }
            return "";
        });
        

    }
}
