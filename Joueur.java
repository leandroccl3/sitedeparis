java.util.LinkedList;


public class Joueur {
private String prenom; 
private String nom;
private String pseudo;
private String passwordJoueur;
private LinkedList<Pari> listeParis;
private int nbreJetons;

public Joueur(String nom, String prenom, String pseudo, String passwordJoueur) {
	this.prenom=prenom;
	this.nom=nom;
	this.pseudo=pseudo;
	this.passwordJoueur=passwordJoueur;
	this.listeParis=listeParis;
	this.nbreJetons=nbreJetons;
	}

public String getPrenom(){
	return prenom;
	}
	
public String getNom(){
	return nom;
	}
		
public String getPseudo(){
	return pseudo;
	}
			
public String getPasswordJoueur(){
	return passwordJoueur;
	}
	
public LinkedList<Pari> getPari(){
	return listeParis;
	}
public int getNobreJetons(){
	return nbreJetons;
	}
public void crediter(int sommeEnJetons){
	nbreJetons=nbreJetons+sommeEnJetons;
	}
public void debiter(int sommeEnJetons){
	nbreJetons=nbreJetons-sommeEnJetons;
	}
public void addPari(Pari pari){
	listeParis.add(pari);
	}
}
