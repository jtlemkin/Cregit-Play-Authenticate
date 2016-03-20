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
name|facebook
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
name|oauth2
operator|.
name|OAuth2AuthProvider
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
name|net
operator|.
name|URI
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

begin_class
annotation|@
name|Singleton
specifier|public
class|class
name|FacebookAuthProvider
extends|extends
name|OAuth2AuthProvider
argument_list|<
name|FacebookAuthUser
argument_list|,
name|FacebookAuthInfo
argument_list|>
block|{
specifier|private
specifier|static
specifier|final
name|String
name|MESSAGE
init|=
literal|"message"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|ERROR
init|=
literal|"error"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|FIELDS
init|=
literal|"fields"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PROVIDER_KEY
init|=
literal|"facebook"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|USER_INFO_URL_SETTING_KEY
init|=
literal|"userInfoUrl"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|USER_INFO_FIELDS_SETTING_KEY
init|=
literal|"userInfoFields"
decl_stmt|;
annotation|@
name|Inject
specifier|public
name|FacebookAuthProvider
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
argument_list|,
name|wsClient
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
specifier|abstract
class|class
name|SettingKeys
extends|extends
name|OAuth2AuthProvider
operator|.
name|SettingKeys
block|{
specifier|public
specifier|static
specifier|final
name|String
name|DISPLAY
init|=
literal|"display"
decl_stmt|;
block|}
specifier|public
specifier|static
specifier|abstract
class|class
name|FacebookConstants
extends|extends
name|Constants
block|{
specifier|public
specifier|static
specifier|final
name|String
name|DISPLAY
init|=
literal|"display"
decl_stmt|;
block|}
annotation|@
name|Override
specifier|protected
name|FacebookAuthUser
name|transform
parameter_list|(
name|FacebookAuthInfo
name|info
parameter_list|,
specifier|final
name|String
name|state
parameter_list|)
throws|throws
name|AuthException
block|{
specifier|final
name|String
name|url
init|=
name|getConfiguration
argument_list|()
operator|.
name|getString
argument_list|(
name|USER_INFO_URL_SETTING_KEY
argument_list|)
decl_stmt|;
specifier|final
name|String
name|fields
init|=
name|getConfiguration
argument_list|()
operator|.
name|getString
argument_list|(
name|USER_INFO_FIELDS_SETTING_KEY
argument_list|)
decl_stmt|;
specifier|final
name|WSResponse
name|r
init|=
name|fetchAuthResponse
argument_list|(
name|url
argument_list|,
operator|new
name|QueryParam
argument_list|(
name|OAuth2AuthProvider
operator|.
name|Constants
operator|.
name|ACCESS_TOKEN
argument_list|,
name|info
operator|.
name|getAccessToken
argument_list|()
argument_list|)
argument_list|,
operator|new
name|QueryParam
argument_list|(
name|FIELDS
argument_list|,
name|fields
argument_list|)
argument_list|)
decl_stmt|;
specifier|final
name|JsonNode
name|result
init|=
name|r
operator|.
name|asJson
argument_list|()
decl_stmt|;
if|if
condition|(
name|result
operator|.
name|get
argument_list|(
name|OAuth2AuthProvider
operator|.
name|Constants
operator|.
name|ERROR
argument_list|)
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|AuthException
argument_list|(
name|result
operator|.
name|get
argument_list|(
name|ERROR
argument_list|)
operator|.
name|get
argument_list|(
name|MESSAGE
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|)
throw|;
block|}
else|else
block|{
name|Logger
operator|.
name|debug
argument_list|(
name|result
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
return|return
operator|new
name|FacebookAuthUser
argument_list|(
name|result
argument_list|,
name|info
argument_list|,
name|state
argument_list|)
return|;
block|}
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
name|FacebookAuthInfo
name|buildInfo
parameter_list|(
specifier|final
name|WSResponse
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
name|get
argument_list|(
name|ERROR
argument_list|)
operator|.
name|get
argument_list|(
name|MESSAGE
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|)
throw|;
block|}
else|else
block|{
specifier|final
name|String
name|query
init|=
name|r
operator|.
name|getBody
argument_list|()
decl_stmt|;
name|Logger
operator|.
name|debug
argument_list|(
name|query
argument_list|)
expr_stmt|;
specifier|final
name|List
argument_list|<
name|NameValuePair
argument_list|>
name|pairs
init|=
name|URLEncodedUtils
operator|.
name|parse
argument_list|(
name|URI
operator|.
name|create
argument_list|(
literal|"/?"
operator|+
name|query
argument_list|)
argument_list|,
literal|"utf-8"
argument_list|)
decl_stmt|;
if|if
condition|(
name|pairs
operator|.
name|size
argument_list|()
operator|<
literal|2
condition|)
block|{
throw|throw
operator|new
name|AccessTokenException
argument_list|()
throw|;
block|}
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|m
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|(
name|pairs
operator|.
name|size
argument_list|()
argument_list|)
decl_stmt|;
for|for
control|(
specifier|final
name|NameValuePair
name|nameValuePair
range|:
name|pairs
control|)
block|{
name|m
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
name|FacebookAuthInfo
argument_list|(
name|m
argument_list|)
return|;
block|}
block|}
annotation|@
name|Override
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
name|super
operator|.
name|getAuthParams
argument_list|(
name|c
argument_list|,
name|request
argument_list|,
name|state
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
name|DISPLAY
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
name|FacebookConstants
operator|.
name|DISPLAY
argument_list|,
name|c
operator|.
name|getString
argument_list|(
name|SettingKeys
operator|.
name|DISPLAY
argument_list|)
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|params
return|;
block|}
block|}
end_class

end_unit

