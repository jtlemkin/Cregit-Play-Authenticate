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
operator|.
name|linkedin
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
name|api
operator|.
name|libs
operator|.
name|oauth
operator|.
name|OAuthCalculator
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
name|oauth
operator|.
name|RequestToken
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
name|oauth1
operator|.
name|OAuth1AuthProvider
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

begin_class
specifier|public
class|class
name|LinkedinAuthProvider
extends|extends
name|OAuth1AuthProvider
argument_list|<
name|LinkedinAuthUser
argument_list|,
name|LinkedinAuthInfo
argument_list|>
block|{
specifier|static
specifier|final
name|String
name|PROVIDER_KEY
init|=
literal|"linkedin"
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
name|USER_EMAIL_URL_SETTING_KEY
init|=
literal|"userEmailUrl"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|OAUTH_ACCESS_DENIED
init|=
literal|"user_refused"
decl_stmt|;
specifier|public
name|LinkedinAuthProvider
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
name|neededSettingKeys
init|=
name|super
operator|.
name|neededSettingKeys
argument_list|()
decl_stmt|;
name|neededSettingKeys
operator|.
name|add
argument_list|(
name|USER_INFO_URL_SETTING_KEY
argument_list|)
expr_stmt|;
name|neededSettingKeys
operator|.
name|add
argument_list|(
name|USER_EMAIL_URL_SETTING_KEY
argument_list|)
expr_stmt|;
return|return
name|neededSettingKeys
return|;
block|}
annotation|@
name|Override
specifier|protected
name|LinkedinAuthUser
name|transform
parameter_list|(
specifier|final
name|LinkedinAuthInfo
name|info
parameter_list|)
throws|throws
name|AuthException
block|{
specifier|final
name|String
name|userInfoUrl
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
name|emailUrl
init|=
name|getConfiguration
argument_list|()
operator|.
name|getString
argument_list|(
name|USER_EMAIL_URL_SETTING_KEY
argument_list|)
decl_stmt|;
specifier|final
name|OAuthCalculator
name|op
init|=
name|getOAuthCalculator
argument_list|(
name|info
argument_list|)
decl_stmt|;
specifier|final
name|JsonNode
name|userJson
init|=
name|signedOauthGet
argument_list|(
name|userInfoUrl
argument_list|,
name|op
argument_list|)
decl_stmt|;
specifier|final
name|JsonNode
name|emailJson
init|=
name|signedOauthGet
argument_list|(
name|emailUrl
argument_list|,
name|op
argument_list|)
decl_stmt|;
return|return
operator|new
name|LinkedinAuthUser
argument_list|(
name|userJson
argument_list|,
name|emailJson
operator|.
name|asText
argument_list|()
argument_list|,
name|info
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|LinkedinAuthInfo
name|buildInfo
parameter_list|(
specifier|final
name|RequestToken
name|requestToken
parameter_list|)
throws|throws
name|AccessTokenException
block|{
return|return
operator|new
name|LinkedinAuthInfo
argument_list|(
name|requestToken
operator|.
name|token
argument_list|()
argument_list|,
name|requestToken
operator|.
name|secret
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|void
name|checkError
parameter_list|(
name|Http
operator|.
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
block|}
end_class

end_unit

