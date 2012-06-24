begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|providers
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Date
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

begin_import
import|import
name|models
operator|.
name|LinkedAccount
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
name|models
operator|.
name|UserActivation
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
name|i18n
operator|.
name|Messages
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
name|Controller
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
name|providers
operator|.
name|MyUsernamePasswordAuthProvider
operator|.
name|MyLogin
operator|.
name|LoginGroup
import|;
end_import

begin_import
import|import
name|providers
operator|.
name|MyUsernamePasswordAuthProvider
operator|.
name|MyLogin
operator|.
name|SignupGroup
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
name|UsernamePasswordAuthProvider
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
name|controllers
operator|.
name|routes
import|;
end_import

begin_class
specifier|public
class|class
name|MyUsernamePasswordAuthProvider
extends|extends
name|UsernamePasswordAuthProvider
argument_list|<
name|String
argument_list|,
name|MyLoginUsernamePasswordAuthUser
argument_list|,
name|MyUsernamePasswordAuthUser
argument_list|,
name|MyUsernamePasswordAuthProvider
operator|.
name|MyLogin
argument_list|,
name|MyUsernamePasswordAuthProvider
operator|.
name|MySignup
argument_list|>
block|{
specifier|public
specifier|static
class|class
name|MyLogin
implements|implements
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
name|UsernamePassword
block|{
specifier|public
specifier|static
interface|interface
name|SignupGroup
block|{ 		}
specifier|public
specifier|static
interface|interface
name|LoginGroup
block|{ 		}
annotation|@
name|Required
argument_list|(
name|groups
operator|=
block|{
name|SignupGroup
operator|.
name|class
block|,
name|LoginGroup
operator|.
name|class
block|}
argument_list|)
annotation|@
name|Email
argument_list|(
name|groups
operator|=
block|{
name|SignupGroup
operator|.
name|class
block|,
name|LoginGroup
operator|.
name|class
block|}
argument_list|)
specifier|public
name|String
name|email
decl_stmt|;
annotation|@
name|Required
argument_list|(
name|groups
operator|=
block|{
name|SignupGroup
operator|.
name|class
block|,
name|LoginGroup
operator|.
name|class
block|}
argument_list|)
annotation|@
name|MinLength
argument_list|(
name|value
operator|=
literal|5
argument_list|,
name|groups
operator|=
block|{
name|SignupGroup
operator|.
name|class
block|}
argument_list|)
specifier|public
name|String
name|password
decl_stmt|;
annotation|@
name|Required
argument_list|(
name|groups
operator|=
block|{
name|SignupGroup
operator|.
name|class
block|}
argument_list|)
annotation|@
name|MinLength
argument_list|(
name|value
operator|=
literal|5
argument_list|,
name|groups
operator|=
block|{
name|SignupGroup
operator|.
name|class
block|}
argument_list|)
specifier|public
name|String
name|repeatPassword
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
name|MySignup
extends|extends
name|MyLogin
block|{
annotation|@
name|Required
argument_list|(
name|groups
operator|=
block|{
name|SignupGroup
operator|.
name|class
block|}
argument_list|)
specifier|public
name|String
name|name
decl_stmt|;
specifier|public
name|String
name|validate
parameter_list|()
block|{
if|if
condition|(
operator|!
name|password
operator|.
name|equals
argument_list|(
name|repeatPassword
argument_list|)
condition|)
block|{
return|return
literal|"Passwords don't match!"
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
name|Form
argument_list|<
name|MySignup
argument_list|>
name|SIGNUP_FORM
init|=
name|Controller
operator|.
name|form
argument_list|(
name|MySignup
operator|.
name|class
argument_list|,
name|SignupGroup
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|Form
argument_list|<
name|MyLogin
argument_list|>
name|LOGIN_FORM
init|=
name|Controller
operator|.
name|form
argument_list|(
name|MyLogin
operator|.
name|class
argument_list|,
name|LoginGroup
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
name|MyUsernamePasswordAuthProvider
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
specifier|protected
name|Form
argument_list|<
name|MySignup
argument_list|>
name|getSignupForm
parameter_list|()
block|{
return|return
name|SIGNUP_FORM
return|;
block|}
specifier|protected
name|Form
argument_list|<
name|MyLogin
argument_list|>
name|getLoginForm
parameter_list|()
block|{
return|return
name|LOGIN_FORM
return|;
block|}
annotation|@
name|Override
specifier|protected
name|SignupResult
name|signupUser
parameter_list|(
specifier|final
name|MyUsernamePasswordAuthUser
name|user
parameter_list|)
block|{
specifier|final
name|User
name|u
init|=
name|User
operator|.
name|findByEmail
argument_list|(
name|user
operator|.
name|getEmail
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|!=
literal|null
condition|)
block|{
if|if
condition|(
name|u
operator|.
name|emailValidated
condition|)
block|{
comment|// This user exists, has its email validated and is active
return|return
name|SignupResult
operator|.
name|USER_EXISTS
return|;
block|}
else|else
block|{
comment|// this user exists, is active but has not yet validated its
comment|// email
return|return
name|SignupResult
operator|.
name|USER_EXISTS_UNVERIFIED
return|;
block|}
block|}
comment|// The user either does not exist or is inactive - create a new one
annotation|@
name|SuppressWarnings
argument_list|(
literal|"unused"
argument_list|)
specifier|final
name|User
name|newUser
init|=
name|User
operator|.
name|create
argument_list|(
name|user
argument_list|)
decl_stmt|;
comment|// Usually the email should be verified before allowing login, however
comment|// if you return
comment|// return SignupResult.USER_CREATED;
comment|// then the user gets logged in directly
return|return
name|SignupResult
operator|.
name|USER_CREATED_UNVERIFIED
return|;
block|}
annotation|@
name|Override
specifier|protected
name|LoginResult
name|loginUser
parameter_list|(
specifier|final
name|MyLoginUsernamePasswordAuthUser
name|authUser
parameter_list|)
block|{
specifier|final
name|User
name|u
init|=
name|User
operator|.
name|findByUsernamePasswordIdentity
argument_list|(
name|authUser
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|==
literal|null
condition|)
block|{
return|return
name|LoginResult
operator|.
name|NOT_FOUND
return|;
block|}
else|else
block|{
if|if
condition|(
operator|!
name|u
operator|.
name|emailValidated
condition|)
block|{
return|return
name|LoginResult
operator|.
name|USER_UNVERIFIED
return|;
block|}
else|else
block|{
for|for
control|(
specifier|final
name|LinkedAccount
name|acc
range|:
name|u
operator|.
name|linkedAccounts
control|)
block|{
if|if
condition|(
name|getKey
argument_list|()
operator|.
name|equals
argument_list|(
name|acc
operator|.
name|providerKey
argument_list|)
condition|)
block|{
if|if
condition|(
name|authUser
operator|.
name|checkPassword
argument_list|(
name|acc
operator|.
name|providerUserId
argument_list|,
name|authUser
operator|.
name|getPassword
argument_list|()
argument_list|)
condition|)
block|{
comment|// Password was correct
return|return
name|LoginResult
operator|.
name|USER_LOGGED_IN
return|;
block|}
else|else
block|{
comment|// if you don't return here,
comment|// you would allow the user to have
comment|// multiple passwords defined
comment|// usually we don't want this
return|return
name|LoginResult
operator|.
name|WRONG_PASSWORD
return|;
block|}
block|}
block|}
return|return
name|LoginResult
operator|.
name|WRONG_PASSWORD
return|;
block|}
block|}
block|}
annotation|@
name|Override
specifier|protected
name|Call
name|userExists
parameter_list|(
specifier|final
name|UsernamePasswordAuthUser
name|authUser
parameter_list|)
block|{
return|return
name|routes
operator|.
name|Signup
operator|.
name|exists
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Call
name|userUnverified
parameter_list|(
specifier|final
name|UsernamePasswordAuthUser
name|authUser
parameter_list|)
block|{
return|return
name|routes
operator|.
name|Signup
operator|.
name|unverified
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|protected
name|MyUsernamePasswordAuthUser
name|buildSignupAuthUser
parameter_list|(
specifier|final
name|MySignup
name|signup
parameter_list|)
block|{
return|return
operator|new
name|MyUsernamePasswordAuthUser
argument_list|(
name|signup
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|MyLoginUsernamePasswordAuthUser
name|buildLoginAuthUser
parameter_list|(
name|MyLogin
name|login
parameter_list|)
block|{
return|return
operator|new
name|MyLoginUsernamePasswordAuthUser
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
name|String
name|getVerifyEmailMailingSubject
parameter_list|(
specifier|final
name|MyUsernamePasswordAuthUser
name|user
parameter_list|,
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
return|return
name|Messages
operator|.
name|get
argument_list|(
literal|"password.verify_email.subject"
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|String
name|onLoginUserNotFound
parameter_list|(
specifier|final
name|Context
name|context
parameter_list|)
block|{
name|context
operator|.
name|flash
argument_list|()
operator|.
name|put
argument_list|(
literal|"error"
argument_list|,
literal|"User could not be found or password was wrong."
argument_list|)
expr_stmt|;
return|return
name|super
operator|.
name|onLoginUserNotFound
argument_list|(
name|context
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|protected
name|Body
name|getVerifyEmailMailingBody
parameter_list|(
specifier|final
name|String
name|token
parameter_list|,
specifier|final
name|MyUsernamePasswordAuthUser
name|user
parameter_list|,
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
specifier|final
name|String
name|url
init|=
name|routes
operator|.
name|Signup
operator|.
name|verify
argument_list|(
name|token
argument_list|)
operator|.
name|absoluteURL
argument_list|(
name|ctx
operator|.
name|request
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|html
init|=
name|views
operator|.
name|html
operator|.
name|account
operator|.
name|signup
operator|.
name|verify_email_body
operator|.
name|render
argument_list|(
name|url
argument_list|,
name|token
argument_list|,
name|user
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
specifier|final
name|String
name|text
init|=
name|views
operator|.
name|txt
operator|.
name|account
operator|.
name|signup
operator|.
name|verify_email_body
operator|.
name|render
argument_list|(
name|url
argument_list|,
name|token
argument_list|,
name|user
operator|.
name|getName
argument_list|()
argument_list|)
operator|.
name|toString
argument_list|()
decl_stmt|;
return|return
operator|new
name|Body
argument_list|(
name|text
argument_list|,
name|html
argument_list|)
return|;
block|}
comment|/** 	 * Verification time frame (until the user clicks on the link in the email) 	 * in seconds 	 * Defaults to one week 	 */
specifier|final
specifier|static
name|long
name|VERIFICATION_TIME
init|=
literal|7
operator|*
literal|24
operator|*
literal|3600
decl_stmt|;
annotation|@
name|Override
specifier|protected
name|String
name|generateVerificationRecord
parameter_list|(
specifier|final
name|MyUsernamePasswordAuthUser
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
comment|// Do database actions, etc.
specifier|final
name|UserActivation
name|ua
init|=
operator|new
name|UserActivation
argument_list|()
decl_stmt|;
name|ua
operator|.
name|unverified
operator|=
name|User
operator|.
name|findByAuthUserIdentity
argument_list|(
name|user
argument_list|)
expr_stmt|;
name|ua
operator|.
name|token
operator|=
name|token
expr_stmt|;
specifier|final
name|Date
name|expirationDate
init|=
operator|new
name|Date
argument_list|()
decl_stmt|;
name|expirationDate
operator|.
name|setTime
argument_list|(
name|System
operator|.
name|currentTimeMillis
argument_list|()
operator|+
name|VERIFICATION_TIME
operator|*
literal|1000
argument_list|)
expr_stmt|;
name|ua
operator|.
name|expires
operator|=
name|expirationDate
expr_stmt|;
name|ua
operator|.
name|save
argument_list|()
expr_stmt|;
return|return
name|token
return|;
block|}
block|}
end_class

end_unit
