package com.overturelabs.cannon.toolbox;

public interface CannonAuthenticator {
    public enum AuthTokenType {
        BSDAUTH, CDSA, GSSAPI, JAAS, NMAS, OAUTH, OAUTH2, OID, OIDC, PAM, SASL, SSPI, XUDA
    }
    
    // public String getAuthToken();
    
    public void setAuthToken(String authToken);
    
    // public AuthTokenType getAuthTokenType();
    
    public void setAuthTokenType(AuthTokenType authTokenType);
    
    // public long getAuthTokenExpiry();
    
    public void setAuthTokenExpiry(long authTokenExpiry);
    
    // public void invalidateAuthToken();
}