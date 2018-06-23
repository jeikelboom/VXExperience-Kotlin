package kassakata;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KortingsRegelTest {
	
	List<BonRegel> regels = new ArrayList<BonRegel>();
	
	KortingsRegel kortingsRegel;
	ArtikelRepository artikelRepository;
	
	@Before 
	public void before() {
		this.kortingsRegel = new NiveaTweedeGratisRegel();
		this.artikelRepository = new MockedArtikelRepository();
	}
	
	private void scan(String sku) {
		regels.add(new BonRegel(artikelRepository.findArtikel(sku)));
	}
	
	
	@Test
	public void test1() {
		scan("23"); // 135
		scan("23"); // 0
		int korting = kortingsRegel.korting(this.regels);
		Assert.assertEquals(0, korting);
		Assert.assertEquals(0, this.regels.get(0).getPrijs());
		Assert.assertEquals(135, this.regels.get(1).getPrijs());
	}

	@Test
	public void test2() {
		scan("23"); // 135
		scan("28"); // 155
		scan("23"); // 0
		int korting = kortingsRegel.korting(this.regels);
		Assert.assertEquals(0, korting);
		Assert.assertEquals(0, this.regels.get(0).getPrijs());
		Assert.assertEquals(155, this.regels.get(1).getPrijs());
		Assert.assertEquals(135, this.regels.get(2).getPrijs());
	}

	@Test
	public void test3() {
		scan("23"); // 135 ->0
		scan("28"); // 155 geen Nivea
		scan("25"); // 250 
		int korting = kortingsRegel.korting(this.regels);
		Assert.assertEquals(0, korting);
		Assert.assertEquals(0, this.regels.get(0).getPrijs());
		Assert.assertEquals(155, this.regels.get(1).getPrijs());
		Assert.assertEquals(250, this.regels.get(2).getPrijs());
	}

	@Test
	public void test4() {
		scan("25"); // 250 
		scan("28"); // 155 geen Nivea
		scan("23"); // 135 -> 0
		scan("28"); // 155 geen Nivea
		scan("28"); // 155 geen Nivea
		int korting = kortingsRegel.korting(this.regels);
		Assert.assertEquals(0, korting);

		Assert.assertEquals(250, this.regels.get(0).getPrijs());
		Assert.assertEquals(155, this.regels.get(1).getPrijs());
		Assert.assertEquals(0, this.regels.get(2).getPrijs());
		Assert.assertEquals(155, this.regels.get(3).getPrijs());
		Assert.assertEquals(155, this.regels.get(4).getPrijs());
	}
	
	@Test
	// drie Nivea 250 en 2 van 135 met wat andere producten
	public void test5() {
		scan("25"); // 250 Nivea
		scan("28"); // 155 geen Nivea
		scan("23"); // 135 -> 0 Nivea
		scan("28"); // 155 geen Nivea
		scan("27"); // 160 geen Nivea
		scan("25"); // 250 Nivea
		scan("28"); // 155 geen Nivea
		scan("23"); // 135 -> 0
		scan("25"); // 250 Nivea
		int korting = kortingsRegel.korting(this.regels);
		Assert.assertEquals(0, korting);

		Assert.assertEquals(250, this.regels.get(0).getPrijs());
		Assert.assertEquals(155, this.regels.get(1).getPrijs());
		Assert.assertEquals(0, this.regels.get(2).getPrijs());
		Assert.assertEquals(155, this.regels.get(3).getPrijs());
		Assert.assertEquals(160, this.regels.get(4).getPrijs());
		Assert.assertEquals(250, this.regels.get(5).getPrijs());
		Assert.assertEquals(155, this.regels.get(6).getPrijs());
		Assert.assertEquals(0, this.regels.get(7).getPrijs());
		Assert.assertEquals(250, this.regels.get(8).getPrijs());
	}
	
	
	
	
}
