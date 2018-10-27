package TrafficMonitorServer;
import com.google.gson.Gson;
import java.io.DataInputStream;
import java.io.ObjectInputStream;
import java.net.Socket;
/**
 *
 * @author Moses
 */
public class ServerThread extends Thread
{

    private final TrafficMonitorServer server;
    private final Socket socket;

    //Constructor
    public ServerThread(TrafficMonitorServer server, Socket socket)
    {
        this.server = server;
        this.socket = socket;
        start();
    }

    // This runs in a separate thread when start() is called in the
    // constructor
    
    public void run()
    {
        try
        {
            //Create a ObjectInputStream for communication; the client
            // is using a ObjectOutputStream to write to us
            ObjectInputStream objectIn = new ObjectInputStream(socket.getInputStream());
            //Over and over forever
            while (true)
            {
                //Adds new Gson instance
                Gson gson = new Gson();
                
                //Read all traffic entries 
                TrafficEntry entry = gson.fromJson(objectIn.readObject().toString(), TrafficEntry.class);
                // if the entry exists then log it and send it on.
                if (entry != null)
                {
                    System.out.println("Received Traffic Entry from " + entry.stationLocationID);
                    server.sendObjectToAll(entry);
                    System.out.println(entry.convertToString());
                }
            }
        } catch (Exception ie)
        {
            System.out.println(ie.getMessage());
        }
        finally{
            //The connection is closed for one reason or another,
            // so have the server dealing with it
            server.removeConnection( socket ); 
        }
    }
}
