package assessment3;
import java.util.*;
public class Salesreport {
	private int i;
	private int invoice;
	public int getInvoice() {
		return invoice;
	}
	public void setInvoice(int invoice) {
		this.invoice = invoice;
	}
	private ArrayList<Stock> report=new ArrayList<>();
	public Salesreport(int i,int in, ArrayList<Stock> report) {
		this.invoice=i;
		this.i = in;
		this.report = report;
	}
	public int getI() {
		return i;
	}
	public void setI(int i) {
		this.i = i;
	}
	public List<Stock> getReport() {
		return report;
	}
	public void setReport(ArrayList<Stock> report) {
		this.report = report;
	}
}
