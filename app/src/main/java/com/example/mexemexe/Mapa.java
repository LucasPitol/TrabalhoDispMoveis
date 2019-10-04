package com.example.mexemexe;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Mapa {

    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");

    public String movimento;
    public Date momento;
    public String momentoStr;

    public Mapa(String movimento, Date m) {
        this.movimento = movimento;
        this.momento = m;
        momentoStr = (formatter.format(m));
    }

    @Override
    public String toString() {
        return this.movimento + "    " + this.momentoStr;
    }
}
