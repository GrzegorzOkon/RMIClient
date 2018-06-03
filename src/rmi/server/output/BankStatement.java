package rmi.server.output;

import java.io.Serializable;
import java.util.ArrayList;

public class BankStatement implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private ArrayList<Bill> historiaRachunkuKlienta;
	private int sumaZakupionychTowar�w;

	public BankStatement(ArrayList<Bill> historiaRachunkuKlienta, int sumaZakupionychTowar�w) {
		this.historiaRachunkuKlienta = historiaRachunkuKlienta;
		this.sumaZakupionychTowar�w = sumaZakupionychTowar�w;
	}

	public ArrayList<Bill> getHistoriaRachunkuKlienta() {
		return historiaRachunkuKlienta;
	}

	public void setHistoriaRachunkuKlienta(ArrayList<Bill> historiaRachunkuKlienta) {
		this.historiaRachunkuKlienta = historiaRachunkuKlienta;
	}

	public int getSumaZakupionychTowar�w() {
		return sumaZakupionychTowar�w;
	}

	public void setSumaZakupionychTowar�w(int sumaZakupionychTowar�w) {
		this.sumaZakupionychTowar�w = sumaZakupionychTowar�w;
	}

	@Override
	public String toString() {
		final int maxLen = 10;
		return "BankStatement [historiaRachunkuKlienta=" + (historiaRachunkuKlienta != null
				? historiaRachunkuKlienta.subList(0, Math.min(historiaRachunkuKlienta.size(), maxLen))
				: null) + ", sumaZakupionychTowar�w=" + sumaZakupionychTowar�w + "]";
	}
}
