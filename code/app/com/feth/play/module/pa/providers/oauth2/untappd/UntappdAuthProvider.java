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
name|untappd
package|;
end_package

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
name|ResolverMissingException
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
name|message
operator|.
name|BasicNameValuePair
import|;
end_import

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
name|ws
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

begin_comment
comment|/**  * Auth provider for Untappd beer social network  * https://www.untappd.com  */
end_comment

begin_class
specifier|public
class|class
name|UntappdAuthProvider
extends|extends
name|OAuth2AuthProvider
argument_list|<
name|UntappdAuthUser
argument_list|,
name|UntappdAuthInfo
argument_list|>
block|{
specifier|public
specifier|static
specifier|final
name|String
name|PROVIDER_KEY
init|=
literal|"untappd"
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
name|NODE_USER
init|=
literal|"user"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|NODE_RESPONSE
init|=
literal|"response"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|NODE_META
init|=
literal|"meta"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|ERROR_DETAIL
init|=
literal|"error_detail"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|ERROR_TYPE
init|=
literal|"error_type"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|REDIRECT_URL
init|=
literal|"redirect_url"
decl_stmt|;
comment|// Use this value for REDIRECT_URL in local development
comment|// and put same URL in your Untappd App page
comment|// private static final String CALLBACK_URL =
comment|// "http://localhost:9000/authenticate/untappd";
specifier|public
name|UntappdAuthProvider
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
name|UntappdAuthUser
name|transform
parameter_list|(
specifier|final
name|UntappdAuthInfo
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
name|WSResponse
name|r
init|=
name|WS
operator|.
name|url
argument_list|(
name|url
argument_list|)
operator|.
name|setQueryParameter
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
operator|.
name|get
argument_list|()
operator|.
name|get
argument_list|(
name|getTimeout
argument_list|()
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
name|UntappdAuthUser
argument_list|(
name|result
operator|.
name|get
argument_list|(
name|NODE_RESPONSE
argument_list|)
operator|.
name|get
argument_list|(
name|NODE_USER
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
specifier|protected
name|String
name|getErrorParameterKey
parameter_list|()
block|{
return|return
name|ERROR_TYPE
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|getRedirectUriKey
parameter_list|()
block|{
comment|// Attention: This is redirect_urL instead of the normal redirect_urI
return|return
name|REDIRECT_URL
return|;
block|}
specifier|protected
name|UntappdAuthInfo
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
throws|,
name|ResolverMissingException
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
name|WSResponse
name|r
init|=
name|WS
operator|.
name|url
argument_list|(
name|url
argument_list|)
operator|.
name|setQueryParameter
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
operator|.
name|setQueryParameter
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
operator|.
name|setQueryParameter
argument_list|(
name|Constants
operator|.
name|RESPONSE_TYPE
argument_list|,
name|Constants
operator|.
name|CODE
argument_list|)
operator|.
name|setQueryParameter
argument_list|(
name|Constants
operator|.
name|CODE
argument_list|,
name|code
argument_list|)
operator|.
name|setQueryParameter
argument_list|(
name|getRedirectUriKey
argument_list|()
argument_list|,
name|getRedirectUrl
argument_list|(
name|request
argument_list|)
argument_list|)
comment|// we use GET here
operator|.
name|get
argument_list|()
operator|.
name|get
argument_list|(
name|getTimeout
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|buildInfo
argument_list|(
name|r
argument_list|)
return|;
block|}
annotation|@
name|Override
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
throws|throws
name|ResolverMissingException
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
return|return
name|params
return|;
block|}
annotation|@
name|Override
specifier|protected
name|UntappdAuthInfo
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
specifier|final
name|JsonNode
name|meta
init|=
name|n
operator|.
name|get
argument_list|(
name|NODE_META
argument_list|)
decl_stmt|;
if|if
condition|(
name|meta
operator|.
name|get
argument_list|(
name|ERROR_TYPE
argument_list|)
operator|!=
literal|null
condition|)
block|{
throw|throw
operator|new
name|AccessTokenException
argument_list|(
name|meta
operator|.
name|get
argument_list|(
name|ERROR_DETAIL
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
name|UntappdAuthInfo
argument_list|(
name|n
operator|.
name|get
argument_list|(
name|NODE_RESPONSE
argument_list|)
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|ACCESS_TOKEN
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

