package components.elements;

import components.GuiCanvas;
import components.GuiComponent;
import components.ScreenTransform;
import display.Camera;
import logic.Transform;

import java.awt.*;

public class Switch extends GuiComponent {

    private boolean active;
    private Color activeColor, disabledColor, pointColor;
    private double switchProgress; // percentage
    private Thread thread;

    public Switch(GuiCanvas parent, ScreenTransform screenTransform) {
        super(parent, screenTransform);
        activeColor = new Color(46, 204, 113);
        disabledColor = new Color(189, 195, 199);
        pointColor = new Color(52, 73, 94);
        switchProgress=0;
        active=false;
    }

    @Override
    public void Render(Graphics2D g, Camera cam) {
        ScreenTransform ownTrans = this.getScreenTransform();
        Transform screenCoord = cam.gui2Screen(ownTrans);

        int x = (int) screenCoord.getX();
        int y = (int) screenCoord.getY();
        int width = (int) screenCoord.getXScale();
        int height = (int) screenCoord.getYScale();
        int radius = height+height/3;
        int progress = (int)(switchProgress * width);

        g.setColor(activeColor);
        g.fillRoundRect(x,y,progress,height, 10,10);
        g.setColor(disabledColor);
        g.fillRoundRect(x+progress,y,width-progress,height, 10,10);
        g.setColor(pointColor);

        x = x + progress-radius/3;

        g.fillOval(x,y-height/6, radius, radius);
    }

    @Override
    public void onClick() {
        active = !active;
        startThread();
    }


    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
        startThread();
    }

    public double getProgress() {
        return switchProgress;
    }

    public void setProgress(double switchProgress) {
        this.switchProgress = switchProgress;
    }

    public void startThread() {
        thread = new SwitchThread(this);
        thread.start();
    }

    public Thread getThread() {
        return thread;
    }

    public void setThread(Thread thread) {
        this.thread = thread;
    }
}

class SwitchThread extends Thread {

    private double speed; // perc per second
    private Switch component;

    public SwitchThread(Switch component) {
        this.component = component;
        this.speed = 5d/100d;
    }

    @Override
    public void run() {

        while (true) {
            // 100 fps is more than enough
            try {
                sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            double progress = component.getProgress();
            boolean active = component.isActive();

            // test if more sliding is needed
            if(active && progress>=1||!active && progress<=0){
                break;
            }

            // change progress
            int dir = (active?1:-1);
            double newProgress = progress+speed*dir;
            newProgress = newProgress>1?1:(newProgress<0?0:newProgress);
            component.setProgress(newProgress);
        }
        component.setThread(null);
    }
}
