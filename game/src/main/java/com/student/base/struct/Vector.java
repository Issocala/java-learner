package com.student.base.struct;

import com.student.base.model.Copyable;

import java.util.Map;

/**
 * @author : luoyong
 * @date : 2021-03-21 14:18
 **/
public class Vector implements Copyable<Vector> {

    public static final Vector ZERO = Vector.valueOf(0, 0);

    private int x;
    private int y;

    public Vector() {
    }

    public Vector(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public Vector(Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public static Vector valueOf(int x, int y) {
        Vector vector = new Vector();
        vector.x = x;
        vector.y = y;
        return vector;
    }

    @Override
    public Vector copy() {
        return new Vector(this);
    }

    public void zero() {
        this.x = 0;
        this.y = 0;
    }

    public void set(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void set(Vector vector) {
        this.x = vector.x;
        this.y = vector.y;
    }

    public void add(int x, int y) {
        this.x = this.x + x;
        this.y = this.y + y;
    }

    public void add(Vector vector) {
        this.x = this.x + vector.x;
        this.y = this.y + vector.y;
    }

    public void sub(int x, int y) {
        this.x = this.x - x;
        this.y = this.y - y;
    }

    public void sub(Vector vector) {
        this.x = this.x - vector.x;
        this.y = this.y - vector.y;
    }

    public void mul(int scalar) {
        this.x = this.x * scalar;
        this.y = this.y * scalar;
    }

    public void div(int scalar) {
        this.x = this.x / scalar;
        this.y = this.y / scalar;
    }

    public void negate() {
        this.x = -this.x;
        this.y = -this.y;
    }

    public int lengthSquared() {
        return (this.x * this.x) + (this.y * this.y);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * 向量归一化
     */
    public void normalize() {
        double length = length();
        if (length != 0) {
            this.x = (int) (this.x / length);
            this.y = (int) (this.y / length);
        }
    }

    /**
     * 获取顺时针垂直向量
     *
     * @return
     */
    public Vector verticalClockwise() {
        return Vector.valueOf(this.y, -this.x);
    }


    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }
}
