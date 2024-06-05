package com.cloth.backend.utils;

import java.math.BigInteger;
import static com.cloth.backend.utils.Math.RandomInteger;

/**
 * Класс, представляющий тест Ферма для проверки числа на простоту.
 */
public class Fermat {

  /**
   * Метод для выполнения теста Ферма.
   *
   * @param n Число, которое требуется проверить на простоту.
   * @param k Количество итераций теста.
   * @return {@code true}, если число вероятно простое, {@code false} в противном случае.
   */
  public static boolean run(BigInteger n, int k) {
    if (n.equals(BigInteger.ONE) || n.equals(BigInteger.valueOf(4)))
      return false;
    else if (n.equals(BigInteger.valueOf(2)) || n.equals(BigInteger.valueOf(3)))
      return true;
    else {
      for (int i = 0; i < k; i++) {
        if (!RandomInteger(BigInteger.valueOf(2),
                n.subtract(BigInteger.valueOf(2))).modPow(n.subtract(BigInteger.ONE), n).equals(BigInteger.ONE))
          return false;
      }
      return true;
    }
  }
}
