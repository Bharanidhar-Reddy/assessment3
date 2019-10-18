package assessment3;
import java.util.*;
import java.util.regex.Pattern;
public class Mainclass {
	ArrayList<Stock> stock=new ArrayList<>();
	ArrayList<Stock> cart = new ArrayList<>();
	ArrayList<Salesreport> sales = new ArrayList<>();
	int invoice=112299;
	int cust=1;
	class IdException extends Exception{
		public IdException(String msg) {
			super(msg);
		}
	}
	class QuantException extends Exception{
		public QuantException(String msg) {
			super(msg);
		}
	}
	public boolean validateid(String id) {
		Pattern p = Pattern.compile("^\\d{3}$");
		boolean flag=true;
		for(Stock s :stock) {
			if(s.getId()==Integer.parseInt(id)) {flag=false;}
			}
		try {
		if(flag) {throw new IdException("The entered id is not present in data base");}
		if(p.matcher(id).matches()) {return false;}
		if (!(p.matcher(id).matches())) {
			throw new IdException("Id must contain 3 digits and no characters");
		}
		else {return false;}
		}
		catch(IdException e){
			System.err.println(e.getMessage());
			return true;
		}
	}
	public boolean validatequant(int q,Stock s) {
		try {
		if(q<s.getQuantity()) {return false;}
		if(q>s.getQuantity()) {
			throw new QuantException("Entered quantity is more than stock available..Please lower the quantity or remove item");}
		else {return false;}
		}
		catch(QuantException e){
			System.err.println(e.getMessage());
			return true;
		}
	}
	
	public void additem() {
		ArrayList<Stock> dummies=new ArrayList<>();
		Scanner bb=new Scanner(System.in);
		boolean flag=true;
		System.out.println("Enter Id of the item to add");
		String id=bb.nextLine();
		while(validateid(id)) {
			System.out.println("Please enter valid ID");
			id=bb.nextLine();
		}
		for(Stock s:stock) {
			if(s.getId()==Integer.parseInt(id)) {
			dummies.add(new Stock(s.getId(),s.getName(),s.getQuantity(),s.getPrice()));
			dummies.add(new Stock(s.getId(),s.getName(),s.getQuantity(),s.getPrice()));
				viewstock(s);}
		}
		System.out.println("Enter Quantity of the item to add in KG");
			String x=bb.nextLine();
			int quant=Integer.parseInt(x);
			dummies.get(0).setQuantity(quant);
			while(validatequant(quant,dummies.get(1))) {
				System.out.println("Enter 1: to update quantity\nEnter 2: to remove item from the cart");
				int in=Integer.parseInt(bb.nextLine());
				if(in==1) {System.out.println("Enter valid Quantity of the item to add in Kg");
				quant=Integer.parseInt(bb.nextLine());
				dummies.get(0).setQuantity(quant);
				}
				else {System.out.println("Item removed succesfully");flag=false;break;}
			}
				if(flag) {
				for(Stock ss:stock) {
					if(ss.getId()==dummies.get(0).getId()) {ss.setQuantity(ss.getQuantity()-dummies.get(0).getQuantity());}
				}
				cart.add(dummies.get(0));}
	}
	public void showbill() {
		int total=0;
		if(cart.size()>0) {
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.printf("%70s\n","Bill");
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.printf("%-13s%-20s%-10s%-15s%-10s\n\n","ItemID","Item Name","Quantity","Price of 1 KG","Cost");
			for(Stock s:cart) {
				System.out.printf("%-13s%-20s%-10s%-15s%-10s\n",String.valueOf(s.getId()),s.getName(),String.valueOf(s.getQuantity()),String.valueOf(s.getPrice()),String.valueOf(s.getQuantity()*s.getPrice()));
				total+=s.getQuantity()*s.getPrice();
			}
			System.out.println("\nTotal : "+ total + " Rs");
			System.out.println("----------------------------------------------------------------------------------------------------");
			Salesreport rep=new Salesreport(invoice,cust,cart);
			sales.add(rep);
			invoice+=30;
			cust+=1;
			cart.clear();
		}
		else {System.out.println("No items in the cart");}
	}
	
	public void viewstock(Stock s) {
		System.out.printf("\n Item id : %-10s Item Name : %-10s Stock of Item : %-4s Kg\n\n",String.valueOf(s.getId()),s.getName(),String.valueOf(s.getQuantity()));
	}
	
	public void addstock() {
		String[] fruits={"Apple","Orange","PineApple","Mango","Banana","Grape"};
		int[] ids= {111,222,333,444,555,666};
		int[] quantity= {100,150,70,200,250,80};
		float[] price= {100.00f,50.00f,70.00f,80.00f,30.00f,75.00f};
		for(int i=0;i<fruits.length;i++) {
			Stock s=new Stock(ids[i],fruits[i],quantity[i],price[i]);
			stock.add(s);
		}
	}
	public void update(Stock s) {
		Scanner aa=new Scanner(System.in);
		System.out.println("Previous name : " + s.getName()+ " Change name to : ");
		s.setName(aa.nextLine());
		System.out.println("Previous quantity : " + s.getQuantity()+ "kg   Change quantity to : ");
		s.setQuantity(Integer.parseInt(aa.nextLine()));
		System.out.println("Previous Price : " + s.getPrice()+ "Rs.   Change Price to :  Enter in xx.xx format");
		try {
		s.setPrice(aa.nextFloat());}
		catch(Exception e) {System.out.println("Price is not in correct format please process again");}
		System.out.println("Details updated succesfully");
	}
	public void report() {
		System.out.println("----------------------------------------------------------------------------------------------------");
		System.out.printf("%70s\n","Sales Report");
		System.out.println("----------------------------------------------------------------------------------------------------");
		for(Salesreport re:sales) {
			System.out.printf("%70s %s\n","Customer",String.valueOf(re.getI()));
			System.out.println("Invoice Number" + String.valueOf(re.getInvoice()));
			System.out.println("----------------------------------------------------------------------------------------------------");
			System.out.printf("%-13s%-20s%-10s%-15s\n\n","ItemID","Item Name","Quantity","Price of 1 KG");
			for(Stock stoc:re.getReport()) {
				System.out.printf("%-13s%-20s%-5sKgs  %-15s\n",String.valueOf(stoc.getId()),stoc.getName(),String.valueOf(stoc.getQuantity()),String.valueOf(stoc.getPrice()));
			}
			System.out.println("=======================================================================================================");
		}
	}
	
	
	public static void main (String args[]) {
		Mainclass m=new Mainclass();
		m.addstock();
		Scanner sc=new Scanner(System.in);
		int option=0;
		do {
			System.out.println("1. Make a bill to the customer");
			System.out.println("2. Update Stock");
			System.out.println("3. To generate sales report");
			System.out.println("4. To view Stock");
			System.out.println("5. Exit !!!DATA OF SALES REPORT WILL BE LOST");
		
		System.out.println("Enter your Option");
		int a=sc.nextInt();
		sc.nextLine();
		switch(a) {
		case 1: boolean tflag=true;
				while(tflag) {
				m.additem();
				System.out.println("Press 1. To add more Items\nPress 2. to show the bill");
				int i=Integer.parseInt(sc.nextLine());
				if(i==2) {m.showbill();tflag=false;}
				}
				break;
		case 2: System.out.println("Enter Id of the item to update");
				String up=sc.nextLine();
				while(m.validateid(up)) {
					System.out.println("Please enter valid id : ");
					up=sc.nextLine();
				}
				for(Stock st:m.stock) {
					if(st.getId()==Integer.parseInt(up)) {m.update(st);}
				}
				break;
		case 3: m.report();
				break;
		case 4: System.out.println("----------------------------------------------------------------------------------------------------");
				System.out.printf("%70s\n","Stock");
				System.out.println("----------------------------------------------------------------------------------------------------");
				System.out.printf("%-13s%-20s%-10s%-15s\n\n","ItemID","Item Name","Quantity","Price of 1 KG");
				for(Stock sto:m.stock) {
					System.out.printf("%-13s%-20s%-5sKgs  %-15s\n",String.valueOf(sto.getId()),sto.getName(),String.valueOf(sto.getQuantity()),String.valueOf(sto.getPrice()));
				}
				System.out.println("=======================================================================================================");
				break;
		case 5 : option=-1; 
				 break;
		default: System.out.println("Please Enter valid option");
		}
		}while(option!=-1);
	}		
}

