package cz.fi.muni.pa165.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 * Created by Erik Macej 433744 , on 20.11.16.
 *
 * @author Erik Macej 433744
 */
public class ForestDTO {

    private Long id;

    @NotNull
    @Size(min = 2)
    private String name;

    private String localityDescription;

    public ForestDTO() {
    }

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
        if (this == o) {
            return true;
        }
        if (o == null) {
            return false;
        }
        if (!(o instanceof ForestDTO)) {
            return false;
        }

        ForestDTO other = (ForestDTO) o;
        if (name == null && other.getName() != null) {
            return false;
        }
        if (!name.equals(other.getName())) {
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
