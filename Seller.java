/*****************************************************
 *Seller.java                                        *
 *                                                   *
 *Provide Sellers with a way to Create, close (or)   *
 *view active AuctionItems                           *
 *****************************************************/

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.net.MalformedURLException;
import java.rmi.NotBoundException;
import java.util.concurrent.ConcurrentHashMap;
import java.util.Map.Entry;
import java.util.Scanner;
import java.util.InputMismatchException;
import java.util.*;

public class Seller extends Buyer{
	final static String host = "localhost";
	final static String serverName = "AuctionServer";
	private ConcurrentHashMap<Long, AuctionItem> sellerMap;
	private long sellerID;

	public Seller(){
		super();
		sellerMap = new ConcurrentHashMap<>(); //Creates a new, empty map

		Random rand = new Random();
		long n = rand.nextInt(9999) + 1111;
		sellerID = n;
		System.out.println("Your SellerID: " + sellerID);
	}

	public static void main(String[] args){
		
		try{	
			AuctionInterface server = (AuctionInterface) Naming.lookup("rmi://"+ host +"/"+ serverName);
		    System.out.println("Connected Server \n");

		    ClientImplement y = new ClientImplement();
		    ClientInterface buyer = (ClientInterface) UnicastRemoteObject.exportObject(y,0);

		    displayOption(server, buyer);

		}catch(MalformedURLException m){
			System.out.println(m);
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*
	* Display options to Seller through which they can 
	* interact with the Auction like create, close, view or exit.
	* @param AuctionInterface, ClientInterface
	*/
	public static void displayOption(AuctionInterface a, ClientInterface c){
		while(true){
		    int choice = 0;
			System.out.println("[1] View all Auction Item(s).   [2] Create an auction  [3] Close Auction  [4] Exit");
			System.out.println("--------------------------------------------------------------");
			System.out.println("Choose an option:");

			Scanner scan = new Scanner(System.in);
			choice =scan.nextInt();

			switch(choice){
				case 1:
				    listAll(a);
				    break;

				case 2:
					createAuction(a, c);
					break;

				case 3:
					closeAuction(a); 
					break;
					//Tip: Please make sure only owner can close their auction	
					
				case 4:
					System.out.println("You have Exited.");
					System.exit(0);
					break;

				default:
					System.out.println("Invalid Option");
			}
		}
	}

	/*
	* Seller can create an auction for an auctionItem by specifying 
	* itemName, description, StartPrice, ReservePrice
	* @param AuctionInterface, ClientInterface
	*/
	public static void createAuction(AuctionInterface a, ClientInterface c){
		try{
			System.out.println("Enter Item name: ");
			Scanner scan = new Scanner(System.in);
			String name =scan.nextLine();

			System.out.println("Enter Item Description: ");
			String des =scan.nextLine();

		    System.out.println("Enter Starting Price: ");
			double min = Double.parseDouble(scan.nextLine());
			while(min < 0){
				System.out.println("Starting Price must be positive int");
				System.out.println("Enter Start Price:");
			    min = Double.parseDouble(scan.nextLine());
			}

			System.out.println("Enter Reserve Price: ");
			double res = Double.parseDouble(scan.nextLine());

			while(min > res){
				System.out.println("Reserve Price lower than Starting Price ");
				System.out.println("Enter Reserve Price:");
			    res = Double.parseDouble(scan.nextLine());
			}

			//sellerMap.put(sellerID, a.createAuctionItem(name, des, min, res, c));
			System.out.println(a.createAuctionItem(name, des, min, res, c));
		}catch(NumberFormatException n){
			System.out.println("Error: Enter a Number");
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	/*
	* Can close an acuction by specifying AuctionID 
	* @param AuctionInterface.
	*/
	public static void closeAuction(AuctionInterface a){

		try{
			System.out.println("Enter the AuctionItem ID you want to close: ");
			Scanner scan = new Scanner(System.in);
			long id = scan.nextLong();

			System.out.println(a.closeAuctionItem(id));

		}catch(Exception e){
			e.printStackTrace();
		}
	}


}
