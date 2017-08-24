package szablon;

/**
 * Created by MS on 29.08.2016.
 */
public class Sklad {
    private int sklID;
    private String sklNAZ;
    private int sklWAR;
    private String sklLIN;

    public int getSklID() {
        return sklID;
    }

    public void setSklID(int sklID) {
        this.sklID = sklID;
    }

    public String getSklNAZ() {
        return sklNAZ;
    }

    public void setSklNAZ(String sklNAZ) {
        this.sklNAZ = sklNAZ;
    }

    public int getSklWAR() {
        return sklWAR;
    }

    public void setSklWAR(int sklWAR) {
        this.sklWAR = sklWAR;
    }

    public String getSklLIN() {
        return sklLIN;
    }

    public void setSklLIN(String sklLIN) {
        this.sklLIN = sklLIN;
    }

    public Sklad(int sklID, String sklNAZ, int sklWAR, String sklLIN) {
        this.sklID = sklID;
        this.sklNAZ = sklNAZ;
        this.sklWAR = sklWAR;
        this.sklLIN = sklLIN;
    }
}
