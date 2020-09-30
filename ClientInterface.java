/*****************************************************
 *ClientInterface.java                               *
 *                                                   *
 *Interface of the Client details                    *
 *                                                   *
 *****************************************************/

import java.rmi.*;

public interface ClientInterface extends Remote{

	// Get Client Name
	public String getName() throws RemoteException;

	// Get Client Email 
	public String getEmail() throws RemoteException;

	// Set Client Name
	public void setName(String name) throws RemoteException;

	// Set Client Email
	public void setEmail(String email) throws RemoteException;

	// Get Message to the client, @param String.
	public void getMessage(String s) throws RemoteException;
  
}