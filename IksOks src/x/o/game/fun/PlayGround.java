package x.o.game.fun;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;

public class PlayGround extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public PlayGround(DrawingLoader loader, JLabel announcer) {
		this.loader = loader;
		this.announcer = announcer;
		init();
	}
	
	private void init() {
		setLayout(new GridLayout(WIDTH, HEIGHT));
		pg = new Polje[WIDTH * HEIGHT];
		Border border_black = BorderFactory.createLineBorder(Color.BLACK, 1);
		Border border_red = BorderFactory.createLineBorder(Color.RED, 1);
		for(int i = 0; i < pg.length; i++) {
			pg[i] = new Polje(i);
			pg[i].setHorizontalAlignment(JLabel.CENTER);
			pg[i].setOpaque(true);
			pg[i].setBackground(Color.WHITE);
			pg[i].setBorder(border_black);
			pg[i].addMouseListener(new MouseListener() {
				@Override
				public void mouseReleased(MouseEvent e) {
					Polje p = (Polje)(e.getSource());
					if(canClick) {
						if(running && p.isEmpty()) {
							putSignAt(p.getPosition());
							if(chechForWin()) {
								announcer.setText(text[xToMove? 1 : 0]);
								running = false;
							} else announcer.setText(text[xToMove? 3 : 4]);
							if(running && aiON) {
								aiMakeMove();
								if(chechForWin()) {
									announcer.setText(text[2]);
									running = false;
								}
								if(running)	announcer.setText(text[xToMove? 3 : 4]);
							}
							if(nMoves == 9 && running) {
								announcer.setText(text[5]);
								running = false;
							}
						}
					}
				}
				@Override
				public void mousePressed(MouseEvent e) {
					pressedPosition = ((Polje)e.getSource()).getPosition();
					canClick = true;
				}
				@Override
				public void mouseExited(MouseEvent e) {
					((JLabel)e.getSource()).setBorder(border_black);
				}
				@Override
				public void mouseEntered(MouseEvent e) {
					((JLabel)e.getSource()).setBorder(border_red);
					if(pressedPosition != ((Polje)e.getSource()).getPosition()) 
						canClick = false;
					else
						canClick = true;
				}
				@Override
				public void mouseClicked(MouseEvent e) {}
			});
			
			add(pg[i]);
		}
	}
	
	private void putSignAt(int pos) {
		if(xToMove) {
			pg[pos].setIcon(loader.getIks());
			pg[pos].setValue(Polje.X);
		} else {
			pg[pos].setIcon(loader.getOks());
			pg[pos].setValue(Polje.O);
		}
		nMoves++;
		xToMove = !xToMove;
	}
	
	private boolean chechForWin() {
		return chech(0,4,8) || chech(2,4,6) || chech(0,1,2) || 
			   chech(3,4,5) || chech(6,7,8) || chech(0,3,6) ||
			   chech(1,4,7) || chech(2,5,8);
	}
	private boolean chech(int pos1, int pos2, int pos3) {
		if(pg[pos1].equals(pg[pos2]) && 
		   pg[pos1].equals(pg[pos3])) return true;
		return false;
	}
	
	public void newGame() {
		running = true;
		aiON = false;
		xToMove = true;
		nMoves = 0;
		for(Polje p : pg) {
			p.reset();
		}
		announcer.setText(text[3]);
	}
	
	public void activateAI() {
		if(!aiON && running) {
			aiON = true;
			aiMakeMove();
		}
	}
	
	public void aiMakeMove() {
		char sign = xToMove? Polje.X : Polje.O;
		if(!canWin(sign)) {
			if(!canWin(sign == Polje.X ? Polje.O : Polje.X)) {
				if(!canSetTrap(sign))
					makeRandomMove();
			}
		}	
	}
	
	private boolean canWin(char sign) {
		for(int i = 0; i < pg.length; i++) {
			if(pg[i].isEmpty()) {
				pg[i].setValue(sign);
				if(chechForWin()) {
					putSignAt(i);
					return true;
				} else pg[i].reset();
			}
		}
		return false;
	}

	private boolean canSetTrap(char sign) {
		int n = 0;
		for(int i = 0; i < pg.length; i++, n = 0) {
			if(pg[i].isEmpty()) {
				pg[i].setValue(sign);
				for(int j = 0; j < pg.length; j++) {
					if(pg[j].isEmpty()) {
						pg[j].setValue(sign);
						if(chechForWin()) n++;
						pg[j].reset();
					}
				}
				pg[i].reset();
				if(n >= 2) {
					putSignAt(i);
					return true;
				}
			}
		}
		return false;
	}
	
	private void makeRandomMove() {
		int pos = (int)((9-nMoves) * Math.random());
		for(int i = 0; i < pg.length; i++) {
			if(pg[i].isEmpty()) {
				if(pos == 0) {
					putSignAt(i);
					break;
				}
				else pos--;
			}
		}
	}
	
	private int nMoves;
	private int pressedPosition;
	private boolean canClick;
	private boolean running;
	private boolean xToMove;
	private boolean aiON;
	private DrawingLoader loader;
	private JLabel announcer;
	private Polje[] pg;
	private String[] text = {
			"X is the winner!!!",
			"O is the winner!!!",
			"A.I. is the winner!!!",
			"X to play...",
			"O to play",
			"it's draw"
	};
	private static final int WIDTH = 3;
	private static final int HEIGHT = 3;
}
