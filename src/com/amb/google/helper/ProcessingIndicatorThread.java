package com.amb.google.helper;

public class ProcessingIndicatorThread extends Thread {

	public void run() {
		try {
			while ( !isInterrupted() ) {
				System.out.print(".");
				sleep(1000);
			}
		} catch (InterruptedException e) {}
		
	}
}
