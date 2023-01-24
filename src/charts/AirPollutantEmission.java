package charts;

public class AirPollutantEmission {
    private String countryName;
    private String countryCode;
    private int year;
    private float nox;
    private float so2;
    private float vocs;

    public AirPollutantEmission() {
        
    }

    public AirPollutantEmission(String countryName, String countryCode, int year, float nox, float so2, float vocs) {
        this.countryName = countryName;
        this.countryCode = countryCode;
        this.year = year;
        this.nox = nox;
        this.so2 = so2;
        this.vocs = vocs;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String countryName) {
        this.countryName = countryName;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public float getNox() {
        return nox;
    }

    public void setNox(float nox) {
        this.nox = nox;
    }

    public float getSo2() {
        return so2;
    }

    public void setSo2(float so2) {
        this.so2 = so2;
    }

    public float getVocs() {
        return vocs;
    }

    public void setVocs(float vocs) {
        this.vocs = vocs;
    }

}
