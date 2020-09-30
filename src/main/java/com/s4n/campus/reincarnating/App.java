package com.s4n.campus.reincarnating;

public class App {
    
    public static void main(String[] args) {
	InfoProcess infoProcesses[] = { new InfoProcess("/home/juancardona/Workbench/suicidalprocess/bin", "normalprocess", 10, 10000L),
	    new InfoProcess("/home/juancardona/Workbench/suicidalprocess/bin", "suicidalprocess", 0, 1000L)
	};
	ProcessCtrl processesCtrled[] = new ProcessCtrl[infoProcesses.length];
	
	for (int i = 0; i < infoProcesses.length; i++) {
	    processesCtrled[i] = new ProcessCtrl(infoProcesses[i]);
	    processesCtrled[i].start();
	}

	// This is a service will wait forever
	try {
	    Thread.currentThread().join();
	}
	catch (InterruptedException ie) { }
    }
}
