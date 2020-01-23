/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkp;

import java.util.ArrayList;

/**
 *
 * @author Christos
 */
public class Player {
    private String name;
    private ArrayList<ItemListing> items;
    private int dkp;
    private int raidsAttended;
    
    public Player(String name){
        this.name = name;
        items = new ArrayList<>();
    }
    
    public String getName(){
        return name;
    }
    
    public void addItemCustom(String item, int date, String boss, int cost){
        int hitemIndex = item.indexOf("Hitem") + 6;
        String hitemString = "";
        for(int c = hitemIndex; c<item.length(); c++){
            if(item.charAt(c) == ':') break;
            else{
                hitemString += item.charAt(c);
            }
        }
        int hitem = Integer.valueOf(hitemString);
        int index = item.indexOf('[');
        String itemName = item.substring(index+1, item.length()-6);
        
        items.add(new ItemListing(itemName,hitem,boss,date,cost));
        //System.out.println("New entry : \n Name: " + name + " \n Item: " + item + "\n Boss: " + boss + "\n DKP: " + cost + "  -  Date : "+ date);
        //System.out.println("Hitem : " + hitem + "  Item : "+itemName);
    }
    
    public void printLoot(){
        int counter = 1;
        System.out.println("History of "+ name);
        System.out.println("No. of Raids : " + raidsAttended);
        System.out.println("Current DKP : " + dkp);
        
        for(ItemListing item : items){
            System.out.println(counter+".");
            counter++;
            item.printListing();
        }
    }

    public void addDkpInfo(int dkpGained, int dkp) {
        this.dkp = dkp;
        raidsAttended = dkpGained;
    }
    
    public int getListingSize(){
        return items.size();
    }
    
    public ArrayList<ItemListing> getListings(){
        return items;
    }
    
    public int getRaids(){
        return raidsAttended;
    }
}
