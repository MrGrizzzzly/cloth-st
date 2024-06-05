package com.cloth.backend.utils;

import java.math.BigInteger;
import java.util.Random;

/**
 * Класс, представляющий тест Миллера-Рабина для проверки числа на простоту.
 */
public class MillerRabin {
  /**
   * Метод для выполнения теста Миллера-Рабина.
   *
   * @param n           Число, которое требуется проверить на простоту.
   * @param k   Количество итераций теста.
   * @return {@code true}, если число вероятно простое, {@code false} в противном случае.
   */
  public static boolean run(BigInteger n, int k) {
    if (n.equals(BigInteger.valueOf(2)))
      return true;
    if (n.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO))
      return false;
    int r = 0;
    BigInteger s = n.subtract(BigInteger.ONE);
    while (s.mod(BigInteger.valueOf(2)).equals(BigInteger.ZERO)) {
      r++;
      s = s.divide(BigInteger.valueOf(2));
    }
    for (int i = 0; i < k; i++) {
      BigInteger x = new BigInteger(n.bitLength(),
              new Random()).mod(n.subtract(BigInteger.valueOf(2))).add(BigInteger.valueOf(2)).modPow(s, n);
      if (x.equals(BigInteger.ONE) || x.equals(n.subtract(BigInteger.ONE)))
        continue;
      for (int j = 0; j < r - 1; j++) {
        x = x.modPow(BigInteger.valueOf(2), n);
        if (x.equals(n.subtract(BigInteger.ONE)))
          break;
      }
      if (!x.equals(n.subtract(BigInteger.ONE)))
        return false;
    }
    return true;
  }
}
