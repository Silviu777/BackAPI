package com.example.menu.item;
import org.springframework.data.annotation.Id;

public class Tara {

    private final long id;
    private final String denumire;
    private final String regiune;
    private final String localitate;

    public Tara(long id, String denumire, String regiune, String localitate) {
        this.id = id;
        this.denumire = denumire;
        this.regiune = regiune;
        this.localitate = localitate;
    }

    @Id
    public long getId() {
        return id;
    }

    public String getDenumire() {
        return denumire;
    }

    public String getRegiune() {
        return regiune;
    }

    public String getLocalitate() {
        return localitate;
    }

    public Tara updateWith(Tara tara) {
        return new Tara(
                this.id,
                tara.denumire,
                tara.regiune,
                tara.localitate
        );
    }


}