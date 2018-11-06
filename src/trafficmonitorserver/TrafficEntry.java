
package TrafficMonitorServer;

import java.io.Serializable;
import org.apache.commons.lang3.StringUtils;

/**
 * Class that holds the properties of one Traffic Entry
 *
 * @author Moses
 */

public class TrafficEntry
{

    //properties
    public String time;
    public int stationLocationID;
    public int numberOfLanes;
    public int totalNumberOfVehicles;
    public int avgNumberOfVehicles;
    public int avgVelocity;

    public TrafficEntry()
    {
    }
/**
 * 
 * @param time - time of Traffic entry
 * @param stationLocationID - Which monitor station
 * @param numberOfLanes - Number of lanes for roads
 * @param totalNumberOfVehicles - Total number of vehicles
 * @param avgNumberOfVehicles - Average number of vehicles
 * @param avgVelocity - Average velocity of each vehicles.
 */
    public TrafficEntry(String time, int stationLocationID, int numberOfLanes, int totalNumberOfVehicles, int avgNumberOfVehicles, int avgVelocity )
    {
        this.time = time;
        this.stationLocationID = stationLocationID;
        this.numberOfLanes = numberOfLanes;
        this.totalNumberOfVehicles = totalNumberOfVehicles;
        this.avgNumberOfVehicles = avgNumberOfVehicles;
        this.avgVelocity = avgVelocity;
    }
    /**
     * Creates an object array with all properties
     * @return 
     */
    public Object[] ToFullObjectArray()
    {
        Object[] fullEntryArray = new Object[]
        {
            this.time,
            this.stationLocationID,
            this.numberOfLanes,
            this.totalNumberOfVehicles,
            this.avgNumberOfVehicles,
            this.avgVelocity
        };
        return fullEntryArray;
    }

    /**
     * Method used for Table model (as the table only requires 4 fields)
     * @return 
     */
    public Object[] ToArray()
    {
           
        Object[] entryArray = new Object[]
        {
            this.time,
            this.stationLocationID,
            this.avgNumberOfVehicles,
            this.avgVelocity

        };
        
        return entryArray;
    }
    /**
     * Converts the traffic entry into a String[]
     * 
     * @return String[] 
     */
    public String[] toStringArray()
    {
        String[] array = new String[]
        {
            this.time,
            Integer.toString(this.stationLocationID),
            Integer.toString(this.numberOfLanes),
            Integer.toString(this.totalNumberOfVehicles),
            Integer.toString(this.avgNumberOfVehicles),
            Integer.toString(this.avgVelocity)
        };
        return array;
    }
    /**
     * Converts the TrafficEntry to a Single String.
     * @return String
     * 
     */
    public String convertToString()
    {
        String str;
        
        str = StringUtils.join(this.toStringArray(), ",");
        
        
//        StringBuilder trafficEntryString = new StringBuilder();
//        trafficEntryString.append(time).append(",");
//        trafficEntryString.append(stationLocationID).append(",");
//        trafficEntryString.append(numberOfLanes).append(",");
//        trafficEntryString.append(totalNumberOfVehicles).append(",");
//        trafficEntryString.append(avgNumberOfVehicles).append(",");
//        trafficEntryString.append(avgVelocity);
//
//        String str = trafficEntryString.toString();
        return str;
    }
    

    //Switch case for Table Model Indexer
//    public Object getTrafficEntry(int index)
//    {
//        Object property = new Object();
//        
//        switch (index)
//        {
//            case 0: property = getDate();
//            break;
//            case 1: property = getStationLocationID();
//            break;
//            case 2: property = getAvgNumberOfVehicles();
//            break;
//            case 3: property = getAvgVelocity();
//            break;
//            default: property = "error";
//            break;
//        }
//        return property;
//    }

}
