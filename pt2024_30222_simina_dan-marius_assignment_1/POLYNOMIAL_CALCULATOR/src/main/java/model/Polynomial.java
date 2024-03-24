package model;

import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Polynomial {
    private final TreeMap<Integer, Double> polynomial;

    public Polynomial() {
        polynomial = new TreeMap<>();
    }

    public Polynomial(String text) {
        polynomial = new TreeMap<>();
        stringToPolynomial(text);
    }

    private void stringToPolynomial(String text) {
        text = text.replace(" ", "");
        text = text.replace("^", "");
        text = text.replace("*", "");
        text = text.toLowerCase();
        Pattern pattern = Pattern.compile("([+-]?+)(\\d*+)(x*+)(\\d*+)");
        Matcher matcher = pattern.matcher(text);

        while (matcher.find()) {
            int sign, coefficient = 0, degree = 0;

            sign = matcher.group(1).isEmpty() || matcher.group(1).contains("+") ? 1 : -1;

            if (!matcher.group(2).isEmpty()) {
                coefficient = Integer.parseInt(matcher.group(2));
            }

            if (matcher.group(3).contains("x")) {
                coefficient = coefficient != 0 ? coefficient : 1;
                degree = 1;
            }

            if (!matcher.group(4).isEmpty()) {
                degree = Integer.parseInt(matcher.group(4));
            }

            coefficient *= sign;
            this.addMonomial(degree, (double) coefficient);
        }
    }

    public void addMonomial(Integer degree, Double coefficient) {
        if (polynomial.containsKey(degree)) {
            double newValue = polynomial.get(degree) + coefficient;

            if (newValue != 0) {
                polynomial.replace(degree, polynomial.get(degree) + coefficient);
            } else {
                polynomial.remove(degree);
            }
        } else if (degree >= 0 && coefficient != 0) {
            polynomial.put(degree, coefficient);
        }
    }

    public TreeMap<Integer, Double> getPolynomial() {
        return polynomial;
    }

    public Integer getDegree() {
        if (polynomial.isEmpty()) {
            return -1;
        }
        return polynomial.lastKey();
    }

    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Map.Entry<Integer, Double> entry : polynomial.descendingMap().entrySet()) {
            if (entry.getValue() < 0) {
                s.append('-');
            } else if (!s.isEmpty()) {
                s.append('+');
            }

            if (Math.abs(entry.getValue()) != 1 || entry.getKey() == 0) {
                Double value = (int) (Math.abs(entry.getValue()) * 100) / 100.0;

                if (value - value.intValue() != 0) {
                    s.append(value);
                } else {
                    s.append(value.intValue());
                }
            }

            if (entry.getKey() != 0) {
                s.append("x");

                if (Math.abs(entry.getKey()) > 1) {
                    s.append("^");
                    s.append(entry.getKey().toString());
                }
            }
        }

        if (s.isEmpty())
            s.append('0');

        return s.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o.getClass().equals(this.getClass()))) {
            return false;
        }

        Polynomial polynomial = (Polynomial) o;

        Iterator<Map.Entry<Integer, Double>> firstPolynomialIterator = this.polynomial.entrySet().iterator();
        Iterator<Map.Entry<Integer, Double>> secondPolynomialIterator = polynomial.getPolynomial().entrySet().iterator();

        while (firstPolynomialIterator.hasNext() && secondPolynomialIterator.hasNext()) {
            Map.Entry<Integer, Double> entryFirstPolynomial = firstPolynomialIterator.next();
            Map.Entry<Integer, Double> entrySecondPolynomial = secondPolynomialIterator.next();

            if (!Objects.equals(entryFirstPolynomial.getKey(), entrySecondPolynomial.getKey()) || !Objects.equals(entryFirstPolynomial.getValue(), entrySecondPolynomial.getValue())) {
                return false;
            }
        }
        return true;
    }
}

