package control.models;

public class MemTableCell {
    private int row;
    private String index;
    private String data;
    private int decData;

    public MemTableCell() {
    }

    public MemTableCell(int row, String index, String data) {
        this.row = row;
        this.index = index;
        this.data = data;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public int getDecData() {
        return decData;
    }

    public void setDecData(int decData) {
        this.decData = decData;
    }
}
