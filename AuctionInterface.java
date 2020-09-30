/*****************************************************
 *AuctionInterface.java                              *
 *                                                   *
 *Interface to the Auction                           *
 *                                                   *
 *****************************************************/

import java.rmi.*;
import java.util.*;

public interface AuctionInterface extends Remote{
	// Create Auction Item
	public String createAuctionItem(String name, String des, double minValue, double maxValue, ClientInterface client) throws RemoteException;

	// Close an auction Item by taking Auction_ID as parameter
	public String closeAuctionItem(long id) throws RemoteException;

	// List all the open auctions on server
	public String listALLItems() throws RemoteException;

	// Set Bid for an auction item 
	public String setBid(ClientInterface bidder, long auctionID, double bidValue) throws RemoteException;
}