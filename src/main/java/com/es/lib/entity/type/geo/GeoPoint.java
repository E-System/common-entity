/*
 * Copyright 2020 E-System LLC
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.es.lib.entity.type.geo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Vitaliy Savchenko - savchenko.v@ext-system.com
 * @since 10.12.17
 */
public class GeoPoint implements Serializable, Cloneable {

    private double x;
    private double y;

    public GeoPoint() { }

    public GeoPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public GeoPoint(String value) {
        this.setValue(value);
    }

    public void setValue(String s) {
        List<String> tokens = tokenize(removePara(s), ',');
        this.x = Double.parseDouble(tokens.get(0));
        this.y = Double.parseDouble(tokens.get(1));
    }

    public void setByteValue(byte[] b, int offset) {
        this.x = float8(b, offset);
        this.y = float8(b, offset + 8);
    }

    public String getValue() {
        return "(" + this.x + "," + this.y + ")";
    }

    public String getSimpleValue() {
        return this.x + "," + this.y;
    }

    public void setSimpleValue(String value) { /* stub for <h:inputHidden>*/}

    public int lengthInBytes() {
        return 16;
    }

    public void toBytes(byte[] b, int offset) {
        float8(b, offset, this.x);
        float8(b, offset + 8, this.y);
    }

    public void translate(int x, int y) {
        this.translate((double) x, (double) y);
    }

    public void translate(double x, double y) {
        this.x += x;
        this.y += y;
    }

    public void move(int x, int y) {
        this.setLocation(x, y);
    }

    public void move(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setLocation(int x, int y) {
        this.move((double) x, (double) y);
    }

    public void setLocation(GeoPoint p) { this.move(p.x, p.y); }

    public double getX() { return x; }

    public void setX(double x) { this.x = x; }

    public double getY() { return y; }

    public void setY(double y) { this.y = y; }

    public Object clone() throws CloneNotSupportedException { return super.clone(); }

    public static String remove(String s, String l, String t) {
        if (s.startsWith(l)) {
            s = s.substring(l.length());
        }

        if (s.endsWith(t)) {
            s = s.substring(0, s.length() - t.length());
        }

        return s;
    }

    public static String removePara(String s) {
        return remove(s, "(", ")");
    }

    public static List<String> tokenize(String string, char delim) {
        List<String> tokens = new ArrayList<>();
        int nest = 0;
        boolean skipChar = false;
        boolean nestedDoubleQuote = false;
        int p = 0;

        int s;
        for (s = 0; p < string.length(); ++p) {
            char c = string.charAt(p);
            if (c == '(' || c == '[' || c == '<' || !nestedDoubleQuote && !skipChar && c == '"') {
                ++nest;
                if (c == '"') {
                    nestedDoubleQuote = true;
                    skipChar = true;
                }
            }

            if (c == ')' || c == ']' || c == '>' || nestedDoubleQuote && !skipChar && c == '"') {
                --nest;
                if (c == '"') {
                    nestedDoubleQuote = false;
                }
            }

            skipChar = c == '\\';
            if (nest == 0 && c == delim) {
                tokens.add(string.substring(s, p));
                s = p + 1;
            }
        }

        if (s < string.length()) {
            tokens.add(string.substring(s));
        }

        return tokens;
    }

    public static long int8(byte[] bytes, int idx) {
        return ((long) (bytes[idx + 0] & 255) << 56) + ((long) (bytes[idx + 1] & 255) << 48) + ((long) (bytes[idx + 2] & 255) << 40) + ((long) (bytes[idx + 3] & 255) << 32) + ((long) (bytes[idx + 4] & 255) << 24) + ((long) (bytes[idx + 5] & 255) << 16) + ((long) (bytes[idx + 6] & 255) << 8) + (long) (bytes[idx + 7] & 255);
    }

    public static void int8(byte[] target, int idx, long value) {
        target[idx + 0] = (byte) ((int) (value >>> 56));
        target[idx + 1] = (byte) ((int) (value >>> 48));
        target[idx + 2] = (byte) ((int) (value >>> 40));
        target[idx + 3] = (byte) ((int) (value >>> 32));
        target[idx + 4] = (byte) ((int) (value >>> 24));
        target[idx + 5] = (byte) ((int) (value >>> 16));
        target[idx + 6] = (byte) ((int) (value >>> 8));
        target[idx + 7] = (byte) ((int) value);
    }

    public static void float8(byte[] target, int idx, double value) {
        int8(target, idx, Double.doubleToRawLongBits(value));
    }

    public static double float8(byte[] bytes, int idx) {
        return Double.longBitsToDouble(int8(bytes, idx));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof GeoPoint)) {
            return false;
        }
        GeoPoint p = (GeoPoint) obj;
        return this.x == p.x && this.y == p.y;
    }

    @Override
    public int hashCode() {
        long v1 = Double.doubleToLongBits(this.x);
        long v2 = Double.doubleToLongBits(this.y);
        return (int) (v1 ^ v2 ^ v1 >>> 32 ^ v2 >>> 32);
    }

    @Override
    public String toString() {
        return "GeoPoint{" +
               "x=" + x +
               ", y=" + y +
               '}';
    }
}
