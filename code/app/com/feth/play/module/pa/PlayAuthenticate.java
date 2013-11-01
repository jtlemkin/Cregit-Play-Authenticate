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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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
name|Play
import|;
end_import

begin_import
import|import
name|play
operator|.
name|i18n
operator|.
name|Messages
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Call
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Controller
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Http
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
name|Session
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Result
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
name|AuthProvider
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
name|service
operator|.
name|UserService
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
name|AuthUser
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|PlayAuthenticate
block|{
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_PLAY_AUTHENTICATE
init|=
literal|"play-authenticate"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_AFTER_AUTH_FALLBACK
init|=
literal|"afterAuthFallback"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_AFTER_LOGOUT_FALLBACK
init|=
literal|"afterLogoutFallback"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_ACCOUNT_MERGE_ENABLED
init|=
literal|"accountMergeEnabled"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_ACCOUNT_AUTO_LINK
init|=
literal|"accountAutoLink"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_ACCOUNT_AUTO_MERGE
init|=
literal|"accountAutoMerge"
decl_stmt|;
specifier|public
specifier|abstract
specifier|static
class|class
name|Resolver
block|{
comment|/** 		 * This is the route to your login page 		 * 		 * @return 		 */
specifier|public
specifier|abstract
name|Call
name|login
parameter_list|()
function_decl|;
comment|/** 		 * Route to redirect to after authentication has been finished. 		 * Only used if no original URL was stored. 		 * If you return null here, the user will get redirected to the URL of 		 * the setting 		 * afterAuthFallback 		 * You can use this to redirect to an external URL for example. 		 * 		 * @return 		 */
specifier|public
specifier|abstract
name|Call
name|afterAuth
parameter_list|()
function_decl|;
comment|/** 		 * This should usually point to the route where you registered 		 * com.feth.play.module.pa.controllers.AuthenticateController. 		 * authenticate(String) 		 * however you might provide your own authentication implementation if 		 * you want to 		 * and point it there 		 * 		 * @param provider 		 *            The provider ID matching one of your registered providers 		 *            in play.plugins 		 * 		 * @return a Call to follow 		 */
specifier|public
specifier|abstract
name|Call
name|auth
parameter_list|(
specifier|final
name|String
name|provider
parameter_list|)
function_decl|;
comment|/** 		 * If you set the accountAutoMerge setting to true, you might return 		 * null for this. 		 * 		 * @return 		 */
specifier|public
specifier|abstract
name|Call
name|askMerge
parameter_list|()
function_decl|;
comment|/** 		 * If you set the accountAutoLink setting to true, you might return null 		 * for this 		 * 		 * @return 		 */
specifier|public
specifier|abstract
name|Call
name|askLink
parameter_list|()
function_decl|;
comment|/** 		 * Route to redirect to after logout has been finished. 		 * If you return null here, the user will get redirected to the URL of 		 * the setting 		 * afterLogoutFallback 		 * You can use this to redirect to an external URL for example. 		 * 		 * @return 		 */
specifier|public
specifier|abstract
name|Call
name|afterLogout
parameter_list|()
function_decl|;
specifier|public
name|Call
name|onException
parameter_list|(
specifier|final
name|AuthException
name|e
parameter_list|)
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|private
specifier|static
name|Resolver
name|resolver
decl_stmt|;
specifier|public
specifier|static
name|void
name|setResolver
parameter_list|(
name|Resolver
name|res
parameter_list|)
block|{
name|resolver
operator|=
name|res
expr_stmt|;
block|}
specifier|public
specifier|static
name|Resolver
name|getResolver
parameter_list|()
block|{
return|return
name|resolver
return|;
block|}
specifier|private
specifier|static
name|UserService
name|userService
decl_stmt|;
specifier|public
specifier|static
name|void
name|setUserService
parameter_list|(
specifier|final
name|UserService
name|service
parameter_list|)
block|{
name|userService
operator|=
name|service
expr_stmt|;
block|}
specifier|public
specifier|static
name|UserService
name|getUserService
parameter_list|()
block|{
if|if
condition|(
name|userService
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.core.exception.no_user_service"
argument_list|)
argument_list|)
throw|;
block|}
return|return
name|userService
return|;
block|}
specifier|private
specifier|static
specifier|final
name|String
name|ORIGINAL_URL
init|=
literal|"pa.url.orig"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|USER_KEY
init|=
literal|"pa.u.id"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|PROVIDER_KEY
init|=
literal|"pa.p.id"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|EXPIRES_KEY
init|=
literal|"pa.u.exp"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SESSION_ID_KEY
init|=
literal|"pa.s.id"
decl_stmt|;
specifier|public
specifier|static
name|Configuration
name|getConfiguration
parameter_list|()
block|{
return|return
name|Play
operator|.
name|application
argument_list|()
operator|.
name|configuration
argument_list|()
operator|.
name|getConfig
argument_list|(
name|SETTING_KEY_PLAY_AUTHENTICATE
argument_list|)
return|;
block|}
specifier|public
specifier|static
specifier|final
name|Long
name|TIMEOUT
init|=
literal|10l
operator|*
literal|1000
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|MERGE_USER_KEY
init|=
literal|null
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|LINK_USER_KEY
init|=
literal|null
decl_stmt|;
specifier|public
specifier|static
name|String
name|getOriginalUrl
parameter_list|(
specifier|final
name|Http
operator|.
name|Context
name|context
parameter_list|)
block|{
return|return
name|context
operator|.
name|session
argument_list|()
operator|.
name|remove
argument_list|(
name|PlayAuthenticate
operator|.
name|ORIGINAL_URL
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|storeOriginalUrl
parameter_list|(
specifier|final
name|Http
operator|.
name|Context
name|context
parameter_list|)
block|{
name|String
name|loginUrl
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|PlayAuthenticate
operator|.
name|getResolver
argument_list|()
operator|.
name|login
argument_list|()
operator|!=
literal|null
condition|)
block|{
name|loginUrl
operator|=
name|PlayAuthenticate
operator|.
name|getResolver
argument_list|()
operator|.
name|login
argument_list|()
operator|.
name|url
argument_list|()
expr_stmt|;
block|}
else|else
block|{
name|Logger
operator|.
name|warn
argument_list|(
literal|"You should define a login call in the resolver"
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|context
operator|.
name|request
argument_list|()
operator|.
name|method
argument_list|()
operator|.
name|equals
argument_list|(
literal|"GET"
argument_list|)
operator|&&
operator|!
name|context
operator|.
name|request
argument_list|()
operator|.
name|path
argument_list|()
operator|.
name|equals
argument_list|(
name|loginUrl
argument_list|)
condition|)
block|{
name|Logger
operator|.
name|debug
argument_list|(
literal|"Path where we are coming from ("
operator|+
name|context
operator|.
name|request
argument_list|()
operator|.
name|uri
argument_list|()
operator|+
literal|") is different than the login URL ("
operator|+
name|loginUrl
operator|+
literal|")"
argument_list|)
expr_stmt|;
name|context
operator|.
name|session
argument_list|()
operator|.
name|put
argument_list|(
name|PlayAuthenticate
operator|.
name|ORIGINAL_URL
argument_list|,
name|context
operator|.
name|request
argument_list|()
operator|.
name|uri
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|Logger
operator|.
name|debug
argument_list|(
literal|"The path we are coming from is the Login URL - delete jumpback"
argument_list|)
expr_stmt|;
name|context
operator|.
name|session
argument_list|()
operator|.
name|remove
argument_list|(
name|PlayAuthenticate
operator|.
name|ORIGINAL_URL
argument_list|)
expr_stmt|;
block|}
return|return
name|context
operator|.
name|session
argument_list|()
operator|.
name|get
argument_list|(
name|ORIGINAL_URL
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|void
name|storeUser
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|,
specifier|final
name|AuthUser
name|authUser
parameter_list|)
block|{
comment|// User logged in once more - wanna make some updates?
specifier|final
name|AuthUser
name|u
init|=
name|getUserService
argument_list|()
operator|.
name|update
argument_list|(
name|authUser
argument_list|)
decl_stmt|;
name|session
operator|.
name|put
argument_list|(
name|PlayAuthenticate
operator|.
name|USER_KEY
argument_list|,
name|u
operator|.
name|getId
argument_list|()
argument_list|)
expr_stmt|;
name|session
operator|.
name|put
argument_list|(
name|PlayAuthenticate
operator|.
name|PROVIDER_KEY
argument_list|,
name|u
operator|.
name|getProvider
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|u
operator|.
name|expires
argument_list|()
operator|!=
name|AuthUser
operator|.
name|NO_EXPIRATION
condition|)
block|{
name|session
operator|.
name|put
argument_list|(
name|EXPIRES_KEY
argument_list|,
name|Long
operator|.
name|toString
argument_list|(
name|u
operator|.
name|expires
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|session
operator|.
name|remove
argument_list|(
name|EXPIRES_KEY
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|boolean
name|isLoggedIn
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|)
block|{
name|boolean
name|ret
init|=
name|session
operator|.
name|containsKey
argument_list|(
name|USER_KEY
argument_list|)
comment|// user is set
operator|&&
name|session
operator|.
name|containsKey
argument_list|(
name|PROVIDER_KEY
argument_list|)
decl_stmt|;
comment|// provider is set
name|ret
operator|&=
name|AuthProvider
operator|.
name|Registry
operator|.
name|hasProvider
argument_list|(
name|session
operator|.
name|get
argument_list|(
name|PROVIDER_KEY
argument_list|)
argument_list|)
expr_stmt|;
comment|// this
comment|// provider
comment|// is
comment|// active
if|if
condition|(
name|session
operator|.
name|containsKey
argument_list|(
name|EXPIRES_KEY
argument_list|)
condition|)
block|{
comment|// expiration is set
specifier|final
name|long
name|expires
init|=
name|getExpiration
argument_list|(
name|session
argument_list|)
decl_stmt|;
if|if
condition|(
name|expires
operator|!=
name|AuthUser
operator|.
name|NO_EXPIRATION
condition|)
block|{
name|ret
operator|&=
operator|(
operator|new
name|Date
argument_list|()
operator|)
operator|.
name|getTime
argument_list|()
operator|<
name|expires
expr_stmt|;
comment|// and the session
comment|// expires after now
block|}
block|}
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|Result
name|logout
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|)
block|{
name|session
operator|.
name|remove
argument_list|(
name|USER_KEY
argument_list|)
expr_stmt|;
name|session
operator|.
name|remove
argument_list|(
name|PROVIDER_KEY
argument_list|)
expr_stmt|;
name|session
operator|.
name|remove
argument_list|(
name|EXPIRES_KEY
argument_list|)
expr_stmt|;
comment|// shouldn't be in any more, but just in case lets kill it from the
comment|// cookie
name|session
operator|.
name|remove
argument_list|(
name|ORIGINAL_URL
argument_list|)
expr_stmt|;
return|return
name|Controller
operator|.
name|redirect
argument_list|(
name|getUrl
argument_list|(
name|getResolver
argument_list|()
operator|.
name|afterLogout
argument_list|()
argument_list|,
name|SETTING_KEY_AFTER_LOGOUT_FALLBACK
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|String
name|peekOriginalUrl
parameter_list|(
specifier|final
name|Context
name|context
parameter_list|)
block|{
return|return
name|context
operator|.
name|session
argument_list|()
operator|.
name|get
argument_list|(
name|ORIGINAL_URL
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|boolean
name|hasUserService
parameter_list|()
block|{
return|return
name|userService
operator|!=
literal|null
return|;
block|}
specifier|private
specifier|static
name|long
name|getExpiration
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|)
block|{
name|long
name|expires
decl_stmt|;
if|if
condition|(
name|session
operator|.
name|containsKey
argument_list|(
name|EXPIRES_KEY
argument_list|)
condition|)
block|{
try|try
block|{
name|expires
operator|=
name|Long
operator|.
name|parseLong
argument_list|(
name|session
operator|.
name|get
argument_list|(
name|EXPIRES_KEY
argument_list|)
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|NumberFormatException
name|nfe
parameter_list|)
block|{
name|expires
operator|=
name|AuthUser
operator|.
name|NO_EXPIRATION
expr_stmt|;
block|}
block|}
else|else
block|{
name|expires
operator|=
name|AuthUser
operator|.
name|NO_EXPIRATION
expr_stmt|;
block|}
return|return
name|expires
return|;
block|}
specifier|public
specifier|static
name|AuthUser
name|getUser
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|)
block|{
specifier|final
name|String
name|provider
init|=
name|session
operator|.
name|get
argument_list|(
name|PROVIDER_KEY
argument_list|)
decl_stmt|;
specifier|final
name|String
name|id
init|=
name|session
operator|.
name|get
argument_list|(
name|USER_KEY
argument_list|)
decl_stmt|;
specifier|final
name|long
name|expires
init|=
name|getExpiration
argument_list|(
name|session
argument_list|)
decl_stmt|;
if|if
condition|(
name|provider
operator|!=
literal|null
operator|&&
name|id
operator|!=
literal|null
condition|)
block|{
return|return
name|getProvider
argument_list|(
name|provider
argument_list|)
operator|.
name|getSessionAuthUser
argument_list|(
name|id
argument_list|,
name|expires
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
name|AuthUser
name|getUser
parameter_list|(
specifier|final
name|Context
name|context
parameter_list|)
block|{
return|return
name|getUser
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isAccountAutoMerge
parameter_list|()
block|{
return|return
name|getConfiguration
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|SETTING_KEY_ACCOUNT_AUTO_MERGE
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isAccountAutoLink
parameter_list|()
block|{
return|return
name|getConfiguration
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|SETTING_KEY_ACCOUNT_AUTO_LINK
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|boolean
name|isAccountMergeEnabled
parameter_list|()
block|{
return|return
name|getConfiguration
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|SETTING_KEY_ACCOUNT_MERGE_ENABLED
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|String
name|getPlayAuthSessionId
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|)
block|{
comment|// Generate a unique id
name|String
name|uuid
init|=
name|session
operator|.
name|get
argument_list|(
name|SESSION_ID_KEY
argument_list|)
decl_stmt|;
if|if
condition|(
name|uuid
operator|==
literal|null
condition|)
block|{
name|uuid
operator|=
name|java
operator|.
name|util
operator|.
name|UUID
operator|.
name|randomUUID
argument_list|()
operator|.
name|toString
argument_list|()
expr_stmt|;
name|session
operator|.
name|put
argument_list|(
name|SESSION_ID_KEY
argument_list|,
name|uuid
argument_list|)
expr_stmt|;
block|}
return|return
name|uuid
return|;
block|}
specifier|private
specifier|static
name|void
name|storeUserInCache
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|,
specifier|final
name|String
name|key
parameter_list|,
specifier|final
name|AuthUser
name|identity
parameter_list|)
block|{
name|storeInCache
argument_list|(
name|session
argument_list|,
name|key
argument_list|,
name|identity
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|storeInCache
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|,
specifier|final
name|String
name|key
parameter_list|,
specifier|final
name|Object
name|o
parameter_list|)
block|{
name|play
operator|.
name|cache
operator|.
name|Cache
operator|.
name|set
argument_list|(
name|getCacheKey
argument_list|(
name|session
argument_list|,
name|key
argument_list|)
argument_list|,
name|o
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|Object
name|removeFromCache
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|,
specifier|final
name|String
name|key
parameter_list|)
block|{
specifier|final
name|Object
name|o
init|=
name|getFromCache
argument_list|(
name|session
argument_list|,
name|key
argument_list|)
decl_stmt|;
specifier|final
name|String
name|k
init|=
name|getCacheKey
argument_list|(
name|session
argument_list|,
name|key
argument_list|)
decl_stmt|;
name|play
operator|.
name|cache
operator|.
name|Cache
operator|.
name|remove
argument_list|(
name|k
argument_list|)
expr_stmt|;
return|return
name|o
return|;
block|}
specifier|private
specifier|static
name|String
name|getCacheKey
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|,
specifier|final
name|String
name|key
parameter_list|)
block|{
specifier|final
name|String
name|id
init|=
name|getPlayAuthSessionId
argument_list|(
name|session
argument_list|)
decl_stmt|;
return|return
name|id
operator|+
literal|"_"
operator|+
name|key
return|;
block|}
specifier|public
specifier|static
name|Object
name|getFromCache
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|,
specifier|final
name|String
name|key
parameter_list|)
block|{
return|return
name|play
operator|.
name|cache
operator|.
name|Cache
operator|.
name|get
argument_list|(
name|getCacheKey
argument_list|(
name|session
argument_list|,
name|key
argument_list|)
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|AuthUser
name|getUserFromCache
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|,
specifier|final
name|String
name|key
parameter_list|)
block|{
specifier|final
name|Object
name|o
init|=
name|getFromCache
argument_list|(
name|session
argument_list|,
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|!=
literal|null
operator|&&
name|o
operator|instanceof
name|AuthUser
condition|)
block|{
return|return
operator|(
name|AuthUser
operator|)
name|o
return|;
block|}
return|return
literal|null
return|;
block|}
specifier|public
specifier|static
name|void
name|storeMergeUser
parameter_list|(
specifier|final
name|AuthUser
name|identity
parameter_list|,
specifier|final
name|Session
name|session
parameter_list|)
block|{
comment|// TODO the cache is not ideal for this, because it might get cleared
comment|// any time
name|storeUserInCache
argument_list|(
name|session
argument_list|,
name|MERGE_USER_KEY
argument_list|,
name|identity
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|AuthUser
name|getMergeUser
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|)
block|{
return|return
name|getUserFromCache
argument_list|(
name|session
argument_list|,
name|MERGE_USER_KEY
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|void
name|removeMergeUser
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|)
block|{
name|removeFromCache
argument_list|(
name|session
argument_list|,
name|MERGE_USER_KEY
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|void
name|storeLinkUser
parameter_list|(
specifier|final
name|AuthUser
name|identity
parameter_list|,
specifier|final
name|Session
name|session
parameter_list|)
block|{
comment|// TODO the cache is not ideal for this, because it might get cleared
comment|// any time
name|storeUserInCache
argument_list|(
name|session
argument_list|,
name|LINK_USER_KEY
argument_list|,
name|identity
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|AuthUser
name|getLinkUser
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|)
block|{
return|return
name|getUserFromCache
argument_list|(
name|session
argument_list|,
name|LINK_USER_KEY
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|void
name|removeLinkUser
parameter_list|(
specifier|final
name|Session
name|session
parameter_list|)
block|{
name|removeFromCache
argument_list|(
name|session
argument_list|,
name|LINK_USER_KEY
argument_list|)
expr_stmt|;
block|}
specifier|private
specifier|static
name|String
name|getJumpUrl
parameter_list|(
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
specifier|final
name|String
name|originalUrl
init|=
name|getOriginalUrl
argument_list|(
name|ctx
argument_list|)
decl_stmt|;
if|if
condition|(
name|originalUrl
operator|!=
literal|null
condition|)
block|{
return|return
name|originalUrl
return|;
block|}
else|else
block|{
return|return
name|getUrl
argument_list|(
name|getResolver
argument_list|()
operator|.
name|afterAuth
argument_list|()
argument_list|,
name|SETTING_KEY_AFTER_AUTH_FALLBACK
argument_list|)
return|;
block|}
block|}
specifier|private
specifier|static
name|String
name|getUrl
parameter_list|(
specifier|final
name|Call
name|c
parameter_list|,
specifier|final
name|String
name|settingFallback
parameter_list|)
block|{
comment|// this can be null if the user did not correctly define the
comment|// resolver
if|if
condition|(
name|c
operator|!=
literal|null
condition|)
block|{
return|return
name|c
operator|.
name|url
argument_list|()
return|;
block|}
else|else
block|{
comment|// go to root instead, but log this
name|Logger
operator|.
name|warn
argument_list|(
literal|"Resolver did not contain information about where to go - redirecting to /"
argument_list|)
expr_stmt|;
specifier|final
name|String
name|afterAuthFallback
init|=
name|getConfiguration
argument_list|()
operator|.
name|getString
argument_list|(
name|settingFallback
argument_list|)
decl_stmt|;
if|if
condition|(
name|afterAuthFallback
operator|!=
literal|null
operator|&&
operator|!
name|afterAuthFallback
operator|.
name|equals
argument_list|(
literal|""
argument_list|)
condition|)
block|{
return|return
name|afterAuthFallback
return|;
block|}
comment|// Not even the config setting was there or valid...meh
name|Logger
operator|.
name|error
argument_list|(
literal|"Config setting '"
operator|+
name|settingFallback
operator|+
literal|"' was not present!"
argument_list|)
expr_stmt|;
return|return
literal|"/"
return|;
block|}
block|}
specifier|public
specifier|static
name|Result
name|link
parameter_list|(
specifier|final
name|Context
name|context
parameter_list|,
specifier|final
name|boolean
name|link
parameter_list|)
block|{
specifier|final
name|AuthUser
name|linkUser
init|=
name|getLinkUser
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|linkUser
operator|==
literal|null
condition|)
block|{
return|return
name|Controller
operator|.
name|forbidden
argument_list|()
return|;
block|}
specifier|final
name|AuthUser
name|loginUser
decl_stmt|;
if|if
condition|(
name|link
condition|)
block|{
comment|// User accepted link - add account to existing local user
name|loginUser
operator|=
name|getUserService
argument_list|()
operator|.
name|link
argument_list|(
name|getUser
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|)
argument_list|,
name|linkUser
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// User declined link - create new user
try|try
block|{
name|loginUser
operator|=
name|signupUser
argument_list|(
name|linkUser
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|AuthException
name|e
parameter_list|)
block|{
return|return
name|Controller
operator|.
name|internalServerError
argument_list|(
name|e
operator|.
name|getMessage
argument_list|()
argument_list|)
return|;
block|}
block|}
name|removeLinkUser
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|loginAndRedirect
argument_list|(
name|context
argument_list|,
name|loginUser
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Result
name|loginAndRedirect
parameter_list|(
specifier|final
name|Context
name|context
parameter_list|,
specifier|final
name|AuthUser
name|loginUser
parameter_list|)
block|{
name|storeUser
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|,
name|loginUser
argument_list|)
expr_stmt|;
return|return
name|Controller
operator|.
name|redirect
argument_list|(
name|getJumpUrl
argument_list|(
name|context
argument_list|)
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Result
name|merge
parameter_list|(
specifier|final
name|Context
name|context
parameter_list|,
specifier|final
name|boolean
name|merge
parameter_list|)
block|{
specifier|final
name|AuthUser
name|mergeUser
init|=
name|getMergeUser
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|mergeUser
operator|==
literal|null
condition|)
block|{
return|return
name|Controller
operator|.
name|forbidden
argument_list|()
return|;
block|}
specifier|final
name|AuthUser
name|loginUser
decl_stmt|;
if|if
condition|(
name|merge
condition|)
block|{
comment|// User accepted merge, so do it
name|loginUser
operator|=
name|getUserService
argument_list|()
operator|.
name|merge
argument_list|(
name|mergeUser
argument_list|,
name|getUser
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|)
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// User declined merge, so log out the old user, and log out with
comment|// the new one
name|loginUser
operator|=
name|mergeUser
expr_stmt|;
block|}
name|removeMergeUser
argument_list|(
name|context
operator|.
name|session
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|loginAndRedirect
argument_list|(
name|context
argument_list|,
name|loginUser
argument_list|)
return|;
block|}
specifier|private
specifier|static
name|AuthUser
name|signupUser
parameter_list|(
specifier|final
name|AuthUser
name|u
parameter_list|)
throws|throws
name|AuthException
block|{
specifier|final
name|AuthUser
name|loginUser
decl_stmt|;
specifier|final
name|Object
name|id
init|=
name|getUserService
argument_list|()
operator|.
name|save
argument_list|(
name|u
argument_list|)
decl_stmt|;
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.core.exception.signupuser_failed"
argument_list|)
argument_list|)
throw|;
block|}
name|loginUser
operator|=
name|u
expr_stmt|;
return|return
name|loginUser
return|;
block|}
specifier|public
specifier|static
name|Result
name|handleAuthentication
parameter_list|(
specifier|final
name|String
name|provider
parameter_list|,
specifier|final
name|Context
name|context
parameter_list|,
specifier|final
name|Object
name|payload
parameter_list|)
block|{
specifier|final
name|AuthProvider
name|ap
init|=
name|getProvider
argument_list|(
name|provider
argument_list|)
decl_stmt|;
if|if
condition|(
name|ap
operator|==
literal|null
condition|)
block|{
comment|// Provider wasn't found and/or user was fooling with our stuff -
comment|// tell him off:
return|return
name|Controller
operator|.
name|notFound
argument_list|(
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.core.exception.provider_not_found"
argument_list|,
name|provider
argument_list|)
argument_list|)
return|;
block|}
try|try
block|{
specifier|final
name|Object
name|o
init|=
name|ap
operator|.
name|authenticate
argument_list|(
name|context
argument_list|,
name|payload
argument_list|)
decl_stmt|;
if|if
condition|(
name|o
operator|instanceof
name|String
condition|)
block|{
return|return
name|Controller
operator|.
name|redirect
argument_list|(
operator|(
name|String
operator|)
name|o
argument_list|)
return|;
block|}
elseif|else
if|if
condition|(
name|o
operator|instanceof
name|AuthUser
condition|)
block|{
specifier|final
name|AuthUser
name|newUser
init|=
operator|(
name|AuthUser
operator|)
name|o
decl_stmt|;
specifier|final
name|Session
name|session
init|=
name|context
operator|.
name|session
argument_list|()
decl_stmt|;
comment|// We might want to do merging here:
comment|// Adapted from:
comment|// http://stackoverflow.com/questions/6666267/architecture-for-merging-multiple-user-accounts-together
comment|// 1. The account is linked to a local account and no session
comment|// cookie is present --> Login
comment|// 2. The account is linked to a local account and a session
comment|// cookie is present --> Merge
comment|// 3. The account is not linked to a local account and no
comment|// session cookie is present --> Signup
comment|// 4. The account is not linked to a local account and a session
comment|// cookie is present --> Linking Additional account
comment|// get the user with which we are logged in - is null if we
comment|// are
comment|// not logged in (does NOT check expiration)
name|AuthUser
name|oldUser
init|=
name|getUser
argument_list|(
name|session
argument_list|)
decl_stmt|;
comment|// checks if the user is logged in (also checks the expiration!)
name|boolean
name|isLoggedIn
init|=
name|isLoggedIn
argument_list|(
name|session
argument_list|)
decl_stmt|;
name|Object
name|oldIdentity
init|=
literal|null
decl_stmt|;
comment|// check if local user still exists - it might have been
comment|// deactivated/deleted,
comment|// so this is a signup, not a link
if|if
condition|(
name|isLoggedIn
condition|)
block|{
name|oldIdentity
operator|=
name|getUserService
argument_list|()
operator|.
name|getLocalIdentity
argument_list|(
name|oldUser
argument_list|)
expr_stmt|;
name|isLoggedIn
operator|=
name|oldIdentity
operator|!=
literal|null
expr_stmt|;
if|if
condition|(
operator|!
name|isLoggedIn
condition|)
block|{
comment|// if isLoggedIn is false here, then the local user has
comment|// been deleted/deactivated
comment|// so kill the session
name|logout
argument_list|(
name|session
argument_list|)
expr_stmt|;
name|oldUser
operator|=
literal|null
expr_stmt|;
block|}
block|}
specifier|final
name|Object
name|loginIdentity
init|=
name|getUserService
argument_list|()
operator|.
name|getLocalIdentity
argument_list|(
name|newUser
argument_list|)
decl_stmt|;
specifier|final
name|boolean
name|isLinked
init|=
name|loginIdentity
operator|!=
literal|null
decl_stmt|;
specifier|final
name|AuthUser
name|loginUser
decl_stmt|;
if|if
condition|(
name|isLinked
operator|&&
operator|!
name|isLoggedIn
condition|)
block|{
comment|// 1. -> Login
name|loginUser
operator|=
name|newUser
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|isLinked
condition|)
block|{
comment|// 2. -> Merge
comment|// merge the two identities and return the AuthUser we want
comment|// to use for the log in
if|if
condition|(
name|isAccountMergeEnabled
argument_list|()
operator|&&
operator|!
name|loginIdentity
operator|.
name|equals
argument_list|(
name|oldIdentity
argument_list|)
condition|)
block|{
comment|// account merge is enabled
comment|// and
comment|// The currently logged in user and the one to log in
comment|// are not the same, so shall we merge?
if|if
condition|(
name|isAccountAutoMerge
argument_list|()
condition|)
block|{
comment|// Account auto merging is enabled
name|loginUser
operator|=
name|getUserService
argument_list|()
operator|.
name|merge
argument_list|(
name|newUser
argument_list|,
name|oldUser
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Account auto merging is disabled - forward user
comment|// to merge request page
specifier|final
name|Call
name|c
init|=
name|getResolver
argument_list|()
operator|.
name|askMerge
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.core.exception.merge.controller_undefined"
argument_list|,
name|SETTING_KEY_ACCOUNT_AUTO_MERGE
argument_list|)
argument_list|)
throw|;
block|}
name|storeMergeUser
argument_list|(
name|newUser
argument_list|,
name|session
argument_list|)
expr_stmt|;
return|return
name|Controller
operator|.
name|redirect
argument_list|(
name|c
argument_list|)
return|;
block|}
block|}
else|else
block|{
comment|// the currently logged in user and the new login belong
comment|// to the same local user,
comment|// or Account merge is disabled, so just change the log
comment|// in to the new user
name|loginUser
operator|=
name|newUser
expr_stmt|;
block|}
block|}
elseif|else
if|if
condition|(
operator|!
name|isLoggedIn
condition|)
block|{
comment|// 3. -> Signup
name|loginUser
operator|=
name|signupUser
argument_list|(
name|newUser
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// !isLinked&& isLoggedIn:
comment|// 4. -> Link additional
if|if
condition|(
name|isAccountAutoLink
argument_list|()
condition|)
block|{
comment|// Account auto linking is enabled
name|loginUser
operator|=
name|getUserService
argument_list|()
operator|.
name|link
argument_list|(
name|oldUser
argument_list|,
name|newUser
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// Account auto linking is disabled - forward user to
comment|// link suggestion page
specifier|final
name|Call
name|c
init|=
name|getResolver
argument_list|()
operator|.
name|askLink
argument_list|()
decl_stmt|;
if|if
condition|(
name|c
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.core.exception.link.controller_undefined"
argument_list|,
name|SETTING_KEY_ACCOUNT_AUTO_LINK
argument_list|)
argument_list|)
throw|;
block|}
name|storeLinkUser
argument_list|(
name|newUser
argument_list|,
name|session
argument_list|)
expr_stmt|;
return|return
name|Controller
operator|.
name|redirect
argument_list|(
name|c
argument_list|)
return|;
block|}
block|}
return|return
name|loginAndRedirect
argument_list|(
name|context
argument_list|,
name|loginUser
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Controller
operator|.
name|internalServerError
argument_list|(
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.core.exception.general"
argument_list|)
argument_list|)
return|;
block|}
block|}
catch|catch
parameter_list|(
specifier|final
name|AuthException
name|e
parameter_list|)
block|{
specifier|final
name|Call
name|c
init|=
name|getResolver
argument_list|()
operator|.
name|onException
argument_list|(
name|e
argument_list|)
decl_stmt|;
if|if
condition|(
name|c
operator|!=
literal|null
condition|)
block|{
return|return
name|Controller
operator|.
name|redirect
argument_list|(
name|c
argument_list|)
return|;
block|}
else|else
block|{
specifier|final
name|String
name|message
init|=
name|e
operator|.
name|getMessage
argument_list|()
decl_stmt|;
if|if
condition|(
name|message
operator|!=
literal|null
condition|)
block|{
return|return
name|Controller
operator|.
name|internalServerError
argument_list|(
name|message
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|Controller
operator|.
name|internalServerError
argument_list|()
return|;
block|}
block|}
block|}
block|}
specifier|public
specifier|static
name|AuthProvider
name|getProvider
parameter_list|(
specifier|final
name|String
name|providerKey
parameter_list|)
block|{
return|return
name|AuthProvider
operator|.
name|Registry
operator|.
name|get
argument_list|(
name|providerKey
argument_list|)
return|;
block|}
block|}
end_class

end_unit

