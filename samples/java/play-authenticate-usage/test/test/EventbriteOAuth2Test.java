begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|test
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
name|providers
operator|.
name|oauth2
operator|.
name|eventbrite
operator|.
name|EventBriteAuthProvider
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
name|eventbrite
operator|.
name|EventBriteAuthUser
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
name|fluentlenium
operator|.
name|core
operator|.
name|domain
operator|.
name|FluentWebElement
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
name|NoSuchElementException
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
name|*
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

begin_import
import|import static
name|org
operator|.
name|fluentlenium
operator|.
name|core
operator|.
name|filter
operator|.
name|FilterConstructor
operator|.
name|*
import|;
end_import

begin_class
specifier|public
class|class
name|EventbriteOAuth2Test
extends|extends
name|OAuth2Test
block|{
specifier|public
specifier|static
specifier|final
name|String
name|EVENTBRITE_USER_EMAIL
init|=
literal|"fethjoscha@gmail.com"
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
name|Object
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
literal|"EVENTBRITE_CLIENT_ID"
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
literal|"EVENTBRITE_CLIENT_SECRET"
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
name|EventBriteAuthProvider
operator|.
name|PROVIDER_KEY
return|;
block|}
specifier|protected
name|Class
argument_list|<
name|EventBriteAuthProvider
argument_list|>
name|getProviderClass
parameter_list|()
block|{
return|return
name|EventBriteAuthProvider
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
block|{
name|signupUser
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
name|EventBriteAuthUser
name|authUser
init|=
call|(
name|EventBriteAuthUser
call|)
argument_list|(
name|MyTestUserServiceService
operator|.
name|getLastAuthUser
argument_list|()
argument_list|)
decl_stmt|;
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
literal|"107949557141"
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
name|EVENTBRITE_USER_EMAIL
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
literal|"Joscha"
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
literal|"Feth"
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
literal|"Joscha Feth"
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
try|try
block|{
specifier|final
name|String
name|migrationLightboxSelector
init|=
literal|"#migration_lightbox"
decl_stmt|;
specifier|final
name|FluentWebElement
name|migrationLightbox
init|=
name|browser
operator|.
name|find
argument_list|(
name|migrationLightboxSelector
argument_list|)
operator|.
name|first
argument_list|()
decl_stmt|;
name|migrationLightbox
operator|.
name|find
argument_list|(
literal|".mfp-close"
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
name|atMost
argument_list|(
literal|5L
argument_list|,
name|TimeUnit
operator|.
name|SECONDS
argument_list|)
operator|.
name|until
argument_list|(
name|migrationLightboxSelector
argument_list|)
operator|.
name|areNotDisplayed
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
comment|// migration lightbox was not shown, so we do not need to close it
block|}
name|browser
operator|.
name|fill
argument_list|(
literal|"input"
argument_list|,
name|withName
argument_list|(
literal|"email"
argument_list|)
argument_list|)
operator|.
name|with
argument_list|(
name|EVENTBRITE_USER_EMAIL
argument_list|)
expr_stmt|;
name|browser
operator|.
name|fill
argument_list|(
literal|"input"
argument_list|,
name|withName
argument_list|(
literal|"password"
argument_list|)
argument_list|)
operator|.
name|with
argument_list|(
name|System
operator|.
name|getenv
argument_list|(
literal|"EVENTBRITE_USER_PASSWORD"
argument_list|)
argument_list|)
expr_stmt|;
name|browser
operator|.
name|find
argument_list|(
literal|"input"
argument_list|,
name|with
argument_list|(
literal|"value"
argument_list|)
operator|.
name|equalTo
argument_list|(
literal|"Log in"
argument_list|)
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
literal|"#access_choices_allow"
argument_list|)
operator|.
name|areEnabled
argument_list|()
expr_stmt|;
name|browser
operator|.
name|find
argument_list|(
literal|"#access_choices_allow"
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

