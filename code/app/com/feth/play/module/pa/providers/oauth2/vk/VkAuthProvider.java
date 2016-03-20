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
name|vk
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

begin_comment
comment|/**  * @author Denis Borisenko  */
end_comment

begin_class
annotation|@
name|Singleton
specifier|public
class|class
name|VkAuthProvider
extends|extends
name|OAuth2AuthProvider
argument_list|<
name|VkAuthUser
argument_list|,
name|VkAuthInfo
argument_list|>
block|{
specifier|public
specifier|static
specifier|final
name|String
name|PROVIDER_KEY
init|=
literal|"vk"
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
specifier|private
specifier|static
specifier|final
name|String
name|FIELDS_REQUEST_KEY
init|=
literal|"fields"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|UIDS_REQUEST_KEY
init|=
literal|"uids"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|BODY_RESPONSE_KEY
init|=
literal|"response"
decl_stmt|;
annotation|@
name|Inject
specifier|public
name|VkAuthProvider
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
annotation|@
name|Override
specifier|protected
name|VkAuthUser
name|transform
parameter_list|(
name|VkAuthInfo
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
name|UIDS_REQUEST_KEY
argument_list|,
name|info
operator|.
name|getUserId
argument_list|()
argument_list|)
argument_list|,
operator|new
name|QueryParam
argument_list|(
name|FIELDS_REQUEST_KEY
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
name|OAuth2AuthProvider
operator|.
name|Constants
operator|.
name|ERROR
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|)
throw|;
block|}
else|else
block|{
return|return
operator|new
name|VkAuthUser
argument_list|(
name|result
operator|.
name|get
argument_list|(
name|BODY_RESPONSE_KEY
argument_list|)
operator|.
name|get
argument_list|(
literal|0
argument_list|)
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
name|VkAuthInfo
name|buildInfo
parameter_list|(
specifier|final
name|WSResponse
name|r
parameter_list|)
throws|throws
name|AccessTokenException
block|{
specifier|final
name|JsonNode
name|n
init|=
name|r
operator|.
name|asJson
argument_list|()
decl_stmt|;
name|Logger
operator|.
name|debug
argument_list|(
name|n
operator|.
name|toString
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|n
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
name|AccessTokenException
argument_list|(
name|n
operator|.
name|get
argument_list|(
name|OAuth2AuthProvider
operator|.
name|Constants
operator|.
name|ERROR
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|)
throw|;
block|}
else|else
block|{
return|return
operator|new
name|VkAuthInfo
argument_list|(
name|n
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

