begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
operator|.
name|FacebookAuthProvider
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
name|facebook
operator|.
name|FacebookAuthUser
import|;
end_import

begin_import
import|import
name|models
operator|.
name|User
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|After
import|;
end_import

begin_import
import|import
name|org
operator|.
name|junit
operator|.
name|Test
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openqa
operator|.
name|selenium
operator|.
name|ElementNotVisibleException
import|;
end_import

begin_import
import|import
name|org
operator|.
name|openqa
operator|.
name|selenium
operator|.
name|NoSuchElementException
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
name|concurrent
operator|.
name|TimeUnit
import|;
end_import

begin_import
import|import static
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
operator|.
name|SettingKeys
operator|.
name|CLIENT_ID
import|;
end_import

begin_import
import|import static
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
operator|.
name|SettingKeys
operator|.
name|CLIENT_SECRET
import|;
end_import

begin_import
import|import static
name|org
operator|.
name|fest
operator|.
name|assertions
operator|.
name|Assertions
operator|.
name|assertThat
import|;
end_import

begin_class
specifier|public
class|class
name|FacebookOAuth2Test
extends|extends
name|OAuth2Test
block|{
specifier|public
specifier|static
specifier|final
name|String
name|FACEBOOK_USER_EMAIL
init|=
literal|"ufbullq_fallerman_1414534488@tfbnw.net"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|FACEBOOK_USER_ID
init|=
literal|"100005169708842"
decl_stmt|;
specifier|protected
name|void
name|amendConfiguration
parameter_list|(
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|additionalConfiguration
parameter_list|)
block|{
name|additionalConfiguration
operator|.
name|put
argument_list|(
name|constructSettingKey
argument_list|(
name|CLIENT_ID
argument_list|)
argument_list|,
name|System
operator|.
name|getenv
argument_list|(
literal|"FACEBOOK_CLIENT_ID"
argument_list|)
argument_list|)
expr_stmt|;
name|additionalConfiguration
operator|.
name|put
argument_list|(
name|constructSettingKey
argument_list|(
name|CLIENT_SECRET
argument_list|)
argument_list|,
name|System
operator|.
name|getenv
argument_list|(
literal|"FACEBOOK_CLIENT_SECRET"
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|protected
name|String
name|getProviderKey
parameter_list|()
block|{
return|return
name|FacebookAuthProvider
operator|.
name|PROVIDER_KEY
return|;
block|}
specifier|protected
name|Class
argument_list|<
name|FacebookAuthProvider
argument_list|>
name|getProviderUnderTest
parameter_list|()
block|{
return|return
name|FacebookAuthProvider
operator|.
name|class
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|itShouldBePossibleToSignUp
parameter_list|()
throws|throws
name|InterruptedException
block|{
name|signupUser
argument_list|()
expr_stmt|;
comment|// Make sure the redirect from localhost to fb happened already (and that {@link MyUserServicePlugin#save()} gets called)
name|Thread
operator|.
name|sleep
argument_list|(
literal|3000
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|browser
operator|.
name|url
argument_list|()
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"/#_=_"
argument_list|)
expr_stmt|;
specifier|final
name|FacebookAuthUser
name|authUser
init|=
call|(
name|FacebookAuthUser
call|)
argument_list|(
name|MyTestUserServicePlugin
operator|.
name|getLastAuthUser
argument_list|()
argument_list|)
decl_stmt|;
name|assertThat
argument_list|(
name|authUser
operator|.
name|getProfileLink
argument_list|()
argument_list|)
operator|.
name|contains
argument_list|(
name|FACEBOOK_USER_ID
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|authUser
operator|.
name|getId
argument_list|()
argument_list|)
operator|.
name|isEqualTo
argument_list|(
name|FACEBOOK_USER_ID
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|authUser
operator|.
name|getGender
argument_list|()
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"female"
argument_list|)
expr_stmt|;
specifier|final
name|User
name|user
init|=
name|User
operator|.
name|findByEmail
argument_list|(
name|FACEBOOK_USER_EMAIL
argument_list|)
decl_stmt|;
name|assertThat
argument_list|(
name|user
argument_list|)
operator|.
name|isNotNull
argument_list|()
expr_stmt|;
name|assertThat
argument_list|(
name|user
operator|.
name|firstName
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"Mary"
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|user
operator|.
name|lastName
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"Fallerman"
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|user
operator|.
name|name
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"Mary Ameafigjhhdb Fallerman"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|signupUser
parameter_list|()
block|{
name|goToLogin
argument_list|()
expr_stmt|;
name|browser
operator|.
name|fill
argument_list|(
literal|"#email"
argument_list|)
operator|.
name|with
argument_list|(
name|FACEBOOK_USER_EMAIL
argument_list|)
operator|.
name|fill
argument_list|(
literal|"#pass"
argument_list|)
operator|.
name|with
argument_list|(
name|System
operator|.
name|getenv
argument_list|(
literal|"FACEBOOK_USER_PASSWORD"
argument_list|)
argument_list|)
operator|.
name|find
argument_list|(
literal|"#u_0_1"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|browser
operator|.
name|await
argument_list|()
operator|.
name|untilPage
argument_list|()
operator|.
name|isLoaded
argument_list|()
expr_stmt|;
comment|// save browser? no!
try|try
block|{
comment|// try, because this is not checked for test users, because they are not asked
name|browser
operator|.
name|find
argument_list|(
literal|"#u_0_2"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|browser
operator|.
name|find
argument_list|(
literal|"#checkpointSubmitButton"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|browser
operator|.
name|await
argument_list|()
operator|.
name|untilPage
argument_list|()
operator|.
name|isLoaded
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
specifier|final
name|NoSuchElementException
name|nsee
parameter_list|)
block|{
comment|// mobile
block|}
catch|catch
parameter_list|(
specifier|final
name|ElementNotVisibleException
name|enve
parameter_list|)
block|{
comment|// desktop
block|}
comment|// check login layout
name|checkLoginLayout
argument_list|()
expr_stmt|;
comment|// confirm login
name|browser
operator|.
name|find
argument_list|(
literal|"[name='__CONFIRM__']"
argument_list|)
operator|.
name|click
argument_list|()
expr_stmt|;
name|browser
operator|.
name|await
argument_list|()
operator|.
name|untilPage
argument_list|()
operator|.
name|isLoaded
argument_list|()
expr_stmt|;
block|}
specifier|protected
name|String
name|expectedLoginLayout
parameter_list|()
block|{
return|return
literal|"page"
return|;
block|}
specifier|private
name|void
name|checkLoginLayout
parameter_list|()
block|{
specifier|final
name|String
name|selector
init|=
literal|"[name='display']"
decl_stmt|;
name|browser
operator|.
name|await
argument_list|()
operator|.
name|atMost
argument_list|(
literal|10
argument_list|,
name|TimeUnit
operator|.
name|SECONDS
argument_list|)
operator|.
name|until
argument_list|(
name|selector
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|browser
operator|.
name|find
argument_list|(
name|selector
argument_list|)
operator|.
name|getValue
argument_list|()
argument_list|)
operator|.
name|isEqualTo
argument_list|(
name|expectedLoginLayout
argument_list|()
argument_list|)
expr_stmt|;
block|}
comment|/**      * After the test is finished, revoke the permission to the app so the login dialog appears again on the next login when running the test      * See https://developers.facebook.com/docs/facebook-login/permissions/v2.1#revokelogin      */
annotation|@
name|After
specifier|public
name|void
name|shutdown
parameter_list|()
block|{
specifier|final
name|FacebookAuthUser
name|authUser
init|=
call|(
name|FacebookAuthUser
call|)
argument_list|(
name|MyTestUserServicePlugin
operator|.
name|getLastAuthUser
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|authUser
operator|==
literal|null
condition|)
block|{
comment|// in case the test failed, we don't have an authUser
return|return;
block|}
specifier|final
name|String
name|url
init|=
name|getConfig
argument_list|()
operator|.
name|getString
argument_list|(
literal|"userInfoUrl"
argument_list|)
operator|+
literal|"/permissions"
decl_stmt|;
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
name|authUser
operator|.
name|getOAuth2AuthInfo
argument_list|()
operator|.
name|getAccessToken
argument_list|()
argument_list|)
operator|.
name|setQueryParameter
argument_list|(
literal|"format"
argument_list|,
literal|"json"
argument_list|)
operator|.
name|setQueryParameter
argument_list|(
literal|"method"
argument_list|,
literal|"delete"
argument_list|)
operator|.
name|get
argument_list|()
operator|.
name|get
argument_list|(
literal|10
argument_list|,
name|TimeUnit
operator|.
name|SECONDS
argument_list|)
expr_stmt|;
block|}
block|}
end_class

end_unit

