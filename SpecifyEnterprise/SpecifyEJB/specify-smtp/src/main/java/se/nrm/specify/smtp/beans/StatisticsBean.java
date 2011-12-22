package se.nrm.specify.smtp.beans;

import java.io.Serializable; 
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap; 
import se.nrm.specify.datamodel.Locality;
import se.nrm.specify.datamodel.Taxon;

/**
 *
 * @author idali
 */
public class StatisticsBean implements Serializable {
    
    private Locality locality;
    private Taxon taxon;

    private List<String> determinations;
    private Map<String, Integer> determinationMap;
      
    private String totalEvents;
    private String totalTraps;
    private String taxontree;
    private String statisticsName;

    public StatisticsBean() {
    }

    public Map<String, Integer> getDeterminationMap() {

        Map<String, Integer> map = new HashMap<String, Integer>();
        if(determinations != null) {
            for (String taxonname : determinations) { 
                if (map.containsKey(taxonname)) {
                    int count = map.get(taxonname) + 1;
                    map.remove(taxonname);
                    map.put(taxonname, count);
                } else {
                    map.put(taxonname, 1);
                }
            }    
        } else {
            determinations = new ArrayList<String>();
        }
        determinationMap = new TreeMap<String, Integer>(map);

        return determinationMap;
    }

    public List<String> getDeterminations() {
        return determinations;
    }

    public void setDeterminations(List<String> determinations) {
        this.determinations = determinations;
    }

    public String getTotalDeterminations() {
        return String.valueOf(determinations.size());
    }

    public String getUniqueDeterminations() {
        return String.valueOf(determinationMap.size());
    }

    public String getTotalEvents() {
        return totalEvents;
    }

    public String getTotalTraps() {
        return totalTraps;
    }

    public void setTotalTraps(String totalTraps) {
        this.totalTraps = totalTraps;
    } 
    
    public void setTotalEvents(String totalEvents) {
        this.totalEvents = totalEvents;
    }

    public Locality getLocality() {
        return locality;
    }

    public void setLocality(Locality locality) {
        this.locality = locality;
    }

    public Taxon getTaxon() {
        return taxon;
    }

    public void setTaxon(Taxon taxon) {
        this.taxon = taxon;
    }

    public String getTaxontree() {
        return taxontree;
    }

    public void setTaxontree(String taxontree) {
        this.taxontree = taxontree;
    }

    public String getStatisticsName() {
        return statisticsName;
    }

    public void setStatisticsName(String statisticsName) {
        this.statisticsName = statisticsName;
    } 
}
