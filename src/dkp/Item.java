/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkp;

import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Christos
 */
public class Item {
    public String itemName;
    public int hitem;
    private ArrayList<PlayerListing> players;
    private int pool;
    private double dropRate;
    
    public Item(String itemName, int hitem){
        this.itemName = itemName;
        this.hitem = hitem;
        players = new ArrayList<>();
    }
    
    public void addPlayerCustom(String name, int date, String boss, int cost){
        players.add(new PlayerListing(name,date,boss,cost));
    }
    
    public void printItemInfo(){
        int counter = 1;
        /*
        int pool = GuildMain.MC;
        if(players.get(0).boss.equals("Onyxia")) pool = GuildMain.ONYXIA;
        */
        getDropChance();
        
        System.out.println("\n==================\n");
        System.out.println("Item: "+ itemName);
        System.out.println("Wowhead link: https://classic.wowhead.com/item=" + hitem);
        System.out.println("");
        System.out.println("Total Raids "+ pool);
        System.out.println("No. of Drops : " + players.size());
        System.out.println("Drop Rate : " + dropRate + "%");
        
        for(PlayerListing player : players){
            System.out.println("\t"+counter+".");
            counter++;
            player.printListing();
        }
    }
    
    public void printItemInfoCondensed(){
        /*
        int pool = GuildMain.MC;
        if(players.get(0).boss.equals("Onyxia")) pool = GuildMain.ONYXIA;
        */
        getDropChance();
        
        System.out.println("\n==================\n");
        System.out.println("Item: "+ itemName);
        System.out.println("Wowhead link: https://classic.wowhead.com/item=" + hitem);
        System.out.println("");
        System.out.println("Total Raids "+ pool);
        System.out.println("No. of Drops : " + players.size());
        System.out.println("Drop Rate : " + dropRate + "%");
    }
    
    public int getListingSize(){
        return players.size();
    }
    
    public ArrayList<PlayerListing> getListings(){
        return players;
    }
    
    public double getDropChance(){
        int onyxiaCounter=0;
        int mcCounter=0;
        
        for(PlayerListing pl : players){
            if(pl.boss.equals("Onyxia")) onyxiaCounter++;
            else mcCounter++;
        }
        
        if (onyxiaCounter > mcCounter) pool = GuildMain.ONYXIA;
        else pool = GuildMain.MC;
        
        dropRate = (players.size()*1.0/pool)*100;
        return dropRate;
    }
    
}
