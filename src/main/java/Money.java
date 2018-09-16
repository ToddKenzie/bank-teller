import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.Locale;

public class Money implements Comparable<Money>{
	
	public static final Money ZERO = new Money("0");
	private NumberFormat currency = NumberFormat.getCurrencyInstance(Locale.US);

	private BigDecimal value;
	
	public Money(String amount) {
		value = new BigDecimal(amount);
	}

	public BigDecimal getValue() {
		return value;
	}

	public Money(BigDecimal value) {
		this.value = value;
	}

	public void add(Money amountToAdd) {
		value = value.add(amountToAdd.getValue());
	}

	public void subtract(Money amountToSubtract) {
		value = value.subtract(amountToSubtract.getValue());
	}

	@Override
	public int compareTo(Money compareValue) {
		if(value.compareTo(compareValue.getValue()) > 0) {
			return 1;
		} else if (value.compareTo(compareValue.getValue()) < 0) {
			return -1;
		} else {
			return 0;
		}
	}
	
	@Override
	public String toString() {
		return currency.format(value);
	}


}
