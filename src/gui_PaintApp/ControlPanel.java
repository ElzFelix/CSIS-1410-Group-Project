package gui_PaintApp;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;

public class ControlPanel extends JPanel {
	
    private JSlider redSlider, greenSlider, blueSlider, penSizeSlider;
    private JButton btnEraseTool, btnStampTool;
    private CanvasPanel canvasPanel; //this is so that the main canvasPanel can be effected by this controlPanel

    public ControlPanel(CanvasPanel canvasPanel) {
    	
    	this.canvasPanel = canvasPanel;
    	
        setLayout(new GridLayout(7, 1));
        
        add(createSliderPanel("Red:", redSlider = new JSlider(0, 255, 0), e -> {updateColor();}));
        add(createSliderPanel("Green:", greenSlider = new JSlider(0, 255, 0), e -> {updateColor();}));
        add(createSliderPanel("Blue:", blueSlider = new JSlider(0, 255, 0), e -> {updateColor();}));

        add(createSliderPanel("Pen Size", penSizeSlider = new JSlider(1, 20, 5), e -> {updateSize();}));

        //erase tool
        createBtnErase(canvasPanel);

        //stamp tool
        createBtnStamp(canvasPanel);
        
        
    }

	/**
	 * @param canvasPanel
	 */
	private void createBtnErase(CanvasPanel canvasPanel)
	{
		btnEraseTool = new JButton("Erase Tool");
        
        btnEraseTool.addActionListener(e -> {
        	canvasPanel.setStamping(false);
        	canvasPanel.setErasing(!canvasPanel.isErasing());
        	});
        
        add(btnEraseTool);
	}

	/**
	 * @param canvasPanel
	 */
	private void createBtnStamp(CanvasPanel canvasPanel)
	{
		btnStampTool = new JButton("Stamp Tool");
        
        btnStampTool.addActionListener(e -> {
        	canvasPanel.setErasing(false);
        	canvasPanel.setStamping(!canvasPanel.isStamping());
        	});
        
        add(btnStampTool);
	}

    private JPanel createSliderPanel(String label, JSlider slider, ChangeListener l) {
        JPanel panel = new JPanel();
        JLabel sliderLabel = new JLabel(label);
        panel.add(sliderLabel);
        
        slider.addChangeListener(l);
        
        panel.add(slider);
        return panel;
    }

    /**
     * Reads the color sliders and sets <code>currentColor</code> the RGB color for the given values.
     * 
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller 
     */
    private void updateColor() {
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        
        canvasPanel.setCurrentColor(new Color(red, green, blue));
    }
    
    private void updateSize() {
    	canvasPanel.setPenSize(penSizeSlider.getValue());
    
    }
}