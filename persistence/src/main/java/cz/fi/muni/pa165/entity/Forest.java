package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Erik Macej 433744 , on 23.10.16.
 *
 * @author Erik Macej 433744
 */

@Entity
public class Forest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 2)
    @Column(nullable = false, unique = true)
    private String name;

    @Column(name = "locality_description")
    private String localityDescription;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalityDescription() {
        return localityDescription;
    }

    public void setLocalityDescription(String localityDescription) {
        this.localityDescription = localityDescription;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Forest)) return false;

        Forest forest = (Forest) o;

        return name != null ? name.equals(forest.name) : forest.name == null;
    }

    @Override
    public int hashCode() {
        return name != null ? name.hashCode() : 0;
    }

    @Override
    public String toString() {
        return "Forest{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", localityDescription='" + localityDescription + '\'' +
                '}';
    }
}
