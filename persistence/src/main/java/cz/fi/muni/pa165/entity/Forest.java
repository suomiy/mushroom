package cz.fi.muni.pa165.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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

    @Column(nullable = false,unique = true)
    @NotNull
    private String name;

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

        if(this == o){
            return true;
        }

        if(o == null){
            return true;
        }

        if(!(o instanceof Forest)){
            return false;
        }

        Forest other = (Forest) o;
        if(name == null && other.getName() != null){
            return false;
        }

        if(!name.equals(other.getName())){
            return false;
        }
        return true;

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
