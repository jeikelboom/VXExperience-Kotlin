package kotlinKassa

/*
 * Assignment:
 * Convert or rebuild the Java kassa to Kotlin.
 */
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


data class Artikel(val prijs: Int,
                   val artikelgroep: String,
                   val sku: String,
                   val merk: String,
                   val omschrijving: String)

interface ArtikelRepository {

    fun findArtikel(sku: String): Artikel

}

data class BonRegel(val prijs: Int, var artikel: Artikel) : Comparable<BonRegel> {

    init {
        this.prijs = artikel.prijs
    }

    override fun compareTo(o: BonRegel): Int {
        return Integer.compare(artikel.prijs, o?.artikel.prijs)
    }


}

interface Kassa {

    fun scan(sku: String)
    fun totaal(): Int
}

interface KortingsRegel {

    /**
     * Een kortingsregel kan
     * prijzen van regels wijzigen
     * of een totaal bedrag teruggeven
     * of beide
     *
     * @param regels
     * @return
     */
    fun korting(regels: List<BonRegel>): Pair<Int, List<BonRegel>>

}

class KassaImpl(val kortingsRegels: List<KortingsRegel>, val repository: ArtikelRepository) : Kassa {
    internal var korting = 0
    internal var regels: MutableList<BonRegel> = ArrayList()

    override fun scan(sku: String) {
        val regel = BonRegel(repository!!.findArtikel(sku))
        regels.add(regel)
    }

    override fun totaal(): Int {
        var totaal = 0
        pasKortingRegelsToe()

        for (regel in regels) {
            totaal = totaal + regel.prijs
        }
        return totaal - korting
    }


    fun pasKortingRegelsToe() {
        var kortingbedragje = 0
        for (kortingsRegel in this.kortingsRegels) {
            kortingbedragje += kortingsRegel.korting(regels)
        }
        this.korting = this.korting + kortingbedragje
    }


}

class NiveaTweedeGratisRegel : KortingsRegel {

    override fun korting(regels: List<BonRegel>): Pair<Int, List<BonRegel>> {
        val niveas = regels.filter({ NIVEA == it.artikel.merk }).sorted()
        val metKorting = niveas.take(niveas.size/2).map {it.copy() }
        geefKorting(niveas)
        return Pair(0, niveas)
    }


    private fun geefKorting(sorted: List<BonRegel>) {
        val aantal = sorted.size / 2
        for (i in 0 until aantal) {
            sorted[i].setPrijs(0)
        }
    }

    companion object {

        val NIVEA = "Nivea"
    }


}


