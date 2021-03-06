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
package org.scribe.up.provider.impl;

import java.util.Map;

import org.codehaus.jackson.JsonNode;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.DropBoxApi;
import org.scribe.model.Token;
import org.scribe.up.credential.OAuthCredential;
import org.scribe.up.profile.AttributesDefinitions;
import org.scribe.up.profile.JsonHelper;
import org.scribe.up.profile.UserProfile;
import org.scribe.up.profile.dropbox.DropBoxProfile;
import org.scribe.up.provider.BaseOAuth10Provider;
import org.scribe.up.session.UserSession;

/**
 * This class is the OAuth provider to authenticate user in DropBox. Scope is not used.<br />
 * Attributes (Java type) available in {@link org.scribe.up.profile.dropbox.DropBoxProfile} : referral_link (String), display_name (String),
 * country (Locale), shared (Integer), quota (Integer) and normal (Integer).<br />
 * More information at https://www.dropbox.com/developers/reference/api#account-info
 * 
 * @author Jerome Leleu
 * @since 1.2.0
 */
public class DropBoxProvider extends BaseOAuth10Provider {
    
    protected DropBoxProvider newProvider() {
        return new DropBoxProvider();
    }
    
    @Override
    protected void internalInit() {
        service = new ServiceBuilder().provider(DropBoxApi.class).apiKey(key).apiSecret(secret).callback(callbackUrl)
            .build();
    }
    
    @Override
    protected String getProfileUrl() {
        return "https://api.dropbox.com/1/account/info";
    }
    
    @Override
    public OAuthCredential extractCredentialFromParameters(UserSession session, Map<String, String[]> parameters) {
        // get tokenRequest from user session
        Token tokenRequest = (Token) session.getAttribute(getRequestTokenSessionAttributeName());
        logger.debug("tokenRequest : {}", tokenRequest);
        // don't get parameters from url
        // token and verifier are equals and extracted from saved request token
        String token = tokenRequest.getToken();
        logger.debug("token = verifier : {}", token);
        return new OAuthCredential(tokenRequest, token, token, getType());
    }
    
    @Override
    protected UserProfile extractUserProfile(String body) {
        DropBoxProfile profile = new DropBoxProfile();
        JsonNode json = JsonHelper.getFirstNode(body);
        if (json != null) {
            profile.setId(JsonHelper.get(json, "uid"));
            for (String attribute : AttributesDefinitions.dropBoxDefinition.getPrincipalAttributes()) {
                profile.addAttribute(attribute, JsonHelper.get(json, attribute));
            }
            json = (JsonNode) JsonHelper.get(json, "quota_info");
            if (json != null) {
                for (String attribute : AttributesDefinitions.dropBoxDefinition.getOtherAttributes()) {
                    profile.addAttribute(attribute, JsonHelper.get(json, attribute));
                }
            }
        }
        return profile;
    }
}
