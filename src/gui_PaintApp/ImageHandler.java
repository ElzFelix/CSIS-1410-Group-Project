package gui_PaintApp;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;


public class ImageHandler {
	
	static FileNameExtensionFilter fileFilter = new FileNameExtensionFilter("Image Files", "jpg", "png", "gif", "jpeg");

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
                ImageIO.write(canvasPanel.getCanvasImage(), "PNG", fileChooser.getSelectedFile());
            } 
            
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }//end of saveImage
    
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
                ((CanvasPanel) canvasPanel).setCanvasImage(ImageIO.read(fileChooser.getSelectedFile())) ;
                canvasPanel.repaint();
            }
        	
        	catch (IOException e) {
                e.printStackTrace();
            }
        }
        
    }//end of openImage
}
