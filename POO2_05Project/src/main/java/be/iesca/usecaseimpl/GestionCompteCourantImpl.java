package be.iesca.usecaseimpl;

import java.util.List;

import be.iesca.dao.BiereDao;
import be.iesca.daoimpl.DaoFactory;
import be.iesca.domaine.Bundle;
import be.iesca.domaine.Biere;
import be.iesca.usecase.GestionCompteCourant;

//TO DO
public class GestionCompteCourantImpl implements GestionCompteCourant {
	private BiereDao biereDao;

	public GestionCompteCourantImpl() {
		this.biereDao = (BiereDao) DaoFactory.getInstance().getDaoImpl(BiereDao.class);
	}

	@Override
	public void lister(Bundle bundle) {
		boolean listeOk = true;
		String message = "";
		List<Biere> listeBieres = null;
		listeBieres = this.biereDao.listerBieres();
		if (listeBieres==null) {
			listeOk = false;
		} else if (listeBieres.isEmpty())
			message = "Liste vide";
		else if (listeBieres.size() == 1)
			message = "Il y a 1 bière";
		else
			message = "Il y a " + listeBieres.size() + " bières";
		bundle.put(Bundle.OPERATION_REUSSIE, listeOk);
		bundle.put(Bundle.MESSAGE, message);
		bundle.put(Bundle.LISTE, listeBieres);
	}

	@Override
	public void modifierBiere(Bundle bundle) {
		boolean modificationReussie = false;
		String message = "";
		Biere biere = (Biere) bundle.get(Bundle.BIERE);
		if (biere.getNom() == null || biere.getNom().isEmpty()) {
			message = "La modification n'a pas pu être réalisée. Il manque le nom de la bière";
		} else if (biere.getBrasserie() == null
				|| biere.getBrasserie().isEmpty()) {
			message = "La modification n'a pas pu être réalisée. Il manque le nom de la brasserie";
		} else if (biere.getCouleur() == null || biere.getCouleur().isEmpty()) {
			message = "La modification n'a pas pu être réalisée. Il manque la couleur";
		} else if (biere.getType() == null || biere.getType().isEmpty()) {
			message = "La modification n'a pas pu être réalisée. Il manque le type";

		} else {
			Biere biereDB = this.biereDao.getBiere(biere.getNom());
			if (biereDB == null) {
				message = "La modification n'a pas pu être réalisée. Cette bière n'existe pas";
			} else {
				modificationReussie = this.biereDao.modifierBiere(biere);
				if (modificationReussie) {
					message = "Modification réalisée avec succès";
				} else {
					message = "La modification n'a pas pu être réalisée";
				}
			}
		}
		bundle.put(Bundle.OPERATION_REUSSIE, modificationReussie);
		bundle.put(Bundle.MESSAGE, message);
	}
}