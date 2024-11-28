package grafo;

import java.util.Objects;

public class Vertice {
    private String label;
    private int index;

    public Vertice(String label) {
        this.label = label;
        
    }

    public String getLabel() {
        return label;
    }

    @Override
    public int hashCode() {
        return Objects.hash(index);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Vertice vertex = (Vertice) obj;
        return label.equals(vertex.label);
    }
}
