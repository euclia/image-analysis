package image.helpers;

import dto.dataset.DataEntry;
import dto.dataset.EntryId;
import dto.dataset.FeatureInfo;

import javax.enterprise.context.Dependent;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.TreeMap;

@Dependent
public class DatasetMakerHelper {

    DatasetMakerHelper(){}

    public enum Particle {
        SPHERICAL, NANOTUBES
    }

    public void getFeatureList(Particle particleEnum, Set<FeatureInfo> featureInfoList, DataEntry dataEntry) {
        String particle = particleEnum.equals(Particle.SPHERICAL) ? "spherical" : "nanotubes";
        for (String s : dataEntry.getValues().keySet()) {
            FeatureInfo featureInfo = new FeatureInfo();
            featureInfo.setName("Identifies the " + s + " of the " + particle + " entities)");
            featureInfo.setURI(s);
            featureInfoList.add(featureInfo);
        }
    }

    public void getEntryList(Particle particleEnum, Integer imageCount, List<DataEntry> dataEntryList, HashMap<String,Object> resultList){
        String particle = particleEnum.equals(Particle.SPHERICAL) ? "Spherical" : "Nanotubes";
            DataEntry dataEntry = new DataEntry();
            EntryId entryId= new EntryId();
            entryId.setName(particle +" image: "+imageCount);
            dataEntry.setEntryId(entryId);
            TreeMap<String, Object> entries = new TreeMap<String, Object>(resultList);
            dataEntry.setValues(entries);
            dataEntryList.add(dataEntry);
    }
}
