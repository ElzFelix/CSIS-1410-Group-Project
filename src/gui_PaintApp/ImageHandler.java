package gui_PaintApp;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.imageio.ImageTypeSpecifier;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class ImageHandler {

    static FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");

    /**
     * returns a BufferedImage to the given size from JFileChooser
     * @param canvasPanel
     */
    public static BufferedImage openStamp(int size)
    {
    	JFileChooser fileChooser = new JFileChooser();
    	fileChooser.setFileFilter(fileFilter);
    	
    	if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) try
    	{
    		
    		File file = fileChooser.getSelectedFile();
    		
    		BufferedImage image = ImageIO.read(file);
    		 
    		image = resizeImage(image, size, image.getHeight()+(size-image.getWidth()));
    		
    		JOptionPane.showMessageDialog(null, "Image loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
    		
    		return image;
    	}
    	catch (Exception e)
    	{
    		JOptionPane.showMessageDialog(null, "Failed to load the image.", "Error", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
    	}
		return null;
    }
    
    
    /**
     * Prompts the user to select a save location and saves the canvas as an image file.
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller
     */
    public static void saveImage(CanvasPanel canvasPanel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);

        if (fileChooser.showSaveDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                // Create a BufferedImage from the CanvasPanel
                BufferedImage image = new BufferedImage(
                        canvasPanel.getWidth(),
                        canvasPanel.getHeight(),
                        BufferedImage.TYPE_INT_ARGB
                );
                
                canvasPanel.paint(image.getGraphics());

                // Ensure the file has a .png extension
                File file = fileChooser.getSelectedFile();
                if (!file.getName().toLowerCase().endsWith(".png")) {
                    file = new File(file.getParentFile(), file.getName() + ".png");
                }

                // Save the image
                ImageIO.write(image, "png", file);
                JOptionPane.showMessageDialog(null, "Image saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
           
            } catch (IOException e) {
                JOptionPane.showMessageDialog(null, "Failed to save the image.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
            }
        }
    }

    /**
     * Prompts the user to select an image and pastes it into the top left of the canvas.
     * <br>
     * Created by: Eleazar Felix
     * <br>
     * Christian Miller cleaned up whitespace
     * <br>
     * Doc comment written by: Christian Miller
     */
    public static void openImage(CanvasPanel canvasPanel) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(fileFilter);

        if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            try {
                // Read the selected image file and set it as the canvas image
                BufferedImage image = ImageIO.read(fileChooser.getSelectedFile());
                
                canvasPanel.clearCanvas(); 
            	
            	//made loaded image scale to canvas -chris
            	int width = image.getWidth();
            	int height = image.getHeight();
            	
            	if (width > canvasPanel.getWidth() || width < canvasPanel.getWidth())
            	{
            		width = canvasPanel.getWidth();
            		
            		height += (width - image.getWidth());
            	}
            	
            	BufferedImage resized = resizeImage(image, width, height);
                
                canvasPanel.setCanvasImage(resized);
                canvasPanel.repaint();
                
                JOptionPane.showMessageDialog(null, "Image loaded successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            
            } 
            catch (IOException e) {
            	
                JOptionPane.showMessageDialog(null, "Failed to load the image.", "Error", JOptionPane.ERROR_MESSAGE);
                e.printStackTrace();
                
            }
        }
    }

	/**
	 * resizes a <code>BufferedImage</code> to the given width and height.
	 * <br>
	 * Written By: Christian Miller
	 * @param image
	 * @param width
	 * @param height
	 * @return new BufferedImage
	 */
	public static BufferedImage resizeImage(BufferedImage image, int width, int height)
	{
		Image scaledImage = image.getScaledInstance(width, height, Image.SCALE_SMOOTH);
		//hopefully this works
		
		BufferedImage becauseNoCast = new BufferedImage(width, height, image.getType());
		//this is because java refuses to cast image to bufferedimage
		
		becauseNoCast.getGraphics().drawImage(scaledImage, 0, 0 , null);
		return becauseNoCast;
	}
}
