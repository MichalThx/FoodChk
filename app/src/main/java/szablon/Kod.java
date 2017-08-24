package szablon;
public class Kod {
    private int kodID;
    private int kodBAR;
    private String kodSKL;


    public int getKodID() {
        return kodID;
    }

    public void setKodID(int kodID) {
        this.kodID = kodID;
    }

    public int getKodBAR() {
        return kodBAR;
    }

    public void setKodBAR(int kodBAR) {
        this.kodBAR = kodBAR;
    }

    public String getKodSKL() {
        return kodSKL;
    }

    public void setKodSKL(String kodSKL) {
        this.kodSKL = kodSKL;
    }

    public Kod(int kodID, int kodBAR, String kodSKL) {
        this.kodID = kodID;
        this.kodBAR = kodBAR;
        this.kodSKL = kodSKL;
    }
}

