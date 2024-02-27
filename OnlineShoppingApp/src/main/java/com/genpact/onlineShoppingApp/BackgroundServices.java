package com.genpact.onlineShoppingApp;

import com.genpact.onlineShoppingApp.controller.VendorController;

public class BackgroundServices implements Runnable {
	VendorController vendorController;
	
	public BackgroundServices(VendorController vendorController) {
		this.vendorController = vendorController;
		Thread thread1 = new Thread(this, "BackgroundServices");
		thread1.start();
	}
	
	public void run() {
		while(true) {
			System.out.println(vendorController.totalRevinue());
			try {Thread.sleep(1000);} catch(Exception e) {}
		}
	}
}
