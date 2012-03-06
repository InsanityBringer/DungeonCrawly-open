package com.mojang.ld19;

import java.awt.event.*;

public class Input implements KeyListener, FocusListener, MouseListener, MouseMotionListener {
    public static final int UP = 0;
    public static final int DOWN = 1;
    public static final int LEFT = 2;
    public static final int RIGHT = 3;
    public static final int TURN_LEFT = 4;
    public static final int TURN_RIGHT = 5;
    public static final int SAVE_TEST = 6;

    public boolean[] keys = new boolean[10];
    public int xMouse, yMouse;
    public int click = 0;

    public Input() {
    }

    public void focusGained(FocusEvent fe) {
    }

    public void focusLost(FocusEvent fe) {
        for (int i = 0; i < keys.length; i++) {
            keys[i] = false;
        }
    }

    private void toggleKey(int keyCode, boolean b) {
        if (keyCode == KeyEvent.VK_W) keys[UP] = b;
        if (keyCode == KeyEvent.VK_S) keys[DOWN] = b;
        if (keyCode == KeyEvent.VK_A) keys[LEFT] = b;
        if (keyCode == KeyEvent.VK_D) keys[RIGHT] = b;
        if (keyCode == KeyEvent.VK_Q) keys[TURN_LEFT] = b;
        if (keyCode == KeyEvent.VK_E) keys[TURN_RIGHT] = b;

        if (keyCode == KeyEvent.VK_Y) keys[UP] = b;
        if (keyCode == KeyEvent.VK_H) keys[DOWN] = b;
        if (keyCode == KeyEvent.VK_G) keys[LEFT] = b;
        if (keyCode == KeyEvent.VK_J) keys[RIGHT] = b;
        if (keyCode == KeyEvent.VK_T) keys[TURN_LEFT] = b;
        if (keyCode == KeyEvent.VK_U) keys[TURN_RIGHT] = b;

        if (keyCode == KeyEvent.VK_UP) keys[UP] = b;
        if (keyCode == KeyEvent.VK_DOWN) keys[DOWN] = b;
        if (keyCode == KeyEvent.VK_LEFT) keys[LEFT] = b;
        if (keyCode == KeyEvent.VK_RIGHT) keys[RIGHT] = b;
        if (keyCode == KeyEvent.VK_HOME) keys[TURN_LEFT] = b;
        if (keyCode == KeyEvent.VK_PAGE_UP) keys[TURN_RIGHT] = b;


        if (keyCode == KeyEvent.VK_NUMPAD8) keys[UP] = b;
        if (keyCode == KeyEvent.VK_NUMPAD5) keys[DOWN] = b;
        if (keyCode == KeyEvent.VK_NUMPAD4) keys[LEFT] = b;
        if (keyCode == KeyEvent.VK_NUMPAD6) keys[RIGHT] = b;
        if (keyCode == KeyEvent.VK_NUMPAD7) keys[TURN_LEFT] = b;
        if (keyCode == KeyEvent.VK_NUMPAD9) keys[TURN_RIGHT] = b;
        
        if (keyCode == KeyEvent.VK_M) keys[SAVE_TEST] = b;
    }

    public void keyPressed(KeyEvent ke) {
        toggleKey(ke.getKeyCode(), true);
    }

    public void keyReleased(KeyEvent ke) {
    }

    public void keyTyped(KeyEvent ke) {
    }

    public void mouseClicked(MouseEvent me) {
    }

    public void mouseEntered(MouseEvent me) {
    }

    public void mouseExited(MouseEvent me) {
    }

    public void mousePressed(MouseEvent me) {
        int button = me.getButton();
        if (button == MouseEvent.BUTTON1) click = 1;
        if (button == MouseEvent.BUTTON3) click = 2;
    }

    public void mouseReleased(MouseEvent me) {
    }

    public void mouseDragged(MouseEvent me) {
        xMouse = me.getX() / 2;
        yMouse = me.getY() / 2;
    }

    public void mouseMoved(MouseEvent me) {
        xMouse = me.getX() / 2;
        yMouse = me.getY() / 2;
    }
}
