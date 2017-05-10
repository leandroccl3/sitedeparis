import java.util.LinkedList;


public class Competition {
private String competition;
private DateFrancaise dateCloture;
private String competiteurs;
private String nom;
private String listeCompetiteur;
private LinkedList Paris;
private String vainqueurs;

public Competiton(String competition, DateFrancaise dateCloture,String competiteurs){
	
	this.competition=competition;
	this.dateCloture=dateCloture;
	this.competiteurs=competiteurs;
	}

public String getNom(){
	
	return nom;
	}

public DateFrancaise getDateCloture(){
	
	return dateCloture;
	}

public String getListeCompetiteur(){
	
	return listeCompetiteur;
	}


public LinkedList<Pari> getParis(){
	
	return Paris;
	}

public void addPari(){
	
	}

public String getCompetiteurs(){
	
	return competiteurs;
	}

public String getVainqueurs(){
	
	return vainqueurs;
	}

public void setVainqueurs(){
	
	}


}