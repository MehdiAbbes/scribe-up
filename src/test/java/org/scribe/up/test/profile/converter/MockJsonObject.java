/*
  Copyright 2012 Jerome Leleu

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
 */
package org.scribe.up.test.profile.converter;

import org.codehaus.jackson.JsonNode;
import org.scribe.up.profile.JsonObject;

/**
 * This class is a mock for JsonObject.
 * 
 * @author Jerome Leleu
 * @since 1.1.0
 */
public final class MockJsonObject extends JsonObject {
    
    private static final long serialVersionUID = 2551743059012070843L;
    
    private String value;
    
    public MockJsonObject(Object json) {
        super(json);
    }
    
    @Override
    protected void buildFromJson(JsonNode json) {
        value = json.getTextValue();
    }
    
    public String getValue() {
        return value;
    }
}
