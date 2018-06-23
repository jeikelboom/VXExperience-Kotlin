package kassakata;

import java.util.ArrayList;
import java.util.List;


/**
 * 
 * De scanner scan ieder artikel en stuur het SKU-nummer naar de kassa.
 * De kassa registreert alle artikelen.
 * De kassa haalt de de omschrijving en de prijs op.
 * De printer kan de lijst met artikelen en aantallen ophalen om een bon te printen.
 * De kassa berekent het totaal bedrag.
 * De printer kan dit ophalen om af te drukken als hij een sein krijgt voor een bon.
 * De klanten display toont ieder artikel als het gescand wordt met prijs.
 * Als het totaalbedrag betaald is gaat de bon naar het voorraad systeem en het financiele systeem.
 * 
 * Prijzen (voorbeeld)
 * Handzeep      Nivea      1,35
 * Handzeep      Dove       1,55
 * Scheerzeep    Nivea      2,50
 * Aardbeien Jam De Betuwe  1,35
 * Pruimen Jam   De Betuwe  1,60
 * Stroop        Huismerk   1,55
 * Brood volkoren           1.95
 * 
 * Korting:
 * Handzeep Dove drie voor vier euro
 * Alle soorten Jam 3e gratis
 * 
 * =======> Alle Nivea producten Tweede gratis mix en match.
 *
 *
 */

public class KassaImpl implements Kassa {
	List<KortingsRegel> kortingsRegels = new ArrayList<KortingsRegel>();
	int korting = 0 ;
	List<BonRegel> regels = new ArrayList<BonRegel>();
	private ArtikelRepository repository;
	

	public void setRepository(ArtikelRepository repository) {
		this.repository = repository;
	}


	public void addKortingsRegel(KortingsRegel kortingsRegel) {
		this.kortingsRegels.add(kortingsRegel);
	}


	@Override
	public void scan(String sku){
		BonRegel regel = new BonRegel(repository.findArtikel(sku));
		regels.add(regel);
	}

	@Override
	public int totaal() {
		int totaal = 0;
		pasKortingRegelsToe();
		
		for (BonRegel regel: regels) {
			totaal = totaal + regel.getPrijs();
		}
		return totaal - korting;
	}


	private void pasKortingRegelsToe() {
		int kortingbedragje = 0;
		for (KortingsRegel kortingsRegel : this.kortingsRegels) {
			kortingbedragje += kortingsRegel.korting(regels);
		}
		this.korting = this.korting + kortingbedragje;
	}
	
	
}
