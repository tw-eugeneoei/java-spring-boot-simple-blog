/* (C)2022 */
package com.example.simpleblog.dto;

public class JwtAuthResponse {
  private String accessToken;
  private UserDto user;
  //    private String tokenType = AppConstants.TOKEN_TYPE;

  public JwtAuthResponse(String accessToken, UserDto user) {
    this.accessToken = accessToken;
    this.user = user;
  }

  public String getAccessToken() {
    return accessToken;
  }

  //    public String getTokenType() {
  //        return tokenType;
  //    }

  public UserDto getUser() {
    return user;
  }

  public void setAccessToken(String accessToken) {
    this.accessToken = accessToken;
  }

  //    public void setTokenType(String tokenType) {
  //        this.tokenType = tokenType;
  //    }

  public void setUser(UserDto user) {
    this.user = user;
  }
}
