/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tikape.runko.domain;

import java.sql.Date;

/**
 *
 * @author Kim
 */
public class Ketju {
    
    private Integer id;
    private String otsikko;
    private Integer alueid;
    private Integer lkm;
    
    public Ketju(Integer id, String otsikko, Integer alueid) {
        this.id = id;
        this.otsikko = otsikko;
        this.alueid = alueid;
    }
    
    public Ketju(Integer id, String otsikko, Integer alueid, Integer lkm) {
        this.id = id;
        this.otsikko = otsikko;
        this.alueid = alueid;
        this.lkm = lkm;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setOtsikko(String otsikko) {
        this.otsikko = otsikko;
    }

    public void setAlueid(Integer alueid) {
        this.alueid = alueid;
    }

    public Integer getId() {
        return id;
    }

    public String getOtsikko() {
        return otsikko;
    }

    public Integer getAlueid() {
        return alueid;
    }
    
    public int getLkm() {
        return lkm;
    }
    
}
