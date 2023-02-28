package nerdle;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class generateEquationTest {

	Equation testInstance = new Equation();
	Equation testInstance2 = new Equation();

	@Test
	public void testStringAndEquationLength() {
		assertEquals(testInstance.getEquationLength(), testInstance.getEquation().length());
         //Denklemin uzunluðu ile girilen tahminin uzunluðunu karþýlaþtýrýyor
	}

	@Test
	public void testNumbersAndOperators() {
		assertEquals(testInstance.getNumbers().size(), testInstance.getOperators().size() + 1);
		
	}

	@Test
	public void testDivisionError() {
		int i;
		if (testInstance.getOperators().contains("/")) {
			i = testInstance.getOperators().indexOf("/");
			assertNotEquals(testInstance.getNumbers().get(i), 0);
		}
	}

	@Test
	public void testNumberOfOperators() {
		while (testInstance.getOperators().size() == testInstance2.getOperators().size()) {
			testInstance2 = new Equation();
		}

		assertNotEquals(testInstance.getOperators().size(), testInstance2.getOperators().size());

	}

	@Test
	public void testOperatorTypes() {

		while (testInstance.getOperators().get(0) == testInstance2.getOperators().get(0)) {
			testInstance2 = new Equation();
		}

		assertNotEquals(testInstance.getOperators().get(0), testInstance2.getOperators().get(0));

	}

	@Test
	public void testEquationLength() {

		while (testInstance.getEquationLength() == testInstance2.getEquationLength()) {
			testInstance2 = new Equation();
		}

		assertNotEquals(testInstance.getEquationLength(), testInstance2.getEquationLength());

	}

}
