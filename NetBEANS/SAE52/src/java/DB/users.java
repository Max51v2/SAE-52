package DB;

/**
 * Tableau users DB
 * 
 * @author root
 */
public class users {
    private Integer id;
    private String login;
    private String nom;
    private String prenom;
    private String role;
    private String hash;
    private String token;
    
    
    users(Integer id, String login, String nom, String prenom, String role, String hash, String token){
        this.id=id;
        this.login=login;
        this.nom=nom;
        this.prenom=prenom;
        this.role=role;
        this.hash=hash;
        this.token=token;
    }
}
