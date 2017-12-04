package x.o.game.fun;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Image;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class IksOks extends JFrame {
	
	private static final long serialVersionUID = 1L;
	
	public IksOks() {
		this("IKS OKS");
	}
	public IksOks(String title) {
		super(title);
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				init();
			}
		});
	}
	private void init() {
		setSize(300, 400);
		setMenuBar(new MBar());
		announcer = new JLabel("", JLabel.CENTER);
		announcer.setFont(new Font(null, Font.BOLD, 24));
		announcer.setOpaque(true);
		announcer.setBackground(Color.GREEN);
		Panel anc = new Panel();
		anc.setLayout(new BorderLayout());
		anc.add(announcer);
		add(anc, "North");
		loader = new DrawingLoader();
		playGround = new PlayGround(loader, announcer);
		add(playGround, "Center");
		Image image;
		for(int i = 0; i < 5; i++) {
			try {
				image = ImageIO.read(ClassLoader.getSystemResourceAsStream("x"+(i+1)+".png"));
				loader.loadIks(new ImageIcon(image));
				image = ImageIO.read(ClassLoader.getSystemResourceAsStream("o"+(i+1)+".png"));
				loader.loadOks(new ImageIcon(image));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private class MBar extends MenuBar {
		private static final long serialVersionUID = 1L;
		public MBar() {
			Menu meni = new Menu("File");
			MenuItem item = new MenuItem("New game");
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					playGround.newGame();
				}
			});
			item.setShortcut(new MenuShortcut(KeyEvent.VK_F1, false));
			meni.add(item);
			item = new MenuItem("VS A.I.");
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					playGround.activateAI();
				}
			});
			item.setShortcut(new MenuShortcut(KeyEvent.VK_A, false));
			meni.add(item);
			item = new MenuItem("Quit");
			item.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					System.exit(0);
				}
			});
			item.setShortcut(new MenuShortcut(KeyEvent.VK_Q, false));
			meni.add(item);
			add(meni);
		}
	}
	
	private DrawingLoader loader;
	private JLabel announcer;
	private PlayGround playGround;
	
	public static void main(String[] args) {
		JFrame frame = new IksOks();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
	}
}


















