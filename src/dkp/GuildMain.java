/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dkp;

import java.io.InputStream;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Scanner;



/**
 *
 * @author Christos
 */
public class GuildMain {
    

    private static ArrayList<Player> roster;
    public static int MC = 32;
    public static int ONYXIA = 40;
    
    
    public static void main(String[] args) 
    {
        String filePath = "items.txt";
        String data  = usingBufferedReader( filePath );
        //System.out.println( data );
        roster = new ArrayList<>();
        
        buildRoster(data);
        filePath = "dkp.txt";
        data  = usingBufferedReader( filePath );
        
        buildPlayerInfo(data);
        
        //clearErrors(); <- dont use this
        
        /*
        You can tweak with those functions.
        */
        //getItemsFromPlayer("Akti"); // get only a specific player's info
        //getItemPerRaidRatio();  // prints ratio of items received per person based on how many raids he attended
        //0 = sort by name, 1 = sory by drop chance
        getItemDropChance(1); // prints drop chance for each item on all our raids
    }
 
    //Read file content into string with - using BufferedReader and FileReader
    //You can use this if you are still not using Java 8
 
    private static String usingBufferedReader(String filePath) 
    {
        InputStream input = GuildMain.class.getResourceAsStream(filePath);
        Scanner s = new Scanner(input).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";
        return result;
    }

    private static void buildPlayerInfo(String data){
        List<String> myList = new ArrayList<String>(Arrays.asList(data.split("\n")));
        myList.remove(0);
        //Create pointers for brackets
        ArrayList<Integer[]> brackets = new ArrayList<>();
        for(int a=0; a<myList.size(); a++){
            if (myList.get(a).contains("{")){
                Integer[] table = new Integer[2];
                table[0] = a + 1; // skip the { line
                for(int b = a + 1; b<myList.size(); b++){
                    if(myList.get(b).contains("}")){
                        table[1] = b - 1; // skip the } line
                        a = b - 1;
                        break;
                    }
                }
                brackets.add(table);
            }
        }
        
        //Go through the pointers
        for(Integer[] bracket : brackets){
            String name = "";
            int dkpGained = -1;
            int dkp = -999;
            String boss = "";
            Player pl = null;
            for(int a = bracket[0]; a<=bracket[1]; a++){
                String dataString = myList.get(a);
                if(dataString.contains("player")){
                    //NAME
                    int index = dataString.indexOf('=');
                    name = dataString.substring(index + 3, dataString.length()-3);
                    //You found a name. Look if the player exists
                    pl = findPlayer(name);
                    if(pl==null){
                        //New player.
                        pl = new Player(name);
                        roster.add(pl);
                    }
                }
                else if(dataString.contains("lifetime_gained")){
                    //DKP GAINED - RAIDS ATTENDED
                    int index2 = dataString.indexOf('=');
                    String dkpGainedS = dataString.substring(index2 + 2, dataString.length() - 2);
                    dkpGained = Integer.valueOf(dkpGainedS);
                }
                else if(dataString.contains("dkp")){
                    //DKP GAINED - RAIDS ATTENDED
                    int index2 = dataString.indexOf('=');
                    String dkpHaveS = dataString.substring(index2 + 2, dataString.length() - 2);
                    dkp = Integer.valueOf(dkpHaveS);
                }
            }
            pl.addDkpInfo(dkpGained,dkp);
        }
    }
    
    private static void buildRoster(String data) {
        List<String> myList = new ArrayList<String>(Arrays.asList(data.split("\n")));
        myList.remove(0);
        //Create pointers for brackets
        ArrayList<Integer[]> brackets = new ArrayList<>();
        for(int a=0; a<myList.size(); a++){
            if (myList.get(a).contains("{")){
                Integer[] table = new Integer[2];
                table[0] = a + 1; // skip the { line
                for(int b = a + 1; b<myList.size(); b++){
                    if(myList.get(b).contains("}")){
                        table[1] = b - 1; // skip the } line
                        a = b - 1;
                        break;
                    }
                }
                brackets.add(table);
            }
        }
        
        //Go through the pointers
        for(Integer[] bracket : brackets){
            String name = "";
            String item = "";
            int date = -1;
            int dkp = -999;
            String boss = "";
            Player pl = null;
            for(int a = bracket[0]; a<=bracket[1]; a++){
                String dataString = myList.get(a);
                if(dataString.contains("player")){
                    //NAME
                    int index = dataString.indexOf('=');
                    name = dataString.substring(index + 3, dataString.length()-3);
                    //You found a name. Look if the player exists
                    pl = findPlayer(name);
                    if(pl==null){
                        //New player.
                        pl = new Player(name);
                        roster.add(pl);
                    }
                }
                else if(dataString.contains("loot")){
                    //ITEM
                    int index1 = dataString.indexOf('=');
                    item = dataString.substring(index1 + 2, dataString.length()-2);
                }
                else if(dataString.contains("boss")){
                    //BOSS
                    int index3 = dataString.indexOf('=');
                    boss = dataString.substring(index3 + 3, dataString.length() - 3);
                }
                else if(dataString.contains("date")){
                    //DATE
                    int index2 = dataString.indexOf('=');
                    String dateS = dataString.substring(index2 + 2, dataString.length() - 2);
                    date = Integer.valueOf(dateS);
                }
                else if(dataString.contains("cost")){
                    //DKP
                    int index4 = dataString.indexOf('=');
                    String dkpS = dataString.substring(index4 + 2, dataString.length() - 2);
                    dkp = Integer.valueOf(dkpS);
                }
            }
            pl.addItemCustom(item,date,boss,dkp);
        }
        
    }
    
    private static Player findPlayer(String byName){
        for(Player p : roster){
            if(p.getName().equals(byName)){
                return p;
            }
        }
        return null;
    }

    private static void getItemsFromPlayer(String byName) {
        for(Player pl : roster){
            if(pl.getName().equals(byName)){
                pl.printLoot();
            }
        }
    }
    
    
    //EXTRA STAT FUNCTIONS
    private static void getItemPerRaidRatio(){
        HashMap<String,Double> iprr = new HashMap<>();
        for(Player pl : roster){
            if(pl.getRaids() != 0)//quick fix to not show deleted entries
                iprr.put(pl.getName(),roundTwoDecimals(pl.getListingSize()*1.0/pl.getRaids()));
        }
        /*
        iprr.entrySet().stream()
                .sorted((k1, k2) -> -k1.getValue().compareTo(k2.getValue()));
        int counter = 1;*/
        iprr.entrySet().stream()
   .sorted(Map.Entry.comparingByValue())
   .forEach(System.out::println);
    }
    
    private static void clearErrors(){
        for(Player pl : roster){
            HashSet<String> checker = new HashSet<>();
            HashSet<String> done = new HashSet<>();
            for(ItemListing item : pl.getListings()){
                if(checker.contains(item.itemName)){
                    if(!done.contains(item.itemName)){
                        System.out.println(pl.getName());
                        System.out.println("==========");
                        for(ItemListing itemRedo : pl.getListings()){
                            if(itemRedo.itemName.equals(item.itemName)){
                                itemRedo.printListing();
                            }
                        }
                    }
                    else done.add(item.itemName);
                    
                }else checker.add(item.itemName);
            }
        }
    }
    
    /**
    * Util function
    */
    private static double roundTwoDecimals(double d) {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
      }

    private static void getItemDropChance(int sortCode) {
        HashSet<String> items = new HashSet<>();
        HashMap<String,Integer> itemToHitem = new HashMap<>();
        for( Player pl : roster){
            for( ItemListing list : pl.getListings()){
                items.add(list.itemName);
                itemToHitem.put(list.itemName, list.hitem);
            }
        }
        
        //Reverse now 
        ArrayList<String> itemsList = new ArrayList<>(items);
        Collections.sort(itemsList);
        ArrayList<Item> collectionItems = new ArrayList<>();
        for(String s : itemsList){
            Item i = new Item(s,itemToHitem.get(s));
            //Loop over all players and find listings
            for(Player pl : roster){
                for(ItemListing il : pl.getListings()){
                    if(il.itemName.equals(s)){
                        i.addPlayerCustom(pl.getName(), il.date, il.boss, il.dkpCost);
                        break;
                    }
                }
            }
            collectionItems.add(i);
        }

        if(sortCode == 0){
          //Print results , sorted by name
            for(Item i : collectionItems){
                i.printItemInfo();
            }

            //Print results , sorted by name
            for(Item i : collectionItems){
                i.printItemInfoCondensed();
            }  
        }
        if(sortCode == 1){
           //Print results sorted by Drop chance
            Collections.sort(collectionItems, new Comparator<Item>(){
                public int compare(Item o1, Item o2){
                    if(o1.getDropChance() == o2.getDropChance())
                        return 0;
                    return o1.getDropChance() < o2.getDropChance() ? -1 : 1;
                }
           });

            //Print results , sorted by name
            for(Item i : collectionItems){
                i.printItemInfo();
            }

            //Print results , sorted by name
            for(Item i : collectionItems){
                i.printItemInfoCondensed();
            } 
        }
        
    }
}
