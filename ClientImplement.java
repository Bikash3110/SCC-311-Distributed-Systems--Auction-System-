/*****************************************************
 *ClientImplement.java                               *
 *                                                   *
 *Implementation of the Clientinterface              *
 *                                                   *
 *****************************************************/

import java.rmi.*;

public class ClientImplement implements ClientInterface{
	private String name;
	private String email;

	/*
	* Contructor which sets the name and email to null.
	*/
	protected ClientImplement() throws RemoteException{
		name = " ";
		email = " ";
	}

	/*
	* Set Name for the Client.
	* @param String
	*/
	public void setName( String n) throws RemoteException{
		name = n;
	}

	/*
	* Set Email for the Client.
	* @param String
	*/
	public void setEmail(String e) throws RemoteException{
		email = e;
	}

	/*
	* Get Name of the Client.
	*/
	public String getName() throws RemoteException{
		return name;
	}

	/*
	* Get Email of the Client.
	*/
	public String getEmail() throws RemoteException{
		return email;
	}	

	/*
	* Prints message to the Client ( i.e Buyer/Seller )
	* @param String
	*/
	public void getMessage(String message) throws RemoteException{
		System.out.println(message);
	}
}