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
name|github
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
name|Application
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
name|java
operator|.
name|util
operator|.
name|Collections
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

begin_class
specifier|public
class|class
name|GithubAuthProvider
extends|extends
name|OAuth2AuthProvider
argument_list|<
name|GithubAuthUser
argument_list|,
name|GithubAuthInfo
argument_list|>
block|{
specifier|static
specifier|final
name|String
name|PROVIDER_KEY
init|=
literal|"github"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|USER_INFO_URL_SETTING_KEY
init|=
literal|"userInfoUrl"
decl_stmt|;
specifier|public
name|GithubAuthProvider
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
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|getHeaders
parameter_list|()
block|{
return|return
name|Collections
operator|.
name|singletonMap
argument_list|(
literal|"Accept"
argument_list|,
literal|"application/json"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|GithubAuthUser
name|transform
parameter_list|(
specifier|final
name|GithubAuthInfo
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
name|setQueryParameter
argument_list|(
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
name|GithubAuthUser
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
specifier|protected
name|GithubAuthInfo
name|buildInfo
parameter_list|(
specifier|final
name|Response
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
if|if
condition|(
name|n
operator|.
name|get
argument_list|(
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
name|GithubAuthInfo
argument_list|(
name|n
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

