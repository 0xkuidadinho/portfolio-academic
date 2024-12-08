package project;

public class Movie {
	private String title;
	private int year;
	private int quantity;
	private String[] rentals;
	private double price;
	private double tax;
	private String code;

	public Movie(String title, int year, int quantity, String[] rentals, double price, double tax) {
		this.title = title;
		this.year = year;
		this.quantity = quantity;
		this.rentals = rentals;
		this.price = price;
		this.tax = tax;
		this.code = code;
	}

	public String getTitle() {
		return title;
	}

	public int getYear() {
		return year;
	}

	public int getQuantity() {
		return quantity;
	}

	public String[] getRentals() {
		return rentals;
	}

	public double getPrice() {
		return price;
	}

	public double getTax() {
		return tax;
	}

	public String getCode() {
		return code;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public void addRental(String rental) {
		for(int i = 0; i < rentals.length; i++) {
			if(rentals[i] == null) {
				rentals[i] = rental;
				break;
			}
		}
	}
	
	public void removeRental(String rental) {
		for(int i = 0; i < rentals.length; i++) {
			if(rentals[i].equals(rental)) {
				rentals[i] = null;
				break;
			}
		}
	}

}
