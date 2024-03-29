begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|providers
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
name|mail
operator|.
name|Mailer
operator|.
name|Mail
operator|.
name|Body
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
name|mail
operator|.
name|Mailer
operator|.
name|MailerFactory
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
name|password
operator|.
name|UsernamePasswordAuthProvider
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
name|password
operator|.
name|UsernamePasswordAuthUser
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
name|data
operator|.
name|validation
operator|.
name|Constraints
operator|.
name|Email
import|;
end_import

begin_import
import|import
name|play
operator|.
name|data
operator|.
name|validation
operator|.
name|Constraints
operator|.
name|MinLength
import|;
end_import

begin_import
import|import
name|play
operator|.
name|data
operator|.
name|validation
operator|.
name|Constraints
operator|.
name|Required
import|;
end_import

begin_import
import|import
name|play
operator|.
name|inject
operator|.
name|ApplicationLifecycle
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Call
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
name|Context
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
name|Map
operator|.
name|Entry
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|UUID
import|;
end_import

begin_class
annotation|@
name|Singleton
specifier|public
class|class
name|TestUsernamePasswordAuthProvider
extends|extends
name|UsernamePasswordAuthProvider
argument_list|<
name|String
argument_list|,
name|TestUsernamePasswordAuthProvider
operator|.
name|LoginUser
argument_list|,
name|TestUsernamePasswordAuthProvider
operator|.
name|SignupUser
argument_list|,
name|TestUsernamePasswordAuthProvider
operator|.
name|Login
argument_list|,
name|TestUsernamePasswordAuthProvider
operator|.
name|Signup
argument_list|>
block|{
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|verifiedUsers
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|unverifiedUsers
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|Map
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|verificationTokens
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
specifier|private
specifier|final
name|FormFactory
name|formFactory
decl_stmt|;
annotation|@
name|Inject
specifier|public
name|TestUsernamePasswordAuthProvider
parameter_list|(
specifier|final
name|PlayAuthenticate
name|auth
parameter_list|,
specifier|final
name|ApplicationLifecycle
name|lifecycle
parameter_list|,
specifier|final
name|MailerFactory
name|mailerFactory
parameter_list|,
specifier|final
name|FormFactory
name|formFactory
parameter_list|)
block|{
name|super
argument_list|(
name|auth
argument_list|,
name|lifecycle
argument_list|,
name|mailerFactory
argument_list|)
expr_stmt|;
name|this
operator|.
name|formFactory
operator|=
name|formFactory
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Login
implements|implements
name|UsernamePasswordAuthProvider
operator|.
name|UsernamePassword
block|{
annotation|@
name|Required
annotation|@
name|Email
specifier|public
name|String
name|email
decl_stmt|;
annotation|@
name|Required
annotation|@
name|MinLength
argument_list|(
literal|5
argument_list|)
specifier|public
name|String
name|password
decl_stmt|;
annotation|@
name|Override
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|email
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPassword
parameter_list|()
block|{
return|return
name|password
return|;
block|}
block|}
specifier|public
specifier|static
class|class
name|Signup
extends|extends
name|Login
block|{ 	}
specifier|public
specifier|static
class|class
name|SignupUser
extends|extends
name|UsernamePasswordAuthUser
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|SignupUser
parameter_list|(
specifier|final
name|String
name|clearPassword
parameter_list|,
specifier|final
name|String
name|email
parameter_list|)
block|{
name|super
argument_list|(
name|clearPassword
argument_list|,
name|email
argument_list|)
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|LoginUser
extends|extends
name|UsernamePasswordAuthUser
block|{
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
specifier|public
name|LoginUser
parameter_list|(
specifier|final
name|String
name|email
parameter_list|)
block|{
name|super
argument_list|(
literal|null
argument_list|,
name|email
argument_list|)
expr_stmt|;
block|}
specifier|public
name|LoginUser
parameter_list|(
specifier|final
name|String
name|clearPassword
parameter_list|,
specifier|final
name|String
name|email
parameter_list|)
block|{
name|super
argument_list|(
name|clearPassword
argument_list|,
name|email
argument_list|)
expr_stmt|;
block|}
block|}
comment|// Only used for testing.
specifier|public
name|String
name|getVerificationToken
parameter_list|(
name|String
name|email
parameter_list|)
block|{
for|for
control|(
name|Entry
argument_list|<
name|String
argument_list|,
name|String
argument_list|>
name|e
range|:
name|verificationTokens
operator|.
name|entrySet
argument_list|()
control|)
block|{
if|if
condition|(
name|e
operator|.
name|getValue
argument_list|()
operator|.
name|equals
argument_list|(
name|email
argument_list|)
condition|)
block|{
return|return
name|e
operator|.
name|getKey
argument_list|()
return|;
block|}
block|}
return|return
literal|null
return|;
block|}
specifier|public
name|LoginUser
name|verifyWithToken
parameter_list|(
name|String
name|token
parameter_list|)
block|{
if|if
condition|(
name|verificationTokens
operator|.
name|containsKey
argument_list|(
name|token
argument_list|)
condition|)
block|{
specifier|final
name|String
name|email
init|=
name|verificationTokens
operator|.
name|get
argument_list|(
name|token
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|unverifiedUsers
operator|.
name|containsKey
argument_list|(
name|email
argument_list|)
condition|)
block|{
return|return
literal|null
return|;
block|}
specifier|final
name|String
name|hashedPassword
init|=
name|unverifiedUsers
operator|.
name|get
argument_list|(
name|email
argument_list|)
decl_stmt|;
name|verifiedUsers
operator|.
name|put
argument_list|(
name|email
argument_list|,
name|hashedPassword
argument_list|)
expr_stmt|;
name|verificationTokens
operator|.
name|remove
argument_list|(
name|token
argument_list|)
expr_stmt|;
name|unverifiedUsers
operator|.
name|remove
argument_list|(
name|email
argument_list|)
expr_stmt|;
return|return
operator|new
name|LoginUser
argument_list|(
name|email
argument_list|)
return|;
block|}
else|else
block|{
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Override
specifier|protected
name|String
name|generateVerificationRecord
parameter_list|(
name|SignupUser
name|user
parameter_list|)
block|{
specifier|final
name|String
name|token
init|=
name|UUID
operator|.
name|randomUUID
argument_list|()
operator|.
name|toString
argument_list|()
decl_stmt|;
name|verificationTokens
operator|.
name|put
argument_list|(
name|token
argument_list|,
name|user
operator|.
name|getEmail
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|token
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|getVerifyEmailMailingSubject
parameter_list|(
name|SignupUser
name|user
parameter_list|,
name|Context
name|ctx
parameter_list|)
block|{
return|return
literal|"Please verify your email address"
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Body
name|getVerifyEmailMailingBody
parameter_list|(
name|String
name|verificationRecord
parameter_list|,
name|SignupUser
name|user
parameter_list|,
name|Context
name|ctx
parameter_list|)
block|{
comment|// No human will ever look at this body, so make it simple
return|return
operator|new
name|Body
argument_list|(
name|verificationRecord
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|LoginUser
name|buildLoginAuthUser
parameter_list|(
name|Login
name|login
parameter_list|,
name|Context
name|ctx
parameter_list|)
block|{
return|return
operator|new
name|LoginUser
argument_list|(
name|login
operator|.
name|getPassword
argument_list|()
argument_list|,
name|login
operator|.
name|getEmail
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|LoginUser
name|transformAuthUser
parameter_list|(
name|SignupUser
name|signupUser
parameter_list|,
name|Context
name|context
parameter_list|)
block|{
return|return
operator|new
name|LoginUser
argument_list|(
name|signupUser
operator|.
name|getEmail
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|SignupUser
name|buildSignupAuthUser
parameter_list|(
name|Signup
name|signup
parameter_list|,
name|Context
name|ctx
parameter_list|)
block|{
return|return
operator|new
name|SignupUser
argument_list|(
name|signup
operator|.
name|getPassword
argument_list|()
argument_list|,
name|signup
operator|.
name|getEmail
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
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
name|password
operator|.
name|UsernamePasswordAuthProvider
operator|.
name|LoginResult
name|loginUser
parameter_list|(
name|LoginUser
name|user
parameter_list|)
block|{
specifier|final
name|String
name|e
init|=
name|user
operator|.
name|getEmail
argument_list|()
decl_stmt|;
if|if
condition|(
name|unverifiedUsers
operator|.
name|containsKey
argument_list|(
name|e
argument_list|)
condition|)
block|{
name|Logger
operator|.
name|debug
argument_list|(
name|e
operator|+
literal|" attempted to login but is still unverified."
argument_list|)
expr_stmt|;
return|return
name|LoginResult
operator|.
name|USER_UNVERIFIED
return|;
block|}
if|if
condition|(
operator|!
name|verifiedUsers
operator|.
name|containsKey
argument_list|(
name|e
argument_list|)
condition|)
block|{
name|Logger
operator|.
name|debug
argument_list|(
name|e
operator|+
literal|" attempted to login but was not found."
argument_list|)
expr_stmt|;
return|return
name|LoginResult
operator|.
name|NOT_FOUND
return|;
block|}
specifier|final
name|boolean
name|passwordCorrect
init|=
name|user
operator|.
name|checkPassword
argument_list|(
name|verifiedUsers
operator|.
name|get
argument_list|(
name|e
argument_list|)
argument_list|,
name|user
operator|.
name|getPassword
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|passwordCorrect
condition|)
block|{
name|Logger
operator|.
name|debug
argument_list|(
name|e
operator|+
literal|" provided an incorrect password."
argument_list|)
expr_stmt|;
return|return
name|LoginResult
operator|.
name|WRONG_PASSWORD
return|;
block|}
name|Logger
operator|.
name|debug
argument_list|(
name|e
operator|+
literal|" successfully authenticated."
argument_list|)
expr_stmt|;
return|return
name|LoginResult
operator|.
name|USER_LOGGED_IN
return|;
block|}
annotation|@
name|Override
specifier|protected
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
name|password
operator|.
name|UsernamePasswordAuthProvider
operator|.
name|SignupResult
name|signupUser
parameter_list|(
name|SignupUser
name|user
parameter_list|)
block|{
specifier|final
name|String
name|e
init|=
name|user
operator|.
name|getEmail
argument_list|()
decl_stmt|;
if|if
condition|(
name|verifiedUsers
operator|.
name|containsKey
argument_list|(
name|e
argument_list|)
condition|)
block|{
return|return
name|SignupResult
operator|.
name|USER_EXISTS
return|;
block|}
if|if
condition|(
name|unverifiedUsers
operator|.
name|containsKey
argument_list|(
name|e
argument_list|)
condition|)
block|{
return|return
name|SignupResult
operator|.
name|USER_EXISTS_UNVERIFIED
return|;
block|}
name|unverifiedUsers
operator|.
name|put
argument_list|(
name|user
operator|.
name|getEmail
argument_list|()
argument_list|,
name|user
operator|.
name|getHashedPassword
argument_list|()
argument_list|)
expr_stmt|;
return|return
name|SignupResult
operator|.
name|USER_CREATED_UNVERIFIED
return|;
block|}
specifier|protected
name|Form
argument_list|<
name|Signup
argument_list|>
name|getSignupForm
parameter_list|()
block|{
return|return
name|formFactory
operator|.
name|form
argument_list|(
name|Signup
operator|.
name|class
argument_list|)
return|;
block|}
specifier|protected
name|Form
argument_list|<
name|Login
argument_list|>
name|getLoginForm
parameter_list|()
block|{
return|return
name|formFactory
operator|.
name|form
argument_list|(
name|Login
operator|.
name|class
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Signup
name|getSignup
parameter_list|(
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
name|Context
operator|.
name|current
operator|.
name|set
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
specifier|final
name|Form
argument_list|<
name|Signup
argument_list|>
name|filledForm
init|=
name|getSignupForm
argument_list|()
operator|.
name|bindFromRequest
argument_list|()
decl_stmt|;
return|return
name|filledForm
operator|.
name|get
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Login
name|getLogin
parameter_list|(
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
comment|// TODO change to getLoginForm().bindFromRequest(request) after 2.1
name|Context
operator|.
name|current
operator|.
name|set
argument_list|(
name|ctx
argument_list|)
expr_stmt|;
specifier|final
name|Form
argument_list|<
name|Login
argument_list|>
name|filledForm
init|=
name|getLoginForm
argument_list|()
operator|.
name|bindFromRequest
argument_list|()
decl_stmt|;
return|return
name|filledForm
operator|.
name|get
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Call
name|userExists
parameter_list|(
name|UsernamePasswordAuthUser
name|authUser
parameter_list|)
block|{
return|return
name|controllers
operator|.
name|routes
operator|.
name|ApplicationController
operator|.
name|userExists
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Call
name|userUnverified
parameter_list|(
name|UsernamePasswordAuthUser
name|authUser
parameter_list|)
block|{
return|return
name|controllers
operator|.
name|routes
operator|.
name|ApplicationController
operator|.
name|userUnverified
argument_list|()
return|;
block|}
block|}
end_class

end_unit

