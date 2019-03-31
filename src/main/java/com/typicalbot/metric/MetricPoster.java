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

package com.typicalbot.metric;

import com.typicalbot.shard.ShardManager;
import com.typicalbot.util.SentryUtil;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class MetricPoster implements Runnable {
    private static final Logger LOGGER = LoggerFactory.getLogger(MetricPoster.class);

    private final String PAGE_ID = "";
    private final String METRIC_ID = "";
    private final String AUTHORIZATION = "";

    private ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
    private OkHttpClient client = new OkHttpClient();

    public synchronized void start() {
        if (!AUTHORIZATION.isEmpty()) {
            this.executorService.scheduleAtFixedRate(this, 0L, 30L, TimeUnit.SECONDS);
        }
    }

    @Override
    public void run() {
        RequestBody body = new MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("data[value]", String.valueOf(ShardManager.getAveragePing()))
            .addFormDataPart("data[timestamp]", String.valueOf(System.currentTimeMillis() / 1000L))
            .build();

        Request request = new Request.Builder()
            .url(String.format("https://api.statuspage.io/v1/pages/%s/metrics/%s/data.json", PAGE_ID, METRIC_ID))
            .addHeader("Authorization", AUTHORIZATION)
            .post(body)
            .build();

        try {
            Response response = client.newCall(request).execute();

            if (response.code() != 201) {
                LOGGER.warn("Failed to post metrics to StatusPage.");
            }

            response.close();

            LOGGER.debug("Successfully posted metrics to StatusPage.");
        } catch (IOException ex) {
            SentryUtil.capture(ex, MetricPoster.class);
        }
    }
}
