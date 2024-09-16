package cz.cvut.fel.pjv.controller;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class InputHandler implements KeyListener {

    private boolean leftPressed;
    private boolean rightPressed;
    private boolean downPressed;
    private boolean upPressed;

    private boolean keySPressed;

    public boolean getLeftPressed() { return leftPressed; }
    public boolean getRightPressed() { return rightPressed; }
    public boolean getDownPressed() { return downPressed; }
    public boolean getUpPressed(){ return upPressed; }

    public boolean getKeySPressed() {
        return keySPressed;
    }


    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * Checks if user press key
     */
    @Override
    public void keyPressed(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){
            upPressed = true;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = true;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = true;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = true;
        }
        if(code == KeyEvent.VK_S){
            keySPressed = true;
        }
    }
    /**
     * Checks if user stop press key
     */

    @Override
    public void keyReleased(KeyEvent e) {
        int code = e.getKeyCode();
        if(code == KeyEvent.VK_UP){
            upPressed = false;
        }
        if(code == KeyEvent.VK_DOWN){
            downPressed = false;
        }
        if(code == KeyEvent.VK_LEFT){
            leftPressed = false;
        }
        if(code == KeyEvent.VK_RIGHT){
            rightPressed = false;
        }
        if(code == KeyEvent.VK_S){
            keySPressed = false;
        }
    }
}
