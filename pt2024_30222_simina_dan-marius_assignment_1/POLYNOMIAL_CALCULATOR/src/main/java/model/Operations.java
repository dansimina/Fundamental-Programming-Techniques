package model;

import java.util.Map;

public class Operations {
    public Polynomial add(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        Polynomial result = new Polynomial();

        for (Map.Entry<Integer, Double> entry : firstPolynomial.getPolynomial().entrySet()) {
            result.addMonomial(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, Double> entry : secondPolynomial.getPolynomial().entrySet()) {
            result.addMonomial(entry.getKey(), entry.getValue());
        }

        return result;
    }

    public Polynomial subtract(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        Polynomial result = new Polynomial();

        for (Map.Entry<Integer, Double> entry : firstPolynomial.getPolynomial().entrySet()) {
            result.addMonomial(entry.getKey(), entry.getValue());
        }

        for (Map.Entry<Integer, Double> entry : secondPolynomial.getPolynomial().entrySet()) {
            result.addMonomial(entry.getKey(), (-1) * entry.getValue());
        }

        return result;
    }

    public Polynomial multiply(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        Polynomial result = new Polynomial();

        for (Map.Entry<Integer, Double> entryFirstPolynomial : firstPolynomial.getPolynomial().entrySet()) {
            for (Map.Entry<Integer, Double> entrySecondPolynomial : secondPolynomial.getPolynomial().entrySet()) {
                result.addMonomial(entryFirstPolynomial.getKey() + entrySecondPolynomial.getKey(), entryFirstPolynomial.getValue() * entrySecondPolynomial.getValue());
            }
        }

        return result;
    }

    private Polynomial multiplyWithMonomial(Polynomial polynomial, Integer monomialDegree, Double monomialCoefficient) {
        Polynomial result = new Polynomial();

        for (Map.Entry<Integer, Double> entry : polynomial.getPolynomial().entrySet()) {
            result.addMonomial(entry.getKey() + monomialDegree, entry.getValue() * monomialCoefficient);
        }

        return result;
    }

    public Map.Entry<Polynomial, Polynomial> divide(Polynomial firstPolynomial, Polynomial secondPolynomial) {
        Polynomial result = new Polynomial();
        Polynomial remainder;
        Map.Entry<Polynomial, Polynomial> pair;

        if (secondPolynomial.getDegree() == -1) {
            return Map.entry(new Polynomial(), new Polynomial());
        }

        while (firstPolynomial.getDegree() >= secondPolynomial.getDegree()) {
            int firstPolynomialDegree = firstPolynomial.getDegree();
            int secondPolynomialDegree = secondPolynomial.getDegree();
            double firstMonomialOfFirstPolynomialCoefficient = firstPolynomial.getPolynomial().get(firstPolynomialDegree);
            double firstMonomialOfSecondPolynomialCoefficient = secondPolynomial.getPolynomial().get(secondPolynomialDegree);

            result.addMonomial(firstPolynomialDegree - secondPolynomialDegree, firstMonomialOfFirstPolynomialCoefficient / firstMonomialOfSecondPolynomialCoefficient);

            Polynomial aux = multiplyWithMonomial(secondPolynomial, firstPolynomialDegree - secondPolynomialDegree, firstMonomialOfFirstPolynomialCoefficient / firstMonomialOfSecondPolynomialCoefficient);
            firstPolynomial = subtract(firstPolynomial, aux);
        }
        remainder = firstPolynomial;
        pair = Map.entry(result, remainder);

        return pair;
    }

    public Polynomial derivative(Polynomial polynomial) {
        Polynomial result = new Polynomial();

        for (Map.Entry<Integer, Double> entry : polynomial.getPolynomial().entrySet()) {
            result.addMonomial(entry.getKey() - 1, entry.getKey() * entry.getValue());
        }

        return result;
    }

    public Polynomial integral(Polynomial polynomial) {
        Polynomial result = new Polynomial();

        for (Map.Entry<Integer, Double> entry : polynomial.getPolynomial().entrySet()) {
            result.addMonomial(entry.getKey() + 1, entry.getValue() / (entry.getKey() + 1));
        }

        return result;
    }
}
