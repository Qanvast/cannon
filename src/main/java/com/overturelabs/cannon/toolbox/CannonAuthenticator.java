package com.overturelabs.cannon.toolbox;

public interface CannonAuthenticator {
    enum AuthTokenType {
        BSDAUTH, CDSA, GSSAPI, JAAS, NMAS, OAUTH, OAUTH2, OID, OIDC, PAM, SASL, SSPI, XUDA
    }
    
    // public String getAuthToken();
    
    void setAuthToken(String authToken);
    
    // public AuthTokenType getAuthTokenType();
    
    void setAuthTokenType(AuthTokenType authTokenType);
    
    // public long getAuthTokenExpiry();
    
    void setAuthTokenExpiry(long authTokenExpiry);
    
    // public void invalidateAuthToken();
}