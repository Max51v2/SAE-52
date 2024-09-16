/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DB;

/**
 *
 * @author root
 */
public class PasswordHashMD5 {
    private Integer id;
    private String login;
    private String nom;
    private String prenom;
    private String hash;
    private String token;
    
    
    PasswordHashMD5(Integer id, String login, String nom, String prenom, String hash, String token){
        this.id=id;
        this.login=login;
        this.nom=nom;
        this.prenom=prenom;
        this.hash=hash;
        this.token=token;
    }
}
