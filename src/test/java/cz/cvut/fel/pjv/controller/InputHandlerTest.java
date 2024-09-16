package cz.cvut.fel.pjv.controller;

import cz.cvut.fel.pjv.controller.collision.CollisionChecker;
import cz.cvut.fel.pjv.model.entity.Player;
import cz.cvut.fel.pjv.model.game.GamePanel;
import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.awt.event.KeyEvent;

@ExtendWith(MockitoExtension.class)
public class InputHandlerTest {

    @Mock
    KeyEvent keyEvent;

    private final InputHandler inputHandler = new InputHandler();

    @Test
    void keyPressed_UP() {
        Mockito.when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_UP);

        inputHandler.keyPressed(keyEvent);

        Assert.assertTrue(inputHandler.getUpPressed());
    }

    @Test
    void keyPressed_DOWN() {
        Mockito.when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_DOWN);

        inputHandler.keyPressed(keyEvent);

        Assert.assertTrue(inputHandler.getDownPressed());
    }

    @Test
    void keyPressed_LEFT() {
        Mockito.when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_LEFT);

        inputHandler.keyPressed(keyEvent);

        Assert.assertTrue(inputHandler.getLeftPressed());
    }

    @Test
    void keyPressed_RIGHT() {
        Mockito.when(keyEvent.getKeyCode()).thenReturn(KeyEvent.VK_RIGHT);

        inputHandler.keyPressed(keyEvent);

        Assert.assertTrue(inputHandler.getRightPressed());
    }
}