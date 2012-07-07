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
name|ArrayList
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|List
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
name|TokenAction
import|;
end_import

begin_import
import|import
name|models
operator|.
name|TokenAction
operator|.
name|Type
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
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_VERIFICATION_LINK_SECURE
init|=
name|SETTING_KEY_MAIL
operator|+
literal|"."
operator|+
literal|"verificationLink.secure"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_PASSWORD_RESET_LINK_SECURE
init|=
name|SETTING_KEY_MAIL
operator|+
literal|"."
operator|+
literal|"passwordResetLink.secure"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_LINK_LOGIN_AFTER_PASSWORD_RESET
init|=
literal|"loginAfterPasswordReset"
decl_stmt|;
annotation|@
name|Override
specifier|protected
name|List
argument_list|<
name|String
argument_list|>
name|neededSettingKeys
parameter_list|()
block|{
specifier|final
name|List
argument_list|<
name|String
argument_list|>
name|needed
init|=
operator|new
name|ArrayList
argument_list|<
name|String
argument_list|>
argument_list|(
name|super
operator|.
name|neededSettingKeys
argument_list|()
argument_list|)
decl_stmt|;
name|needed
operator|.
name|add
argument_list|(
name|SETTING_KEY_VERIFICATION_LINK_SECURE
argument_list|)
expr_stmt|;
name|needed
operator|.
name|add
argument_list|(
name|SETTING_KEY_PASSWORD_RESET_LINK_SECURE
argument_list|)
expr_stmt|;
name|needed
operator|.
name|add
argument_list|(
name|SETTING_KEY_LINK_LOGIN_AFTER_PASSWORD_RESET
argument_list|)
expr_stmt|;
return|return
name|needed
return|;
block|}
specifier|public
specifier|static
name|MyUsernamePasswordAuthProvider
name|getProvider
parameter_list|()
block|{
return|return
operator|(
name|MyUsernamePasswordAuthProvider
operator|)
name|PlayAuthenticate
operator|.
name|getProvider
argument_list|(
name|UsernamePasswordAuthProvider
operator|.
name|PROVIDER_KEY
argument_list|)
return|;
block|}
specifier|public
specifier|static
class|class
name|MyIdentity
block|{
specifier|public
name|MyIdentity
parameter_list|()
block|{ 		}
specifier|public
name|MyIdentity
parameter_list|(
specifier|final
name|String
name|email
parameter_list|)
block|{
name|this
operator|.
name|email
operator|=
name|email
expr_stmt|;
block|}
annotation|@
name|Required
annotation|@
name|Email
specifier|public
name|String
name|email
decl_stmt|;
block|}
specifier|public
specifier|static
class|class
name|MyLogin
extends|extends
name|MyIdentity
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
name|MySignup
extends|extends
name|MyLogin
block|{
annotation|@
name|Required
annotation|@
name|MinLength
argument_list|(
literal|5
argument_list|)
specifier|public
name|String
name|repeatPassword
decl_stmt|;
annotation|@
name|Required
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
name|password
operator|==
literal|null
operator|||
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
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.password.signup.error.passwords_not_same"
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
specifier|public
specifier|static
specifier|final
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
argument_list|)
decl_stmt|;
specifier|public
specifier|static
specifier|final
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
literal|"playauthenticate.password.verify_signup.subject"
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
name|controllers
operator|.
name|Application
operator|.
name|FLASH_ERROR_KEY
argument_list|,
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.password.login.unknown_user_or_pw"
argument_list|)
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
name|boolean
name|isSecure
init|=
name|getConfiguration
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|SETTING_KEY_VERIFICATION_LINK_SECURE
argument_list|)
decl_stmt|;
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
argument_list|,
name|isSecure
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
name|email
operator|.
name|verify_email
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
name|email
operator|.
name|verify_email
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
specifier|private
specifier|static
name|String
name|generateToken
parameter_list|()
block|{
return|return
name|UUID
operator|.
name|randomUUID
argument_list|()
operator|.
name|toString
argument_list|()
return|;
block|}
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
return|return
name|generateVerificationRecord
argument_list|(
name|User
operator|.
name|findByAuthUserIdentity
argument_list|(
name|user
argument_list|)
argument_list|)
return|;
block|}
specifier|protected
name|String
name|generateVerificationRecord
parameter_list|(
specifier|final
name|User
name|user
parameter_list|)
block|{
specifier|final
name|String
name|token
init|=
name|generateToken
argument_list|()
decl_stmt|;
comment|// Do database actions, etc.
name|TokenAction
operator|.
name|create
argument_list|(
name|Type
operator|.
name|EMAIL_VERIFICATION
argument_list|,
name|token
argument_list|,
name|user
argument_list|)
expr_stmt|;
return|return
name|token
return|;
block|}
specifier|protected
name|String
name|generatePasswordResetRecord
parameter_list|(
specifier|final
name|User
name|u
parameter_list|)
block|{
specifier|final
name|String
name|token
init|=
name|generateToken
argument_list|()
decl_stmt|;
name|TokenAction
operator|.
name|create
argument_list|(
name|Type
operator|.
name|PASSWORD_RESET
argument_list|,
name|token
argument_list|,
name|u
argument_list|)
expr_stmt|;
return|return
name|token
return|;
block|}
specifier|protected
name|String
name|getPasswordResetMailingSubject
parameter_list|(
specifier|final
name|User
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
literal|"playauthenticate.password.reset_email.subject"
argument_list|)
return|;
block|}
specifier|protected
name|Body
name|getPasswordResetMailingBody
parameter_list|(
specifier|final
name|String
name|token
parameter_list|,
specifier|final
name|User
name|user
parameter_list|,
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
specifier|final
name|boolean
name|isSecure
init|=
name|getConfiguration
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|SETTING_KEY_PASSWORD_RESET_LINK_SECURE
argument_list|)
decl_stmt|;
specifier|final
name|String
name|url
init|=
name|routes
operator|.
name|Signup
operator|.
name|resetPassword
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
argument_list|,
name|isSecure
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
name|email
operator|.
name|password_reset
operator|.
name|render
argument_list|(
name|url
argument_list|,
name|token
argument_list|,
name|user
operator|.
name|name
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
name|email
operator|.
name|password_reset
operator|.
name|render
argument_list|(
name|url
argument_list|,
name|token
argument_list|,
name|user
operator|.
name|name
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
specifier|public
name|void
name|sendPasswordResetMailing
parameter_list|(
specifier|final
name|User
name|user
parameter_list|,
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
specifier|final
name|String
name|token
init|=
name|generatePasswordResetRecord
argument_list|(
name|user
argument_list|)
decl_stmt|;
specifier|final
name|String
name|subject
init|=
name|getPasswordResetMailingSubject
argument_list|(
name|user
argument_list|,
name|ctx
argument_list|)
decl_stmt|;
specifier|final
name|Body
name|body
init|=
name|getPasswordResetMailingBody
argument_list|(
name|token
argument_list|,
name|user
argument_list|,
name|ctx
argument_list|)
decl_stmt|;
name|mailer
operator|.
name|sendMail
argument_list|(
name|subject
argument_list|,
name|body
argument_list|,
name|getEmailName
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
name|boolean
name|isLoginAfterPasswordReset
parameter_list|()
block|{
return|return
name|getConfiguration
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|SETTING_KEY_LINK_LOGIN_AFTER_PASSWORD_RESET
argument_list|)
return|;
block|}
specifier|protected
name|String
name|getVerifyEmailMailingSubjectAfterSignup
parameter_list|(
specifier|final
name|User
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
literal|"playauthenticate.password.verify_email.subject"
argument_list|)
return|;
block|}
specifier|protected
name|Body
name|getVerifyEmailMailingBodyAfterSignup
parameter_list|(
specifier|final
name|String
name|token
parameter_list|,
specifier|final
name|User
name|user
parameter_list|,
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
specifier|final
name|boolean
name|isSecure
init|=
name|getConfiguration
argument_list|()
operator|.
name|getBoolean
argument_list|(
name|SETTING_KEY_VERIFICATION_LINK_SECURE
argument_list|)
decl_stmt|;
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
argument_list|,
name|isSecure
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
name|email
operator|.
name|verify_email
operator|.
name|render
argument_list|(
name|url
argument_list|,
name|token
argument_list|,
name|user
operator|.
name|name
argument_list|,
name|user
operator|.
name|email
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
name|email
operator|.
name|verify_email
operator|.
name|render
argument_list|(
name|url
argument_list|,
name|token
argument_list|,
name|user
operator|.
name|name
argument_list|,
name|user
operator|.
name|email
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
specifier|public
name|void
name|sendVerifyEmailMailingAfterSignup
parameter_list|(
specifier|final
name|User
name|user
parameter_list|,
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
specifier|final
name|String
name|subject
init|=
name|getVerifyEmailMailingSubjectAfterSignup
argument_list|(
name|user
argument_list|,
name|ctx
argument_list|)
decl_stmt|;
specifier|final
name|String
name|token
init|=
name|generateVerificationRecord
argument_list|(
name|user
argument_list|)
decl_stmt|;
specifier|final
name|Body
name|body
init|=
name|getVerifyEmailMailingBodyAfterSignup
argument_list|(
name|token
argument_list|,
name|user
argument_list|,
name|ctx
argument_list|)
decl_stmt|;
name|mailer
operator|.
name|sendMail
argument_list|(
name|subject
argument_list|,
name|body
argument_list|,
name|getEmailName
argument_list|(
name|user
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|private
name|String
name|getEmailName
parameter_list|(
specifier|final
name|User
name|user
parameter_list|)
block|{
return|return
name|getEmailName
argument_list|(
name|user
operator|.
name|email
argument_list|,
name|user
operator|.
name|name
argument_list|)
return|;
block|}
block|}
end_class

end_unit

