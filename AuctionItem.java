/*****************************************************
 *AuctionItem.java                                   *
 *                                                   *
 *Can create Auction_Item in auction                 *
 *through this class.                                *
 *****************************************************/

import java.io.*;
import java.rmi.*;
import java.text.*;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.*;

public class AuctionItem implements Serializable{

	private long ID;
	private String item_name;
	private String item_description;
	private double start_price;
	private double reserve_price;
	private String status;
	private ClientInterface owner;
	private ClientInterface last_bidder;
	private String last_bidderName;
	private Hashtable<String, ClientInterface> bids;

    /*
	* This Constructor holds the attributes of an AuctionItem.
    */
	public AuctionItem(long auctionID, String name, String des, double minValue, double maxValue, ClientInterface client){
		ID = auctionID;
		item_name = name;
		item_description = des;
		start_price = minValue;
		reserve_price = maxValue;
		owner = client;
		status = "open";
		bids = new Hashtable<>();
	}

	/*
	* Sets the bid and sends message to the bidder(s) on their bid status
	* @param ClientInterface, bidValue.
	*/	
    public synchronized void bid(ClientInterface c, double value){
    	try{
    		// put the bidder in the map
    		bids.put(c.getEmail(), c);
    		last_bidder = c;
    		last_bidderName = c.getName();
 			start_price = value;
            
            // Sends the bid status back to bidders
            for(Entry<String, ClientInterface> entry : bids.entrySet()){
            	ClientInterface x = entry.getValue();
            	if(x == last_bidder){
            		last_bidder.getMessage("You bid has been successfully placed for item: "+ item_name + " for " + start_price +"\n");
            	}else{
            		x.getMessage("You bid for Item: "+ item_name + " was outbidded at price of  "+ start_price + " by " + last_bidderName +"\n");
            	}
            }
    	}catch(Exception e){
    		e.printStackTrace();
    	}

    }	
  	
  	/*
	* sets the Auction status to "closed " and 
	* notify the bidders and the seller.
  	*/
    public synchronized void closeAuction(){
    	status = "closed";

    	try{
    		if(last_bidder == null){
    			owner.getMessage("\n Your Auction Item: "+ ID +", "+ item_name +" has closed with no bids \n");
    		}
    		else{
    			owner.getMessage("\n Your Auction Item: "+ ID +", "+ item_name +" has a winner --> "+ last_bidderName + " Bid Price: "+ start_price + " Email: "+ last_bidder.getEmail() +"\n");
    			for(Entry<String, ClientInterface> entry : bids.entrySet()){
    				ClientInterface bidder = entry.getValue();
    				if(bidder == last_bidder){
    					last_bidder.getMessage("\n Congrats!!! You have won the bid for --> "+ item_name+ "  at the price " +start_price );
    				}else{
    					bidder.getMessage("\n An Auction item --> "+ item_name + " you bid on was outbidded and won by" + last_bidderName +" for "+ start_price +"\n");
    				}
    			}  
    		}
    	}catch(Exception e){
    		e.printStackTrace();
    	}
    }

    /*
	* Get the detail of AuctionItem like name, AuctionID
	* StartPrice, ReservePrice, AuctionStatus.
    */
    public synchronized String getItemDetails(){
    	String s = "\n========================================";
    	s += "\nID: "+ ID;
    	s += "\nName: "+ item_name;
    	s += "\nStarting Price: "+ start_price;
    	s += "\nNumber Of bidders: " + bids.size();
    	s += "\n status: " + status;

    	if(status == "open"){
    		if(last_bidder == null){
  				s += "\nLast Bidder: No bids yet";
    		}else{
    			s += "\n last_bidder: "+ last_bidderName;
    		}
    	}else if(status == "closed"){
    		if(last_bidder == null){
  				s += "\nLast Bidder: Closed with No bids";
    		}else{
    			s += "\n Won by: "+ last_bidderName +" at price of "+ start_price;
    		}
    	}
    	s += "\n========================================";
        return s;
    }

    /*
	*Get AutionItem Name
    */
    public synchronized String getName(){
    	return item_name;
    }

    /*
	* Get AuctionItem Start Price
    */
    public synchronized double getStartPrice(){
    	return start_price;
    }

    /*
	* Get AuctionItem Reserve Price
    */
    public synchronized double getReservePrice(){
    	return reserve_price;
    }

    /*
	* Get Auctionitem Description
    */    
    public synchronized String getDescription(){
    	return item_description;
    }

    /*
	* Get AuctionItem auction Status ( i.e Open/Closed ) 
    */
	public synchronized String getStatus(){
		return status;
	}
}