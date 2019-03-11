/*
 * Copyright 2019 Bryan Pikaard, Nicholas Sylke and the TypicalBot contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.typicalbot.data.storage;

// TODO(nsylke): Documentation
public abstract class DataStructureVisitor<E> {
    private int start = 0;
    private int end = -1;

    public DataStructureVisitor<E> from(int start) {
        this.start = start;
        return this;
    }

    public DataStructureVisitor<E> to(int end) {
        this.end = end;
        return this;
    }

    public int from() {
        return this.start;
    }

    public int endOr(int length) {
        if (end < 0) {
            return length;
        } else {
            return this.end;
        }
    }

    public abstract void accept(E item);
}
