package org.example.main;

import java.awt.*;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ImageToFileConverter
{
    public static Map<Character, CustomColors> hexToCharMap = new HashMap<>();
    private StringBuilder decodedHexadecimalString;

    private CustomLogger logger;


    public ImageToFileConverter(Image image, int pixelSize, int width, int height)
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

        // Rgb matrix of the image
        int[][] decodedRgbMatrix = image.getRgbMatrix();

        // Decoded hexadecimal
        decodedHexadecimalString = new StringBuilder();


        // Read the image
        outer_loop:
        for (int y = 0; y <= height - pixelSize; y += pixelSize)
        {
            for (int x = 0; x <= width - pixelSize; x += pixelSize)
            {
                var color = decodedRgbMatrix[y][x];

                if (Color.white.getRGB() == color)
                {
                    break outer_loop;
                }

                var digit = getKey(CustomColors.findClosestColor(new Color(color)));
                if (digit == '?')
                {
                    logger.error("couldn't find the closest color");
                    break outer_loop;
                }
                decodedHexadecimalString.append(digit);
            }
        }

    }

    private char getKey(CustomColors color) {
        for (var entry : hexToCharMap.entrySet()) {
            if (color.equals(entry.getValue())) {
                return entry.getKey();
            }
        }
        return '?';
    }

    public void save(String path)
    {
        convertHexToFile(decodedHexadecimalString + "", path);
    }


    /**
     * Converts hexadecimal string number into the corresponding file specified.
     * @param hexRepresentation hexadecimal string number.
     * @param outputPath destination of the file to be reconstructed from hexadecimal.
     */
    private void convertHexToFile(String hexRepresentation, String outputPath) {
        try (FileOutputStream fos = new FileOutputStream(outputPath)) {
            byte[] bytes = hexStringToByteArray(hexRepresentation);
            fos.write(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    // helper method for method 'convertHexToFile()'
    private static byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4)
                    + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }





}
