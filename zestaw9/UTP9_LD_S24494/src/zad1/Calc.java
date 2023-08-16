/**
 *
 *  @author Łęczycka Dominika S24494
 *
 */

package zad1;


import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;

public class Calc {
	private int boolToInt(Boolean b) {
	    return b.compareTo(false);
	}

    private static final Map<String, Operator> operatorMap = new HashMap<>();
    static {
        operatorMap.put("+", BigDecimal::add);
        operatorMap.put("-", BigDecimal::subtract);
        operatorMap.put("*", BigDecimal::multiply);
        operatorMap.put("/", (x, y) -> x.divide(y, 7, RoundingMode.HALF_UP));
    }

    public String doCalc(String cmd) {
    	String[] parts = cmd.split("\\s+");
    	int tab[] = {0};
    	boolean b;
    	try {
        	b = parts.length != 3;
        	tab[boolToInt(b)] = 0;
        } catch(Exception e) {
        	return "Invalid command to calc";
        }
        try {
            BigDecimal num1 = new BigDecimal(parts[0]);
            BigDecimal num2 = new BigDecimal(parts[2]);
            Operator operator = operatorMap.get(parts[1]);
            try {
            	b = operator == null;
            	tab[boolToInt(b)] = 0;
            } catch(Exception e) {
            	return "Invalid command to calc";
            }
            BigDecimal result = operator.calculate(num1, num2);
            return result.toString();
        } catch (NumberFormatException e) {
            return "Invalid command to calc";
        }
    }

    private interface Operator {
        BigDecimal calculate(BigDecimal x, BigDecimal y);
    }
}
