package kassakata;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 * Dit is meer een integratie test om te kijken of de
 * kassa werkt icm de kortingregel.
 * Er zijn voor beide unit tests, dus dit is een doublure.
 * @author jeikelboom
 *
 */
public class KortingTest {
	KassaImpl kassa;
	
	@Before
	public void before() {
		kassa = new KassaImpl();
		kassa.setRepository(new MockedArtikelRepository());
		kassa.addKortingsRegel(new NiveaTweedeGratisRegel());
	}

	@Test
	// drie Nivea 250 en 2 van 135 met wat andere producten
	public void test5() {
		kassa.scan("25"); // 250 Nivea
		kassa.scan("28"); // 155 geen Nivea
		kassa.scan("23"); // 135 -> 0 Nivea
		kassa.scan("28"); // 155 geen Nivea
		kassa.scan("27"); // 160 geen Nivea 
		kassa.scan("25"); // 250 Nivea
		kassa.scan("28"); // 155 geen Nivea
		kassa.scan("23"); // 135 -> 0
		kassa.scan("25"); // 250 Nivea
		int totaal = kassa.totaal();
		
		Assert.assertEquals(1375, totaal);
		
	}
	
	
	
	
	
}
