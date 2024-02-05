package com.genpact.onlineShoppingApp.service;

import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;
import com.genpact.onlineShoppingApp.repository.VendorRepository;

@Component
public class VendorServiceImpl implements VendorService {
	@Autowired
	private VendorRepository vendorRepository;
	
	public String dotedBox(String string, Integer width) {
		if(width<7)
			width=7;
		int maxStringLen = width-4;
		
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = string.lines().toList();
		for (int i = 0; i < width; i++) {
            stringBuilder.append("*");
        }
		stringBuilder.append("\n");
		
		for(String line: lines) {
	        int remainingChars = line.length();
	        int currentIndex = 0;

	        while (remainingChars > 0) {
	            stringBuilder.append("* ");
	            int charsToPrint = Math.min(maxStringLen, remainingChars);
	            
	            if(charsToPrint!=maxStringLen) {
	            	int padding = (maxStringLen - charsToPrint)/2;
	            	stringBuilder.append(" ".repeat(padding));
	            	stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            	stringBuilder.append(" ".repeat((((maxStringLen - charsToPrint)%2)==0)? padding: padding+1));
	            	stringBuilder.append(" *\n");
	            	break;
	            }
	            
	            stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            stringBuilder.append(" ".repeat(maxStringLen - charsToPrint));

	            stringBuilder.append(" *\n");

	            currentIndex += charsToPrint;
	            remainingChars -= charsToPrint;
	        }
		}
		for (int i = 0; i < width; i++) {
            stringBuilder.append("*");
        }
		
		return stringBuilder.toString();
	}

	public String dotedBox(String string) {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = string.lines().toList();
		int maxStringLen = lines.get(0).length();
		for(int i=1; i<lines.size(); i++) {
			if(maxStringLen<lines.get(i).length())
				maxStringLen = lines.get(i).length();
		}
		int width = maxStringLen + 4;
		
		for (int i = 0; i < width; i++) {
            stringBuilder.append("*");
        }
		stringBuilder.append("\n");
		
		for(String line: lines) {
	        int remainingChars = line.length();
	        int currentIndex = 0;

	        while (remainingChars > 0) {
	            stringBuilder.append("* ");
	            int charsToPrint = Math.min(maxStringLen, remainingChars);
	            
	            if(charsToPrint!=maxStringLen) {
	            	int padding = (maxStringLen - charsToPrint)/2;
	            	stringBuilder.append(" ".repeat(padding));
	            	stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            	stringBuilder.append(" ".repeat((((maxStringLen - charsToPrint)%2)==0)? padding: padding+1));
	            	stringBuilder.append(" *\n");
	            	break;
	            }
	            
	            stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            stringBuilder.append(" ".repeat(maxStringLen - charsToPrint));

	            stringBuilder.append(" *\n");

	            currentIndex += charsToPrint;
	            remainingChars -= charsToPrint;
	        }
		}
		for (int i = 0; i < width; i++) {
            stringBuilder.append("*");
        }
		
		return stringBuilder.toString();
	}
	
	public String solidBox(String string, Integer width) {
		if(width<7)
			width=7;
		int maxStringLen = width-4;
		
		StringBuilder stringBuilder = new StringBuilder("+");
		List<String> lines = string.lines().toList();
		for (int i = 0; i < width-2; i++) {
            stringBuilder.append("-");
        }
		stringBuilder.append("+\n");
		
		for(String line: lines) {
	        int remainingChars = line.length();
	        int currentIndex = 0;

	        while (remainingChars > 0) {
	            stringBuilder.append("| ");
	            int charsToPrint = Math.min(maxStringLen, remainingChars);
	            
	            if(charsToPrint!=maxStringLen) {
	            	int padding = (maxStringLen - charsToPrint)/2;
	            	stringBuilder.append(" ".repeat(padding));
	            	stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            	stringBuilder.append(" ".repeat((((maxStringLen - charsToPrint)%2)==0)? padding: padding+1));
	            	stringBuilder.append(" |\n");
	            	break;
	            }
	            
	            stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            stringBuilder.append(" ".repeat(maxStringLen - charsToPrint));

	            stringBuilder.append(" |\n");

	            currentIndex += charsToPrint;
	            remainingChars -= charsToPrint;
	        }
		}
		stringBuilder.append("+");
		for (int i = 0; i < width-2; i++) {
            stringBuilder.append("-");
        }
		stringBuilder.append("+");
		
		return stringBuilder.toString();
	}
	
	public String solidBox(String string) {
		StringBuilder stringBuilder = new StringBuilder();
		List<String> lines = string.lines().toList();
		int maxStringLen = lines.get(0).length();
		for(int i=1; i<lines.size(); i++) {
			if(maxStringLen<lines.get(i).length())
				maxStringLen = lines.get(i).length();
		}
		int width = maxStringLen + 4;
		
		for (int i = 0; i < width-2; i++) {
            stringBuilder.append("-");
        }
		stringBuilder.append("+\n");
		
		for(String line: lines) {
	        int remainingChars = line.length();
	        int currentIndex = 0;

	        while (remainingChars > 0) {
	            stringBuilder.append("| ");
	            int charsToPrint = Math.min(maxStringLen, remainingChars);
	            
	            if(charsToPrint!=maxStringLen) {
	            	int padding = (maxStringLen - charsToPrint)/2;
	            	stringBuilder.append(" ".repeat(padding));
	            	stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            	stringBuilder.append(" ".repeat((((maxStringLen - charsToPrint)%2)==0)? padding: padding+1));
	            	stringBuilder.append(" |\n");
	            	break;
	            }
	            
	            stringBuilder.append(line.substring(currentIndex, currentIndex + charsToPrint));
	            stringBuilder.append(" ".repeat(maxStringLen - charsToPrint));

	            stringBuilder.append(" |\n");

	            currentIndex += charsToPrint;
	            remainingChars -= charsToPrint;
	        }
		}
		stringBuilder.append("+");
		for (int i = 0; i < width-2; i++) {
            stringBuilder.append("-");
        }
		stringBuilder.append("+");
		
		return stringBuilder.toString();
	}
	
	public String viewOfUnaccepetedOrders(Object object) {
		StringBuilder stringBuilder = new StringBuilder();
		//TODO:view of un-accepted orders.
		return stringBuilder.toString();
	}
	
	public String viewOfShopkeeper(Shopkeeper shopkeeper) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(String.format("%-15s: %-25s (%s)\n", "Name", shopkeeper.getName(), "Can't change"));
		stringBuilder.append(String.format("%-15s: %-25s (%s)\n", "Contact", shopkeeper.getContact(), "Changable"));
		stringBuilder.append(String.format("%-15s: %-25s (%s)\n", "Email", shopkeeper.getName(), "Changable"));
		stringBuilder.append(String.format("%-15s: %-25s (%s)\n", "Username", shopkeeper.getUserName(), "Can't change"));
		stringBuilder.append(String.format("%-15s: %-25s (%s)", "Password", "********", "Changable"));
		
		return stringBuilder.toString();
	}
	
	public String viewOfProducts(Product product) {
		StringBuilder stringBuilder = new StringBuilder();
		if(!product.getBrand().isEmpty())
			stringBuilder.append(String.format("%s\n", product.getBrand()));
		
		stringBuilder.append(String.format("%s\n", product.getName()));
		
		Double rating = product.getRating();
		stringBuilder.append("●".repeat(rating.intValue()));
		Integer partialRating = Double.valueOf((rating*100)%100).intValue();
		if(partialRating>0 && partialRating<=25)
			stringBuilder.append("◔");
		else if(partialRating>25 && partialRating<=50)
			stringBuilder.append("◑");
		else if(partialRating>50 && partialRating<=75)
			stringBuilder.append("◕");
		stringBuilder.append("◌".repeat(5-rating.intValue()));
		
		stringBuilder.append(" " + product.getPurchased() + "\n");
		
		stringBuilder.append("₹" + product.getCost());
		
		return stringBuilder.toString();
	}
	
	@Override
	public void createAccount() {
		Function<String, Boolean> nameCondition = (name) ->(
				name.matches("^[a-zA-Z]{1,}$"));
		
		Function<String, Boolean> contactCondition = (contact) -> (
				contact.matches("^\\d{10}$"));
		
		Function<String, Boolean> emailCondition = (email) -> (
				(email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
						"[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")));
		
		Function<String, Boolean> passwordCondition = (password) -> (
				(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")));
		
		int result = 0;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nEnter Your First Name: ");
		String firstName = scanner.nextLine().strip();
		while(!nameCondition.apply(firstName)) {
			System.out.print("\n\tName can only have characters.\n\n" +
					"Enter a valid First Name: ");
			firstName = scanner.nextLine().strip();
		}
		System.out.print("\nEnter Your Last Name: ");
		String lastName = scanner.nextLine().strip();
		while(!nameCondition.apply(lastName)) {
			System.out.print("\n\tName can only have characters.\n\n" +
					"Enter a valid Last Name: ");
			lastName = scanner.nextLine().strip();
		}
		String name = firstName.toLowerCase().replaceFirst("^[a-z]", firstName.substring(0, 1).toUpperCase())+
				lastName.toLowerCase().replaceFirst("^[a-z]", lastName.substring(0, 1).toUpperCase());
		System.out.print("\nEnter Your Contact Number: ");
		String contact = scanner.nextLine().strip();
		while(!contactCondition.apply(contact)) {
			System.out.print("\nEnter a valid 10 digits Contact Number: ");
			contact = scanner.nextLine().strip();
		}
		System.out.print("\nEnter Your Email ID: ");
		String email = scanner.nextLine().strip();
		while(!emailCondition.apply(email)) {
			System.out.print("\nThe following restrictions are imposed in the email address local part:\n"
					+ "\t1. Dot isn’t allowed at the start and end of the local part.\n"
					+ "\t2. Consecutive dots aren’t allowed.\n"
					+ "\t3. A maximum of 64 characters are allowed.\n"
					+ "Restrictions for the domain part in this regular expression include:\n"
					+ "\t1. Hyphen “-” and dot “.” aren’t allowed at the start and end of the domain part.\r\n"
					+ "\t2. No consecutive dots.\n\n");
			System.out.print("Enter a valid Email ID: ");
			email = scanner.nextLine().strip();
		}
		System.out.print("\nEnter Your Username: ");
		String userName = scanner.nextLine().strip();
		System.out.print("\nEnter Your Password: ");
		String password = scanner.nextLine().strip();
		while(!passwordCondition.apply(password)) {
			System.out.print("\nPassword must contain:\n" +
					"\t1. At least one Special character.\n" +
					"\t2. Minimun length of 8.\n" +
					"\t3. At least one number.\n" +
					"\t4. At least one lower and one upper cahracter.\n" +
					"\t5. Does't contain space, tabs, etc.\n\n" +
					"\nEnter a valid password: ");
			password = scanner.nextLine().strip();
		}
		
		result = vendorRepository.createAccount(name, contact,
				email, userName, password);
		String conformation;
		while(result==0) {
			System.out.print("\nIt seems like this Username already exists.\n" +
					"Do you want to change the Username(Y) or Cancle(N): ");
			conformation = scanner.nextLine();
			while(!(conformation.equalsIgnoreCase("N") || conformation.equalsIgnoreCase("Y"))) {
				System.out.print("\nDo you want to change the Username(Y) or Cancle(N): ");
				conformation = scanner.nextLine();
			}
			if(conformation.equalsIgnoreCase("N"))
				break;
			System.out.print("\nEnter Your Username: ");
			userName = scanner.nextLine().strip();
			result = vendorRepository.createAccount(name, contact,
					email, userName, password);
		}

		System.out.println((result==1)?dotedBox("Account created successfully :)") :
			dotedBox("Account can't be created successfully :("));
		
	}

	@Override
	public void shopkeeperLogin() {
		int result = 0;
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.print("\nEnter Your Username: ");
		String userName = scanner.nextLine().strip();
		System.out.print("\nEnter Your Password: ");
		String password = scanner.nextLine().strip();
		
		result = vendorRepository.shopkeeperLogin(userName, password);
		String conformation;
		while(result==0) {
			System.out.print("\n\tLogin Unsuccessful.\n" +
					"\tPlease check your Username or Password\n" +
					"Do you want to Retry(Y) or Cancle(N): ");
			conformation = scanner.nextLine();
			while(!(conformation.equalsIgnoreCase("N") || conformation.equalsIgnoreCase("Y"))) {
				System.out.print("\nDo you want to Retry(Y) or Cancle(N): ");
				conformation = scanner.nextLine();
			}
			if(conformation.equalsIgnoreCase("N"))
				break;
			System.out.print("\nEnter Your Username: ");
			userName = scanner.nextLine().strip();
			System.out.print("\nEnter Your Password: ");
			password = scanner.nextLine().strip();
			
			result = vendorRepository.shopkeeperLogin(userName, password);
		}
		
		System.out.println((result==1)?dotedBox("Login Successfully :)") :
			dotedBox("Login Unsuccessfully :("));
		
	}
	
	@Override
	public void addNewProduct() {
		Function<String, Boolean> costCondition = (stringCost) -> (
				stringCost.matches("^([0-9]{1,})((\\.\\d+)?)$"));
		
		Function<String, Boolean> warehouseCondition = (stringWarehouse) -> (
				stringWarehouse.matches("^([0-9]{1,})$"));
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		
		System.out.print("\nEnter Product name: ");
		String name = scanner.nextLine().strip();
		
		System.out.print("\nEnter Product brand: ");
		String brand = scanner.nextLine().strip();
		
		System.out.print("\nEnter Product category: ");
		String category = scanner.nextLine().strip();
		
		System.out.print("\nEnter Product Cost: ");
		String stringCost = scanner.nextLine().strip();
		while(!costCondition.apply(stringCost)) {
			System.out.print("\tEnter a valid cost: ");
			stringCost = scanner.nextLine().strip();
		}
		Double cost = Double.parseDouble(stringCost);
		
		System.out.print("\nEnter Quantity of product availabel in the warehouse: ");
		String stringWarehouse = scanner.nextLine().strip();
		while(!warehouseCondition.apply(stringWarehouse)) {
			System.out.print("\tEnter a amount: ");
			stringWarehouse = scanner.nextLine().strip();
		}
		Integer warehouse = Integer.parseInt(stringWarehouse);
		
		int result = vendorRepository.addNewProduct(name, brand, category, cost, warehouse);
		System.out.println((result==1)?dotedBox("Product added successfully :)") :
				dotedBox("A Product with the same name and category already exist :("));
		
	}

	@Override
	public Boolean getProducts(Integer pageNumber) {
		Page<Product> currentPage = vendorRepository.getProducts(pageNumber, 5);
		List<Product> products = currentPage.getContent();
		if(products.isEmpty())
			return false;
		products.forEach(product -> System.out.println(solidBox(viewOfProducts(product), 30) + "\n"));
		
		return true;
	}

	@Override
	public void changePersonalInformadtion() {
		Function<String, Boolean> contactCondition = (contact) -> (
				contact.matches("^\\d{10}$"));
		
		Function<String, Boolean> emailCondition = (email) -> (
				(email.matches("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" +
						"[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")));
		
		Function<String, Boolean> passwordCondition = (password) -> (
				(password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$")));
		int result = 0;
		Shopkeeper shopkeeper = vendorRepository.getShopkeeper();
		@SuppressWarnings("resource")
		Scanner scanner = new Scanner(System.in);
		System.out.println("\n" +solidBox(viewOfShopkeeper(shopkeeper), 45));
		
		String contact = "";
		System.out.print("\nDo you want to change your Contact Number (Y)or(N): ");
		String conformation = scanner.nextLine();
		while(!(conformation.equalsIgnoreCase("N") || conformation.equalsIgnoreCase("Y"))) {
			System.out.print("Do you want to change your Contact Number (Y)or(N): ");
			conformation = scanner.nextLine();
		}
		if(conformation.equalsIgnoreCase("Y")) {
			System.out.print("\nEnter Your Contact Number: ");
			contact = scanner.nextLine().strip();
			while(!contactCondition.apply(contact)) {
				System.out.print("\nEnter a valid 10 digits Contact Number: ");
				contact = scanner.nextLine().strip();
			}
		}
		
		String email = "";
		System.out.print("\nDo you want to change your Email (Y)or(N): ");
		conformation = scanner.nextLine();
		while(!(conformation.equalsIgnoreCase("N") || conformation.equalsIgnoreCase("Y"))) {
			System.out.print("Do you want to change your Email (Y)or(N): ");
			conformation = scanner.nextLine();
		}
		if(conformation.equalsIgnoreCase("Y")) {
			System.out.print("\nEnter Your Email ID: ");
			email = scanner.nextLine().strip();
			while(!emailCondition.apply(email)) {
				System.out.print("\nThe following restrictions are imposed in the email address local part:\n"
						+ "\t1. Dot isn’t allowed at the start and end of the local part.\n"
						+ "\t2. Consecutive dots aren’t allowed.\n"
						+ "\t3. A maximum of 64 characters are allowed.\n"
						+ "Restrictions for the domain part in this regular expression include:\n"
						+ "\t1. Hyphen “-” and dot “.” aren’t allowed at the start and end of the domain part.\r\n"
						+ "\t2. No consecutive dots.\n\n");
				System.out.print("Enter a valid Email ID: ");
				email = scanner.nextLine().strip();
			}
		}
		
		String password = "";
		System.out.print("\nDo you want to change your Password (Y)or(N): ");
		conformation = scanner.nextLine();
		while(!(conformation.equalsIgnoreCase("N") || conformation.equalsIgnoreCase("Y"))) {
			System.out.print("Do you want to change your Password (Y)or(N): ");
			conformation = scanner.nextLine();
		}
		while(conformation.equalsIgnoreCase("Y")) {
			System.out.print("\nEnter your Current Password: ");
			String currentPassword = scanner.nextLine().strip();
			if(!currentPassword.equals(shopkeeper.getPassword())) {
				System.out.print("\n\bWrong password!!");
				continue;
			}
			System.out.print("\nEnter Your New Password: ");
			password = scanner.nextLine().strip();
			while(!passwordCondition.apply(password)) {
				System.out.print("\nPassword must contain:\n" +
						"\t1. At least one Special character.\n" +
						"\t2. Minimun length of 8.\n" +
						"\t3. At least one number.\n" +
						"\t4. At least one lower and one upper cahracter.\n" +
						"\t5. Does't contain space, tabs, etc.\n\n" +
						"\nEnter a valid password: ");
				password = scanner.nextLine().strip();
			}
			break;
		}
		
		result = vendorRepository.cahngePersonalInformadtion(contact, email, password);
		System.out.println("\n" + ((result==1)? dotedBox("Information updated successfully :)") :
			dotedBox("Information was't updated :(")));
		
		if(!(contact.isEmpty() && email.isEmpty()))
			System.out.println("\n" + viewOfShopkeeper(shopkeeper) + "\n");
	}

	@Override
	public void setUnacceptedOrders() {
		
		
	}
	
}
