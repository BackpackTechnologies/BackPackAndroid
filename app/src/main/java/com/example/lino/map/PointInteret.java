package com.example.lino.map;

public class PointInteret {

    PointInteret(String n, String d, String t, Double la, Double lo, Double a) {
        Name = n;
        Desc = d;
        Type = t;
        Latitude = la;
        Longitude = lo;
        Altitude = a;
    }

    private String  Name;
    private String  Desc;
    private String  Type;
    private Double  Latitude;
    private Double  Longitude;
    private Double  Altitude;

    public String getName() {
        return Name;
    }

    public String getDesc() {
        return Desc;
    }

    public String getType() {
        return Type;
    }

    public Double getLatitude() {
        return Latitude;
    }

    public Double getLongitude() {
        return Longitude;
    }

    public Double getAltitude() {
        return Altitude;
    }
}
