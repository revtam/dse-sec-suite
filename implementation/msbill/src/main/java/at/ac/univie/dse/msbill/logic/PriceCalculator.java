package at.ac.univie.dse.msbill.logic;

public class PriceCalculator {

	private Double basePrice;
	private Double factor;

	public PriceCalculator(Double basePrice, Double factor) {
		this.basePrice = basePrice;
		this.factor = factor;
	}

	/**
	 * The formula used for price calculation is: basePrice*factor^load where load
	 * is an integer, basePrice and factor are Double. Given the load is 0, the
	 * price is the basePrice.
	 * 
	 * @param load Number of tasks in the queue
	 */
	public Double calculate(int load) {
		return basePrice * Math.pow(factor, load);
	}

}
