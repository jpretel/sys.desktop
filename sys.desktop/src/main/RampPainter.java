package main;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.Shape;

public class RampPainter implements IIconPaint {

	public RampPainter() {
	}

	public void paint(Graphics2D g, boolean isSelected) {
		Shape clip = g.getClip();
		Rectangle area = clip.getBounds();

		int x1 = area.x;
		int x2 = area.x + area.width - 1;

		Color color1 = Color.green;
		Color color2 = Color.red;
		g.setPaint(new GradientPaint(area.x, 0, color1, area.x + area.width, 0, color2, false));
		g.fillRect(area.x, area.y, area.width, area.height);

		if (isSelected)
			g.setColor(Color.black);
		else
			g.setColor(new Color(96, 96, 96));
		g.drawRect(x1, area.y, x2 - x1, area.height - 1);
	}
}