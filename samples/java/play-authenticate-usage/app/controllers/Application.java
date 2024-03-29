begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|controllers
package|;
end_package

begin_import
import|import
name|be
operator|.
name|objectify
operator|.
name|deadbolt
operator|.
name|java
operator|.
name|actions
operator|.
name|Group
import|;
end_import

begin_import
import|import
name|be
operator|.
name|objectify
operator|.
name|deadbolt
operator|.
name|java
operator|.
name|actions
operator|.
name|Restrict
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
name|models
operator|.
name|User
import|;
end_import

begin_import
import|import
name|play
operator|.
name|data
operator|.
name|Form
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Controller
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
name|providers
operator|.
name|MyUsernamePasswordAuthProvider
import|;
end_import

begin_import
import|import
name|providers
operator|.
name|MyUsernamePasswordAuthProvider
operator|.
name|MyLogin
import|;
end_import

begin_import
import|import
name|providers
operator|.
name|MyUsernamePasswordAuthProvider
operator|.
name|MySignup
import|;
end_import

begin_import
import|import
name|service
operator|.
name|UserProvider
import|;
end_import

begin_import
import|import
name|views
operator|.
name|html
operator|.
name|*
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
name|java
operator|.
name|text
operator|.
name|SimpleDateFormat
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
import|;
end_import

begin_class
specifier|public
class|class
name|Application
extends|extends
name|Controller
block|{
specifier|public
specifier|static
specifier|final
name|String
name|FLASH_MESSAGE_KEY
init|=
literal|"message"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|FLASH_ERROR_KEY
init|=
literal|"error"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|USER_ROLE
init|=
literal|"user"
decl_stmt|;
specifier|private
specifier|final
name|PlayAuthenticate
name|auth
decl_stmt|;
specifier|private
specifier|final
name|MyUsernamePasswordAuthProvider
name|provider
decl_stmt|;
specifier|private
specifier|final
name|UserProvider
name|userProvider
decl_stmt|;
specifier|public
specifier|static
name|String
name|formatTimestamp
parameter_list|(
specifier|final
name|long
name|t
parameter_list|)
block|{
return|return
operator|new
name|SimpleDateFormat
argument_list|(
literal|"yyyy-dd-MM HH:mm:ss"
argument_list|)
operator|.
name|format
argument_list|(
operator|new
name|Date
argument_list|(
name|t
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Inject
specifier|public
name|Application
parameter_list|(
specifier|final
name|PlayAuthenticate
name|auth
parameter_list|,
specifier|final
name|MyUsernamePasswordAuthProvider
name|provider
parameter_list|,
specifier|final
name|UserProvider
name|userProvider
parameter_list|)
block|{
name|this
operator|.
name|auth
operator|=
name|auth
expr_stmt|;
name|this
operator|.
name|provider
operator|=
name|provider
expr_stmt|;
name|this
operator|.
name|userProvider
operator|=
name|userProvider
expr_stmt|;
block|}
specifier|public
name|Result
name|index
parameter_list|()
block|{
return|return
name|ok
argument_list|(
name|index
operator|.
name|render
argument_list|(
name|this
operator|.
name|userProvider
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Restrict
argument_list|(
annotation|@
name|Group
argument_list|(
name|Application
operator|.
name|USER_ROLE
argument_list|)
argument_list|)
specifier|public
name|Result
name|restricted
parameter_list|()
block|{
specifier|final
name|User
name|localUser
init|=
name|this
operator|.
name|userProvider
operator|.
name|getUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|ok
argument_list|(
name|restricted
operator|.
name|render
argument_list|(
name|this
operator|.
name|userProvider
argument_list|,
name|localUser
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Restrict
argument_list|(
annotation|@
name|Group
argument_list|(
name|Application
operator|.
name|USER_ROLE
argument_list|)
argument_list|)
specifier|public
name|Result
name|profile
parameter_list|()
block|{
specifier|final
name|User
name|localUser
init|=
name|userProvider
operator|.
name|getUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
return|return
name|ok
argument_list|(
name|profile
operator|.
name|render
argument_list|(
name|this
operator|.
name|auth
argument_list|,
name|this
operator|.
name|userProvider
argument_list|,
name|localUser
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|Result
name|login
parameter_list|()
block|{
return|return
name|ok
argument_list|(
name|login
operator|.
name|render
argument_list|(
name|this
operator|.
name|auth
argument_list|,
name|this
operator|.
name|userProvider
argument_list|,
name|this
operator|.
name|provider
operator|.
name|getLoginForm
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|Result
name|doLogin
parameter_list|()
block|{
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
operator|.
name|noCache
argument_list|(
name|response
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|Form
argument_list|<
name|MyLogin
argument_list|>
name|filledForm
init|=
name|this
operator|.
name|provider
operator|.
name|getLoginForm
argument_list|()
operator|.
name|bindFromRequest
argument_list|()
decl_stmt|;
if|if
condition|(
name|filledForm
operator|.
name|hasErrors
argument_list|()
condition|)
block|{
comment|// User did not fill everything properly
return|return
name|badRequest
argument_list|(
name|login
operator|.
name|render
argument_list|(
name|this
operator|.
name|auth
argument_list|,
name|this
operator|.
name|userProvider
argument_list|,
name|filledForm
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
comment|// Everything was filled
return|return
name|this
operator|.
name|provider
operator|.
name|handleLogin
argument_list|(
name|ctx
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
name|Result
name|signup
parameter_list|()
block|{
return|return
name|ok
argument_list|(
name|signup
operator|.
name|render
argument_list|(
name|this
operator|.
name|auth
argument_list|,
name|this
operator|.
name|userProvider
argument_list|,
name|this
operator|.
name|provider
operator|.
name|getSignupForm
argument_list|()
argument_list|)
argument_list|)
return|;
block|}
specifier|public
name|Result
name|jsRoutes
parameter_list|()
block|{
return|return
name|ok
argument_list|(
name|play
operator|.
name|routing
operator|.
name|JavaScriptReverseRouter
operator|.
name|create
argument_list|(
literal|"jsRoutes"
argument_list|,
name|routes
operator|.
name|javascript
operator|.
name|Signup
operator|.
name|forgotPassword
argument_list|()
argument_list|)
argument_list|)
operator|.
name|as
argument_list|(
literal|"text/javascript"
argument_list|)
return|;
block|}
specifier|public
name|Result
name|doSignup
parameter_list|()
block|{
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
operator|.
name|noCache
argument_list|(
name|response
argument_list|()
argument_list|)
expr_stmt|;
specifier|final
name|Form
argument_list|<
name|MySignup
argument_list|>
name|filledForm
init|=
name|this
operator|.
name|provider
operator|.
name|getSignupForm
argument_list|()
operator|.
name|bindFromRequest
argument_list|()
decl_stmt|;
if|if
condition|(
name|filledForm
operator|.
name|hasErrors
argument_list|()
condition|)
block|{
comment|// User did not fill everything properly
return|return
name|badRequest
argument_list|(
name|signup
operator|.
name|render
argument_list|(
name|this
operator|.
name|auth
argument_list|,
name|this
operator|.
name|userProvider
argument_list|,
name|filledForm
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
comment|// Everything was filled
comment|// do something with your part of the form before handling the user
comment|// signup
return|return
name|this
operator|.
name|provider
operator|.
name|handleSignup
argument_list|(
name|ctx
argument_list|()
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

