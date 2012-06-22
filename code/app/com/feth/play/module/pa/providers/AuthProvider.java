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
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Collection
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
name|Plugin
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
name|user
operator|.
name|AuthUser
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|AuthProvider
extends|extends
name|Plugin
block|{
specifier|public
specifier|abstract
specifier|static
class|class
name|Registry
block|{
specifier|private
specifier|static
name|Map
argument_list|<
name|String
argument_list|,
name|AuthProvider
argument_list|>
name|providers
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|AuthProvider
argument_list|>
argument_list|()
decl_stmt|;
specifier|public
specifier|static
name|void
name|register
parameter_list|(
name|String
name|provider
parameter_list|,
name|AuthProvider
name|p
parameter_list|)
block|{
specifier|final
name|Object
name|previous
init|=
name|providers
operator|.
name|put
argument_list|(
name|provider
argument_list|,
name|p
argument_list|)
decl_stmt|;
if|if
condition|(
name|previous
operator|!=
literal|null
condition|)
block|{
name|Logger
operator|.
name|warn
argument_list|(
literal|"There are multiple AuthProviders registered for key '"
operator|+
name|provider
operator|+
literal|"'"
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
name|void
name|unregister
parameter_list|(
name|String
name|provider
parameter_list|)
block|{
name|providers
operator|.
name|remove
argument_list|(
name|provider
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
name|AuthProvider
name|get
parameter_list|(
name|String
name|provider
parameter_list|)
block|{
return|return
name|providers
operator|.
name|get
argument_list|(
name|provider
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Collection
argument_list|<
name|AuthProvider
argument_list|>
name|getProviders
parameter_list|()
block|{
return|return
name|providers
operator|.
name|values
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|boolean
name|hasProvider
parameter_list|(
specifier|final
name|String
name|provider
parameter_list|)
block|{
return|return
name|providers
operator|.
name|containsKey
argument_list|(
name|provider
argument_list|)
return|;
block|}
block|}
specifier|private
name|Application
name|application
decl_stmt|;
specifier|public
name|AuthProvider
parameter_list|(
specifier|final
name|Application
name|app
parameter_list|)
block|{
name|application
operator|=
name|app
expr_stmt|;
block|}
specifier|protected
name|Application
name|getApplication
parameter_list|()
block|{
return|return
name|application
return|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onStart
parameter_list|()
block|{
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|neededSettings
init|=
name|neededSettingKeys
argument_list|()
decl_stmt|;
if|if
condition|(
name|neededSettings
operator|!=
literal|null
condition|)
block|{
specifier|final
name|Configuration
name|c
init|=
name|getConfiguration
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
literal|"No settings for provider '"
operator|+
name|getKey
argument_list|()
operator|+
literal|"' available at all!"
argument_list|)
throw|;
block|}
for|for
control|(
specifier|final
name|String
name|key
range|:
name|neededSettings
control|)
block|{
specifier|final
name|String
name|setting
init|=
name|c
operator|.
name|getString
argument_list|(
name|key
argument_list|)
decl_stmt|;
if|if
condition|(
name|setting
operator|==
literal|null
operator|||
literal|""
operator|.
name|equals
argument_list|(
name|setting
argument_list|)
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Provider '"
operator|+
name|getKey
argument_list|()
operator|+
literal|"' missing needed setting '"
operator|+
name|key
operator|+
literal|"'"
argument_list|)
throw|;
block|}
block|}
block|}
name|Registry
operator|.
name|register
argument_list|(
name|getKey
argument_list|()
argument_list|,
name|this
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|onStop
parameter_list|()
block|{
name|Registry
operator|.
name|unregister
argument_list|(
name|getKey
argument_list|()
argument_list|)
expr_stmt|;
block|}
specifier|public
name|String
name|getUrl
parameter_list|()
block|{
return|return
name|PlayAuthenticate
operator|.
name|getResolver
argument_list|()
operator|.
name|auth
argument_list|(
name|getKey
argument_list|()
argument_list|)
operator|.
name|url
argument_list|()
return|;
block|}
specifier|protected
name|String
name|getAbsoluteUrl
parameter_list|(
specifier|final
name|Request
name|request
parameter_list|)
block|{
return|return
name|PlayAuthenticate
operator|.
name|getResolver
argument_list|()
operator|.
name|auth
argument_list|(
name|getKey
argument_list|()
argument_list|)
operator|.
name|absoluteURL
argument_list|(
name|request
argument_list|)
return|;
block|}
specifier|public
specifier|abstract
name|String
name|getKey
parameter_list|()
function_decl|;
specifier|protected
name|Configuration
name|getConfiguration
parameter_list|()
block|{
return|return
name|PlayAuthenticate
operator|.
name|getConfiguration
argument_list|()
operator|.
name|getConfig
argument_list|(
name|getKey
argument_list|()
argument_list|)
return|;
block|}
comment|/** 	 * Returns either an AuthUser object or a String (URL) 	 *  	 * @param context 	 * @param payload 	 *            Some arbitrary payload that shall get passed into the 	 *            authentication process 	 * @return 	 * @throws AuthException 	 */
specifier|public
specifier|abstract
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
function_decl|;
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|neededSettingKeys
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
specifier|public
specifier|abstract
name|AuthUser
name|getSessionAuthUser
parameter_list|(
name|String
name|id
parameter_list|,
name|long
name|expires
parameter_list|)
function_decl|;
block|}
end_class

end_unit

