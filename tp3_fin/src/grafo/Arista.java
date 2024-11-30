package grafo;

import java.util.Objects;

public class Arista {
    private Vertice src;
    private Vertice dest;
    private Double peso;

    public Arista(Vertice source,Vertice dest, Double weight) {
        this.src = source;
        this.dest = dest;
        this.peso = weight;
    }

   
	public Vertice getSrc() {
        return src;
    }

    public Vertice getDest() {
        return dest;
    }

    public Double getPeso() {
        return peso;
    }

    @Override
	public int hashCode() {
		return Objects.hash(src, dest, peso);
	}

    @Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Arista other = (Arista) obj;
		return Objects.equals(dest, other.dest) && Objects.equals(src, other.src) && peso == other.peso;
	}
}

