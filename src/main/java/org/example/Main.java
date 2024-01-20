package org.example;

import org.example.main.CustomLogger;
import org.example.main.FileToImageConverter;
import org.example.main.ImageToFileConverter;

public class Main
{
    public static void main(String[] args)
    {
        FileToImageConverter fileToImageConverter = new FileToImageConverter("src/main/resources/123.zip", 6, 1920, 1080);
        fileToImageConverter.getDataFrame().save("compressedImage.png");

        ImageToFileConverter imageToFileConverter = new ImageToFileConverter(fileToImageConverter.getDataFrame(), 6, 1920, 1080);
        imageToFileConverter.save("output.zip");


    }
}