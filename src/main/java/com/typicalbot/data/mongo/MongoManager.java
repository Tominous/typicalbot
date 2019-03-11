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
package com.typicalbot.data.mongo;

import com.mongodb.MongoClient;
import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.DefaultCreator;

public class MongoManager {
    private MongoClient client;
    private Morphia morphia;
    private Datastore datastore;

    public MongoManager() {
        this.client = new MongoClient("localhost", 27017);

        this.morphia = new Morphia();

        // Set options
        this.morphia.getMapper().getOptions().setObjectFactory(new DefaultCreator() {
            @Override
            protected ClassLoader getClassLoaderForClass() {
                return MongoManager.class.getClassLoader();
            }
        });

        this.morphia.getMapper().getOptions().setMapSubPackages(true);
        this.morphia.getMapper().getOptions().setStoreNulls(true); // might need to allow empty too, not sure yet

        this.morphia.mapPackage("com.typicalbot.data.mongo.objects");

        this.datastore = this.morphia.createDatastore(this.client, "typicalbot");
        this.datastore.ensureIndexes();
    }

    public Morphia getMorphia() {
        return this.morphia;
    }

    public Datastore getDatastore() {
        return this.datastore;
    }

    public MongoClient getClient() {
        return this.client;
    }
}
