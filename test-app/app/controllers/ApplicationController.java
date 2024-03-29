begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|controllers
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
name|PlayAuthenticate
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
name|data
operator|.
name|Form
import|;
end_import

begin_import
import|import
name|play
operator|.
name|data
operator|.
name|FormFactory
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
name|TestUsernamePasswordAuthProvider
import|;
end_import

begin_import
import|import
name|providers
operator|.
name|TestUsernamePasswordAuthProvider
operator|.
name|Login
import|;
end_import

begin_import
import|import
name|providers
operator|.
name|TestUsernamePasswordAuthProvider
operator|.
name|Signup
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

begin_class
annotation|@
name|Singleton
specifier|public
class|class
name|ApplicationController
extends|extends
name|Controller
block|{
annotation|@
name|Inject
name|FormFactory
name|formFactory
decl_stmt|;
specifier|public
specifier|final
name|String
name|FLASH_ERROR_KEY
init|=
literal|"error"
decl_stmt|;
specifier|private
name|TestUsernamePasswordAuthProvider
name|testProvider
decl_stmt|;
annotation|@
name|Inject
specifier|public
name|ApplicationController
parameter_list|(
name|TestUsernamePasswordAuthProvider
name|testProvider
parameter_list|)
block|{
name|this
operator|.
name|testProvider
operator|=
name|testProvider
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
name|views
operator|.
name|html
operator|.
name|index
operator|.
name|render
argument_list|(
name|testProvider
operator|.
name|getAuth
argument_list|()
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
name|views
operator|.
name|html
operator|.
name|login
operator|.
name|render
argument_list|(
name|formFactory
operator|.
name|form
argument_list|(
name|Login
operator|.
name|class
argument_list|)
operator|.
name|bindFromRequest
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
name|Login
argument_list|>
name|filledForm
init|=
name|formFactory
operator|.
name|form
argument_list|(
name|Login
operator|.
name|class
argument_list|)
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
name|views
operator|.
name|html
operator|.
name|login
operator|.
name|render
argument_list|(
name|filledForm
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
comment|// Everything was filled
return|return
name|testProvider
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
name|views
operator|.
name|html
operator|.
name|signup
operator|.
name|render
argument_list|(
name|formFactory
operator|.
name|form
argument_list|(
name|Signup
operator|.
name|class
argument_list|)
operator|.
name|bindFromRequest
argument_list|()
argument_list|)
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
name|Signup
argument_list|>
name|filledForm
init|=
name|formFactory
operator|.
name|form
argument_list|(
name|Signup
operator|.
name|class
argument_list|)
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
name|views
operator|.
name|html
operator|.
name|signup
operator|.
name|render
argument_list|(
name|filledForm
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
comment|// Everything was filled
return|return
name|testProvider
operator|.
name|handleSignup
argument_list|(
name|ctx
argument_list|()
argument_list|)
return|;
block|}
block|}
specifier|public
name|Result
name|userExists
parameter_list|()
block|{
return|return
name|badRequest
argument_list|(
literal|"User exists."
argument_list|)
return|;
block|}
specifier|public
name|Result
name|userUnverified
parameter_list|()
block|{
return|return
name|badRequest
argument_list|(
literal|"User not yet verified."
argument_list|)
return|;
block|}
specifier|public
name|Result
name|verify
parameter_list|(
name|String
name|token
parameter_list|)
block|{
name|TestUsernamePasswordAuthProvider
operator|.
name|LoginUser
name|loginUser
init|=
name|this
operator|.
name|testProvider
operator|.
name|verifyWithToken
argument_list|(
name|token
argument_list|)
decl_stmt|;
if|if
condition|(
name|loginUser
operator|==
literal|null
condition|)
block|{
return|return
name|notFound
argument_list|()
return|;
block|}
return|return
name|testProvider
operator|.
name|getAuth
argument_list|()
operator|.
name|loginAndRedirect
argument_list|(
name|ctx
argument_list|()
argument_list|,
name|loginUser
argument_list|)
return|;
block|}
specifier|public
name|Result
name|oAuthDenied
parameter_list|(
name|String
name|providerKey
parameter_list|)
block|{
name|flash
argument_list|(
name|FLASH_ERROR_KEY
argument_list|,
literal|"You need to accept the OAuth connection"
operator|+
literal|" in order to use this website!"
argument_list|)
expr_stmt|;
return|return
name|redirect
argument_list|(
name|routes
operator|.
name|ApplicationController
operator|.
name|index
argument_list|()
argument_list|)
return|;
block|}
block|}
end_class

end_unit

