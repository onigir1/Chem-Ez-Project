package Models;

public class CategoryModel {
    private String docID;
    private String name;
    private int noOfTests;
    private int imageResId; // Add this field

    public CategoryModel(String docID, String name, int noOfTests, int imageResId) {
        this.docID = docID;
        this.name = name;
        this.noOfTests = noOfTests;
        this.imageResId = imageResId; // Initialize this field
    }

    public String getDocID() {
        return docID;
    }

    public String getName() {
        return name;
    }

    public int getNoOfTests() {
        return noOfTests;
    }

    public int getImageResId() {
        return imageResId;
    }

    public void setDocID(String docID) {
        this.docID = docID;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setNoOfTests(int noOfTests) {
        this.noOfTests = noOfTests;
    }

    public void setImageResId(int imageResId) {
        this.imageResId = imageResId;
    }
}
