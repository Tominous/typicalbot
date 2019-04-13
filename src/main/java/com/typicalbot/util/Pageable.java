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

package com.typicalbot.util;

import java.util.List;

public class Pageable<T> {
    private List<T> list;

    // current page
    private int page;

    // start/end page
    private int pageStart;
    private int pageEnd;

    // max page
    private int pageMax;

    public Pageable(List<T> list) {
        this.list = list;
        this.page = 1;
        this.pageMax = 1;

        if (list.size() % 10 == 0) {
            this.pageMax = list.size() / 10;
        } else {
            this.pageMax = (list.size() / 10) + 1;
        }
    }

    public List<T> getList() {
        return this.list;
    }

    public List<T> getListForPage() {
        return this.list.subList(pageStart, pageEnd);
    }

    public int getPage() {
        return this.page;
    }

    public void setPage(int page) {
        if (page >= this.pageMax) {
            this.page = this.pageMax;
        } else if (page <= 1) {
            this.page = 1;
        } else {
            this.page = page;
        }

        this.pageStart = 10 * (page - 1);
        if (this.pageStart < 0) {
            this.pageStart = 0;
        }

        this.pageEnd = this.pageStart + 10;
        if (this.pageEnd > this.list.size()) {
            this.pageEnd = this.list.size();
        }
    }

    public int getMaxPages() {
        return this.pageMax;
    }

    public int getPreviousPage() {
        if (this.page > 1) {
            return this.page - 1;
        } else {
            return 0;
        }
    }

    public int getNextPage() {
        if (this.page < this.pageMax) {
            return this.page + 1;
        } else {
            return 0;
        }
    }
}
