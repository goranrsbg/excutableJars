package com.main.slagalica;

import java.awt.Label;

public class Stoperica implements Runnable {
	
	private Label prikaz;
	private Thread thread;
	private boolean running;
	private boolean pause;
	private long oldTime;
	private long nowTime;
	private int min;
	private int sec;
	private int sto;
	
	public Stoperica(Label prikaz) {
		this.prikaz = prikaz;
		running = false;
		pause = false;
		reset();
		prikaz.setText(toString());
	}
	public void reset() {
		oldTime = 0;
		min = sec = sto = 0;
	}
	public void start() {
		if(!running) {
			running = true;
			pause = false;
			thread = new Thread(this);
			reset();
			thread.start();
		}
	}
	public synchronized void pause() {
		if(pause) {
			pause = false;
			reset();
			notify();
		} else
			pause = running;
	}
	public synchronized void stop() {
		if(running) {
			running = false;
			pause = false;
			reset();
			notify();
		}
	}
	public boolean isAlive() {
		return running;
	}

	@Override
	public void run() {
		try {
			while(running) {
				if(pause) synchronized(this) {
					while(pause) wait();
					oldTime = 0;
				}
				nowTime = System.currentTimeMillis();
				if(oldTime != 0) {
					sto += (int)((nowTime - oldTime) / 10);
					sec += sto / 100;
					min += sec / 60;
					sto %= 100;
					sec %= 60;
					min %= 60;
				}
				prikaz.setText(toString());
				oldTime = nowTime;
				Thread.sleep(10);
			}
		} catch(InterruptedException e) {
			e.printStackTrace();
		}	
	}
	public String toString() {
		return String.format("%02d:%02d:%02d", min, sec, sto);
	}
}
















