package dto.jpdi;


import dto.dataset.Dataset;

public class DescriptorResponse {

    private Dataset responseDataset;

    public Dataset getResponseDataset() {
        return responseDataset;
    }

    public void setResponseDataset(Dataset responseDataset) {
        this.responseDataset = responseDataset;
    }
}
