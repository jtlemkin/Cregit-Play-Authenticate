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
name|twitter
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
name|api
operator|.
name|libs
operator|.
name|json
operator|.
name|JsValue
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
name|ConsumerKey
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
name|play
operator|.
name|api
operator|.
name|libs
operator|.
name|ws
operator|.
name|Response
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
name|Json
import|;
end_import

begin_import
import|import
name|scala
operator|.
name|concurrent
operator|.
name|Future
import|;
end_import

begin_class
specifier|public
class|class
name|TwitterAuthProvider
extends|extends
name|OAuth1AuthProvider
argument_list|<
name|TwitterAuthUser
argument_list|,
name|TwitterAuthInfo
argument_list|>
block|{
specifier|static
specifier|final
name|String
name|PROVIDER_KEY
init|=
literal|"twitter"
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
name|TwitterAuthProvider
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
name|TwitterAuthUser
name|transform
parameter_list|(
specifier|final
name|TwitterAuthInfo
name|info
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
name|Configuration
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
specifier|final
name|OAuthCalculator
name|op
init|=
operator|new
name|OAuthCalculator
argument_list|(
name|cK
argument_list|,
name|token
argument_list|)
decl_stmt|;
specifier|final
name|Future
argument_list|<
name|Response
argument_list|>
name|resp
init|=
name|WS
operator|.
name|url
argument_list|(
name|url
argument_list|)
operator|.
name|sign
argument_list|(
name|op
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
specifier|final
name|Future
argument_list|<
name|play
operator|.
name|api
operator|.
name|libs
operator|.
name|ws
operator|.
name|Response
argument_list|>
name|future
init|=
name|WS
operator|.
name|url
argument_list|(
name|url
argument_list|)
operator|.
name|sign
argument_list|(
name|op
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
name|play
operator|.
name|api
operator|.
name|libs
operator|.
name|ws
operator|.
name|Response
name|response
init|=
operator|new
name|play
operator|.
name|libs
operator|.
name|F
operator|.
name|Promise
argument_list|<
name|play
operator|.
name|api
operator|.
name|libs
operator|.
name|ws
operator|.
name|Response
argument_list|>
argument_list|(
name|future
argument_list|)
operator|.
name|get
argument_list|()
decl_stmt|;
specifier|final
name|JsValue
name|json
init|=
name|response
operator|.
name|json
argument_list|()
decl_stmt|;
return|return
operator|new
name|TwitterAuthUser
argument_list|(
name|Json
operator|.
name|parse
argument_list|(
name|json
operator|.
name|toString
argument_list|()
argument_list|)
argument_list|,
name|info
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|TwitterAuthInfo
name|buildInfo
parameter_list|(
specifier|final
name|RequestToken
name|rtoken
parameter_list|)
throws|throws
name|AccessTokenException
block|{
return|return
operator|new
name|TwitterAuthInfo
argument_list|(
name|rtoken
operator|.
name|token
argument_list|()
argument_list|,
name|rtoken
operator|.
name|secret
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

