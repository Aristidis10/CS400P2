// --== CS400 File Header Information ==--
// Name: Caroline Taft
// Email: ctaft@wisc.edu
// Team: NC
// Test Engineer
// TA: Daniel Finer
// Lecturer: Gary Dahl
// Notes to Grader: <optional extra notes>

import org.junit.jupiter.api.Test;  //changed the import statment a little bit
import static org.junit.jupiter.api.Assertions.fail; // changed the import statement a little bit

public class TestStore2 {
	/**
	 * test if the file is added and the max and mins are correct
	 */
	@Test
	public void testAddFile() {
		Store store = new Store();
		store.addFile("project2data.txt"); //added .txt at the end
		if (!store.lookMax().equals("(Honey: $4.49)")) {
			fail("This did not print out the correct max. It printed out " + store.lookMax());
		}
		if (!store.lookMin().equals("(Tomato_Sauce: $0.29)")) {
			fail("This did nto print out the correct min. It printed out " + store.lookMin());
		}
	}
	/**
	 * This tests both lookup for looking up a string as well as a double
	 */
	@Test
	public void testLookUp() {
		Store store = new Store();
		store.addFile("project2data.txt"); // added .txt at the end
		if (store.lookUp("Mustard").equals("(Mustard: $0.69)")) {
			fail("This did not print out the correct value. It printed out " + store.lookUp("Mustard") + 
					" instead of (Mustard: $0.69)");
		}
		if (!store.lookUp(2.49).equals("(Ice_Cream: $2.49)")) {
			fail("This did not print out the correct value. It printed out " + store.lookUp(2.49) + 
					" instead of (Ice_Cream: 2.49)");
		}
	}
	/**
	 * This tests toString
	 */
	@Test
	public void testToString() {
		Store store = new Store();
		store.add("Mustard", 0.69);
		store.add("Honey", 4.49);
		store.add("Ketchup", 1.49);
		store.add("Lemons", 1.99);
		if (!store.toString().equals("[(Ketchup: $1.49), (Mustard: $0.69), (Honey: $4.49), (Lemons: $1.99)]")) {
			fail("This does not print the correct values it is supposed to print " + store.toString());
		}
	}
	/**
	 * tests add
	 */
	@Test
	public void testAdd() {
		Store store = new Store();
		store.add("Mustard", 0.69);
		store.add("Honey", 4.49);
		store.add("Ketchup", 1.49);
		store.add("Lemons", 1.99);
		store.add("Yogurt", 1.99);
		store.add("Onions", 1.69);
		if (!store.lookMin().equals("(Mustard: $0.69)")) {
			fail("This did nto print out the correct min. It printed out " + store.lookMin());
		}
		if (!store.lookMax().equals("(Honey: $4.49)")) {
			fail("This did not print out the correct max. It printed out " + store.lookMax());
		}
		if (!store.lookUp(1.49).equals("(Ketchup: $1.49)")){
			fail("This did not print out the correct value. It printed out " + store.lookUp(1.49));
		}
	}
	
}
