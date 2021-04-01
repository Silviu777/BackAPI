package com.example.menu.item;

import org.springframework.data.annotation.Id;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;


public class Tara {

    private final long id;

    @NotNull(message = "Denumirea tarii este necesara!")
    @Pattern(regexp="^[a-zA-Z ]+$", message = "Tara trebuie sa fie text!")
    private final String denumire;

    @NotNull(message = "Denumirea regiunii este necesara!")
    @Pattern(regexp="^[a-zA-Z ]+$", message = "Regiune trebuie sa fie text!")
    private final String regiune;

    @NotNull(message = "Denumirea localitatii este necesara!")
    @Pattern(regexp="^[a-zA-Z ]+$", message = "Localitatea trebuie sa fie text!")
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
