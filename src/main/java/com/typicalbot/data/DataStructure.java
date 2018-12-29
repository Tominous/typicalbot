/**
 * Copyright 2016-2018 Bryan Pikaard & Nicholas Sylke
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.data;

public class DataStructure<E> implements DataStructureInterface<E> {
    private static final double DEFAULT_RESIZE_RATIO = 0.75;

    private Object[] array;
    private int length;
    private double resizeRatio = DEFAULT_RESIZE_RATIO;

    public DataStructure() {
        this(10);
    }

    public DataStructure(int size) {
        if (size < 1) {
            throw new IllegalArgumentException();
        }

        this.array = new Object[size];
    }

    public void setResizeRatio(double ratio) {
        if (ratio > 1) {
            throw new IllegalArgumentException();
        } else if (ratio < 0) {
            throw new IllegalArgumentException();
        }

        this.resizeRatio = ratio;
    }

    @Override
    public E read(int index) {
        return (E) array[index];
    }

    @Override
    public int indexOf(Object item) {
        Object[] array = this.array;
        for (int i = 0; i < length; i++) {
            Object o = array[i];
            if (item.equals(o)) {
                return i;
            }
        }

        return -1;
    }

    @Override
    public boolean has(Object item) {
        return indexOf(item) != -1;
    }

    @Override
    public void insert(E item) {
        resize();
        array[length] = item;
        length++;
    }

    @Override
    public boolean delete(Object item) {
        Object[] array = this.array;

        for (int i = 0; i < length; i++) {
            Object o = array[i];

            if (item.equals(o)) {
                int shift = length - i - 1;
                System.arraycopy(array, i + 1, array, i, shift);
                length--;
                array[length] = null;
            }
        }

        return false;
    }

    @Override
    public void iterate(DataStructureVisitor<E> visitor) {
        int start = visitor.from();
        int end = visitor.endOr(this.length - 1);

        for (int i = start; i <= end; i++) {
            E item = (E) array[i];
            visitor.accept(item);
        }
    }

    @Override
    public int length() {
        return this.length;
    }

    @Override
    public void purge() {
        array = new Object[16];
        length = 0;
    }

    private void resize() {
        int arrayLength = array.length;
        double ratio = this.length / arrayLength;
        Object[] array = this.array;

        if (ratio >= resizeRatio) {
            Object[] objects = new Object[arrayLength << 1];
            System.arraycopy(array, 0, objects, 0, arrayLength);
            this.array = objects;
        }
    }
}
