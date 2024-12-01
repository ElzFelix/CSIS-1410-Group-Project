package gui_PaintApp;

import java.awt.Color;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class ControlPanel extends JPanel {
    private JSlider redSlider, greenSlider, blueSlider, penSizeSlider;
    private JButton btnEraseTool, btnStampTool;
    private Color currentColor = Color.BLACK;
    private int penSize = 5;

    public ControlPanel() {
        setLayout(new GridLayout(5, 1));
        add(createSliderPanel("Red", redSlider = new JSlider(0, 255, 0)));
        add(createSliderPanel("Green", greenSlider = new JSlider(0, 255, 0)));
        add(createSliderPanel("Blue", blueSlider = new JSlider(0, 255, 0)));

        penSizeSlider = new JSlider(1, 20, 5);
        add(new JLabel("Pen Size"));
        add(penSizeSlider);

        btnEraseTool = new JButton("Erase Tool");
        btnEraseTool.addActionListener(e -> {/* toggle eraser */});
        add(btnEraseTool);

        btnStampTool = new JButton("Stamp Tool");
        btnStampTool.addActionListener(e -> {/* toggle stamp tool */});
        add(btnStampTool);
    }

    private JPanel createSliderPanel(String label, JSlider slider) {
        JPanel panel = new JPanel();
        JLabel sliderLabel = new JLabel(label);
        panel.add(sliderLabel);
        slider.addChangeListener(e -> updateColor());
        panel.add(slider);
        return panel;
    }

    private void updateColor() {
        int red = redSlider.getValue();
        int green = greenSlider.getValue();
        int blue = blueSlider.getValue();
        currentColor = new Color(red, green, blue);
    }

    public Color getCurrentColor() {
        return currentColor;
    }

    public int getPenSize() {
        return penSizeSlider.getValue();
    }
}