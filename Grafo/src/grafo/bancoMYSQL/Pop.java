/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo.bancoMYSQL;

/**
 *
 * @author Jefferson
 */
public class Pop {
    private int id;
    private String Pop1="";
    private String Pop2="";

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the Pop1
     */
    public String getPop1() {
        return Pop1;
    }

    /**
     * @param Pop1 the Pop1 to set
     */
    public void setPop1(String Pop1) {
        this.Pop1 = Pop1;
    }

    /**
     * @return the Pop2
     */
    public String getPop2() {
        return Pop2;
    }

    /**
     * @param Pop2 the Pop2 to set
     */
    public void setPop2(String Pop2) {
        this.Pop2 = Pop2;
    }

    public Pop() {
    }
    
     public String toString() {
                String s = "";
                if(this.getPop1()!=null){
                s+= this.getPop1();
                }
                if(this.getPop2()!=null){
                s+= this.getPop2();
                }
                return s;
        }
}
