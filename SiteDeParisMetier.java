package siteParis;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.LinkedList;


/**
*
* @author Bernard Prou et Julien Mallet
* <br><br>
* La classe qui contient toutes les méthodes "Métier" de la gestion du site de paris.
* <br><br>
* Dans toutes les méthodes :
* <ul>
* <li>un paramètre de type <code>String</code> est invalide si il n'est pas instancié.</li>
* <li>pour la validité d'un password de gestionnaire et d'un password de joueur :
* <ul>
* <li> lettres et chiffres sont les seuls caractères autorisés </li>
* <li> il doit avoir une longueur d'au moins 8 caractères </li>
* </ul></li>
* <li>pour la validité d'un pseudo de joueur :
* <ul>
* <li> lettres et chiffres sont les seuls caractères autorisés </li>
* <li> il doit avoir une longueur d'au moins 4 caractères</li>
* </ul></li>
* <li>pour la validité d'un prénom de joueur et d'un nom de joueur :
* <ul>
* <li> lettres et tiret sont les seuls caractères autorisés </li>
* <li> il doit avoir une longueur d'au moins 1 caractère </li>
* </ul></li>
* <li>pour la validité d'une compétition :
* <ul>
* <li> lettres, chiffres, point, trait d'union et souligné sont les seuls caractères autorisés </li>
* <li> elle doit avoir une longueur d'au moins 4 caractères</li>
* </ul></li>
* <li>pour la validité d'un compétiteur :
* <ul>
* <li> lettres, chiffres, trait d'union et souligné sont les seuls caractères autorisés </li>
* <li> il doit avoir une longueur d'au moins 4 caractères.</li>
* </ul></li></ul>
*/
public class SiteDeParisMetier {
	
	private String passwordGestionnaire;
	private LinkedList<Joueur> joueurs;
	private LinkedList<Competition> competitions;
    
	
	/**
* constructeur de <code>SiteDeParisMetier</code>.
*
* @param passwordGestionnaire le password du gestionnaire.
*
* @throws MetierException levée
* si le <code>passwordGestionnaire</code> est invalide
*/
public SiteDeParisMetier(String passwordGestionnaire) throws MetierException {
	if (passwordGestionnaire==null) throw new MetierException();
	if (!passwordGestionnaire.matches("[0-9A-Za-z],{8,}")) throw new MetierException();
	this.passwordGestionnaire=passwordGestionnaire;
	this.joueurs= new LinkedList<Joueur>();
	this.competitions = new LinkedList<Competition>();
	}

// Les méthodes du gestionnaire (avec mot de passe gestionnaire)
/**
* inscrire un joueur.
*
* @param nom le nom du joueur
* @param prenom le prénom du joueur
* @param pseudo le pseudo du joueur
* @param passwordGestionnaire le password du gestionnaire du site
*
* @throws MetierException levée
* si le <code>passwordGestionnaire</code> proposé est invalide,
* si le <code>passwordGestionnaire</code> est incorrect.
* @throws JoueurExistantException levée si un joueur existe avec les mêmes noms et prénoms ou le même pseudo.
* @throws JoueurException levée si <code>nom</code>, <code>prenom</code>, <code>pseudo</code> sont invalides.
*
* @return le mot de passe (déterminé par le site) du nouveau joueur inscrit.
*/
public String inscrireJoueur(String nom, String prenom, String pseudo, String passwordGestionnaire) throws MetierException, JoueurExistantException, JoueurException {
    validitePasswordGestionnaire(passwordGestionnaire);  
	validiteNomPrenomPseudoJoueur(nom, prenom, pseudo);    //terminar metodo
	for (Joueur j:joueurs){
		if( (j.getNom().equals(nom)) && (j.getPrenom().equals(prenom)) ) throw new JoueurExistantException();
		if (j.getPseudo().equals(pseudo)) throw new JoueurExistantException();
		}
	String pass = creationPasswordJoueur(nom,prenom);
	Joueur j=new Joueur(nom,prenom,pseudo,pass);   ///////////////////////// ERROR
	joueurs.add(j);
	return (pass);
	}

/**
* supprimer un joueur.
*
* @param nom le nom du joueur
* @param prenom le prénom du joueur
* @param pseudo le pseudo du joueur
* @param passwordGestionnaire le password du gestionnaire du site
*
* @throws MetierException
* si le <code>passwordGestionnaire</code> est invalide,
* si le <code>passwordGestionnaire</code> est incorrect.
* @throws JoueurInexistantException levée si il n'y a pas de joueur avec le même <code>nom</code>, <code>prenom</code> et <code>pseudo</code>.
* @throws JoueurException levée
* si le joueur a des paris en cours,
* si <code>nom</code>, <code>prenom</code>, <code>pseudo</code> sont invalides.
*
* @return le nombre de jetons à rembourser au joueur qui vient d'être désinscrit.
*
*/
public long desinscrireJoueur(String nom, String prenom, String pseudo, String passwordGestionnaire) throws MetierException, JoueurInexistantException, JoueurException {
	validitePasswordGestionnaire(passwordGestionnaire);  
	validiteNomPrenomPseudoJoueur(nom, prenom, pseudo);
	boolean existantJoueur = false;
	long nomJetons=0;
	int index=-1;
	for (Joueur j:joueurs){
		index++;
		if( (j.getNom().equals(nom)) && (j.getPrenom().equals(prenom)) && (j.getPseudo().equals(pseudo))){
			existantJoueur = true;
			nomJetons=j.getNbreJetons();
		};
	}
	if (existantJoueur = false) throw new JoueurInexistantException();
	else {
		joueurs.remove(index);
		}
	
	return (nomJetons);
}
/**
* ajouter une compétition.
*
* @param competition le nom de la compétition
* @param dateCloture la date à partir de laquelle il ne sera plus possible de miser
* @param competiteurs les noms des différents compétiteurs de la compétition
* @param passwordGestionnaire le password du gestionnaire du site
*
* @throws MetierException levée si le tableau des
* compétiteurs n'est pas instancié, si le
* <code>passwordGestionnaire</code> est invalide, si le
* <code>passwordGestionnaire</code> est incorrect.
* @throws CompetitionExistanteException levée si une compétition existe avec le même nom.
* @throws CompetitionException levée si le nom de la
* compétition ou des compétiteurs sont invalides, si il y a
* moins de 2 compétiteurs, si un des competiteurs n'est pas instancié,
* si deux compétiteurs ont le même nom, si la date de clôture
* n'est pas instanciée ou est dépassée.
*/
public void ajouterCompetition(String nomCompetition, DateFrancaise dateCloture, String [] competiteurs, String passwordGestionnaire) throws MetierException, CompetitionExistanteException, CompetitionException {
	validitePasswordGestionnaire(passwordGestionnaire); 
	if (nomCompetition==null) throw new MetierException();
	validiteNomCompetition(nomCompetition);
	for (Competition j:competitions){
		if(j.getNomCompetition().equals(nomCompetition)) throw new CompetitionExistanteException(); 
		}
	if ((competiteurs.length)<2) throw new CompetitionException();
	for (String c:competiteurs){
		validiteNomCompetiteur(c);
		
	if (dateCloture==null||(dateCloture.estDansLePasse())) throw new CompetitionException();
	int j=0;
	for(String c1:competiteurs){
		j++;
		int k=0;
		for (String c2:competiteurs){
			k++;
			if (c1.equals(c2) && !(j==k)) throw new CompetitionException();
			}
		}
	competitions.add((new Competition(nomCompetition, dateCloture, competiteurs)));
	}
	
	
	
	
}
/**
* solder une compétition vainqueur (compétition avec vainqueur).
*
* Chaque joueur ayant misé sur cette compétition
* en choisissant ce compétiteur est crédité d'un nombre de
* jetons égal à :
*
* (le montant de sa mise * la somme des
* jetons misés pour cette compétition) / la somme des jetons
* misés sur ce compétiteur.
*
* Si aucun joueur n'a trouvé le
* bon compétiteur, des jetons sont crédités aux joueurs ayant
* misé sur cette compétition (conformément au montant de
* leurs mises). La compétition est "supprimée" si il ne reste
* plus de mises suite à ce solde.
*
* @param competition le nom de la compétition
* @param vainqueur le nom du vainqueur de la compétition
* @param passwordGestionnaire le password du gestionnaire du site
*
* @throws MetierException levée
* si le <code>passwordGestionnaire</code> est invalide,
* si le <code>passwordGestionnaire</code> est incorrect.
* @throws CompetitionInexistanteException levée si il n'existe pas de compétition de même nom.
* @throws CompetitionException levée
* si le nom de la compétition ou du vainqueur est invalide,
* si il n'existe pas de compétiteur du nom du vainqueur dans la compétition,
* si la date de clôture de la compétition est dans le futur.
*
*/	
public void solderVainqueur(String competition, String vainqueur, String passwordGestionnaire) throws MetierException, CompetitionInexistanteException, CompetitionException {
	validitePasswordGestionnaire(passwordGestionnaire);
	validiteNomCompetition(competition);
	validiteNomCompetiteur(vainqueur);
	int i=-1;
	boolean trouveCompetition=false;
	for(Competition c:competitions){
		i++;
		if (c.getNom().equals(competition)){
			trouveCompetition=true;
			break;
			}
		}
	if (trouveCompetition==false){throw new CompetitionInexistanteException();}
	String[] listeCompetiteurs = (competitions.get(i)).getCompetiteurs();
	boolean trouveVainqueur=false;
	for (String c:listeCompetiteurs){
		if (c.equals(vainqueur)){
			trouverVainqueur=true;
			break;
			}
		}
	if (trouveVainqueur==false){throw new CompetitionException();}
	DateFrancaise dateCloture = (competitions.get(i)).getDateCloture();
	if (dateCloture==null) throw new competitionException();
	if(!(dateCloture.estDansLePasse())) throw new CompetitionException();
	long jetonsMisesVainqueur=0;
	long jetonsMisesCompetition=0;
	LinkedList<Pari> listeParis=(competitions.get(i)).getParis();
	for(Pari p: listeParis){
		if(vainqueur.equals(p.getVainqueurEnvisage())){
			jetonsMisesVainqueur+=p.getMiseEnJetons();
			}
		jetonsMisesCompetition+=p.getMiseEnJetons();
	}
	Joueur j=null;
	if(jetonsMisesVainqueur==0){
		for(Pari p: listeParis){
			j=p.getJoueur();
			j=crediter((int) p.getMiseEnJetons());
			j.getPari().removeFirstOccurrence(p);
			}
		}
	}
else{
	for(Pari p: listeParis){
		j=p.getJoueur();
		if(vainqueur.equals(p.getVainqueurEnvisage())){
			long gain=0;
			gain=p.getMiseEnJetons()*jetonsMiosesCompetition/jetonsMisesVainqueur;
			j.crediter((int) gain)
			}
		j.getPari().removeFirsOcurrece(p);
		}
	}competitions.remove(i);
}
/**
* créditer le compte en jetons d'un joueur du site de paris.
*
* @param nom le nom du joueur
* @param prenom le prénom du joueur
* @param pseudo le pseudo du joueur
* @param sommeEnJetons la somme en jetons à créditer
* @param passwordGestionnaire le password du gestionnaire du site
*
* @throws MetierException levée
* si le <code>passwordGestionnaire</code> est invalide,
* si le <code>passwordGestionnaire</code> est incorrect,
* si la somme en jetons est négative.
* @throws JoueurException levée
* si <code>nom</code>, <code>prenom</code>, <code>pseudo</code> sont invalides.
* @throws JoueurInexistantException levée si il n'y a pas de joueur avec les mêmes nom, prénom et pseudo.
*/
public void crediterJoueur(String nom, String prenom, String pseudo, long sommeEnJetons, String passwordGestionnaire) throws MetierException, JoueurException, JoueurInexistantException {
}
/**
* débiter le compte en jetons d'un joueur du site de paris.
*
* @param nom le nom du joueur
* @param prenom le prénom du joueur
* @param pseudo le pseudo du joueur
* @param sommeEnJetons la somme en jetons à débiter
* @param passwordGestionnaire le password du gestionnaire du site
*
* @throws MetierException levée
* si le <code>passwordGestionnaire</code> est invalide,
* si le <code>passwordGestionnaire</code> est incorrect,
* si la somme en jetons est négative.
* @throws JoueurException levée
* si <code>nom</code>, <code>prenom</code>, <code>pseudo</code> sont invalides
* si le compte en jetons du joueur est insuffisant (il deviendrait négatif).
* @throws JoueurInexistantException levée si il n'y a pas de joueur avec les mêmes nom, prénom et pseudo.
*
*/
public void debiterJoueur(String nom, String prenom, String pseudo, long sommeEnJetons, String passwordGestionnaire) throws MetierException, JoueurInexistantException, JoueurException {
}
/**
* consulter les joueurs.
*
* @param passwordGestionnaire le password du gestionnaire du site de paris
* @throws MetierException levée
* si le <code>passwordGestionnaire</code> est invalide,
* si le <code>passwordGestionnaire</code> est incorrect.
*
* @return une liste de liste dont les éléments (de type <code>String</code>) représentent un joueur avec dans l'ordre :
* <ul>
* <li> le nom du joueur </li>
* <li> le prénom du joueur </li>
* <li> le pseudo du joueur </li>
* <li> son compte en jetons restant disponibles </li>
* <li> le total de jetons engagés dans ses mises en cours. </li>
* </ul>
*/
public LinkedList <LinkedList <String>> consulterJoueurs(String passwordGestionnaire) throws MetierException {
return new LinkedList <LinkedList <String>>();
}
// Les méthodes avec mot de passe utilisateur
/**
* miserVainqueur (parier sur une compétition, en désignant un vainqueur).
* Le compte du joueur est débité du nombre de jetons misés.
*
* @param pseudo le pseudo du joueur
* @param passwordJoueur le password du joueur
* @param miseEnJetons la somme en jetons à miser
* @param competition le nom de la compétition relative au pari effectué
* @param vainqueurEnvisage le nom du compétiteur sur lequel le joueur mise comme étant le vainqueur de la compétition
*
* @throws MetierException levée si la somme en jetons est négative.
* @throws JoueurInexistantException levée si il n'y a pas de
* joueur avec les mêmes pseudos et password.
* @throws CompetitionInexistanteException levée si il n'existe pas de compétition de même nom.
* @throws CompetitionException levée
* si <code>competition</code> ou <code>vainqueurEnvisage</code> sont invalides,
* si il n'existe pas un compétiteur de nom <code>vainqueurEnvisage</code> dans la compétition,
* si la compétition n'est plus ouverte (la date de clôture est dans le passé).
* @throws JoueurException levée
* si <code>pseudo</code> ou <code>password</code> sont invalides,
* si le <code>compteEnJetons</code> du joueur est insuffisant (il deviendrait négatif).
*/
public void miserVainqueur(String pseudo, String passwordJoueur, long miseEnJetons, String competition, String vainqueurEnvisage) throws MetierException, JoueurInexistantException, CompetitionInexistanteException, CompetitionException, JoueurException {
}
// Les méthodes sans mot de passe
/**
* connaître les compétitions en cours.
*
* @return une liste de liste dont les éléments (de type <code>String</code>) représentent une compétition avec dans l'ordre :
* <ul>
* <li> le nom de la compétition, </li>
* <li> la date de clôture de la compétition. </li>
* </ul>
*/
public LinkedList <LinkedList <String>> consulterCompetitions(){
return new LinkedList <LinkedList <String>>();
}
/**
* connaître la liste des noms des compétiteurs d'une compétition.
*
* @param competition le nom de la compétition
*
* @throws CompetitionException levée
* si le nom de la compétition est invalide.
* @throws CompetitionInexistanteException levée si il n'existe pas de compétition de même nom.
*
* @return la liste des compétiteurs de la compétition.
*/
public LinkedList <String> consulterCompetiteurs(String competition) throws CompetitionException, CompetitionInexistanteException{
return new LinkedList <String> ();
}
/**
* vérifier la validité du password du gestionnaire.
*
* @param passwordGestionnaire le password du gestionnaire à vérifier
*
* @throws MetierException levée
* si le <code>passwordGestionnaire</code> est invalide.
*/

//creacion de metodos de validacion

protected void validitePasswordGestionnaire(String passwordGestionnaire) throws MetierException {
if (passwordGestionnaire==null) throw new MetierException();
if (!passwordGestionnaire.matches("[0-9A-Za-z]{8,}")) throw new MetierException();
}



protected void validiteNomPrenomPseudoJoueur(String nom,String prenom,String pseudo){//terminar metodo
	}

protected String creationPasswordJoueur(String nom,String prenom){      // terminar
	int numRandom = (int)(Math.random()*10000); 
	numRandom=numRandom+10000000;
	String numRan=Integer.toString(numRandom);
	return (nom+prenom+numRan);
	}

protected void validiteNomCompetition(String nomCompetition) throws CompetitionException{
	if(	nomCompetition==null) throw new CompetitionException();
	if (!nomCompetition.mqtches("[0-9A-Za-z-_.]{4,}")) throw new CompetitionException();
	}

protected void validiteNomCompetiteur(String nom) throws CompetitionException{
	if(nom==null) throw new CompetitionException();
	if(!nom.matches("[0-9A-Za-z-_]{4,}")) throw new CompetitionException();
}

}
