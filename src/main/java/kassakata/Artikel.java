package kassakata;

public class Artikel {

	private int prijs;
	private String artikelgroep;
	private String sku;
	private String merk;
	private String omschrijving;

	public Artikel(int prijs, String artikelgroep, String sku, String merk, String omschrijving) {
		super();
		this.prijs = prijs;
		this.artikelgroep = artikelgroep;
		this.sku = sku;
		this.merk = merk;
		this.omschrijving = omschrijving;
	}

	public int getPrijs() {
		return prijs;
	}

	public String getArtikelgroep() {
		return artikelgroep;
	}

	public String getSku() {
		return sku;
	}

	public String getMerk() {
		return merk;
	}

	public String getOmschrijving() {
		return omschrijving;
	}
	
	
	
}
