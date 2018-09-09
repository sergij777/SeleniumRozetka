package org.mycomp.service;

public class Timer {
	 public static void waitSeconds(int seconds) {
		  Thread thread = new Thread();
		  try {
		   thread.sleep(1000*seconds);
		  } catch (InterruptedException e) {
		   e.printStackTrace();
		  }
		 }

}
