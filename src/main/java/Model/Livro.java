package Model;

import java.util.Date;

public class Livro {
    private int idLivro;
    private String nomeLivro;
    private String autorLivro;
    private String dsecLivro;
    private int idGeneroLiv;
    private Date dataCriacaoLivro;
    private int curtida;
     private String imagemCapa; // <-- Novo campo

    public Livro() {}

    public Livro(int idLivro, String nomeLivro, String autorLivro, String dsecLivro, int idGeneroLiv, Date dataCriacaoLivro, int curtida, String imagemCapa) {
        this.idLivro = idLivro;
        this.nomeLivro = nomeLivro;
        this.autorLivro = autorLivro;
        this.dsecLivro = dsecLivro;
        this.idGeneroLiv = idGeneroLiv;
        this.dataCriacaoLivro = dataCriacaoLivro;
        this.curtida = curtida;
        this.imagemCapa = imagemCapa;
    }

    public int getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(int idLivro) {
        this.idLivro = idLivro;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getAutorLivro() {
        return autorLivro;
    }

    public void setAutorLivro(String autorLivro) {
        this.autorLivro = autorLivro;
    }

    public String getDsecLivro() {
        return dsecLivro;
    }

    public void setDsecLivro(String dsecLivro) {
        this.dsecLivro = dsecLivro;
    }

    public int getIdGeneroLiv() {
        return idGeneroLiv;
    }

    public void setIdGeneroLiv(int idGeneroLiv) {
        this.idGeneroLiv = idGeneroLiv;
    }


    public Date getDataCriacaoLivro() {
        return dataCriacaoLivro;
    }

    public void setDataCriacaoLivro(Date dataCriacaoLivro) {
        this.dataCriacaoLivro = dataCriacaoLivro;
    }

    public int getCurtida() {
        return curtida;
    }

    public void setCurtida(int curtida) {
        this.curtida = curtida;
    }
     public String getImagemCapa() {
        return imagemCapa;
    }

    public void setImagemCapa(String imagemCapa) {
        this.imagemCapa = imagemCapa;
    }
}
