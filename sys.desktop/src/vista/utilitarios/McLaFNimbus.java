package vista.utilitarios;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.Painter;
import javax.swing.UIDefaults;
import javax.swing.plaf.nimbus.NimbusLookAndFeel;

public class McLaFNimbus extends NimbusLookAndFeel {
	 
	private static final long serialVersionUID = 1L;

	public McLaFNimbus() {
        super();
    }
 
    @Override
    public UIDefaults getDefaults() {
        UIDefaults defaults = super.getDefaults();
        String closePressed = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[Pressed].backgroundPainter";
        String closeEnable = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[Enabled].backgroundPainter";
        String closeEnableNotFocused = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[Enabled+WindowNotFocused].backgroundPainter";
        String closeRollOverNotFocus = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[MouseOver+WindowNotFocused].backgroundPainter";
        String closeRollOver = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.closeButton\"[MouseOver].backgroundPainter";
 
        String iconEnable = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[Enabled].backgroundPainter";
        String iconRollOver = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[MouseOver].backgroundPainter";
        String iconPressed = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[Pressed].backgroundPainter";
        String iconRollOverNotFocus = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[MouseOver+WindowNotFocused].backgroundPainter";
        String iconNotFocus = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.iconifyButton\"[Enabled+WindowNotFocused].backgroundPainter";
 
        String maximizeEnable = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Enabled].backgroundPainter";
        String maximizeMaxEnable = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Enabled+WindowMaximized].backgroundPainter";
        String maximizeRollOverFocus = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[MouseOver].backgroundPainter";
        String maximizeMaxRollOverFocus = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[MouseOver+WindowMaximized].backgroundPainter";
        String maximizePressed = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Pressed].backgroundPainter";
        String maximizeMaxPressed = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Pressed+WindowMaximized].backgroundPainter";
        String maximizeNotFocus = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[Enabled+WindowNotFocused].backgroundPainter";
        String maximizeRollOverNotFocus = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[MouseOver+WindowNotFocused].backgroundPainter";
        String maximizeMaxNotFocus = "InternalFrame:InternalFrameTitlePane:\"InternalFrameTitlePane.maximizeButton\"[MouseOver+WindowMaximized+WindowNotFocused].backgroundPainter";
 
        defaults.put(closeEnableNotFocused, getPainter("main/resources/LookAndFeel/closeNotFocused.png"));
        defaults.put(closeRollOver, getPainter("main/resources/LookAndFeel/FrameCloseRoll.png"));
        defaults.put(closeEnable, getPainter("main/resources/LookAndFeel/FrameClose.png"));
        defaults.put(closePressed, getPainter("main/resources/LookAndFeel/FrameClosePush.png"));
        defaults.put(closeRollOverNotFocus, getPainter("main/resources/LookAndFeel/rollOverNotFocus.png"));
 
        defaults.put(iconEnable, getPainter("main/resources/LookAndFeel/iconEnable.png"));
        defaults.put(iconRollOver, getPainter("main/resources/LookAndFeel/iconRollOverFocus.png"));
        defaults.put(iconPressed, getPainter("main/resources/LookAndFeel/iconPush.png"));
        defaults.put(iconRollOverNotFocus, getPainter("main/resources/LookAndFeel/iconRollOverNotFocus.png"));
        defaults.put(iconNotFocus, getPainter("main/resources/LookAndFeel/iconNotFocus.png"));
 
        defaults.put(maximizeEnable, getPainter("main/resources/LookAndFeel/FrameMaximize.png"));
        defaults.put(maximizeMaxEnable, getPainter("main/resources/LookAndFeel/FrameRestore.png"));
        defaults.put(maximizeRollOverFocus, getPainter("main/resources/LookAndFeel/maximizeRollOverFocus.png"));
        defaults.put(maximizeMaxRollOverFocus, getPainter("main/resources/LookAndFeel/FrameRestoreMaxRollOver.png"));
        defaults.put(maximizePressed, getPainter("main/resources/LookAndFeel/FrameRestorePressed.png"));
        defaults.put(maximizeMaxPressed, getPainter("main/resources/LookAndFeel/FrameRestoreMaxPressed.png"));
        defaults.put(maximizeNotFocus, getPainter("main/resources/LookAndFeel/maximizeNotFocus.png"));
        defaults.put(maximizeRollOverNotFocus, getPainter("src/resources/LookAndFeel/maximizeRollOverNotFocus.png"));
        defaults.put(maximizeMaxNotFocus, getPainter("main/resources/LookAndFeel/maximizeMaxNotFocus.png"));
        return defaults;
    }
 
    public Painter<JComponent> getPainter(final String path) {
        return new Painter<JComponent>() {
 
            public void paint(Graphics2D g, JComponent object, int width, int height) {
                g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g.setStroke(new BasicStroke(2f));
                g.drawImage(ImageUtils.getScaledImage(path).getImage(), 0, 0, null);
            }
        };
    }
}
class ImageUtils {
 
    /**
     *
     * @param URL direccion de la imagen dentro del proyecto
     * @return una Imagen redimensionada a 16 x 16
     */
    public static ImageIcon getScaledImage(String path){
        ImageIcon sourceIcon = new ImageIcon(path);
        ImageIcon scaledIcon = new ImageIcon(sourceIcon.getImage().getScaledInstance(16, 16, Image.SCALE_DEFAULT));
        return scaledIcon;
    }
 
}