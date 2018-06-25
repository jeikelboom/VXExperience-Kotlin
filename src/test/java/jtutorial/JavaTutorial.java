package jtutorial;

import org.junit.Assert;
import org.junit.Test;


public class JavaTutorial {

    static class AnimalJ {}
    static class CatJ extends AnimalJ {}
    static class DogJ extends AnimalJ{}

    @Test
    public void arrayAnomaliesInJava() {
        boolean exc = false;
        CatJ[] cats = new CatJ[3];
        AnimalJ[] animals = cats;
        animals[0] = new CatJ();
        try {
            animals[1] = new DogJ();
        } catch (Exception e) {
            System.out.println("Cannot mix cats and dogs");
            exc = true;
        }
        Assert.assertTrue(exc);
    }


}
