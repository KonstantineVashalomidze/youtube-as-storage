package org.example.main;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileToImageConverter {

    private String filePath;
    private CustomLogger logger;

    private Image image;

    public Map<Character, CustomColors> hexToCharMap = new HashMap<>();



    /**
     *
     * @param filePath path of the file to be converted into hexadecimal string number.
     * @param width width of the image which will hold the file input data
     * @param height height of the image which will hold the file input data
     * @param pixelSize what do we consider as one pixel, for example if 'pixelSize' is 2 then we consider one pixel to be 2x2. Make sure that this number can divide both width and height variables.
     */
    public FileToImageConverter(String filePath, int pixelSize, int width, int height)
    {
        this.filePath = filePath;

        // Check if the pixelSize is divisible for both width and height
        if (width % pixelSize != 0 || height % pixelSize != 0)
        {
            logger.error("variable 'pixelSize' doesn't meet restrictions");
            System.out.println("INVALID pixelSize IN CLASS 'fileToImageConverter'");
        }
        else
        {
            // Initialize map
            // Assigning distinct colors to hexadecimal characters
            hexToCharMap.put('0', CustomColors.COLOR_0);
            hexToCharMap.put('1', CustomColors.COLOR_1);
            hexToCharMap.put('2', CustomColors.COLOR_2);
            hexToCharMap.put('3', CustomColors.COLOR_3);
            hexToCharMap.put('4', CustomColors.COLOR_4);
            hexToCharMap.put('5', CustomColors.COLOR_5);
            hexToCharMap.put('6', CustomColors.COLOR_6);
            hexToCharMap.put('7', CustomColors.COLOR_7);
            hexToCharMap.put('8', CustomColors.COLOR_8);
            hexToCharMap.put('9', CustomColors.COLOR_9);
            hexToCharMap.put('A', CustomColors.COLOR_10);
            hexToCharMap.put('B', CustomColors.COLOR_11);
            hexToCharMap.put('C', CustomColors.COLOR_12);
            hexToCharMap.put('D', CustomColors.COLOR_13);
            hexToCharMap.put('E', CustomColors.COLOR_14);
            hexToCharMap.put('F', CustomColors.COLOR_15);


            // Convert file into the hexadecimal number
            var hexadecimalFile = convertFileToHex();

            // Size of the hexadecimalFile
            var hexadecimalFileSize = hexadecimalFile.length();


            // Matrix where encoded data will be stored and then will be converted into corresponding image 1920 × 1080
            int[][] encodedRgbMatrix = new int[height][width];

            // Index of hexadecimal string
            int hexStringIndex = 0;

            // Fill dataframeRgbMatrix
            for (int y = 0; y <= height - pixelSize; y += pixelSize)
            {
                for (int x = 0; x <= width - pixelSize; x += pixelSize)
                {
                    // Color the whole pixel
                    for (int i = y; i < y + pixelSize; i++)
                    {
                        for (int j = x; j < x + pixelSize; j++)
                        {
                            // If whole string is mapped then fill the rest of the dimension with white color
                            if (hexadecimalFileSize > hexStringIndex)
                            {
                                encodedRgbMatrix[i][j] = hexToCharMap.get(hexadecimalFile.charAt(hexStringIndex)).getColor().getRGB();
                            }
                            else
                            {
                                // -1 corresponds to white color
                                encodedRgbMatrix[i][j] = -1;
                            }

                        }
                    }
                    hexStringIndex++;
                }
            }

            // Construct image with corresponding matrix
            image = new Image(encodedRgbMatrix, width, height);





        }

    }


    public Image getDataFrame()
    {
        return image;
    }


    /**
     * Converts file into the hexadecimal string object and returns it.
     * @return hexadecimal string object constructed from specified file.
     */
    private StringBuilder convertFileToHex() {
        try (FileInputStream fis = new FileInputStream(filePath)) {
            StringBuilder hexString = new StringBuilder();
            int data;
            while ((data = fis.read()) != -1) {
                hexString.append(String.format("%02X", data));
            }
            return hexString;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
