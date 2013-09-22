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
name|List
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
name|ext
operator|.
name|ExternalAuthProvider
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
specifier|abstract
class|class
name|OAuth2AuthProvider
parameter_list|<
name|U
extends|extends
name|AuthUserIdentity
parameter_list|,
name|I
extends|extends
name|OAuth2AuthInfo
parameter_list|>
extends|extends
name|ExternalAuthProvider
block|{
specifier|public
name|OAuth2AuthProvider
parameter_list|(
specifier|final
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
name|addAll
argument_list|(
name|super
operator|.
name|neededSettingKeys
argument_list|()
argument_list|)
expr_stmt|;
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
name|CLIENT_ID
argument_list|)
expr_stmt|;
name|ret
operator|.
name|add
argument_list|(
name|SettingKeys
operator|.
name|CLIENT_SECRET
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
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
name|CLIENT_ID
init|=
literal|"clientId"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CLIENT_SECRET
init|=
literal|"clientSecret"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SCOPE
init|=
literal|"scope"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ACCESS_TYPE
init|=
literal|"accessType"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|APPROVAL_PROMPT
init|=
literal|"approvalPrompt"
decl_stmt|;
block|}
specifier|public
specifier|static
specifier|abstract
class|class
name|Constants
block|{
specifier|public
specifier|static
specifier|final
name|String
name|CLIENT_ID
init|=
literal|"client_id"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CLIENT_SECRET
init|=
literal|"client_secret"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|REDIRECT_URI
init|=
literal|"redirect_uri"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SCOPE
init|=
literal|"scope"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ACCESS_TYPE
init|=
literal|"access_type"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|APPROVAL_PROMPT
init|=
literal|"approval_prompt"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|RESPONSE_TYPE
init|=
literal|"response_type"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|STATE
init|=
literal|"state"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|GRANT_TYPE
init|=
literal|"grant_type"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|AUTHORIZATION_CODE
init|=
literal|"authorization_code"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ACCESS_TOKEN
init|=
literal|"access_token"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ERROR
init|=
literal|"error"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CODE
init|=
literal|"code"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|TOKEN_TYPE
init|=
literal|"token_type"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EXPIRES_IN
init|=
literal|"expires_in"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|REFRESH_TOKEN
init|=
literal|"refresh_token"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ACCESS_DENIED
init|=
literal|"access_denied"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|REDIRECT_URI_MISMATCH
init|=
literal|"redirect_uri_mismatch"
decl_stmt|;
block|}
specifier|protected
name|String
name|getAccessTokenParams
parameter_list|(
specifier|final
name|Configuration
name|c
parameter_list|,
specifier|final
name|String
name|code
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
name|getParams
argument_list|(
name|request
argument_list|,
name|c
argument_list|)
decl_stmt|;
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|Constants
operator|.
name|CLIENT_SECRET
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|CLIENT_SECRET
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
name|GRANT_TYPE
argument_list|,
name|Constants
operator|.
name|AUTHORIZATION_CODE
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
name|code
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|URLEncodedUtils
operator|.
name|format
argument_list|(
name|params
argument_list|,
literal|"UTF-8"
argument_list|)
return|;
block|}
specifier|protected
name|I
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
name|String
name|params
init|=
name|getAccessTokenParams
argument_list|(
name|c
argument_list|,
name|code
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
literal|"application/x-www-form-urlencoded"
argument_list|)
operator|.
name|post
argument_list|(
name|params
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
specifier|abstract
name|I
name|buildInfo
parameter_list|(
specifier|final
name|Response
name|r
parameter_list|)
throws|throws
name|AccessTokenException
function_decl|;
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
name|getAuthParams
argument_list|(
name|c
argument_list|,
name|request
argument_list|,
name|state
argument_list|)
decl_stmt|;
return|return
name|generateURI
argument_list|(
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|AUTHORIZATION_URL
argument_list|)
argument_list|,
name|params
argument_list|)
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
name|Configuration
name|c
parameter_list|,
specifier|final
name|Request
name|request
parameter_list|,
specifier|final
name|String
name|state
parameter_list|)
throws|throws
name|AuthException
block|{
specifier|final
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|params
init|=
name|getParams
argument_list|(
name|request
argument_list|,
name|c
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|SCOPE
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|Constants
operator|.
name|SCOPE
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|SCOPE
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|Constants
operator|.
name|RESPONSE_TYPE
argument_list|,
name|Constants
operator|.
name|CODE
argument_list|)
argument_list|)
expr_stmt|;
if|if
condition|(
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|ACCESS_TYPE
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|Constants
operator|.
name|ACCESS_TYPE
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|ACCESS_TYPE
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|APPROVAL_PROMPT
argument_list|)
operator|!=
literal|null
condition|)
block|{
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|Constants
operator|.
name|APPROVAL_PROMPT
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|APPROVAL_PROMPT
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|state
operator|!=
literal|null
condition|)
block|{
name|params
operator|.
name|add
argument_list|(
operator|new
name|BasicNameValuePair
argument_list|(
name|Constants
operator|.
name|STATE
argument_list|,
name|state
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|params
return|;
block|}
specifier|protected
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|getParams
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
name|Constants
operator|.
name|CLIENT_ID
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|CLIENT_ID
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
specifier|protected
name|String
name|getRedirectUriKey
parameter_list|()
block|{
return|return
name|Constants
operator|.
name|REDIRECT_URI
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
comment|// Attention: facebook does *not* support state that is non-ASCII - not
comment|// even encoded.
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
name|isCallbackRequest
argument_list|(
name|context
argument_list|)
condition|)
block|{
comment|// second step in auth process
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
name|I
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
comment|// System.out.println(accessToken.getAccessToken());
block|}
else|else
block|{
comment|// no auth, yet
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
specifier|protected
name|boolean
name|isCallbackRequest
parameter_list|(
specifier|final
name|Context
name|context
parameter_list|)
block|{
return|return
name|context
operator|.
name|request
argument_list|()
operator|.
name|queryString
argument_list|()
operator|.
name|containsKey
argument_list|(
name|Constants
operator|.
name|CODE
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getErrorParameterKey
parameter_list|()
block|{
return|return
name|Constants
operator|.
name|ERROR
return|;
block|}
comment|/** 	 * This allows custom implementations to enrich an AuthUser object or 	 * provide their own implementaion 	 *  	 * @param info 	 * @param state 	 * @return 	 * @throws AuthException 	 */
specifier|protected
specifier|abstract
name|AuthUserIdentity
name|transform
parameter_list|(
specifier|final
name|I
name|info
parameter_list|,
specifier|final
name|String
name|state
parameter_list|)
throws|throws
name|AuthException
function_decl|;
block|}
end_class

end_unit

