package com.main.slagalica;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;

public class Slagalica extends JFrame {

	private static final long serialVersionUID = 1L;
	
	private Label vreme;
	private Tabla polja;
	private Panel time;
	private String[] boje_n = { "Bela", "Crvena", "Plava", "Zelena", "Siva" };
	private Color[] boje = {Color.WHITE, Color.RED, Color.BLUE, Color.GREEN, Color.GRAY};
	
	
	public Slagalica() {
		super("Slagalica");
		vreme = new Label("", Label.CENTER);
		time = new Panel();
		time.add(vreme, "Center");
		polja = new Tabla(vreme);
		vreme.setFont(new Font(null, Font.BOLD, 24));
		add(time, "South");
		add(polja, "Center");
		setMenuBar(new GenMenu());
		setMinimumSize(new Dimension(400, 400));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	private class GenMenu extends MenuBar {
		
		private static final long serialVersionUID = 1L;

		public GenMenu() {
			Menu meni = new Menu("File");
			add(meni);
			MenuItem nova = new MenuItem("New game");
			meni.add(nova);
			nova.setShortcut(new MenuShortcut('R'));
			nova.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					polja.startNewGame(100);
				}
			});
			nova = new MenuItem("Quit");
			meni.add(nova);
			nova.setShortcut(new MenuShortcut('Q'));
			nova.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					dispose();
				}
			});
			meni = new Menu("Color");
			add(meni);
			for(String boja: boje_n) {
				meni.add(nova = new MenuItem(boja));
				nova.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						for(int i = 0; i < boje_n.length; i++)
							if(boje_n[i].equals(((MenuItem)e.getSource()).getLabel())) polja.setColor(boje[i]);
					}
				});
			}
		}
	}
	
	public static void main(String[] args) {
		new Slagalica();
	}
}














