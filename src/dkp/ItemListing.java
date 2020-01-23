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
class ItemListing {
    public String itemName;
    public int hitem;
    public String boss;
    public int date;
    public Date dateFormat;
    public int dkpCost;
    
    public ItemListing(String itemName, int hitem, String boss, int date, int dkpCost){
        this.itemName = itemName;
        this.hitem = hitem;
        this.boss = boss;
        this.date = date;
        this.dkpCost = dkpCost;
        dateFormat = new java.util.Date((long)date*1000);
    }
    
    public void printListing(){
        System.out.println("==================");
        System.out.println("Item: "+ itemName);
        System.out.println("Wowhead link: https://classic.wowhead.com/item=" + hitem);
        System.out.println("");
        System.out.println("Dropped from: "+ boss);
        System.out.println("Date: "+ dateFormat.toString());
        System.out.println("Payed DKP: "+ dkpCost);
        System.out.println("");
        
    }
}
