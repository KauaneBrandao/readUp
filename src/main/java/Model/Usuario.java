package Model;
public class Usuario {
    private int idUsuario;
    private String loginUsuario;
    private String senhaUsuario;
    private String telefoneUsuario;
    private String emailUsuario;
    private String privilegioUsuario;
    private int idadeUsuario;

    public Usuario() {}

    public Usuario(int idUsuario, String loginUsuario, String senhaUsuario, String telefoneUsuario, String emailUsuario, String privilegioUsuario, int idadeUsuario) {
        this.idUsuario = idUsuario;
        this.loginUsuario = loginUsuario;
        this.senhaUsuario = senhaUsuario;
        this.telefoneUsuario = telefoneUsuario;
        this.emailUsuario = emailUsuario;
        this.privilegioUsuario = privilegioUsuario;
        this.idadeUsuario = idadeUsuario;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getLoginUsuario() {
        return loginUsuario;
    }

    public void setLoginUsuario(String loginUsuario) {
        this.loginUsuario = loginUsuario;
    }

    public String getSenhaUsuario() {
        return senhaUsuario;
    }

    public void setSenhaUsuario(String senhaUsuario) {
        this.senhaUsuario = senhaUsuario;
    }

    public String getTelefoneUsuario() {
        return telefoneUsuario;
    }

    public void setTelefoneUsuario(String telefoneUsuario) {
        this.telefoneUsuario = telefoneUsuario;
    }

    public String getEmailUsuario() {
        return emailUsuario;
    }

    public void setEmailUsuario(String emailUsuario) {
        this.emailUsuario = emailUsuario;
    }

    public String getPrivilegioUsuario() {
        return privilegioUsuario;
    }

    public void setPrivilegioUsuario(String privilegioUsuario) {
        this.privilegioUsuario = privilegioUsuario;
    }

    public int getIdadeUsuario() {
        return idadeUsuario;
    }

    public void setIdadeUsuario(int idadeUsuario) {
        this.idadeUsuario = idadeUsuario;
    }

   
}
