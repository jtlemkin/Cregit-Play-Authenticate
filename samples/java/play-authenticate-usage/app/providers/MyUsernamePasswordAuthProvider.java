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
name|controllers
operator|.
name|routes
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
name|i18n
operator|.
name|Lang
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
name|lang
operator|.
name|reflect
operator|.
name|InvocationTargetException
import|;
end_import

begin_import
import|import
name|java
operator|.
name|lang
operator|.
name|reflect
operator|.
name|Method
import|;
end_import

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
import|import static
name|play
operator|.
name|data
operator|.
name|Form
operator|.
name|form
import|;
end_import

begin_class
annotation|@
name|Singleton
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
specifier|private
specifier|static
specifier|final
name|String
name|EMAIL_TEMPLATE_FALLBACK_LANGUAGE
init|=
literal|"en"
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
specifier|protected
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
specifier|public
name|void
name|setEmail
parameter_list|(
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
specifier|public
name|void
name|setPassword
parameter_list|(
name|String
name|password
parameter_list|)
block|{
name|this
operator|.
name|password
operator|=
name|password
expr_stmt|;
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
specifier|private
name|String
name|repeatPassword
decl_stmt|;
annotation|@
name|Required
specifier|private
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
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|void
name|setName
parameter_list|(
name|String
name|name
parameter_list|)
block|{
name|this
operator|.
name|name
operator|=
name|name
expr_stmt|;
block|}
specifier|public
name|String
name|getRepeatPassword
parameter_list|()
block|{
return|return
name|repeatPassword
return|;
block|}
specifier|public
name|void
name|setRepeatPassword
parameter_list|(
name|String
name|repeatPassword
parameter_list|)
block|{
name|this
operator|.
name|repeatPassword
operator|=
name|repeatPassword
expr_stmt|;
block|}
block|}
specifier|private
specifier|final
name|Form
argument_list|<
name|MySignup
argument_list|>
name|SIGNUP_FORM
decl_stmt|;
specifier|private
specifier|final
name|Form
argument_list|<
name|MyLogin
argument_list|>
name|LOGIN_FORM
decl_stmt|;
annotation|@
name|Inject
specifier|public
name|MyUsernamePasswordAuthProvider
parameter_list|(
specifier|final
name|PlayAuthenticate
name|auth
parameter_list|,
specifier|final
name|FormFactory
name|formFactory
parameter_list|,
specifier|final
name|ApplicationLifecycle
name|lifecycle
parameter_list|)
block|{
name|super
argument_list|(
name|auth
argument_list|,
name|lifecycle
argument_list|)
expr_stmt|;
name|this
operator|.
name|SIGNUP_FORM
operator|=
name|formFactory
operator|.
name|form
argument_list|(
name|MySignup
operator|.
name|class
argument_list|)
expr_stmt|;
name|this
operator|.
name|LOGIN_FORM
operator|=
name|formFactory
operator|.
name|form
argument_list|(
name|MyLogin
operator|.
name|class
argument_list|)
expr_stmt|;
block|}
specifier|public
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
specifier|public
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
name|findByUsernamePasswordIdentity
argument_list|(
name|user
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
parameter_list|,
specifier|final
name|Context
name|ctx
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
specifier|final
name|MyLogin
name|login
parameter_list|,
specifier|final
name|Context
name|ctx
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
name|MyLoginUsernamePasswordAuthUser
name|transformAuthUser
parameter_list|(
specifier|final
name|MyUsernamePasswordAuthUser
name|authUser
parameter_list|,
specifier|final
name|Context
name|context
parameter_list|)
block|{
return|return
operator|new
name|MyLoginUsernamePasswordAuthUser
argument_list|(
name|authUser
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
name|Lang
name|lang
init|=
name|Lang
operator|.
name|preferred
argument_list|(
name|ctx
operator|.
name|request
argument_list|()
operator|.
name|acceptLanguages
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|langCode
init|=
name|lang
operator|.
name|code
argument_list|()
decl_stmt|;
specifier|final
name|String
name|html
init|=
name|getEmailTemplate
argument_list|(
literal|"views.html.account.signup.email.verify_email"
argument_list|,
name|langCode
argument_list|,
name|url
argument_list|,
name|token
argument_list|,
name|user
operator|.
name|getName
argument_list|()
argument_list|,
name|user
operator|.
name|getEmail
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|text
init|=
name|getEmailTemplate
argument_list|(
literal|"views.txt.account.signup.email.verify_email"
argument_list|,
name|langCode
argument_list|,
name|url
argument_list|,
name|token
argument_list|,
name|user
operator|.
name|getName
argument_list|()
argument_list|,
name|user
operator|.
name|getEmail
argument_list|()
argument_list|)
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
name|Lang
name|lang
init|=
name|Lang
operator|.
name|preferred
argument_list|(
name|ctx
operator|.
name|request
argument_list|()
operator|.
name|acceptLanguages
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|langCode
init|=
name|lang
operator|.
name|code
argument_list|()
decl_stmt|;
specifier|final
name|String
name|html
init|=
name|getEmailTemplate
argument_list|(
literal|"views.html.account.email.password_reset"
argument_list|,
name|langCode
argument_list|,
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
decl_stmt|;
specifier|final
name|String
name|text
init|=
name|getEmailTemplate
argument_list|(
literal|"views.txt.account.email.password_reset"
argument_list|,
name|langCode
argument_list|,
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
name|String
name|getEmailTemplate
parameter_list|(
specifier|final
name|String
name|template
parameter_list|,
specifier|final
name|String
name|langCode
parameter_list|,
specifier|final
name|String
name|url
parameter_list|,
specifier|final
name|String
name|token
parameter_list|,
specifier|final
name|String
name|name
parameter_list|,
specifier|final
name|String
name|email
parameter_list|)
block|{
name|Class
argument_list|<
name|?
argument_list|>
name|cls
init|=
literal|null
decl_stmt|;
name|String
name|ret
init|=
literal|null
decl_stmt|;
try|try
block|{
name|cls
operator|=
name|Class
operator|.
name|forName
argument_list|(
name|template
operator|+
literal|"_"
operator|+
name|langCode
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
name|Logger
operator|.
name|warn
argument_list|(
literal|"Template: '"
operator|+
name|template
operator|+
literal|"_"
operator|+
name|langCode
operator|+
literal|"' was not found! Trying to use English fallback template instead."
argument_list|)
expr_stmt|;
block|}
if|if
condition|(
name|cls
operator|==
literal|null
condition|)
block|{
try|try
block|{
name|cls
operator|=
name|Class
operator|.
name|forName
argument_list|(
name|template
operator|+
literal|"_"
operator|+
name|EMAIL_TEMPLATE_FALLBACK_LANGUAGE
argument_list|)
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|ClassNotFoundException
name|e
parameter_list|)
block|{
name|Logger
operator|.
name|error
argument_list|(
literal|"Fallback template: '"
operator|+
name|template
operator|+
literal|"_"
operator|+
name|EMAIL_TEMPLATE_FALLBACK_LANGUAGE
operator|+
literal|"' was not found either!"
argument_list|)
expr_stmt|;
block|}
block|}
if|if
condition|(
name|cls
operator|!=
literal|null
condition|)
block|{
name|Method
name|htmlRender
init|=
literal|null
decl_stmt|;
try|try
block|{
name|htmlRender
operator|=
name|cls
operator|.
name|getMethod
argument_list|(
literal|"render"
argument_list|,
name|String
operator|.
name|class
argument_list|,
name|String
operator|.
name|class
argument_list|,
name|String
operator|.
name|class
argument_list|,
name|String
operator|.
name|class
argument_list|)
expr_stmt|;
name|ret
operator|=
name|htmlRender
operator|.
name|invoke
argument_list|(
literal|null
argument_list|,
name|url
argument_list|,
name|token
argument_list|,
name|name
argument_list|,
name|email
argument_list|)
operator|.
name|toString
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|NoSuchMethodException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|IllegalAccessException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
catch|catch
parameter_list|(
name|InvocationTargetException
name|e
parameter_list|)
block|{
name|e
operator|.
name|printStackTrace
argument_list|()
expr_stmt|;
block|}
block|}
return|return
name|ret
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
name|Lang
name|lang
init|=
name|Lang
operator|.
name|preferred
argument_list|(
name|ctx
operator|.
name|request
argument_list|()
operator|.
name|acceptLanguages
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|langCode
init|=
name|lang
operator|.
name|code
argument_list|()
decl_stmt|;
specifier|final
name|String
name|html
init|=
name|getEmailTemplate
argument_list|(
literal|"views.html.account.email.verify_email"
argument_list|,
name|langCode
argument_list|,
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
decl_stmt|;
specifier|final
name|String
name|text
init|=
name|getEmailTemplate
argument_list|(
literal|"views.txt.account.email.verify_email"
argument_list|,
name|langCode
argument_list|,
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

