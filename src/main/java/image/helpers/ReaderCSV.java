package image.helpers;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import image.models.ParticleResult;
import java.io.StringReader;

public class ReaderCSV {

    private BufferedReader br;
    private HashMap<String, String> map;
    private String filePath;
    public List<ParticleResult> particleResults;
    public String[] headersArray;

    private String csv;

    public ReaderCSV(String theFile) {
        filePath = theFile;
    }

    public ReaderCSV(StringBuilder sb) {
        this.csv = sb.toString();
    }

    public List<ParticleResult> read() {
        String line;
        String csvSplitBy = ",";

        try {
            map = new HashMap<>();
            particleResults = new ArrayList<>();
            if (filePath != null) {
                br = new BufferedReader(new FileReader(filePath));
            } else {
                br = new BufferedReader(new StringReader(csv));
            }
            String[] rowHeaders = br.readLine().split(csvSplitBy);

            this.headersArray = rowHeaders;
            rowHeaders[0] = "id";
            while ((line = br.readLine()) != null) {
                String[] row = line.split(csvSplitBy);
                map = matchHeadersWithData(rowHeaders, row);
                ParticleResult imr = new ParticleResult(map);
                particleResults.add(imr);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return particleResults;
    }

    private HashMap<String, String> matchHeadersWithData(String[] headers, String[] data) {
        if (headers.length != data.length) {
            return map;
        } else {
            for (int i = 0; i < headers.length; i++) {
                map.put(headers[i], data[i]);
            }
        }
        return map;
    }
}
