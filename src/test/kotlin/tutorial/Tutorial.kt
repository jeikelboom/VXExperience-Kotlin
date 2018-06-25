package tutorial

import com.sun.org.apache.bcel.internal.generic.VariableLengthInstruction
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.fail

/* ============================================================================
Top level elements at package level
values, variables, functions, classes, objects
 */

// val is an immutable variable
// the type is String, which is inferred by the compiler
val visitor = "Donald"

// var defines a mutable variable.
// Note that types are specified as <name>: <type>
var totalGreetings: Int = 0

// functions can be defined standalone
fun makeGreeting(name: String) : String {
    totalGreetings += 1
    return "Welcome to the zoo ${name}"
}



// It is only fair to have syntax for objects in a object-oriented language.
// This exampe would correspond to Singleton pattern in Java.
object zoo {
    val director = "hardcoded"
}

// and like Java we can have classes.
class globalsTest {

    @Test
    fun greeting(): Unit { // Unit is the default as return type, (Java: void).
        val actual = makeGreeting(visitor)
        val expected = "Welcome to the zoo Donald"
        assertEquals(expected, actual)
    }

    // Functions can also be defined with a statement (between {}) or an expression body
    // if is in Kotlin an expression.
    // An assignment in Java is an expression, but in Kotlin a statement
    fun least(a: Int, b: Int): Int = if (a < b) a else b

    // we can even use Junit annotations
    @Test
    fun smallest() {
        assertEquals(4, least(6, 4))
    }
}




/* ============================================================================
Simplified class definition
Defaults are different in Kotlin, for example everything is public
unless specified otherwise

Classes are final by default.
If you want a hierarchy you must explicitly specify "open"

The constructor defines the class members.
 */
open class Animal(val name: String) {
    override fun toString(): String {
        return " ${this.javaClass.kotlin.simpleName}: ${this.name} "
    }
}

/* ============================================================================
Getters and setters are now properties.
 */

class AnimalTest {
    @Test
    fun named() {
        val puppy = Animal("trippy")
        assertEquals("trippy", puppy.name) // this under water call the getter
    }
}

/* ============================================================================
enum classes and when expression.
 */

enum class Card{Hearts, Diamonds, Clubs, Spades}
val mycard = Card.Hearts

// when is an exprsssion, unlike switch in Java.
// it must be exhaustive, and no break needed.
// NB when can also be used without argument, to select the
// first case that evaluates to true.
fun card2string(card: Card): String =
    when (card) {
        Card.Clubs -> "clubs"
        Card.Diamonds -> "diamonds"
        Card.Hearts -> "hearts"
        Card.Spades -> "spades"
    }


/* ============================================================================
Null safety
nullability is part of the type, indicated by a questionmark.
 */

var monkey: Animal? = null // can be null
var dog: Animal = Animal("frodo") // will not compile with null

val monkeysnam: String = monkey?.name ?: "unknown" // safe call ?. and safe elvis operator ?:



/* ============================================================================
String interpolation and multiline strings.
Inside strings values can be substited with ${}
Strings with triple quotes can span multiple lines
 */
val welcomePage = """
    <html>
        <head>
            <title>Zoo</title>
        </head>
        <body>
            <h1>Welcome in the zoo ${visitor}<h1>
        </body>
    </html>
""".trimIndent()  // remove any common indent.


/* ============================================================================
Making a type hierarchy
Use : as type declaration rather the extends or implements.
Call the constructor of the superclass in the declaration
 */
interface Noise {
    fun makeSound() : String
}
open class Mammal(name: String) : Animal(name)
open class Fish(name: String) : Animal(name)

class Cat(name: String) : Mammal(name), Noise {
    override fun makeSound() = "Mieuw"
}
open class Camel(name: String) : Mammal(name)
open class Goldfish(name: String) : Fish(name)

/* ============================================================================
data classes are great as immutable value objects
data classes can not be inherrited from
 */

data class Product(val name: String, val brand: String, val price: Int)
val blueToothbrush = Product("blue toothbrush for child", "prodent", 165)
val redToothbrush = blueToothbrush.copy(name = "red toothbrush for child")

/* ============================================================================
 * Kotlin supports polymorphism by subtyping, and parametric polymorphism
 * Types are not available on runtime (type erasure)
 * A monomorphism is when we make a definition for a specific variant.
 * reified compiles in the specific type, when the function is called.
 * So we can check the type parameter.
 * In Java you can do this by passing the Class as a parameter.
 */

/*
 * Kotlin has smart casts.
 * After the test inhabitant is Cat, the inhabitant is declared as Cat
 * and we can access Cat specific functionality.
 */
class Cage<T: Animal> (var animal : T?) {
    inline fun <reified T>  containsA() = animal is T
    fun containsCat() = containsA<Cat>()
    fun containsCamel() = containsA<Camel>()
    fun mew(): String {
        val inhabitant: T? = animal
        return if (inhabitant is Cat)
            // inhabitant is now a Cat,
            // it is not null either, as we did not cast to 'Cat?'
            inhabitant.makeSound () else ""
    }
}
/*
 * A subtle point here. Why do we have to create an extra variable inhabitant?
 * theoretically another thread may assign somthing to animal (var).
 * inhabitant cannot be altered halfway the loop.
 * Many restrictions in Kotlin enable compiler functionality.
 * Try out what happens if you leave out inhabitant and use animal directly.
 */


/* ============================================================================
 * Extension functions
 * An extension function defines a function that looks like it is
 * added to the class.
 * The class is not changed bij this. It is similar to a static function with 2 arguments.
 */
infix fun Int.gedeeldDoor(denominator: Int) = Rational(this, denominator)

val vijf = 10.gedeeldDoor(2)
/* ============================================================================
 *  an infix function can be written between its two arguments.
 *  this is syntactical sugar for gedeeldDoor(6, 2)
 */
val zes = 12 gedeeldDoor  2

/* ============================================================================
 * Type systeem, Lists etc.
 * Cat is a subtype of Animal.
 * Nullability is part of the type system
 * Cat is a subtype of Cat?
 *
 */

class NullDemo() {

    @Test
    fun nullSafety() {
        var kitty :Cat? = null
        if (kitty is Cat) {
            println("Kit is a Cat")
        } else {
            println("Kit is not a Cat")
        }
    }
}


/*
 * Immutable collections of immutable objects have subtype relations.
 * A List in Kotlin is immutable by default.
 * In that case a List<Cat> is a subtype of List<Animal>
 * An off course a List<Cat> subtype if List<Cat?> subtype of List<Animal?>
 */


class SubtypeDemo() {

    @Test
    fun subtypes() {
        val cats: List<Cat> = listOf(Cat("flippy"), Cat("floppy "))
        val animals: List<Animal> = listOf(Animal("flappy"))
        val allAnimals :List<Animal> = animals + cats
        println(allAnimals)

    }
}

/* ============================================================================
 * Overview of the type system.
 * We denote supertype here by a >, like Super > Sub
 * The type Any (like Object in Java) is the supertype of all other classes.
 * But there is also a type Nothing which is the subtype of all other classes.
 *  Any > Animal > Cat > Nothing
 * There is a parallel universe of nullable types:
 *  Any? > Animal? > Cat? > Nothing?
 * And off course, between the parallel universes:
 *  Any? > Any
 *  Aminal? > Animal
 *  Cat? > Cat
 * On the bottom of the type system we have Unit an Nothing
 * Nothing is an empty type, it has an empty set of permissable values.
 * Returning Nothing results in stopping the program or looping forever.
 * Nothing? is in fact the type of null, the only value possible.
 * Unit is a type with exactly one value Unit.
 * Returning Unit is similar to returning void in Java.
 *
 */

/* ============================================================================
 * Lambdas
 * Kotlin has support for lambda expressions
 */

val addThemUp: (Int, Int) -> Int = {x, y -> x + y}
fun addThemUp2(x: Int, y: Int): Int = x + y

// Can use it as shortcut
val incThemUp: (Int) -> Int = {it + 1}

class LambdaDemo {
    @Test
    fun arith() {
        assertEquals(7, addThemUp(3,4))
        assertEquals(7, addThemUp2(3,4))
        assertEquals(7, incThemUp(6))
    }

    data class Person(val name: String, val money: Int)
    val bill = Person("Bill", 5000)
    val larry = Person("Larry", 300)
    val donald= Person("Donald", 2500)
    val richPeople: List<Person> = listOf(larry, bill, donald)

    /* Lambdas can be used with lists.
     */
    @Test
    fun list() {
        assertEquals(7800, richPeople.sumBy { it.money })
        assertEquals(bill, richPeople.maxBy { it.money })
        assertEquals(listOf(bill, donald), richPeople.filter({it.money > 500 }))
    }
}




/* ============================================================================
 An extensive example on defininig a datatype.
This class has initialization logic
It show operator overloading (you can define your own {+, -, *, /}
 */
class Rational(nume: Int, denume: Int) {
    val numerator: Int
    val denominator: Int
    init {
        if (denume == 0) {
            throw IllegalArgumentException("Denominator must not be zero")
        }
        fun gcd ( a: Int,  b: Int) :Int {
            return if (b==0) Math.abs(a) else if (a == 0) Math.abs(b) else gcd (b , a % b)
        }
        fun sgn ( a: Int) :Int = if (a > 0) 1 else if (a == 0)  0 else -1
        if (nume == 0) {
            numerator = 0
            denominator = 1
        } else {
            val gcd = gcd(nume, denume)
            val sgn = sgn(denume)
            numerator = sgn * nume / gcd
            denominator = sgn * denume / gcd
        }
    }

    constructor(i : Int) : this (i, 1)

    override fun toString(): String {
        return "${numerator}/${denominator}"
    }

    operator fun plus(r :  Rational) : Rational{
        return Rational(this.numerator * r.denominator + this.denominator* r.numerator, this.denominator * r.denominator)
    }

    operator fun minus(r :  Rational) : Rational{
        return Rational(this.numerator * r.denominator - this.denominator* r.numerator, this.denominator * r.denominator)
    }

    operator fun times(r :  Rational) : Rational{
        return Rational(this.numerator * r.numerator, this.denominator * r.denominator)
    }

    operator fun div(r :  Rational) : Rational{
        return Rational(this.numerator * r.denominator, this.denominator * r.numerator)
    }

    operator fun unaryMinus(): Rational {
        return Rational( - this.numerator, this.denominator)
    }
    override fun hashCode(): Int {
        var result = numerator
        result = 31 * result + denominator
        return result
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Rational // Smart cast from now on other is a Rational

        if (numerator != other.numerator) return false
        if (denominator != other.denominator) return false

        return true
    }

}

class RationalsTest {


    @Test
    fun denominatorCanNotBeZero() {
        try {
            val z = Rational(5, 0)
        } catch (e: IllegalArgumentException) {
            return;
        }
        fail(message = "Denominator must not be zero" )
    }

    @Test
    fun positiveNumbers() {
        val r = Rational(8,2)
        assertEquals(1, r.denominator)
        assertEquals(4, r.numerator)
    }

    @Test
    fun denomNeg() {
        val r = Rational(8,-2)
        assertEquals(1, r.denominator)
        assertEquals(-4, r.numerator)
    }

    @Test
    fun nomerNeg() {
        val r = Rational(-8,2)
        assertEquals(1, r.denominator)
        assertEquals(-4, r.numerator)
    }

    @Test
    fun bothNeg() {
        val r = Rational(-8,-2)
        assertEquals(1, r.denominator)
        assertEquals(4, r.numerator)
    }

    @Test
    fun aritmetic2() {
        val a = 24 gedeeldDoor 19 // infix function
        val b = Rational(2)
        assertEquals(Rational(12,19), a / b)
    }

    @Test
    fun negation() {
        val a = Rational(3, 5)
        val b = - a
        assertEquals(Rational(-3, 5), b)
    }

    @Test fun zero() {
        val one = 0 gedeeldDoor 5
        assertEquals(0, one.numerator)
        assertEquals(1, one.denominator)
    }

}
