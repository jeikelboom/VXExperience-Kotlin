package kassakata;

public class BonRegel implements Comparable<BonRegel>{
	private int prijs;
	private Artikel artikel;
	
	public BonRegel(Artikel artikel) {
		super();
		this.artikel = artikel;
		this.prijs = artikel.getPrijs();
	}

	public int getPrijs() {
		return prijs;
	}

	public void setPrijs(int prijs) {
		this.prijs = prijs;
	}

	public Artikel getArtikel() {
		return artikel;
	}

	public void setArtikel(Artikel artikel) {
		this.artikel = artikel;
	}

	@Override
	public int compareTo(BonRegel o) {
		return Integer.compare(artikel.getPrijs(), o.getArtikel().getPrijs());
	}
	

}
