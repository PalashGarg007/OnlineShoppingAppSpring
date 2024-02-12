package com.genpact.onlineShoppingApp.controller;

import java.util.List;

import com.genpact.onlineShoppingApp.entity.Customer;
import com.genpact.onlineShoppingApp.entity.Payment;
import com.genpact.onlineShoppingApp.entity.Product;
import com.genpact.onlineShoppingApp.entity.Shopkeeper;

public class Views {
	

	public String viewOfUnaccepetedOrdersForVendor(Object object) {
		StringBuilder stringBuilder = new StringBuilder();
		//TODO:view of un-accepted orders.
		return stringBuilder.toString();
	}
	
	public String viewOfShopkeeperForVendor(Shopkeeper shopkeeper) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("%-15s: %-25s (%s)\n".formatted("Name", shopkeeper.getName(), "Can't change"));
		stringBuilder.append("%-15s: %-25s (%s)\n".formatted("Contact", shopkeeper.getContact(), "Changable"));
		stringBuilder.append("%-15s: %-25s (%s)\n".formatted("Email", shopkeeper.getName(), "Changable"));
		stringBuilder.append("%-15s: %-25s (%s)\n".formatted("Username", shopkeeper.getUserName(), "Can't change"));
		stringBuilder.append("%-15s: %-25s (%s)".formatted("Password", "********", "Changable"));
		
		return stringBuilder.toString();
	}
	
	public String viewOfProductsForVendor(Product product) {
		StringBuilder stringBuilder = new StringBuilder();
		if(!product.getBrand().isEmpty())
			stringBuilder.append("%s\n".formatted(product.getBrand()));
		
		stringBuilder.append("%s\n".formatted(product.getName()));
		
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
	
	public String viewOfShopkeeperForAdmin(Shopkeeper shopkeeper) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("%-15s: %-25s\n".formatted("Name", shopkeeper.getName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Contact", shopkeeper.getContact()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Email", shopkeeper.getName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Username", shopkeeper.getUserName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Password", "********"));
		
		return stringBuilder.toString();
	}
	
	public String viewOfCustomerForAdmin(Customer customer) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("%-15s: %-25s\n".formatted("Name", customer.getName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Contact", customer.getContact()));
		stringBuilder.append("%-15s: %-25s\n".formatted("DOB", customer.getDob()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Email", customer.getEmail()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Address", customer.getAddress()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Username", customer.getUserName()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Password", "********"));
		
		return stringBuilder.toString();
	}
	
	public String viewOfPaymentForAdmin(Payment payment) {
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append("%-15s: %-25s\n".formatted("ID", payment.getId()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Mode of Payment", payment.getMethod()));
		stringBuilder.append("%-15s: %-25s\n".formatted("Discount", payment.getDiscount()));
		
		return stringBuilder.toString();
	}
	
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
	
	public String solidBox(String string, int width) {
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
	
}
