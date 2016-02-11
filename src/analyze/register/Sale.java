package analyze.register;

public class Sale {
	private Payment p;

	public void makePayment(int cashTendered) {
		p = new Payment(cashTendered);
		p.authorize();
	}
}
