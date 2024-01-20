package org.example.main;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class Image
{
    private int[][] rgbMatrix; // Represents the rgb matrix that belongs to the image, it is holding int rgb value
    private BufferedImage image; // Image

    private final int width;
    private final int height;
    public Image(int[][] rgbMatrix, int width, int height)
    {
        this.rgbMatrix = rgbMatrix;
        this.width = width;
        this.height = height;


        // Construct corresponding Buffered image
        constructBufferedImage();
    }


    public Image(BufferedImage image, int width, int height)
    {
        this.image = image;
        this.width = width;
        this.height = height;


        // Construct corresponding rgbMatrix
        constructRgbMatrix();
    }


    // Creates 'BufferedImage' depending on 'rgbMatrix'

    private void constructBufferedImage()
    {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        var g = image.createGraphics();
        g.setColor(Color.white);
        g.fillRect(0, 0, width, height); // give the whole image a white background

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
               g.setColor(new Color(rgbMatrix[y][x]));
               g.fillRect(x, y, 1, 1);
            }
        }
        g.dispose();

    }

    // Creates 'rgbMatrix' depending on 'BufferedImage'

    private void constructRgbMatrix()
    {
        rgbMatrix = new int[width][height];

        for (int y = 0; y < height; y++)
        {
            for (int x = 0; x < width; x++)
            {
                int pixelColor = image.getRGB(x, y);
                rgbMatrix[y][x] = pixelColor;
            }
        }
    }


    /**
     * Save the constructed image as a PNG file as compressedImage.png.
     */
    public void save(String path)
    {
        try {
            File output = new File(path);
            ImageIO.write(image, "png", output);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * read image as BufferedImage
     * @param path path of the image to be read
     * @return returns corresponding buffered image
     */
    public static BufferedImage readAsBufferedImage(String path)
    {
        File input = new File(path);
        BufferedImage image;
        try
        {
            image = ImageIO.read(input);
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
        return image;
    }


    // Getters
    public int[][] getRgbMatrix()
    {
        return this.rgbMatrix;
    }


    public BufferedImage getImage()
    {
        return this.image;
    }



    // Setters



}
