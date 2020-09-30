package com.s4n.campus.reincarnating;

import java.io.PrintStream;
import java.io.IOException;
import java.util.Random;
import java.util.Scanner;

public class ProcessCtrl implements IDevice {
    private InfoProcess infoProcess;
    private int currentRetries;
    private int idProcessCtrl;
    private static int currentId = 0;
    private Thread thrProcess;
    private boolean stopped;
    private Process process;
    
    public ProcessCtrl(InfoProcess infoProcess) {
	this.infoProcess = infoProcess;
	this.currentRetries = 0;
	this.idProcessCtrl = ProcessCtrl.currentId++;
	this.process = null;
	this.thrProcess = null;
	this.stopped = false;
    }

    public InfoProcess getInfoProcess() {
	return infoProcess;
    }

    public int getCurrentRetries() {
	return currentRetries;
    }

    public int getId() {
	return idProcessCtrl;
    }

    public void start() {
	if (stopped) return;
	
	Runnable execProcess = new Runnable() {
		public void run() {
		    while (true) {
			try {
			    process = (new ProcessBuilder())
				.command(infoProcess.getCompletePath())
				.start();		    
			    
			    process.waitFor();
			    currentRetries++;
			    if (infoProcess.getMaxRetries() == 0)
				continue;
			    if (currentRetries > infoProcess.getMaxRetries())
				break;
			}
			catch (IOException ie) {
			    System.err.println("Process: " +
					       infoProcess.getCompletePath() +
					       " cannot be running");
			    stopped = true;
			}
			catch (InterruptedException ie) {
			    break;
			}
			
		    }
		    stopped = true;
		}
	    };

	thrProcess = new Thread(execProcess);
	thrProcess.start();

	Runnable sendRequest = new Runnable() {
		public void run() {
		    Random random = new Random(System.currentTimeMillis());
		    Scanner scan  = new Scanner(process.getInputStream());
		    PrintStream writer =
			new PrintStream(process.getOutputStream());
		    while (!stopped) {
			int numberToSend = random.nextInt();
			writer.println(numberToSend);
			int numberReceived = scan.nextInt();
			System.out.println("Id: " + currentId + 
					   " Send: " + numberToSend +
					   " Received: " + numberReceived);
			try {
			    // Can wait for almost 10 seconds
			    Thread.currentThread().sleep(random.nextInt() % 10000); 
			}
			catch (InterruptedException ie) {
			}
		    }
		}
	    };
	
	// (new Thread(sendRequest)).start();
		
    }

    public void stop() {
	if (stopped) return;
	thrProcess.interrupt();
    }

    public boolean isStopped() {
	return stopped;
    }
}
