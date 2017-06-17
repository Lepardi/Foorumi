/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

/**
 *
 * @author Kim
 */
public class Alue {
    private Integer id;
    private String aihe;
    private Integer lkm;
    
    public Alue(Integer id, String aihe) {
        this.id = id;
        this.aihe = aihe;
    }
    
    public Alue(Integer id, String aihe, Integer lkm) {
        this.id = id;
        this.aihe = aihe;
        this.lkm = lkm;
    }

    public Integer getId() {
        return id;
    }

    public String getAihe() {
        return aihe;
    }
    
    public int getLkm() {
        return lkm;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setAihe(String aihe) {
        this.aihe = aihe;
    }
    
}
