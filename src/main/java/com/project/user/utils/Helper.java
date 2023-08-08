package com.project.user.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Helper {

  private Helper() {
  }

  public static String hashPassword(String password) throws NoSuchAlgorithmException {
    MessageDigest md = MessageDigest.getInstance("SHA-256");
    byte[] hashBytes = md.digest(password.getBytes());

    StringBuilder hexString = new StringBuilder();
    for (byte b : hashBytes) {
      hexString.append(String.format("%02x", b));
    }

    return hexString.toString();
  }
}