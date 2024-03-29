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
name|oauth1
package|;
end_package

begin_import
import|import
name|com
operator|.
name|fasterxml
operator|.
name|jackson
operator|.
name|databind
operator|.
name|JsonNode
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

begin_import
import|import
name|com
operator|.
name|typesafe
operator|.
name|config
operator|.
name|Config
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
name|inject
operator|.
name|ApplicationLifecycle
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|oauth
operator|.
name|OAuth
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|oauth
operator|.
name|OAuth
operator|.
name|ConsumerKey
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|oauth
operator|.
name|OAuth
operator|.
name|OAuthCalculator
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|oauth
operator|.
name|OAuth
operator|.
name|RequestToken
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|oauth
operator|.
name|OAuth
operator|.
name|ServiceInfo
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|ws
operator|.
name|WSClient
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|ws
operator|.
name|WSResponse
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
name|java
operator|.
name|io
operator|.
name|Serializable
import|;
end_import

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
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|ExecutionException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|Future
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|TimeoutException
import|;
end_import

begin_import
import|import static
name|java
operator|.
name|util
operator|.
name|concurrent
operator|.
name|TimeUnit
operator|.
name|MILLISECONDS
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|OAuth1AuthProvider
parameter_list|<
name|U
extends|extends
name|AuthUserIdentity
parameter_list|,
name|I
extends|extends
name|OAuth1AuthInfo
parameter_list|>
extends|extends
name|ExternalAuthProvider
block|{
specifier|private
specifier|static
specifier|final
name|String
name|CACHE_TOKEN
init|=
literal|"pa.oauth1.rtoken"
decl_stmt|;
specifier|private
specifier|final
name|WSClient
name|wsClient
decl_stmt|;
specifier|public
name|OAuth1AuthProvider
parameter_list|(
specifier|final
name|PlayAuthenticate
name|auth
parameter_list|,
specifier|final
name|ApplicationLifecycle
name|lifecycle
parameter_list|,
specifier|final
name|WSClient
name|wsClient
parameter_list|)
block|{
name|super
argument_list|(
name|auth
argument_list|,
name|lifecycle
argument_list|)
expr_stmt|;
name|this
operator|.
name|wsClient
operator|=
name|wsClient
expr_stmt|;
block|}
specifier|protected
specifier|abstract
name|I
name|buildInfo
parameter_list|(
specifier|final
name|RequestToken
name|rtoken
parameter_list|)
throws|throws
name|AccessTokenException
function_decl|;
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
name|ret
operator|.
name|add
argument_list|(
name|SettingKeys
operator|.
name|CONSUMER_SECRET
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
name|REQUEST_TOKEN_URL
init|=
literal|"requestTokenUrl"
decl_stmt|;
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
name|CONSUMER_KEY
init|=
literal|"consumerKey"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|CONSUMER_SECRET
init|=
literal|"consumerSecret"
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
name|OAUTH_TOKEN_SECRET
init|=
literal|"oauth_token_secret"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|OAUTH_TOKEN
init|=
literal|"oauth_token"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|OAUTH_VERIFIER
init|=
literal|"oauth_verifier"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|OAUTH_PROBLEM
init|=
literal|"oauth_problem"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|OAUTH_ACCESS_DENIED
init|=
literal|"access_denied"
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|SerializableRequestToken
extends|extends
name|RequestToken
implements|implements
name|Serializable
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|SerializableRequestToken
parameter_list|()
block|{
name|super
argument_list|(
literal|null
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|SerializableRequestToken
parameter_list|(
name|RequestToken
name|source
parameter_list|)
block|{
name|super
argument_list|(
name|source
operator|.
name|token
argument_list|,
name|source
operator|.
name|secret
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|void
name|checkError
parameter_list|(
name|Request
name|request
parameter_list|)
throws|throws
name|AuthException
block|{
specifier|final
name|String
name|error
init|=
name|request
operator|.
name|getQueryString
argument_list|(
name|Constants
operator|.
name|OAUTH_PROBLEM
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
name|OAUTH_ACCESS_DENIED
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
specifier|final
name|String
name|uri
init|=
name|request
operator|.
name|uri
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
name|uri
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
specifier|final
name|Config
name|c
init|=
name|getConfiguration
argument_list|()
decl_stmt|;
specifier|final
name|ConsumerKey
name|key
init|=
operator|new
name|ConsumerKey
argument_list|(
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|CONSUMER_KEY
argument_list|)
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|CONSUMER_SECRET
argument_list|)
argument_list|)
decl_stmt|;
specifier|final
name|String
name|requestTokenURL
init|=
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|REQUEST_TOKEN_URL
argument_list|)
decl_stmt|;
specifier|final
name|String
name|accessTokenURL
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
name|String
name|authorizationURL
init|=
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|AUTHORIZATION_URL
argument_list|)
decl_stmt|;
specifier|final
name|ServiceInfo
name|info
init|=
operator|new
name|ServiceInfo
argument_list|(
name|requestTokenURL
argument_list|,
name|accessTokenURL
argument_list|,
name|authorizationURL
argument_list|,
name|key
argument_list|)
decl_stmt|;
specifier|final
name|OAuth
name|service
init|=
operator|new
name|OAuth
argument_list|(
name|info
argument_list|,
literal|true
argument_list|)
decl_stmt|;
name|checkError
argument_list|(
name|request
argument_list|)
expr_stmt|;
if|if
condition|(
name|uri
operator|.
name|contains
argument_list|(
name|Constants
operator|.
name|OAUTH_VERIFIER
argument_list|)
condition|)
block|{
specifier|final
name|RequestToken
name|rtoken
init|=
operator|(
name|RequestToken
operator|)
name|this
operator|.
name|auth
operator|.
name|removeFromCache
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|,
name|CACHE_TOKEN
argument_list|)
decl_stmt|;
specifier|final
name|String
name|verifier
init|=
name|request
operator|.
name|getQueryString
argument_list|(
name|Constants
operator|.
name|OAUTH_VERIFIER
argument_list|)
decl_stmt|;
try|try
block|{
specifier|final
name|RequestToken
name|response
init|=
name|service
operator|.
name|retrieveAccessToken
argument_list|(
name|rtoken
argument_list|,
name|verifier
argument_list|)
decl_stmt|;
specifier|final
name|I
name|i
init|=
name|buildInfo
argument_list|(
name|response
argument_list|)
decl_stmt|;
return|return
name|transform
argument_list|(
name|i
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|ex
parameter_list|)
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
name|ex
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
else|else
block|{
specifier|final
name|String
name|callbackURL
init|=
name|getRedirectUrl
argument_list|(
name|request
argument_list|)
decl_stmt|;
try|try
block|{
specifier|final
name|RequestToken
name|response
init|=
name|service
operator|.
name|retrieveRequestToken
argument_list|(
name|callbackURL
argument_list|)
decl_stmt|;
comment|// All good, we have the request token
specifier|final
name|String
name|token
init|=
name|response
operator|.
name|token
decl_stmt|;
specifier|final
name|String
name|redirectUrl
init|=
name|service
operator|.
name|redirectUrl
argument_list|(
name|token
argument_list|)
decl_stmt|;
name|this
operator|.
name|auth
operator|.
name|storeInCache
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|,
name|CACHE_TOKEN
argument_list|,
operator|new
name|SerializableRequestToken
argument_list|(
name|response
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|redirectUrl
return|;
block|}
catch|catch
parameter_list|(
name|RuntimeException
name|ex
parameter_list|)
block|{
comment|// Exception happened
throw|throw
operator|new
name|AuthException
argument_list|(
name|ex
operator|.
name|getLocalizedMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
block|}
specifier|protected
name|JsonNode
name|signedOauthGet
parameter_list|(
specifier|final
name|String
name|url
parameter_list|,
specifier|final
name|OAuthCalculator
name|calculator
parameter_list|)
throws|throws
name|AuthException
block|{
specifier|final
name|Future
argument_list|<
name|WSResponse
argument_list|>
name|oathFuture
init|=
name|wsClient
operator|.
name|url
argument_list|(
name|url
argument_list|)
operator|.
name|sign
argument_list|(
name|calculator
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|toCompletableFuture
argument_list|()
decl_stmt|;
try|try
block|{
specifier|final
name|WSResponse
name|response
init|=
name|oathFuture
operator|.
name|get
argument_list|(
name|getTimeout
argument_list|()
argument_list|,
name|MILLISECONDS
argument_list|)
decl_stmt|;
return|return
name|response
operator|.
name|asJson
argument_list|()
return|;
block|}
catch|catch
parameter_list|(
name|InterruptedException
decl||
name|ExecutionException
decl||
name|TimeoutException
name|e
parameter_list|)
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|,
name|e
argument_list|)
throw|;
block|}
block|}
specifier|protected
name|OAuthCalculator
name|getOAuthCalculator
parameter_list|(
specifier|final
name|OAuth1AuthInfo
name|info
parameter_list|)
block|{
specifier|final
name|RequestToken
name|token
init|=
operator|new
name|RequestToken
argument_list|(
name|info
operator|.
name|getAccessToken
argument_list|()
argument_list|,
name|info
operator|.
name|getAccessTokenSecret
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|Config
name|c
init|=
name|getConfiguration
argument_list|()
decl_stmt|;
specifier|final
name|ConsumerKey
name|cK
init|=
operator|new
name|ConsumerKey
argument_list|(
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|CONSUMER_KEY
argument_list|)
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|CONSUMER_SECRET
argument_list|)
argument_list|)
decl_stmt|;
return|return
operator|new
name|OAuthCalculator
argument_list|(
name|cK
argument_list|,
name|token
argument_list|)
return|;
block|}
comment|/** 	 * This allows custom implementations to enrich an AuthUser object or 	 * provide their own implementation 	 * 	 * @return 	 * @throws AuthException 	 */
specifier|protected
specifier|abstract
name|U
name|transform
parameter_list|(
specifier|final
name|I
name|identity
parameter_list|)
throws|throws
name|AuthException
function_decl|;
block|}
end_class

end_unit

