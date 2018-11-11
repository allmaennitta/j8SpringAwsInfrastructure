package de.allmaennitta.java.aws.infrastructure.config.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import org.apache.commons.codec.CharEncoding;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class Utils {

  private static final Logger LOG = LoggerFactory.getLogger(Utils.class);

  public static String zipString(String data) {
    byte[] bytesCompressed = compress(data);
    return Base64.getEncoder().encodeToString(bytesCompressed);
  }


  public static String unzipString(String zippedString) {
    String result = decompressToString(Base64.getDecoder().decode(zippedString));
    return result;
  }

  public static String getId(Pattern idPattern, String payload) {
    final Matcher matcher = idPattern.matcher(payload);

    if (matcher.find()) {

      return matcher.group(1);
    }

    return "NOT_EXTRACTABLE";
  }

  public static byte[] compress(byte[] bytesToCompress) {
    Deflater deflater = new Deflater();
    deflater.setInput(bytesToCompress);
    deflater.finish();

    byte[] bytesCompressed = new byte[Short.MAX_VALUE];

    int numberOfBytesAfterCompression = deflater.deflate(bytesCompressed);

    byte[] returnValues = new byte[numberOfBytesAfterCompression];

    System.arraycopy
        (
            bytesCompressed,
            0,
            returnValues,
            0,
            numberOfBytesAfterCompression
        );

    return returnValues;
  }

  public static byte[] compress(String stringToCompress) {
    byte[] returnValues = null;

    try {

      returnValues = compress
          (
              stringToCompress.getBytes(CharEncoding.UTF_8)
          );
    } catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
    }

    return returnValues;
  }

  public static byte[] decompress(byte[] bytesToDecompress) {
    byte[] returnValues = null;

    Inflater inflater = new Inflater();

    int numberOfBytesToDecompress = bytesToDecompress.length;

    inflater.setInput
        (
            bytesToDecompress,
            0,
            numberOfBytesToDecompress
        );

    int bufferSizeInBytes = numberOfBytesToDecompress;

    int numberOfBytesDecompressedSoFar = 0;
    List<Byte> bytesDecompressedSoFar = new ArrayList<>();

    try {
      while (inflater.needsInput() == false) {
        byte[] bytesDecompressedBuffer = new byte[bufferSizeInBytes];

        int numberOfBytesDecompressedThisTime = inflater.inflate
            (
                bytesDecompressedBuffer
            );

        numberOfBytesDecompressedSoFar += numberOfBytesDecompressedThisTime;

        for (int b = 0; b < numberOfBytesDecompressedThisTime; b++) {
          bytesDecompressedSoFar.add(bytesDecompressedBuffer[b]);
        }
      }

      returnValues = new byte[bytesDecompressedSoFar.size()];
      for (int b = 0; b < returnValues.length; b++) {
        returnValues[b] = (byte) (bytesDecompressedSoFar.get(b));
      }

    } catch (DataFormatException dfe) {
      dfe.printStackTrace();
    }

    inflater.end();

    return returnValues;
  }

  public static String decompressToString(byte[] bytesToDecompress) {
    byte[] bytesDecompressed = decompress
        (
            bytesToDecompress
        );

    String returnValue = null;

    try {
      returnValue = new String
          (
              bytesDecompressed,
              0,
              bytesDecompressed.length,
              CharEncoding.UTF_8
          );
    } catch (UnsupportedEncodingException uee) {
      uee.printStackTrace();
    }

    return returnValue;
  }

  public static String stringFromFile(URL dirURL, String filename) throws MalformedURLException {
    URL resourceURL = new URL(dirURL.toString()+"./"+filename);
    if (resourceURL == null) {
      throw new IllegalArgumentException("Filename not valid: " + filename);
    } else {
      try {
        File file = new File(resourceURL.toURI());
        return (String) Files.readAllLines(file.toPath()).stream().reduce("", String::concat);
      } catch (URISyntaxException | IOException var3) {
        throw new IllegalStateException(var3);
      }
    }
  }
}
