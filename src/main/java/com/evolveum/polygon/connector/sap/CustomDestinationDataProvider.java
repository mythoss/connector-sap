/*
 * Copyright (c) 2010-2016 Evolveum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.evolveum.polygon.connector.sap;

import com.sap.conn.jco.ext.DestinationDataEventListener;
import com.sap.conn.jco.ext.DestinationDataProvider;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by gpalos on 19. 1. 2016.
 *
 * The custom destination data provider implements DestinationDataProvider and
 * provides an implementation for at least getDestinationProperties(String).
 */
class CustomDestinationDataProvider implements DestinationDataProvider {


    private static final CustomDestinationDataProvider INSTANCE = new CustomDestinationDataProvider();
    private DestinationDataEventListener eL;

    private CustomDestinationDataProvider() {
    }

    public static CustomDestinationDataProvider getInstance() {
        /*
        try {
            com.sap.conn.jco.ext.Environment.registerDestinationDataProvider(INSTANCE);
        } catch (IllegalStateException providerAlreadyRegisteredException) {
            //somebody else registered its implementation,
        }*/
        return INSTANCE;
    }

    private Map<String, Properties> propertiesMap = new HashMap<String, Properties>();

    public Properties getDestinationProperties(String destinationName) {
        return propertiesMap.get(destinationName);
    }

    public void setDestinationProperties(String destinationName, Properties properties) {
        propertiesMap.put(destinationName, properties);
        if(this.eL != null)
           this.eL.updated(destinationName);
    }

    public void setDestinationDataEventListener(DestinationDataEventListener eventListener) {
        this.eL = eventListener;
    }

    public boolean supportsEvents() {
        return true;
    }

}