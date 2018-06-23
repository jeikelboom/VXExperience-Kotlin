package kassakata;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


/**
 * Bij aankoop nivea tweede gratis
 * Eigenlijk: de goedkoopste gratis.
 * 
 * @author jeikelboom
 *
 */
public class NiveaTweedeGratisRegel implements KortingsRegel {

	public static final String NIVEA = "Nivea";

	@Override
	public int korting(List<BonRegel> regels) {
		List<BonRegel> niveas = filterNiveas(regels);
		sorteerOpPrijs(niveas);
		geefKorting(niveas);
		return 0;
	}

	private List<BonRegel> filterNiveas(List<BonRegel> regels) {
		List<BonRegel> filtered = new ArrayList<BonRegel>();
		for (BonRegel regel : regels) {
			if (NIVEA.equals(regel.getArtikel().getMerk())) {
				filtered.add(regel);
			}
		}
		return filtered;
	}

	private void sorteerOpPrijs(List<BonRegel> regels) {
		Collections.sort(regels);
	}

	private void geefKorting(List<BonRegel> sorted) {
		int aantal = sorted.size() / 2;
		for (int i = 0; i < aantal; i++) {
			sorted.get(i).setPrijs(0);
		}
	}

	
}
