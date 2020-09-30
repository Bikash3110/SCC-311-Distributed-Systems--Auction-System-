/*****************************************************
 *Auction.java                                       *
 *                                                   *
 *Implementation of the Auction                      *
 *Implements the meathods in AuctionInterface        *
 *****************************************************/

import java.rmi.*;
import java.io.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;
import java.util.*;

public class Auction implements AuctionInterface{

    // Stores all AuctionItems in it by Auction ID
	private ConcurrentHashMap<Long, AuctionItem> auctionMap;

	public Auction() throws RemoteException{
		auctionMap = new ConcurrentHashMap<>(); //Creates a new, empty map 
	} 

	/*
	* Creates the AuctionItem by taking
	* @Para name, description, starting price, ReservePrice, ClientInterface
	* Returns an Auction ID once created
	*/
	public String createAuctionItem(String name, String des, double minValue, double maxValue, ClientInterface client) throws RemoteException{
		String result = " ";
		long id = 0;

		id = random();
		AuctionItem a = new AuctionItem(id, name, des, minValue, maxValue, client);
		auctionMap.put(id, a);
		result = "Created auction successfully with aution ID: " + id;
		return result;
	}

	/*
	* Closes an auction by taking the AuctionItem ID as
	* @param id
	*/
	public String closeAuctionItem(long id) throws RemoteException{
		String result= " ";
		if(auctionMap.isEmpty()){
			result = "There are currently no aution item in system to close";
		}else{
			// Checks the id in the map and remove the AuctionItem if found
			for(Entry<Long, AuctionItem> entry : auctionMap.entrySet()){
				if(id == entry.getKey()){
					entry.getValue().closeAuction();
					auctionMap.remove(id, entry.getValue());
					result = "Auction with AuctionID: "+ id +" Closed.";
				}else{
					System.out.println("Please Enter a valid ID.");
				}
			}

		}
		return result;
	}

	/*
	* Lists all active the Auction Item(s) presents in the 
	* concurrentHashMap.
	*/
	public String listALLItems() throws RemoteException{
		String result = " ";
		if(auctionMap.isEmpty()){
			result = " There are currently no auction items in the system. ";
		}else{
			for(Entry<Long, AuctionItem> entry : auctionMap.entrySet()){
				result = result + entry.getValue().getItemDetails();
			}
		}
		return result;
	}

	/*
	* Can bid for an AuctionItem by providing
	* @param Client Info, AuctionID, bidValue
	* and get bid status as return
	*/
	public String setBid(ClientInterface bidder, long auctionID, double bidValue) throws RemoteException{
        String result = " ";
        AuctionItem a = auctionMap.get(auctionID);
        if(a == null){
        	result = "Please enter a valid ID";
        }else if(bidValue <= a.getStartPrice()){
        	result = "Please place a higher bid";
        }else if(a.getStatus() == "closed"){
        	result = "Sorry, this auction is closed ";
        }else{
        	a.bid(bidder, bidValue);
        	result = "Your bid has been placed !!! ------- Auction ID: " + auctionID;
        }
        return result;
	}

	/*
	* Generates a random number between 11111 and 99999
	*/
	public long random(){
		Random rand = new Random();
		long n = rand.nextInt(99999) + 11111; //Min is 11111, Max is 99999
		return n;
	}
}