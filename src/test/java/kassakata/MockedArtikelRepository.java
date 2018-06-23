package kassakata;

import static kassakata.ArtikelBuilder.artikel;

import java.util.Arrays;
import java.util.List;

public class MockedArtikelRepository implements ArtikelRepository {

	public static final String HANDZEEP_NIVEA = "handzeep Nivea";
	private static final String SCHEERZEEP = "Scheerzeep";
	private static final String BROOD = "Brood";
	private static final String STROOP = "Stroop";
	private static final String HUISMERK = "Huismerk";
	private static final String JAM = "Jam";
	private static final String DE_BETUWE = "De Betuwe";
	private static final String DOVE = "Dove";
	private static final String HANDZEEP = "Handzeep";
	private static List<Artikel> artikelLijst;
	
	static {
		
		Artikel[] hulp = {
				artikel().sku("23").merk(NiveaTweedeGratisRegel.NIVEA).artikelgroep(HANDZEEP).omschrijving(HANDZEEP_NIVEA).prijs(135).build(),
				artikel().sku("24").merk(DOVE).artikelgroep(HANDZEEP).omschrijving("handzeep Dove").prijs(155).build(),
				artikel().sku("25").merk(NiveaTweedeGratisRegel.NIVEA).artikelgroep(SCHEERZEEP).omschrijving("scheerzeep").prijs(250).build(),
				artikel().sku("26").merk(DE_BETUWE).artikelgroep(JAM).omschrijving("aardbeien jam").prijs(135).build(),
				artikel().sku("27").merk(DE_BETUWE).artikelgroep(JAM).omschrijving("pruimen jam").prijs(160).build(),
				artikel().sku("28").merk(HUISMERK).artikelgroep(STROOP).omschrijving("appelstroop").prijs(155).build(),
				artikel().sku("29").merk(HUISMERK).artikelgroep(BROOD).omschrijving("volkoren brood").prijs(195).build()
		};
		artikelLijst = Arrays.asList(hulp);
	}

	
	@Override
	public Artikel findArtikel(String sku) {
		for (Artikel artikel : artikelLijst) {
			if(artikel.getSku().equals(sku)) {
				return artikel;
			}
		}
		return null;
	}

}
