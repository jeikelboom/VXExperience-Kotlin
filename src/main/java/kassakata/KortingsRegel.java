package kassakata;

import java.util.List;

public interface KortingsRegel {
	
	/**
	 * Een kortingsregel kan 
	 * prijzen van regels wijzigen 
	 * of een totaal bedrag teruggeven
	 * of beide
	 * 
	 * @param regels
	 * @return
	 */
	public int korting(List<BonRegel> regels);
	
}
