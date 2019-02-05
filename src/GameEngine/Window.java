package GameEngine;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import static java.awt.RenderingHints.KEY_RENDERING;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

public class Window extends JPanel {

    //
    private long lastRepaintTime = -1;
    private float FrameTime = 0;
    //
   
    private JFrame frame;

    private GameContainer container;

    public Window(GameContainer container) {

        this.container = container;

        Dimension s = new Dimension((int) (container.getWidth() * container.getScale()), (int) (container.getHeight() * container.getScale()));
        
        setPreferredSize(s);
        setMaximumSize(s);
        setMinimumSize(s);

        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());
        frame.add(this, BorderLayout.CENTER);
        
        if(container.getGame().getPanel()!=null)
            addPanel();
        
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setResizable(container.getGame().isResizable());
        frame.setVisible(true);
        
        requestFocus();

        frame.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                
                int nh, nw;
                
                nw = frame.getWidth();
                nh = frame.getHeight();
                
                float scaleW = (float)nw / container.getWidth();
                float scaleH = (float)nh / container.getHeight();
                float scale;
                
                scale = scaleW;
                
                nw = (int) (scale * container.getWidth());
                nh = (int) (scale * container.getHeight());

                Dimension s = new Dimension(nw, nh);
                setPreferredSize(s);
                setMaximumSize(s);
                setMinimumSize(s);
                frame.pack();
                
                container.setScale(scale);
            }
        });

        

        final SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() throws Exception {
                repaintPanel();
                return null;
            }
        };

        worker.execute();

    }

    private static final class SwingRepainter implements Runnable {

        private JPanel panel = null;

        public SwingRepainter(JPanel panel) {
            this.panel = panel;
        }

        @Override
        public void run() {
            panel.repaint();
        }
    }

    private final Runnable repaint = new SwingRepainter(this);

    public void repaintPanel() {
        SwingUtilities.invokeLater(repaint);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (lastRepaintTime < 0) {
            lastRepaintTime = System.currentTimeMillis();

        } else {
            long now = System.currentTimeMillis();
            FrameTime = (float) (now - lastRepaintTime) * 0.001f;
            lastRepaintTime = now;
        }

        Graphics2D g2 = (Graphics2D) g;

        g2.scale(container.getScale(), container.getScale());

        g2.setRenderingHint(KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2.setRenderingHint( RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR );
        
        g2.setColor(Color.black);
        g2.fillRect(0, 0, container.getWidth(), container.getHeight());

        //
        container.getGame().renderGame(g2);
        //
        
        if(container.getGame().isDebugInfoDisplayed()) {
            
            container.getGame().displayElapsedTime(g2);

            String str = String.format("Frame Time: %fs, FPS: %.1f", FrameTime, 1.0 / FrameTime);

            int sx = 5;
            int sy = container.getHeight() - 5;

            g2.setColor(Color.BLACK);
            g2.drawString(str, sx + 1, sy + 1);
            g2.setColor(Color.WHITE);
            g2.drawString(str, sx, sy);
        }
        
    }
    
    public void removePanel() {
        frame.remove(container.getGame().getPanel());
        frame.pack();
    }
    
    public void addPanel() {
        frame.add(container.getGame().getPanel(), BorderLayout.NORTH);
        frame.pack();
    }
    
    public float getFrameTime() {
        return FrameTime;
    }
    
    public void close() {
        frame.dispose();
    }
    
}
