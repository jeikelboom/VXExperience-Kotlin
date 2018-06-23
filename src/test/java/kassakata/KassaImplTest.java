package kassakata;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class KassaImplTest {

	KassaImpl kassa;
	
	@Before
	public void before() {
		kassa = new KassaImpl();
		kassa.setRepository(new MockedArtikelRepository());
		// we voeren een kortingesregel op
		// het eerste product voor 27 cent en 3 cent totaal korting
		kassa.addKortingsRegel(new KortingsRegel() {
			
			@Override
			public int korting(List<BonRegel> regels) {
				BonRegel eersteRegel = regels.get(0);
				if (eersteRegel != null) {
					eersteRegel.setPrijs(27);
				}
				return 3;
			}
		});
	}
	
	@Test
	public void test() {
		kassa.scan("23");
		int antwoord = kassa.totaal();
		Assert.assertEquals(24, antwoord);
	}
	
	@Test
	public void test2() {
		kassa.scan("23");
		kassa.scan("23");
		int antwoord = kassa.totaal();
		Assert.assertEquals(159, antwoord); 
	}
	
	@Test
	public void test3() {
		kassa.scan("23");
		kassa.scan("23");
		kassa.scan("25");
		
		int antwoord = kassa.totaal();
		Assert.assertEquals(409, antwoord); 
	}
	
	@Test
	public void test4(){
		kassa.scan("25");
		kassa.scan("23");
		kassa.scan("24");
		
		Assert.assertEquals(314, kassa.totaal());
	}
	
	
	
}
