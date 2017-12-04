package x.o.game.fun;

import java.util.ArrayList;
import java.util.List;

import javax.swing.ImageIcon;

public class DrawingLoader {
	private List<ImageIcon> iks;
	private List<ImageIcon> oks;
	private int nextX;
	private int nextO;
	
	public DrawingLoader() {
		iks = new ArrayList<ImageIcon>();
		oks = new ArrayList<ImageIcon>();
		nextX = 0;
		nextO = 0;
	}
	
	public void loadIks(ImageIcon image) {
		iks.add(image);
	}
	
	public void loadOks(ImageIcon image) {
		oks.add(image);
	}
	
	public ImageIcon getIks() {
		if(nextX == 5) nextX = 0;
		return iks.get(nextX++);
	}
	
	public ImageIcon getOks() {
		if(nextO == 5) nextO = 0;
		return oks.get(nextO++);
	}
	
}
