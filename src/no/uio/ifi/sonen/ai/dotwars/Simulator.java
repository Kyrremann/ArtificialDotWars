package no.uio.ifi.sonen.ai.dotwars;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferStrategy;
import java.io.File;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import javax.swing.Timer;

public class Simulator {

	private Gfx gfx;
	private BufferStrategy strategy;
	private int delay = 1000;
	private Timer timer;
	private Type[][] map;

	/** All the images we need. */
	private ImageIcon imgEmpty;
	private ImageIcon imgResource;
	private ImageIcon imgCastle;

	public Simulator() {

		map = BattleField.loadMap("res/test.map");

		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				gfx = new Gfx();
				strategy = gfx.getBufferStrategy();
				loadImages();
				timer = new Timer(delay, new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent e) {
						display();
						strategy.show();

					}
				});
				timer.start();
			}
		});
	}

	/**
	 * Loads an image through the resource system.
	 * 
	 * @param fileName
	 *            The name of the file to load.
	 * @return An ImageIcon containing the loaded image.
	 */
	private ImageIcon loadImage(String fileName) {
		ImageIcon ret = null;
		try {
			ret = new ImageIcon(fileName);
		} catch (Exception e) {
			System.err.printf("Could not load image (%s) - %s%n", fileName,
					e.getMessage());
			System.exit(1);
		}
		return ret;
	}

	/** Loads the necessary image files. */
	private void loadImages() {
		imgEmpty = loadImage("res/gfx/empty.png");
		imgResource = loadImage("res/gfx/grass.png");
		imgCastle = loadImage("res/gfx/skigard.png");
	}

	private void display() {
		Graphics graphics = strategy.getDrawGraphics();
		ImageIcon drawMe = null;

		graphics.setColor(Color.green);
		graphics.fillRect(0, 0, Gfx.WIDTH, Gfx.HEIGHT);

		for (int y = 0; y < Gfx.YUNIT; y++) {
			for (int x = 0; x < Gfx.XUNIT; x++) {

				switch (map[y][x]) {
				case EMPTY:
					drawMe = imgEmpty;
					break;
				case RESOURCE:
					drawMe = imgResource;
					break;
				case CASTLE:
					drawMe = imgCastle;
					break;
				default:
					break;
				}

				graphics.drawImage(drawMe.getImage(), (x * Gfx.UNIT), (y
						* Gfx.UNIT), null, null);
			}
		}

		graphics.dispose();
	}
}
