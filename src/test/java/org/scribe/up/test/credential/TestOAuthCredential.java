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
package org.scribe.up.test.credential;

import junit.framework.TestCase;

import org.scribe.model.Token;
import org.scribe.up.credential.OAuthCredential;
import org.scribe.up.test.util.CommonHelper;

/**
 * This class tests the {@link org.scribe.up.credential.OAuthCredential} class.
 * 
 * @author Jerome Leleu
 * @since 1.0.0
 */
public final class TestOAuthCredential extends TestCase {
    
    private final static String TOKEN = "token";
    
    private final static String VERIFIER = "verifier";
    
    private final static String TYPE = "type";
    
    private final static String SECRET = "secret";
    
    private final static Token REQUEST_TOKEN = new Token(TOKEN, SECRET);
    
    public void testOAuthCredential() {
        OAuthCredential credential = new OAuthCredential(REQUEST_TOKEN, TOKEN, VERIFIER, TYPE);
        assertEquals(TOKEN, credential.getToken());
        assertEquals(VERIFIER, credential.getVerifier());
        assertEquals(TYPE, credential.getProviderType());
        Token requestToken = credential.getRequestToken();
        assertEquals(TOKEN, requestToken.getToken());
        assertEquals(SECRET, requestToken.getSecret());
        // test serialization
        byte[] bytes = CommonHelper.serialize(credential);
        OAuthCredential credential2 = (OAuthCredential) CommonHelper.unserialize(bytes);
        assertEquals(credential.getRequestToken().toString(), credential2.getRequestToken().toString());
        assertEquals(credential.getToken(), credential2.getToken());
        assertEquals(credential.getVerifier(), credential2.getVerifier());
        assertEquals(credential.getProviderType(), credential2.getProviderType());
    }
}
