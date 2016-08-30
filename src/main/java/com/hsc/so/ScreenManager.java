/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hsc.so;

import java.awt.DisplayMode;
import java.awt.Graphics2D;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.awt.Window;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import javax.swing.JFrame;

/**
 * A Classe ScreenManager e usada para fazer o full screen do modo Gráfico.
 */
public class ScreenManager {

    private static ScreenManager myInstance = new ScreenManager();
    private static GraphicsDevice device; //Dispositivo de Gráfico

    /**
     * Instancia e configura um device como Default
     */
    private ScreenManager() {
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        // Apena um dispositivo.
        ScreenManager.device = env.getDefaultScreenDevice();
    }

    /**
     *
     * @return
     */
    public static ScreenManager getInstance() {
        return myInstance;
    }

    /**
     * Abilitar o fullScreen
     *
     * @return Status do fullScreen
     */
    public boolean isFullScreenSupported() {
        return ScreenManager.device.isFullScreenSupported();
    }

    /**
     * Configuração Default
     */
    public void getDefaultConfiguration() {
        ScreenManager.device.getDefaultConfiguration();
    }

    /**
     * Retorna apenas a configuração original
     *
     * @return
     */
    public DisplayMode getDisplayMode() {
        return ScreenManager.device.getDisplayMode();
    }

    /**
     * Retorna uma lista de modos de configuração de tela validos para este
     * computador.
     *
     * @return
     */
    public DisplayMode[] getCompatibleDisplayModes() {
        return ScreenManager.device.getDisplayModes();
    }

    /**
     * Retorna a primeira compatibilidade da lista de modos de tela.
     *
     * @param modes
     * @return
     */
    public DisplayMode findFirstCompatibleMode(DisplayMode modes[]) {
        //  Retorna array com as resoluções compativeis com a placa de vídeo
        DisplayMode systemModes[] = ScreenManager.device.getDisplayModes();

        for (DisplayMode mode : modes) {
            for (DisplayMode systemMode : systemModes) {
                if (displayModesMatch(mode, systemMode)) {
                    return systemMode;
                }
            }
        }
        return null;
    }

    /**
     * Retorna o modo de exibição atual.
     *
     * @return
     */
    public DisplayMode getCurrentDisplayMode() {
        return ScreenManager.device.getDisplayMode();
    }

    /**
     * Determina se dois modos de exibição "match ". Dois modos de exibição de
     * jogo se ter a mesma resolução, profundidade de bits e taxa de
     * atualização. A profundidade de bits é ignorado se um dos modos tem uma
     * profundidade de bits de DisplayMode.BIT_DEPTH_MULTI. Da mesma forma, a
     * taxa de atualização é ignorado se um dos modos tem uma taxa de
     * atualização de DisplayMode.REFRESH_RATE_UNKNOWN.
     *
     * @param mode1
     * @param systemMode
     * @return
     */
    public boolean displayModesMatch(DisplayMode mode1, DisplayMode systemMode) {
        if (mode1.getWidth() != systemMode.getWidth() || mode1.getHeight() != systemMode.getHeight()) {
            return false;
        }

        if (mode1.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
                && systemMode.getBitDepth() != DisplayMode.BIT_DEPTH_MULTI
                && mode1.getBitDepth() != systemMode.getBitDepth()) {
            return false;
        }

        if (mode1.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
                && systemMode.getRefreshRate() != DisplayMode.REFRESH_RATE_UNKNOWN
                && mode1.getRefreshRate() != systemMode.getRefreshRate()) {
            return false;
        }

        return true;
    }

    /**
     * Faz dormir por alguns segundos
     *
     * @param millis
     */
    private void sleep(long millis) {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException e) {
        }
    }

    /**
     * Entra no modo de tela cheia e muda o modo de exibição. Se o especificado
     * Modo de exibição * é nulo ou não compatíveis com este dispositivo, ou se
     * o O modo de exibição não pode ser mudado no sistema, o modo de exibição
     * atual É usado.
     * <p>
     * O display usa uma BufferStrategy com dois amortecedores.
     *
     * @param displayMode
     */
    public void setFullScreen(DisplayMode displayMode) throws InterruptedException {
        final JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setUndecorated(true);
        frame.setIgnoreRepaint(true);
        frame.setResizable(false);

        device.setFullScreenWindow(frame);

        if (displayMode != null && device.isDisplayChangeSupported()) {
            this.setDisplayMode(displayMode);
            // fix for mac os x
            frame.setSize(displayMode.getWidth(), displayMode.getHeight());
        }

        frame.createBufferStrategy(2);
        sleep(500);
    }

    /**
     * Obtém o contexto gráfico para a exposição. A dupla usa ScreenManager
     * Buffering *, para que os aplicativos devem chamar update () para mostrar
     * qualquer gráfico desenhado.
     * <p>
     * O requerimento deve dispor do objeto gráfico.
     *
     * @return
     */
    public Graphics2D getGraphics() {
        if (getFullScreenWindow() == null) {
            throw new IllegalStateException("Not in full screen mode.");
        }

        return (Graphics2D) getFullScreenWindow().getBufferStrategy().getDrawGraphics();
    }

    /**
     * Atualizar a tela.
     */
    public void update() {
        if (getFullScreenWindow() != null) {
            BufferStrategy strategy = getFullScreenWindow().getBufferStrategy();
            if (!strategy.contentsLost()) {
                strategy.show();
            }
        }

        // Sync the display on some systems.
        // (on Linux, this fixes event queue problems)
        Toolkit.getDefaultToolkit().sync();
    }

    /**
     * Returns the window currently used in full screen mode. Returns null if
     * the device is not in full screen mode.
     *
     * @return
     */
    public JFrame getFullScreenWindow() {
        return (JFrame) ScreenManager.device.getFullScreenWindow();
    }

    /**
     * Returns the width of the window currently used in full screen mode.
     * Returns 0 if the device is not in full screen mode.
     *
     * @return
     */
    public int getWidth() {
        if (getFullScreenWindow() == null) {
            throw new IllegalStateException("Not in full screen mode.");
        }
        return getFullScreenWindow().getWidth();
    }

    /**
     * Returns the height of the window currently used in full screen mode.
     * Returns 0 if the device is not in full screen mode.
     *
     * @return
     */
    public int getHeight() {
        if (getFullScreenWindow() == null) {
            throw new IllegalStateException("Not in full screen mode.");
        }
        return getFullScreenWindow().getHeight();
    }

    /**
     * Restores the screen's display mode.
     */
    public void restoreScreen() {
        if (getFullScreenWindow() != null) {
            getFullScreenWindow().dispose();
        }
        ScreenManager.device.setFullScreenWindow(null);
    }

    /**
     * Creates an image compatible with the current display.
     *
     * @param w
     * @param h
     * @param transparency
     * @return
     */
    public BufferedImage createCompatibleImage(int w, int h, int transparency) {
        if (getFullScreenWindow() == null) {
            throw new IllegalStateException("Not in full screen mode.");
        }

        return getFullScreenWindow().getGraphicsConfiguration().createCompatibleImage(w, h, transparency);

    }

    /**
     * Creates an image compatible with the current display.
     *
     * @param w
     * @param h
     * @return
     */
    public BufferedImage createCompatibleImage(int w, int h) {
        if (getFullScreenWindow() == null) {
            throw new IllegalStateException("Not in full screen mode.");
        }

        return getFullScreenWindow().getGraphicsConfiguration().createCompatibleImage(w, h);
    }

    /**
     * Changes the video display mode, if possible.
     *
     * @param displayMode
     */
    public void setDisplayMode(DisplayMode displayMode) throws InterruptedException, IllegalArgumentException {
        ScreenManager.device.setDisplayMode(displayMode);
        Thread.sleep(600);
    }

    /**
     *
     * @return
     */
    public boolean isDisplayChangeSupported() {
        return ScreenManager.device.isDisplayChangeSupported();
    }

    /**
     *
     * @param w
     */
    public void setFullScreenWindow(Window w) {
        device.setFullScreenWindow(w);
    }
}
