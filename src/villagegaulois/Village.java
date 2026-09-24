package villagegaulois;

import java.util.Iterator;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbetals) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		marche=new Marche(nbetals);
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbetals) {
			etals = new Etal[nbetals];
			for (int i = 0; i < nbetals; i++) {
				etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			for (int i = 0; i < etals.length; i++) {
				if (!etals[i].isEtalOccupe()) {
					return i;
				}
			}
			return -1;
		}
		
		private Etal[] trouverEtals(String produit) {
			int nb_etal=0;
			
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].contientProduit(produit)) {
					nb_etal++;
				}
			}
			Etal etalP[]=new Etal[nb_etal];
			int indice=0;
			for (int j = 0; j < etals.length; j++) {
				if (etals[j].contientProduit(produit)) {
					etalP[indice]=etals[j];
					indice++;
				}
			}
			return etalP;
		}
		
		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur()==gaulois) {
					return etals[i];
				}
			}
			return null;
		}
		
		private String afficherMarche() {
			StringBuilder chaine = new StringBuilder();
			int nbEtalVide=0;
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].isEtalOccupe()) {
					chaine.append(etals[i].afficherEtal());
				}
				else {
					nbEtalVide++;
				}
			}
			chaine.append("Il reste " + nbEtalVide + " étals non utilisés dans le marché.\n");
			return chaine.toString();
		}
	}
	
	 public String installerVendeur(Gaulois vendeur, String produit,int nbProduit) {
			StringBuilder chaine = new StringBuilder();
			chaine.append(vendeur.getNom()+" cherche un endroit pour vendre " +nbProduit+produit+"\n");
			int numero_etal=marche.trouverEtalLibre();
			marche.utiliserEtal(numero_etal, vendeur, produit, nbProduit);
			chaine.append("Le vendeur "+vendeur.getNom()+" vend des "+produit+" à l'étal n°"+numero_etal+"\n");
			return chaine.toString();
	 }
	 
	 public String rechercherVendeursProduit(String produit) {
		 StringBuilder chaine = new StringBuilder();
		 if (marche.trouverEtals(produit).length==0) {
			chaine.append("Il n'y a pas de vendeur qui propose des "+produit+" au marché.\n");
		}
		 
		 if (marche.trouverEtals(produit).length==1) {
				chaine.append("Seul le vendeur "+marche.trouverEtals(produit)[0].getVendeur().getNom()+" propose des "+produit+" au marché.\n");
		}
		 
		 else {
			 chaine.append("Les vendeurs qui proposent des "+produit+" sont :\n");
			 for (int i = 0; i < marche.trouverEtals(produit).length; i++) {
				chaine.append("-"+marche.trouverEtals(produit)[i].getVendeur().getNom()+"\n");
			}
		 }
		 return chaine.toString();
	 }

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}
}