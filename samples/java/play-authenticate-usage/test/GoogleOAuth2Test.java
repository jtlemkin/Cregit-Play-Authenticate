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
name|google
operator|.
name|GoogleAuthProvider
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
name|google
operator|.
name|GoogleAuthUser
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
name|Test
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
name|test
operator|.
name|FakeApplication
import|;
end_import

begin_import
import|import
name|play
operator|.
name|test
operator|.
name|Helpers
import|;
end_import

begin_import
import|import
name|play
operator|.
name|test
operator|.
name|TestBrowser
import|;
end_import

begin_import
import|import
name|play
operator|.
name|test
operator|.
name|WithBrowser
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
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
name|GoogleOAuth2Test
extends|extends
name|WithBrowser
block|{
specifier|public
specifier|static
specifier|final
name|String
name|GOOGLE_USER_EMAIL
init|=
literal|"fethjoscha@gmail.com"
decl_stmt|;
specifier|public
specifier|static
class|class
name|MyTestUserServicePlugin
extends|extends
name|service
operator|.
name|MyUserServicePlugin
block|{
specifier|private
specifier|static
name|AuthUser
name|lastAuthUser
decl_stmt|;
specifier|public
name|MyTestUserServicePlugin
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
name|Object
name|save
parameter_list|(
specifier|final
name|AuthUser
name|authUser
parameter_list|)
block|{
name|lastAuthUser
operator|=
name|authUser
expr_stmt|;
return|return
name|super
operator|.
name|save
argument_list|(
name|authUser
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|AuthUser
name|getLastAuthUser
parameter_list|()
block|{
return|return
name|lastAuthUser
return|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|TestBrowser
name|provideBrowser
parameter_list|(
name|int
name|port
parameter_list|)
block|{
return|return
name|Helpers
operator|.
name|testBrowser
argument_list|(
name|Helpers
operator|.
name|FIREFOX
argument_list|,
name|port
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|int
name|providePort
parameter_list|()
block|{
return|return
literal|9000
return|;
comment|// This needs to be 9000, because the registered Applications are expecting the return URL to be on :9000
block|}
annotation|@
name|Override
specifier|protected
name|FakeApplication
name|provideFakeApplication
parameter_list|()
block|{
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|additionalConfiguration
init|=
operator|new
name|HashMap
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
argument_list|()
decl_stmt|;
name|additionalConfiguration
operator|.
name|putAll
argument_list|(
name|Helpers
operator|.
name|inMemoryDatabase
argument_list|()
argument_list|)
expr_stmt|;
name|additionalConfiguration
operator|.
name|put
argument_list|(
literal|"smtp.mock"
argument_list|,
literal|"true"
argument_list|)
expr_stmt|;
name|additionalConfiguration
operator|.
name|put
argument_list|(
literal|"logger.application"
argument_list|,
literal|"WARN"
argument_list|)
expr_stmt|;
name|additionalConfiguration
operator|.
name|put
argument_list|(
literal|"play-authenticate.google.clientId"
argument_list|,
name|System
operator|.
name|getenv
argument_list|(
literal|"GOOGLE_CLIENT_ID"
argument_list|)
argument_list|)
expr_stmt|;
name|additionalConfiguration
operator|.
name|put
argument_list|(
literal|"play-authenticate.google.clientSecret"
argument_list|,
name|System
operator|.
name|getenv
argument_list|(
literal|"GOOGLE_CLIENT_SECRET"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|Helpers
operator|.
name|fakeApplication
argument_list|(
name|additionalConfiguration
argument_list|,
name|Arrays
operator|.
name|asList
argument_list|(
name|MyTestUserServicePlugin
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|,
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
name|google
operator|.
name|GoogleAuthProvider
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|,
name|Collections
operator|.
name|singletonList
argument_list|(
name|service
operator|.
name|MyUserServicePlugin
operator|.
name|class
operator|.
name|getName
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|itShouldBePossibleToSignUpWithGoogle
parameter_list|()
block|{
name|signupGoogleUser
argument_list|()
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
literal|"/"
argument_list|)
expr_stmt|;
specifier|final
name|GoogleAuthUser
name|authUser
init|=
call|(
name|GoogleAuthUser
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
name|isEqualTo
argument_list|(
literal|"https://plus.google.com/109975614317978623876"
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
literal|"109975614317978623876"
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
literal|"male"
argument_list|)
expr_stmt|;
specifier|final
name|User
name|googleUser
init|=
name|User
operator|.
name|findByEmail
argument_list|(
name|GOOGLE_USER_EMAIL
argument_list|)
decl_stmt|;
name|assertThat
argument_list|(
name|googleUser
argument_list|)
operator|.
name|isNotNull
argument_list|()
expr_stmt|;
name|assertThat
argument_list|(
name|googleUser
operator|.
name|firstName
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"Joscha"
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|googleUser
operator|.
name|lastName
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"Feth"
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|googleUser
operator|.
name|name
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"Joscha Feth"
argument_list|)
expr_stmt|;
block|}
specifier|private
name|void
name|signupGoogleUser
parameter_list|()
block|{
name|browser
operator|.
name|goTo
argument_list|(
literal|"/authenticate/"
operator|+
name|GoogleAuthProvider
operator|.
name|PROVIDER_KEY
argument_list|)
operator|.
name|fill
argument_list|(
literal|"#Email"
argument_list|)
operator|.
name|with
argument_list|(
name|GOOGLE_USER_EMAIL
argument_list|)
operator|.
name|fill
argument_list|(
literal|"#Passwd"
argument_list|)
operator|.
name|with
argument_list|(
name|System
operator|.
name|getenv
argument_list|(
literal|"GOOGLE_USER_PASSWORD"
argument_list|)
argument_list|)
operator|.
name|find
argument_list|(
literal|"#signIn"
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
name|browser
operator|.
name|await
argument_list|()
operator|.
name|atMost
argument_list|(
literal|5
argument_list|,
name|TimeUnit
operator|.
name|SECONDS
argument_list|)
operator|.
name|until
argument_list|(
literal|"#submit_approve_access"
argument_list|)
operator|.
name|areEnabled
argument_list|()
expr_stmt|;
name|browser
operator|.
name|find
argument_list|(
literal|"#submit_approve_access"
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
block|}
end_class

end_unit

