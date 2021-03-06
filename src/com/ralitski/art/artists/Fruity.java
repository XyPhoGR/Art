package com.ralitski.art.artists;

import java.awt.Color;
import java.util.Random;

import com.ralitski.art.api.PixelArtist;
import com.ralitski.art.core.Settings;

/*
 * Pastel colors separated by 20-30 between parts
 * Fruity colors separated by 50-70
 */
public class Fruity implements PixelArtist {
	
	private int color;
	
	public Fruity() {
		color = getColorInt(generateColor());
	}

	@Override
	public int getWidth(Settings settings) {
		return settings.getInt("width", 200);
	}

	@Override
	public int getHeight(Settings settings) {
		return settings.getInt("height", 200);
	}

	@Override
	public int getColor(int x, int y, Settings settings) {
		return color;
	}
	
	private static Color generateColor() {
		Random random = new Random();
		int main = random.nextInt(3);
		int sub = random.nextInt(2);
		
		int c1 = 230 + random.nextInt(26); //230-255
		int c2 = 100 + random.nextInt(66); //100-165
		int c3 = 50 + random.nextInt(106); //50-155
		
		int r, g, b;
		if(main == 0) {
			r = c1;
			if(sub == 0) {
				g = c2;
				b = c3;
			} else {
				g = c3;
				b = c2;
			}
		} else if(main == 1) {
			g = c1;
			if(sub == 0) {
				r = c2;
				b = c3;
			} else {
				r = c3;
				b = c2;
			}
		} else {
			b = c1;
			if(sub == 0) {
				r = c2;
				g = c3;
			} else {
				r = c3;
				g = c2;
			}
		}
		return new Color(r, g, b);
	}
	
	private static int getColorInt(Color color) {
	  return (color.getAlpha() << 24) | (color.getRed() << 16) | (color.getGreen() << 8) | (color.getBlue());
	}

}
