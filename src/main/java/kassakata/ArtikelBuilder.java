package kassakata;

public class ArtikelBuilder {
	
	private int prijs;
	private String artikelgroep;
	private String sku;
	private String merk;
	private String omschrijving;
	
	public ArtikelBuilder prijs(int prijs) {
		this.prijs = prijs;
		return this;
	}
	
	public ArtikelBuilder artikelgroep(String artikelgroep) {
		this.artikelgroep = artikelgroep;
		return this;
	}
	
	public ArtikelBuilder sku(String sku) {
		this.sku = sku;
		return this;
	}
	
	public ArtikelBuilder merk(String merk) {
		this.merk = merk;
		return this;
	}
	
	public ArtikelBuilder omschrijving(String omschrijving) {
		this.omschrijving = omschrijving;
		return this;
	}
	
	public Artikel build() {
		return new Artikel(prijs, artikelgroep, sku, merk, omschrijving);
	}

	public static ArtikelBuilder artikel() {
		return new ArtikelBuilder();
	}
}
