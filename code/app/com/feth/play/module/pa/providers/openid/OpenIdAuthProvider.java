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
name|openid
package|;
end_package

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
name|providers
operator|.
name|openid
operator|.
name|exceptions
operator|.
name|NoOpenIdAuthException
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
name|openid
operator|.
name|exceptions
operator|.
name|OpenIdConnectException
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
name|api
operator|.
name|libs
operator|.
name|openid
operator|.
name|OpenIDError
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
name|openid
operator|.
name|OpenIdClient
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|openid
operator|.
name|UserInfo
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
name|javax
operator|.
name|inject
operator|.
name|Inject
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|inject
operator|.
name|Singleton
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
name|Map
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Set
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
name|stream
operator|.
name|Collectors
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
annotation|@
name|Singleton
specifier|public
class|class
name|OpenIdAuthProvider
extends|extends
name|ExternalAuthProvider
block|{
specifier|static
specifier|abstract
class|class
name|SettingKeys
block|{
specifier|public
specifier|final
specifier|static
name|String
name|PROVIDER_KEY
init|=
literal|"openid"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ATTRIBUTES
init|=
literal|"attributes"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ATTRIBUTES_REQUIRED
init|=
literal|"required"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|ATTRIBUTES_OPTIONAL
init|=
literal|"optional"
decl_stmt|;
block|}
specifier|private
specifier|final
name|OpenIdClient
name|openIdClient
decl_stmt|;
annotation|@
name|Inject
specifier|public
name|OpenIdAuthProvider
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
name|OpenIdClient
name|openIdClient
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
name|openIdClient
operator|=
name|openIdClient
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|SettingKeys
operator|.
name|PROVIDER_KEY
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
name|boolean
name|hasOpenID
init|=
name|payload
operator|!=
literal|null
operator|&&
operator|!
name|payload
operator|.
name|toString
argument_list|()
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
decl_stmt|;
if|if
condition|(
operator|!
name|hasOpenID
condition|)
block|{
try|try
block|{
specifier|final
name|Future
argument_list|<
name|UserInfo
argument_list|>
name|pu
init|=
name|openIdClient
operator|.
name|verifiedId
argument_list|()
operator|.
name|toCompletableFuture
argument_list|()
decl_stmt|;
return|return
operator|new
name|OpenIdAuthUser
argument_list|(
name|pu
operator|.
name|get
argument_list|(
name|getTimeout
argument_list|()
argument_list|,
name|MILLISECONDS
argument_list|)
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|t
parameter_list|)
block|{
if|if
condition|(
name|t
operator|instanceof
name|OpenIDError
condition|)
block|{
if|if
condition|(
operator|!
name|hasOpenID
condition|)
block|{
throw|throw
operator|new
name|NoOpenIdAuthException
argument_list|(
literal|"OpenID endpoint is required"
argument_list|)
throw|;
block|}
else|else
block|{
if|if
condition|(
operator|(
operator|(
name|OpenIDError
operator|)
name|t
operator|)
operator|.
name|message
argument_list|()
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
operator|(
operator|(
name|OpenIDError
operator|)
name|t
operator|)
operator|.
name|message
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
literal|"Bad response from OpenID provider"
argument_list|)
throw|;
block|}
block|}
block|}
else|else
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
block|}
else|else
block|{
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|required
init|=
name|getAttributes
argument_list|(
name|SettingKeys
operator|.
name|ATTRIBUTES_REQUIRED
argument_list|)
decl_stmt|;
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|optional
init|=
name|getAttributes
argument_list|(
name|SettingKeys
operator|.
name|ATTRIBUTES_OPTIONAL
argument_list|)
decl_stmt|;
try|try
block|{
specifier|final
name|Future
argument_list|<
name|String
argument_list|>
name|pr
init|=
name|openIdClient
operator|.
name|redirectURL
argument_list|(
name|payload
operator|.
name|toString
argument_list|()
argument_list|,
name|getRedirectUrl
argument_list|(
name|context
operator|.
name|request
argument_list|()
argument_list|)
argument_list|,
name|required
argument_list|,
name|optional
argument_list|)
operator|.
name|toCompletableFuture
argument_list|()
decl_stmt|;
return|return
name|pr
operator|.
name|get
argument_list|(
name|getTimeout
argument_list|()
argument_list|,
name|MILLISECONDS
argument_list|)
return|;
block|}
catch|catch
parameter_list|(
specifier|final
name|Throwable
name|t
parameter_list|)
block|{
if|if
condition|(
name|t
operator|instanceof
name|java
operator|.
name|net
operator|.
name|ConnectException
condition|)
block|{
throw|throw
operator|new
name|OpenIdConnectException
argument_list|(
name|t
operator|.
name|getMessage
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
name|t
operator|.
name|getMessage
argument_list|()
argument_list|)
throw|;
block|}
block|}
block|}
block|}
specifier|private
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getAttributes
parameter_list|(
specifier|final
name|String
name|subKey
parameter_list|)
block|{
specifier|final
name|Config
name|attributes
init|=
name|getConfiguration
argument_list|()
operator|.
name|getConfig
argument_list|(
name|SettingKeys
operator|.
name|ATTRIBUTES
operator|+
literal|"."
operator|+
name|subKey
argument_list|)
decl_stmt|;
if|if
condition|(
name|attributes
operator|!=
literal|null
condition|)
block|{
specifier|final
name|Set
argument_list|<
name|String
argument_list|>
name|keys
init|=
name|attributes
operator|.
name|entrySet
argument_list|()
operator|.
name|stream
argument_list|()
operator|.
name|map
argument_list|(
name|x
lambda|->
name|x
operator|.
name|getKey
argument_list|()
argument_list|)
operator|.
name|collect
argument_list|(
name|Collectors
operator|.
name|toSet
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|ret
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|(
name|keys
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|String
name|key
range|:
name|keys
control|)
block|{
name|ret
operator|.
name|put
argument_list|(
name|key
argument_list|,
name|attributes
operator|.
name|getString
argument_list|(
name|key
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|ret
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

