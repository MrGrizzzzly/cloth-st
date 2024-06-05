package com.cloth.backend.models;

import java.math.BigInteger;
import java.util.Arrays;
import com.cloth.backend.utils.Fermat;
import com.cloth.backend.utils.MillerRabin;
import com.cloth.backend.utils.Math;

/**
 * Класс, представляющий реализацию протокола Диффи-Хеллмана для обмена ключами.
 */
public class DH {

    /**
     * Простое число p.
     */
    private final BigInteger p;

    /**
     * Первообразный корень по модулю p.
     */
    private final BigInteger g;

    /**
     * Секретный ключ b.
     */
    private final BigInteger b;

    /**
     * Открытый ключ A.
     */
    private BigInteger A;

    /**
     * Открытый ключ B.
     */
    private BigInteger B;

    /**
     * Общий секретный ключ K.
     */
    private byte[] K;


    /**
     * геттер простого числа p
     *
     * @return простое число p
     */
    public BigInteger getP() {
    return p;
    }

    /**
     * геттер Первообразного корня по модулю p
     *
     * @return первообразный корень по модулю p
     */
    public BigInteger getG() {
    return g;
    }

    /**
     * геттер секретного ключа b
     *
     * @return секретный ключ b
     */
    public BigInteger getbLow() {
    return b;
    }

    /**
     * геттер открытого ключа A
     *
     * @return открытый ключ A
     */
    public BigInteger getA() {
    return A;
    }

    /**
     * геттер открытого ключа B
     *
     * @return открытый ключ B
     */
    public BigInteger getB() {
    return B;
    }

    /**
     * геттер общего секретного ключа K
     *
     * @return общий секретный ключ K
     */
    public byte[] getK() {
    return K;
    }

    /**
     * сеттер открытого ключа A
     *
     * @param a пароль
     */
    public void setA(BigInteger a) {
    A = a;
    }

    /**
     * сеттер открытого ключа B
     *
     * @param b пароль
     */
    public void setB(BigInteger b) {
    B = b;
    }

    /**
     * сеттер общего секретного ключа K
     *
     * @param k общий секретный ключ K
     */
    public void setK(byte[] k) {
    K = k;
    }


    /**
     * Конструктор класса для инициализации параметров протокола Диффи-Хеллмана.
     */
    public DH() {
        this.p = findPrime(BigInteger.TEN.pow(12), BigInteger.TEN.pow(13).subtract(BigInteger.ONE));
        this.b = findPrime(BigInteger.TEN.pow(12), BigInteger.TEN.pow(13).subtract(BigInteger.ONE));
        this.g = Math.pRoot(p);
    }

    /**
     * Метод для нахождения простого числа в заданном диапазоне.
     *
     * @param lb Нижняя граница диапазона.
     * @param rb Верхняя граница диапазона.
     * @return Найденное простое число.
     */
    private BigInteger findPrime(BigInteger lb, BigInteger rb) {
    BigInteger num;
    do
        num = Math.RandomInteger(lb, rb);
    while (!(MillerRabin.run(num, 10) && Fermat.run(num, 10)));
    return num;
    }

    /**
     * Метод для установки значения открытого ключа A,
     * вычисления открытого ключа B и общего секретного ключа K.
     *
     * @param A Значение открытого ключа A.
     */
    public void setFromA(BigInteger A) {
        setA(A);
        setB(g.modPow(b, p));
        setK((Math.Hash(String.valueOf(A.modPow(b, p)))));
    }

    /**
     * Строковое представление
     * @return строковое представление
     */
    @Override
    public String toString() {
        return "DH{" +
            "p=" + p +
            ", g=" + g +
            ", b=" + b +
            ", A=" + A +
            ", B=" + B +
            ", K=" + Arrays.toString(K) +
            '}';
    }
    }
