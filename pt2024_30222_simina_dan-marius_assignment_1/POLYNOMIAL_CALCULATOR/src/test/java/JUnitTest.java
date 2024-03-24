import model.Operations;
import model.Polynomial;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.Test;

import java.util.Map;

import static org.junit.Assert.*;

public class JUnitTest {

//  P1 = x^3-2x^2+6x-5
//  P2 = x^2-1

    private static int numberOfTestsPerformed = 0;
    private static int numberOfSuccessfulTests = 0;

    private static final Operations op = new Operations();
    private static Polynomial firstTestFirstPolynomial;
    private static Polynomial firstTestSecondPolynomial;

    private static Polynomial secondTestFirstPolynomial;
    private static Polynomial secondTestSecondPolynomial;

    @Before
    public void setUp() {
        firstTestFirstPolynomial = new Polynomial();
        firstTestFirstPolynomial.addMonomial(0, -5.0);
        firstTestFirstPolynomial.addMonomial(1, 6.0);
        firstTestFirstPolynomial.addMonomial(2, -2.0);
        firstTestFirstPolynomial.addMonomial(3, 1.0);

        firstTestSecondPolynomial = new Polynomial();
        firstTestSecondPolynomial.addMonomial(0, -1.0);
        firstTestSecondPolynomial.addMonomial(2, 1.0);

        secondTestFirstPolynomial = new Polynomial("-x^3 - x^2 + x + 1");
        secondTestSecondPolynomial = new Polynomial("-x + 1");

        numberOfTestsPerformed++;
    }

    @AfterClass
    public static void afterClass() {
        System.out.println(numberOfTestsPerformed + " tests were performed\n" + numberOfSuccessfulTests + " tests were successful");
    }

    @Test
    public void testStringToPolynomialConstructor() {
        Polynomial polynomial = new Polynomial("-2x^3 +  3x^2 + x +    4");
        assertEquals(polynomial.toString(), "-2x^3+3x^2+x+4");
        numberOfSuccessfulTests++;
    }

    @Test
    public void firstTestAddOperation() {
        Polynomial res = op.add(firstTestFirstPolynomial, firstTestSecondPolynomial);
        assertEquals(res.toString(), "x^3-x^2+6x-6");
        numberOfSuccessfulTests++;
    }

    @Test
    public void secondTestAddOperation() {
        Polynomial res = op.add(secondTestFirstPolynomial, secondTestSecondPolynomial);
        assertEquals(res.toString(), "-x^3-x^2+2");
        numberOfSuccessfulTests++;
    }

    @Test
    public void firstTestSubtractOperation() {
        Polynomial res = op.subtract(firstTestFirstPolynomial, firstTestSecondPolynomial);
        assertEquals(res.toString(), "x^3-3x^2+6x-4");
        numberOfSuccessfulTests++;
    }

    @Test
    public void secondTestSubtractOperation() {
        Polynomial res = op.subtract(secondTestFirstPolynomial, secondTestSecondPolynomial);
        assertEquals(res.toString(), "-x^3-x^2+2x");
        numberOfSuccessfulTests++;
    }

    @Test
    public void firstTestMultiplyOperation() {
        Polynomial res = op.multiply(firstTestFirstPolynomial, firstTestSecondPolynomial);
        assertEquals(res.toString(), "x^5-2x^4+5x^3-3x^2-6x+5");
        numberOfSuccessfulTests++;
    }

    @Test
    public void secondTestMultiplyOperation() {
        Polynomial res = op.multiply(secondTestFirstPolynomial, secondTestSecondPolynomial);
        assertEquals(res.toString(), "x^4-2x^2+1");
        numberOfSuccessfulTests++;
    }

    @Test
    public void firstTestDivideOperation() {
        Map.Entry<Polynomial, Polynomial> res = op.divide(firstTestFirstPolynomial, firstTestSecondPolynomial);
        assertEquals(res.getKey().toString(), "x-2");
        assertEquals(res.getValue().toString(), "7x-7");
        numberOfSuccessfulTests++;
    }

    @Test
    public void secondTestDivideOperation() {
        Map.Entry<Polynomial, Polynomial> res = op.divide(secondTestFirstPolynomial, secondTestSecondPolynomial);
        assertEquals(res.getKey().toString(), "x^2+2x+1");
        assertEquals(res.getValue().toString(), "0");
        numberOfSuccessfulTests++;
    }

    @Test
    public void firstTestDerivativeOperation() {
        Polynomial res = op.derivative(firstTestFirstPolynomial);
        assertEquals(res.toString(), "3x^2-4x+6");
        numberOfSuccessfulTests++;
    }

    @Test
    public void secondTestDerivativeOperation() {
        Polynomial res = op.derivative(secondTestFirstPolynomial);
        assertEquals(res.toString(), "-3x^2-2x+1");
        numberOfSuccessfulTests++;
    }

    @Test
    public void firstTestIntegralOperation() {
        Polynomial res = op.integral(firstTestFirstPolynomial);
        assertEquals(res.toString(), "0.25x^4-0.66x^3+3x^2-5x");
        numberOfSuccessfulTests++;
    }

    @Test
    public void secondTestIntegralOperation() {
        Polynomial res = op.integral(secondTestFirstPolynomial);
        assertEquals(res.toString(), "-0.25x^4-0.33x^3+0.5x^2+x");
        numberOfSuccessfulTests++;
    }
}
