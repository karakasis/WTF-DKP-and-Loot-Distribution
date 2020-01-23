/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkp;

import java.util.Date;

/**
 *
 * @author Christos
 */
public class PlayerListing {
    public String playerName;
    public String boss;
    public int date;
    public Date dateFormat;
    public int dkpCost;
    
    public PlayerListing(String playerName, int date, String boss, int dkpCost){
        this.playerName = playerName;
        this.boss = boss;
        this.date = date;
        this.dkpCost = dkpCost;
        dateFormat = new java.util.Date((long)date*1000);
    }
    
    public void printListing(){
        System.out.println("\t==================");
        System.out.println("\tPlayer: "+ playerName);
        System.out.println("");
        System.out.println("\tDropped from: "+ boss);
        System.out.println("\tDate: "+ dateFormat.toString());
        System.out.println("\tPayed DKP: "+ dkpCost);
        System.out.println("");
        
    }
}
