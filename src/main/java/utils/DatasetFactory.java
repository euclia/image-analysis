package utils;
import dto.dataset.DataEntry;
import dto.dataset.Dataset;
import dto.dataset.EntryId;
import dto.dataset.FeatureInfo;
import org.apache.commons.lang3.math.NumberUtils;


import java.io.InputStream;
import java.util.*;

import static utils.CSVUtils.parseLine;


public class DatasetFactory {
    public static Dataset mergeRows(Dataset dataset, Dataset other) {
        if (dataset != null && other == null) {
            return dataset;
        } else if (dataset == null && other != null) {
            return other;
        } else if (dataset == null && other == null) {
            return null;
        } else {
            for (int i = 0; i < dataset.getDataEntry().size(); i++)
                dataset.getDataEntry().get(i).getValues().putAll(other.getDataEntry().get(i).getValues());
            dataset.getFeatures().addAll(other.getFeatures());
            return dataset;
        }
    }

    public static  Dataset calculateRowsAndColumns(InputStream stream) {
        Dataset dataset = new Dataset();
        Scanner scanner = new Scanner(stream);

        Set<FeatureInfo> featureInfoList = new HashSet<>();
        List<DataEntry> dataEntryList = new ArrayList<>();
        List<String> feature = new LinkedList<>();
        boolean firstLine = true;
        int count = 0;
        while (scanner.hasNext()) {

            List<String> line = parseLine(scanner.nextLine());
            if (firstLine) {
                for (String l : line) {
                    String pseudoURL = ("/feature/" + l).replaceAll(" ","_"); //uriInfo.getBaseUri().toString()+
                    feature.add(pseudoURL);
                    featureInfoList.add(new FeatureInfo(pseudoURL, l,"NA",new HashMap<>(),Dataset.DescriptorCategory.EXPERIMENTAL));
                }
                firstLine = false;
            } else {
                Iterator<String> it1 = feature.iterator();
                Iterator<String> it2 = line.iterator();
                TreeMap<String, Object> values = new TreeMap<>();
                while (it1.hasNext() && it2.hasNext()) {
                    String it = it2.next();
                    if (!NumberUtils.isParsable(it))
                        values.put(it1.next(), it);
                    else
                        values.put(it1.next(),Float.parseFloat(it));
                }
                EntryId entryId = new EntryId();
                entryId.setName("row" + count);

               // DataEntry dataEntry = new DataEntry();
               // dataEntry.setValues(values);
               // dataEntry.setEntryId();
               // dataEntry.setCompound(substance);
              //  dataEntryList.add(dataEntry);
            }
            count++;
        }
        scanner.close();
        dataset.setFeatures(featureInfoList);
        dataset.setDataEntry(dataEntryList);
        return dataset;
    }
}
