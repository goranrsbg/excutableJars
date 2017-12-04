package com.main.slagalica;

import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Label;
import java.awt.Panel;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.border.Border;

public class Tabla extends Panel {

	private static final long serialVersionUID = 1L;
	private JLabel[] polja;
	private Stoperica time;
	private int empty;
	private int top;
	private int bot;
	private int left;
	private int right;
	private static final int NONE = -1; 
	private boolean running;
	
	public Tabla(Label time) {
		this.time = new Stoperica(time);
		polja = new JLabel[16];
		setLayout(new GridLayout(4, 4));
		String text;
		empty = 15;
		running = false;
		Border border = BorderFactory.createLineBorder(Color.BLACK, 1);
		for(int i = 0; i < 16; i++) {
			if(i < 15) 
				text = "" + (i+1);
			else
				text = "";
			polja[i] = new JLabel(text, JLabel.CENTER);
			polja[i].setFont(new Font(null, Font.BOLD, 24));
			polja[i].setBorder(border);
			polja[i].setFocusable(true);
			polja[i].setOpaque(true);
			polja[i].addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					if(running) {
						String val = ((JLabel)e.getSource()).getText();
						boolean notMoved = true;
						if(top != NONE) {
							if(val.equals(polja[top].getText())) notMoved = move(top);
						}
						if(bot != NONE && notMoved) {
							if(val.equals(polja[bot].getText())) notMoved = move(bot);
						}
						if(left != NONE && notMoved) {
							if(val.equals(polja[left].getText())) notMoved = move(left);
						}
						if(right != NONE && notMoved) {
							if(val.equals(polja[right].getText())) notMoved = move(right);
						}
					}
				}
				@Override
				public void mouseClicked(MouseEvent arg0) {}
				@Override
				public void mouseEntered(MouseEvent arg0) {}
				@Override
				public void mouseExited(MouseEvent arg0) {}
				@Override
				public void mousePressed(MouseEvent arg0) {}
			});
			add(polja[i]);
		}
	}
	public void setColor(Color color) {
		for(int i = 0; i < 16; i++) {
			polja[i].setBackground(color);
			if(color == Color.BLUE) polja[i].setForeground(Color.WHITE);
			else polja[i].setForeground(Color.BLACK);
		}
	}
	public void startNewGame(int level) {
		getMovable();
		randomize(level);
		if(time.isAlive()) time.pause();
		else time.start();
		running = true;
	}
	private boolean move(int pos) {
		polja[empty].setText(polja[pos].getText());
		polja[pos].setText("");
		empty = pos;
		getMovable();
		if(running) 
			if(!chech()) {
				running = false;
				time.pause();
		};
		return false;
	}
	private boolean chech() {
		for(int i = 0; i < 15; i++)
			if(!(polja[i].getText().equals(i+1+""))) return true;
		return false;
	}
	private void getMovable() {
		if((empty / 4 - 1) < 4 && (empty / 4 - 1) >= 0)
			top = (empty / 4 - 1) * 4 + empty % 4;
		else top = NONE;
		if((empty / 4 + 1) < 4 && (empty / 4 + 1) >= 0)
			bot = (empty / 4 + 1) * 4 + empty % 4;
		else bot = NONE;
		if((empty % 4 - 1) < 4 && (empty % 4 - 1) >= 0)
			left  = (empty / 4) * 4 + empty % 4 - 1;
		else left = NONE;
		if((empty % 4 + 1) < 4 && (empty % 4 + 1) >= 0)
			right = (empty / 4) * 4 + empty % 4 + 1;
		else right = NONE;
	}
	private void randomize(int level) {
		int next;
		while(level > 0) {
			next = (int)(4 * Math.random());
			switch(next) {
				case 0: if(top   != NONE) { move(top);   level--; break; }
				case 1: if(bot   != NONE) { move(bot);   level--; break; }
				case 2: if(left  != NONE) { move(left);  level--; break; }
				case 3: if(right != NONE) { move(right); level--; break; }
			}
		}
	}
}



























