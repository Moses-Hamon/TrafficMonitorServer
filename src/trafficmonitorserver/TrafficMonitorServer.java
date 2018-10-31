package TrafficMonitorServer;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Set;
import sun.security.x509.IPAddressName;

/**
 * Server used for communication between Monitoring stations and Monitor
 * application
 *
 * @author Moses
 */
public class TrafficMonitorServer
{
   //InetAddress address = InetAddress.getByName("60.230.168.124");
    // The ServerSocket we'll use for accepting new connections
    private ServerSocket ss;
    // A mapping from sockets to DataOutputStreams. This will
// help us avoid having to create a DataOutputStream each time
// we want to write to a stream.
private final Hashtable objectOutputStreams;
    
//Main routine
    // Usage: java Server >port<
         public static void main(String args[]) throws Exception
    {
        int port;
        if (args.length == 0)
        {
            port = 5000;
        } else
        {
            // Get the port # from the command line
            System.out.println(args[0]);
            port = Integer.parseInt(args[0]);
        }

        //Create a new server object, which will automatically begin
        // accepting conecitons
        new TrafficMonitorServer(port);
    }

         
    // Constructor and while-accept loop all in one.
    public TrafficMonitorServer(int port) throws IOException
    {
        this.objectOutputStreams = new Hashtable();
// All we have to do is listen
        listen(port);
    }



    /**
     * Listens for a connection on a port, accepts connections and creates
     * threads to deal with them
     *
     * @param port
     * @throws IOException
     */
    private void listen(int port) throws IOException
    {
        // create the SeverSocket
         ss = new ServerSocket(port);

        // Confirmation that socket is listening
        System.out.println("Listening on: " + ss);

        //Keep accepting connections forever
        while (true)
        {

            //grab the next incoming connection
            Socket s = ss.accept();

            // displays connection on console
            System.out.println("Connection from: " + s);

            // Create a DataOutputStream for writing data to the
            // other side
            ObjectOutputStream objectOut = new ObjectOutputStream(s.getOutputStream());
            // Save this stream so we don't need to make it again

            objectOutputStreams.put(s, objectOut);

            // Create a new thread for this connection, and then forget
            //about it
            new ServerThread(this, s);
        }
    }
    
    // Get an enumeration of all the OutputStreams, one for each client
    // connected to us
    Enumeration getObjectOutputStreams() {
        return objectOutputStreams.elements();
    }
    
    /**
     * Sends out a TrafficEntry Object to all Streams using the HashTable
     * @param entry - Must pass a TrafficEntry to be sent to each client
     */
    void sendObjectToAll(TrafficEntry entry)
    {
        synchronized (objectOutputStreams)
        {
            for (Enumeration e = getObjectOutputStreams(); e.hasMoreElements();)
            {
                ObjectOutputStream objectOut = (ObjectOutputStream) e.nextElement();

                try
                {
                    Gson gson = new Gson();
                    
                    objectOut.writeObject(gson.toJson(entry));
                    System.out.println("Sent Object: "+ entry.convertToString() + " To Soocket" + e.toString());
                } catch (IOException ex)
                {
                    System.out.println("Error sending Object: " + ex);
                }
            }
        }
    }

    // Remove a socket, and it's corresponding output stream, from our
    // list. This is usually called by a connection thread that has
    // discovered that the connectin to the client is dead.
    void removeConnection(Socket s)
    {
        // Synchronize so we don't mess up sendToAll() while it walks
        // down the list of all output streamsa

        synchronized (objectOutputStreams)
        {
            // Tell the world
            System.out.println("Removing connection to " + s);
            // Remove it from our hashtable/list
//            outputStreams.remove(s);
            objectOutputStreams.remove(s);
            // Make sure it's closed
            try
            {
                s.close();
            } catch (IOException ie)
            {
                System.out.println("Error closing " + s);
                ie.printStackTrace();
            }
        }
    }
    
        
    
    
    

}
