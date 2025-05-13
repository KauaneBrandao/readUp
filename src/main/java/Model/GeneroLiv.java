package Model;


public class GeneroLiv {
    private int idGeneroLiv;
    private String nomeGeneroLiv;

    public GeneroLiv() {}

    public GeneroLiv(int idGeneroLiv, String nomeGeneroLiv) {
        this.idGeneroLiv = idGeneroLiv;
        this.nomeGeneroLiv = nomeGeneroLiv;
    }

    public int getIdGeneroLiv() {
        return idGeneroLiv;
    }

    public void setIdGeneroLiv(int idGeneroLiv) {
        this.idGeneroLiv = idGeneroLiv;
    }

    public String getNomeGeneroLiv() {
        return nomeGeneroLiv;
    }

    public void setNomeGeneroLiv(String nomeGeneroLiv) {
        this.nomeGeneroLiv = nomeGeneroLiv;
    }
    
      @Override
    public String toString() {
        return nomeGeneroLiv; // Isso faz com que o JComboBox mostre o nome
    }
}

