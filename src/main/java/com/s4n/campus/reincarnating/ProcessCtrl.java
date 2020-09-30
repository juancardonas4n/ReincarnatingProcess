package com.s4n.campus.reincarnating;

import java.io.IOException;

public class ProcessCtrl implements IDevice {
    private InfoProcess infoProcess;
    private int currentRetries;
    private int idProcessCtrl;
    private static int currentId = 0;
    private Runnable execProcess;
    private Thread thrProcess;
    private boolean stopped;
    
    public ProcessCtrl(InfoProcess infoProcess) {
	this.infoProcess = infoProcess;
	this.currentRetries = 0;
	this.idProcessCtrl = ProcessCtrl.currentId++;
	this.execProcess = this.thrProcess = null;
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
	
	execProcess = new Runnable() {
		public void run() {
		    while (true) {
		       
			Process process = null;

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
			    System.err.println("Process cannot be running");
			    System.exit(1);
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
    }

    public void stop() {
	if (stopped) return;
	thrProcess.interrupt();
    }
}
