begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
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
name|play
operator|.
name|test
operator|.
name|Helpers
operator|.
name|callAction
import|;
end_import

begin_import
import|import static
name|play
operator|.
name|test
operator|.
name|Helpers
operator|.
name|session
import|;
end_import

begin_import
import|import static
name|play
operator|.
name|test
operator|.
name|Helpers
operator|.
name|fakeApplication
import|;
end_import

begin_import
import|import static
name|play
operator|.
name|test
operator|.
name|Helpers
operator|.
name|fakeRequest
import|;
end_import

begin_import
import|import static
name|play
operator|.
name|test
operator|.
name|Helpers
operator|.
name|running
import|;
end_import

begin_import
import|import static
name|play
operator|.
name|test
operator|.
name|Helpers
operator|.
name|status
import|;
end_import

begin_import
import|import static
name|play
operator|.
name|mvc
operator|.
name|Http
operator|.
name|Status
operator|.
name|OK
import|;
end_import

begin_import
import|import static
name|play
operator|.
name|mvc
operator|.
name|Http
operator|.
name|Status
operator|.
name|SEE_OTHER
import|;
end_import

begin_import
import|import static
name|scala
operator|.
name|collection
operator|.
name|JavaConversions
operator|.
name|asJavaMap
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
name|javax
operator|.
name|mail
operator|.
name|Session
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
name|Logger
import|;
end_import

begin_import
import|import
name|play
operator|.
name|Play
import|;
end_import

begin_import
import|import
name|play
operator|.
name|libs
operator|.
name|F
operator|.
name|Promise
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

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Result
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Results
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
name|FakeRequest
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
name|providers
operator|.
name|TestUsernamePasswordAuthProvider
import|;
end_import

begin_import
import|import
name|service
operator|.
name|TestUserServicePlugin
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
name|service
operator|.
name|UserServicePlugin
import|;
end_import

begin_class
specifier|public
class|class
name|JavaControllerTest
block|{
annotation|@
name|Test
specifier|public
name|void
name|redirectsWhenNotLoggedIn
parameter_list|()
block|{
name|running
argument_list|(
name|fakeApplication
argument_list|()
argument_list|,
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|assertThat
argument_list|(
name|userServicePlugin
argument_list|()
argument_list|)
operator|.
name|isNotNull
argument_list|()
expr_stmt|;
name|Result
name|result
init|=
name|callAction
argument_list|(
name|controllers
operator|.
name|routes
operator|.
name|ref
operator|.
name|JavaController
operator|.
name|index
argument_list|()
argument_list|)
decl_stmt|;
name|assertThat
argument_list|(
name|status
argument_list|(
name|result
argument_list|)
argument_list|)
operator|.
name|isEqualTo
argument_list|(
name|SEE_OTHER
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Test
specifier|public
name|void
name|okWhenLoggedIn
parameter_list|()
block|{
name|running
argument_list|(
name|fakeApplication
argument_list|()
argument_list|,
operator|new
name|Runnable
argument_list|()
block|{
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
name|assertThat
argument_list|(
name|userServicePlugin
argument_list|()
argument_list|)
operator|.
name|isNotNull
argument_list|()
expr_stmt|;
name|Http
operator|.
name|Session
name|session
init|=
name|signupAndLogin
argument_list|()
decl_stmt|;
name|FakeRequest
name|request
init|=
name|fakeRequest
argument_list|()
decl_stmt|;
for|for
control|(
name|Map
operator|.
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|e
range|:
name|session
operator|.
name|entrySet
argument_list|()
control|)
block|{
name|request
operator|=
name|request
operator|.
name|withSession
argument_list|(
name|e
operator|.
name|getKey
argument_list|()
argument_list|,
name|e
operator|.
name|getValue
argument_list|()
argument_list|)
expr_stmt|;
block|}
name|Result
name|result
init|=
name|callAction
argument_list|(
name|controllers
operator|.
name|routes
operator|.
name|ref
operator|.
name|JavaController
operator|.
name|index
argument_list|()
argument_list|,
name|request
argument_list|)
decl_stmt|;
name|assertThat
argument_list|(
name|status
argument_list|(
name|result
argument_list|)
argument_list|)
operator|.
name|isEqualTo
argument_list|(
name|OK
argument_list|)
expr_stmt|;
block|}
block|}
argument_list|)
expr_stmt|;
block|}
specifier|private
name|Http
operator|.
name|Session
name|signupAndLogin
parameter_list|()
block|{
name|String
name|email
init|=
literal|"user@example.com"
decl_stmt|;
name|String
name|password
init|=
literal|"PaSSW0rd"
decl_stmt|;
block|{
comment|// Signup with a username/password
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|data
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
name|data
operator|.
name|put
argument_list|(
literal|"email"
argument_list|,
name|email
argument_list|)
expr_stmt|;
name|data
operator|.
name|put
argument_list|(
literal|"password"
argument_list|,
name|password
argument_list|)
expr_stmt|;
name|Result
name|result
init|=
name|callAction
argument_list|(
name|controllers
operator|.
name|routes
operator|.
name|ref
operator|.
name|Application
operator|.
name|doSignup
argument_list|()
argument_list|,
name|fakeRequest
argument_list|()
operator|.
name|withFormUrlEncodedBody
argument_list|(
name|data
argument_list|)
argument_list|)
decl_stmt|;
name|assertThat
argument_list|(
name|status
argument_list|(
name|result
argument_list|)
argument_list|)
operator|.
name|isEqualTo
argument_list|(
name|SEE_OTHER
argument_list|)
expr_stmt|;
block|}
block|{
comment|// Validate the token
name|String
name|token
init|=
name|upAuthProvider
argument_list|()
operator|.
name|getVerificationToken
argument_list|(
name|email
argument_list|)
decl_stmt|;
name|assertThat
argument_list|(
name|token
argument_list|)
operator|.
name|isNotNull
argument_list|()
expr_stmt|;
name|Logger
operator|.
name|info
argument_list|(
literal|"Verifying token: "
operator|+
name|token
argument_list|)
expr_stmt|;
name|Result
name|result
init|=
name|callAction
argument_list|(
name|controllers
operator|.
name|routes
operator|.
name|ref
operator|.
name|Application
operator|.
name|verify
argument_list|(
name|token
argument_list|)
argument_list|)
decl_stmt|;
name|assertThat
argument_list|(
name|status
argument_list|(
name|result
argument_list|)
argument_list|)
operator|.
name|isEqualTo
argument_list|(
name|SEE_OTHER
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|upAuthProvider
argument_list|()
operator|.
name|getVerificationToken
argument_list|(
name|email
argument_list|)
argument_list|)
operator|.
name|isNull
argument_list|()
expr_stmt|;
name|play
operator|.
name|api
operator|.
name|mvc
operator|.
name|Result
name|actualResult
init|=
name|actualResult
argument_list|(
name|result
argument_list|)
decl_stmt|;
comment|// We should actually be logged in here, but let's ignore that
comment|// as we want to test login too.
name|assertThat
argument_list|(
name|play
operator|.
name|api
operator|.
name|test
operator|.
name|Helpers
operator|.
name|redirectLocation
argument_list|(
name|actualResult
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"/"
argument_list|)
expr_stmt|;
block|}
block|{
comment|// Log the user in
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|data
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
name|data
operator|.
name|put
argument_list|(
literal|"email"
argument_list|,
name|email
argument_list|)
expr_stmt|;
name|data
operator|.
name|put
argument_list|(
literal|"password"
argument_list|,
name|password
argument_list|)
expr_stmt|;
name|Result
name|result
init|=
name|callAction
argument_list|(
name|controllers
operator|.
name|routes
operator|.
name|ref
operator|.
name|Application
operator|.
name|doLogin
argument_list|()
argument_list|,
name|fakeRequest
argument_list|()
operator|.
name|withFormUrlEncodedBody
argument_list|(
name|data
argument_list|)
argument_list|)
decl_stmt|;
name|play
operator|.
name|api
operator|.
name|mvc
operator|.
name|Result
name|actualResult
init|=
name|actualResult
argument_list|(
name|result
argument_list|)
decl_stmt|;
name|assertThat
argument_list|(
name|status
argument_list|(
name|result
argument_list|)
argument_list|)
operator|.
name|isEqualTo
argument_list|(
name|SEE_OTHER
argument_list|)
expr_stmt|;
name|assertThat
argument_list|(
name|play
operator|.
name|api
operator|.
name|test
operator|.
name|Helpers
operator|.
name|redirectLocation
argument_list|(
name|actualResult
argument_list|)
operator|.
name|get
argument_list|()
argument_list|)
operator|.
name|isEqualTo
argument_list|(
literal|"/"
argument_list|)
expr_stmt|;
comment|// Create a Java session from the Scala session
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|sessionData
init|=
name|asJavaMap
argument_list|(
name|play
operator|.
name|api
operator|.
name|test
operator|.
name|Helpers
operator|.
name|session
argument_list|(
name|actualResult
argument_list|)
operator|.
name|data
argument_list|()
argument_list|)
decl_stmt|;
return|return
operator|new
name|Http
operator|.
name|Session
argument_list|(
name|sessionData
argument_list|)
return|;
block|}
block|}
specifier|private
name|play
operator|.
name|api
operator|.
name|mvc
operator|.
name|Result
name|actualResult
parameter_list|(
name|Result
name|asyncResult
parameter_list|)
block|{
return|return
operator|(
operator|new
name|Promise
argument_list|<
name|play
operator|.
name|api
operator|.
name|mvc
operator|.
name|Result
argument_list|>
argument_list|(
operator|(
operator|(
name|play
operator|.
name|api
operator|.
name|mvc
operator|.
name|AsyncResult
operator|)
name|asyncResult
operator|.
name|getWrappedResult
argument_list|()
operator|)
operator|.
name|result
argument_list|()
argument_list|)
operator|)
operator|.
name|get
argument_list|()
return|;
block|}
specifier|private
name|TestUsernamePasswordAuthProvider
name|upAuthProvider
parameter_list|()
block|{
return|return
name|Play
operator|.
name|application
argument_list|()
operator|.
name|plugin
argument_list|(
name|TestUsernamePasswordAuthProvider
operator|.
name|class
argument_list|)
return|;
block|}
specifier|private
name|UserServicePlugin
name|userServicePlugin
parameter_list|()
block|{
return|return
name|Play
operator|.
name|application
argument_list|()
operator|.
name|plugin
argument_list|(
name|TestUserServicePlugin
operator|.
name|class
argument_list|)
return|;
block|}
block|}
end_class

end_unit

