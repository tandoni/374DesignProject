package analyze.register;

public class Register {
	private Sale sale;

	public void checkout(int cashTendered) {
		sale.makePayment(cashTendered);
	}
}
