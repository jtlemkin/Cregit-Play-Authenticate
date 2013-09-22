begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|providers
operator|.
name|oauth2
operator|.
name|pocket
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|NameValuePair
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|methods
operator|.
name|HttpPost
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|client
operator|.
name|utils
operator|.
name|URLEncodedUtils
import|;
end_import

begin_import
import|import
name|org
operator|.
name|apache
operator|.
name|http
operator|.
name|message
operator|.
name|BasicNameValuePair
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|JsonNode
import|;
end_import

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|map
operator|.
name|ObjectMapper
import|;
end_import

begin_import
import|import
name|play
operator|.
name|Application
import|;
end_import

begin_import
import|import
name|play
operator|.
name|Configuration
import|;
end_import

begin_import
import|import
name|play
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|WS
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|WS
operator|.
name|Response
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Http
operator|.
name|Context
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Http
operator|.
name|Request
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|PlayAuthenticate
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|controllers
operator|.
name|Authenticate
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|exceptions
operator|.
name|AccessDeniedException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|exceptions
operator|.
name|AccessTokenException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|exceptions
operator|.
name|AuthException
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|exceptions
operator|.
name|RedirectUriMismatch
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|providers
operator|.
name|oauth2
operator|.
name|OAuth2AuthProvider
import|;
end_import

begin_import
import|import
name|com
operator|.
name|feth
operator|.
name|play
operator|.
name|module
operator|.
name|pa
operator|.
name|user
operator|.
name|AuthUserIdentity
import|;
end_import

begin_class
specifier|public
class|class
name|PocketAuthProvider
extends|extends
name|OAuth2AuthProvider
argument_list|<
name|PocketAuthUser
argument_list|,
name|PocketAuthInfo
argument_list|>
block|{
specifier|static
specifier|final
name|String
name|PROVIDER_KEY
init|=
literal|"pocket"
decl_stmt|;
name|String
name|requestToken
decl_stmt|;
specifier|public
name|PocketAuthProvider
parameter_list|(
name|Application
name|app
parameter_list|)
block|{
name|super
argument_list|(
name|app
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|abstract
class|class
name|SettingKeys
block|{
specifier|public
specifier|static
specifier|final
name|String
name|AUTHORIZATION_URL
init|=
literal|"authorizationUrl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ACCESS_TOKEN_URL
init|=
literal|"accessTokenUrl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|REQUEST_TOKEN_URL
init|=
literal|"requestTokenUrl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CONSUMER_KEY
init|=
literal|"consumer_key"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|REQUEST_TOKEN
init|=
literal|"request_token"
decl_stmt|;
block|}
specifier|public
specifier|static
specifier|abstract
class|class
name|PocketConstants
extends|extends
name|Constants
block|{
specifier|public
specifier|static
specifier|final
name|String
name|CONSUMER_KEY
init|=
literal|"consumer_key"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|REQUEST_TOKEN
init|=
literal|"request_token"
decl_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|neededSettingKeys
parameter_list|()
block|{
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|ret
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|SettingKeys
operator|.
name|ACCESS_TOKEN_URL
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|SettingKeys
operator|.
name|AUTHORIZATION_URL
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|SettingKeys
operator|.
name|REQUEST_TOKEN_URL
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|SettingKeys
operator|.
name|CONSUMER_KEY
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|PROVIDER_KEY
return|;
block|}
annotation|@
name|Override
specifier|protected
name|PocketAuthInfo
name|buildInfo
parameter_list|(
name|Response
name|r
parameter_list|)
throws|throws
name|AccessTokenException
block|{
if|if
condition|(
name|r
operator|.
name|getStatus
argument_list|()
operator|>=
literal|400
condition|)
block|{
throw|throw
operator|new
name|AccessTokenException
argument_list|(
name|r
operator|.
name|asJson
argument_list|()
operator|.
name|asText
argument_list|()
argument_list|)
throw|;
block|}
else|else
block|{
name|JsonNode
name|response
init|=
name|r
operator|.
name|asJson
argument_list|()
decl_stmt|;
name|String
name|accessToken
init|=
name|response
operator|.
name|get
argument_list|(
literal|"access_token"
argument_list|)
operator|.
name|asText
argument_list|()
decl_stmt|;
name|String
name|userName
init|=
name|response
operator|.
name|get
argument_list|(
literal|"username"
argument_list|)
operator|.
name|asText
argument_list|()
decl_stmt|;
name|PocketAuthInfo
name|info
init|=
operator|new
name|PocketAuthInfo
argument_list|(
name|accessToken
argument_list|,
name|requestToken
argument_list|,
name|userName
argument_list|)
decl_stmt|;
return|return
name|info
return|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|AuthUserIdentity
name|transform
parameter_list|(
name|PocketAuthInfo
name|info
parameter_list|,
name|String
name|state
parameter_list|)
throws|throws
name|AuthException
block|{
return|return
operator|new
name|PocketAuthUser
argument_list|(
name|info
argument_list|,
name|state
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|authenticate
parameter_list|(
specifier|final
name|Context
name|context
parameter_list|,
specifier|final
name|Object
name|payload
parameter_list|)
throws|throws
name|AuthException
block|{
specifier|final
name|Request
name|request
init|=
name|context
operator|.
name|request
argument_list|()
decl_stmt|;
if|if
condition|(
name|Logger
operator|.
name|isDebugEnabled
argument_list|()
condition|)
block|{
name|Logger
operator|.
name|debug
argument_list|(
literal|"Returned with URL: '"
operator|+
name|request
operator|.
name|uri
argument_list|()
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
specifier|final
name|String
name|error
init|=
name|Authenticate
operator|.
name|getQueryString
argument_list|(
name|request
argument_list|,
name|getErrorParameterKey
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|code
init|=
name|Authenticate
operator|.
name|getQueryString
argument_list|(
name|request
argument_list|,
name|Constants
operator|.
name|CODE
argument_list|)
decl_stmt|;
specifier|final
name|String
name|state
init|=
name|Authenticate
operator|.
name|getQueryString
argument_list|(
name|request
argument_list|,
name|Constants
operator|.
name|STATE
argument_list|)
decl_stmt|;
if|if
condition|(
name|error
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|error
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|ACCESS_DENIED
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|AccessDeniedException
argument_list|(
name|getKey
argument_list|()
argument_list|)
throw|;
block|}
elseif|else
if|if
condition|(
name|error
operator|.
name|equals
argument_list|(
name|Constants
operator|.
name|REDIRECT_URI_MISMATCH
argument_list|)
condition|)
block|{
name|Logger
operator|.
name|error
argument_list|(
literal|"You must set the redirect URI for your provider to whatever you defined in your routes file."
operator|+
literal|"For this provider it is: '"
operator|+
name|getRedirectUrl
argument_list|(
name|request
argument_list|)
operator|+
literal|"'"
argument_list|)
expr_stmt|;
throw|throw
operator|new
name|RedirectUriMismatch
argument_list|()
throw|;
block|}
else|else
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
name|error
argument_list|)
throw|;
block|}
block|}
elseif|else
if|if
condition|(
name|requestToken
operator|!=
literal|null
condition|)
block|{
comment|// second step in auth process
specifier|final
name|PocketAuthInfo
name|info
init|=
name|getAccessToken
argument_list|(
name|code
argument_list|,
name|request
argument_list|)
decl_stmt|;
specifier|final
name|AuthUserIdentity
name|u
init|=
name|transform
argument_list|(
name|info
argument_list|,
name|state
argument_list|)
decl_stmt|;
return|return
name|u
return|;
block|}
else|else
block|{
comment|// no auth, yet
name|requestToken
operator|=
name|getRequestToken
argument_list|(
name|request
argument_list|)
expr_stmt|;
specifier|final
name|String
name|url
init|=
name|getAuthUrl
argument_list|(
name|request
argument_list|,
name|state
argument_list|)
decl_stmt|;
name|Logger
operator|.
name|debug
argument_list|(
literal|"generated redirect URL for dialog: "
operator|+
name|url
argument_list|)
expr_stmt|;
return|return
name|url
return|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|PocketAuthInfo
name|getAccessToken
parameter_list|(
specifier|final
name|String
name|code
parameter_list|,
specifier|final
name|Request
name|request
parameter_list|)
throws|throws
name|AccessTokenException
block|{
specifier|final
name|Configuration
name|c
init|=
name|getConfiguration
argument_list|()
decl_stmt|;
specifier|final
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|params
init|=
name|getAccessTokenParams
argument_list|(
name|c
argument_list|,
name|request
argument_list|)
decl_stmt|;
specifier|final
name|String
name|url
init|=
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|ACCESS_TOKEN_URL
argument_list|)
decl_stmt|;
specifier|final
name|Response
name|r
init|=
name|WS
operator|.
name|url
argument_list|(
name|url
argument_list|)
operator|.
name|setHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|setHeader
argument_list|(
literal|"X-Accept"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|post
argument_list|(
name|encodeParamsAsJson
argument_list|(
name|params
argument_list|)
argument_list|)
operator|.
name|get
argument_list|(
name|PlayAuthenticate
operator|.
name|TIMEOUT
argument_list|)
decl_stmt|;
return|return
name|buildInfo
argument_list|(
name|r
argument_list|)
return|;
block|}
specifier|protected
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|getAccessTokenParams
parameter_list|(
specifier|final
name|Configuration
name|c
parameter_list|,
name|Request
name|request
parameter_list|)
block|{
specifier|final
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|params
init|=
operator|new
name|ArrayList
argument_list|<
name|NameValuePair
argument_list|>
argument_list|()
decl_stmt|;
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|PocketConstants
operator|.
name|CONSUMER_KEY
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|CONSUMER_KEY
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|Constants
operator|.
name|CODE
argument_list|,
name|requestToken
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|params
return|;
block|}
specifier|protected
name|String
name|getRequestToken
parameter_list|(
specifier|final
name|Request
name|request
parameter_list|)
throws|throws
name|AuthException
block|{
specifier|final
name|Configuration
name|c
init|=
name|getConfiguration
argument_list|()
decl_stmt|;
specifier|final
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|params
init|=
name|getRequestTokenParams
argument_list|(
name|request
argument_list|,
name|c
argument_list|)
decl_stmt|;
specifier|final
name|Response
name|r
init|=
name|WS
operator|.
name|url
argument_list|(
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|REQUEST_TOKEN_URL
argument_list|)
argument_list|)
operator|.
name|setHeader
argument_list|(
literal|"Content-Type"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|setHeader
argument_list|(
literal|"X-Accept"
argument_list|,
literal|"application/json"
argument_list|)
operator|.
name|post
argument_list|(
name|encodeParamsAsJson
argument_list|(
name|params
argument_list|)
argument_list|)
operator|.
name|get
argument_list|(
name|PlayAuthenticate
operator|.
name|TIMEOUT
argument_list|)
decl_stmt|;
if|if
condition|(
name|r
operator|.
name|getStatus
argument_list|()
operator|>=
literal|400
condition|)
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
name|r
operator|.
name|asJson
argument_list|()
operator|.
name|asText
argument_list|()
argument_list|)
throw|;
block|}
else|else
block|{
return|return
name|r
operator|.
name|asJson
argument_list|()
operator|.
name|get
argument_list|(
literal|"code"
argument_list|)
operator|.
name|asText
argument_list|()
return|;
block|}
block|}
specifier|protected
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|getRequestTokenParams
parameter_list|(
specifier|final
name|Request
name|request
parameter_list|,
specifier|final
name|Configuration
name|c
parameter_list|)
block|{
specifier|final
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|params
init|=
operator|new
name|ArrayList
argument_list|<
name|NameValuePair
argument_list|>
argument_list|()
decl_stmt|;
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|PocketConstants
operator|.
name|CONSUMER_KEY
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|CONSUMER_KEY
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|getRedirectUriKey
argument_list|()
argument_list|,
name|getRedirectUrl
argument_list|(
name|request
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|params
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|getAuthUrl
parameter_list|(
specifier|final
name|Request
name|request
parameter_list|,
specifier|final
name|String
name|state
parameter_list|)
block|{
specifier|final
name|Configuration
name|c
init|=
name|getConfiguration
argument_list|()
decl_stmt|;
specifier|final
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|params
init|=
name|getAuthParams
argument_list|(
name|request
argument_list|,
name|c
argument_list|)
decl_stmt|;
specifier|final
name|HttpPost
name|m
init|=
operator|new
name|HttpPost
argument_list|(
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|AUTHORIZATION_URL
argument_list|)
operator|+
literal|"?"
operator|+
name|URLEncodedUtils
operator|.
name|format
argument_list|(
name|params
argument_list|,
literal|"UTF-8"
argument_list|)
argument_list|)
decl_stmt|;
return|return
name|m
operator|.
name|getURI
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|protected
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|getAuthParams
parameter_list|(
specifier|final
name|Request
name|request
parameter_list|,
specifier|final
name|Configuration
name|c
parameter_list|)
block|{
specifier|final
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|params
init|=
operator|new
name|ArrayList
argument_list|<
name|NameValuePair
argument_list|>
argument_list|()
decl_stmt|;
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|PocketConstants
operator|.
name|REQUEST_TOKEN
argument_list|,
name|requestToken
argument_list|)
argument_list|)
expr_stmt|;
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|getRedirectUriKey
argument_list|()
argument_list|,
name|getRedirectUrl
argument_list|(
name|request
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|params
return|;
block|}
specifier|private
name|JsonNode
name|encodeParamsAsJson
parameter_list|(
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|params
parameter_list|)
block|{
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|map
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
for|for
control|(
name|NameValuePair
name|nameValuePair
range|:
name|params
control|)
block|{
name|map
operator|.
name|put
argument_list|(
name|nameValuePair
operator|.
name|getName
argument_list|()
argument_list|,
name|nameValuePair
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
return|return
operator|new
name|ObjectMapper
argument_list|()
operator|.
name|valueToTree
argument_list|(
name|map
argument_list|)
return|;
block|}
block|}
end_class

end_unit
