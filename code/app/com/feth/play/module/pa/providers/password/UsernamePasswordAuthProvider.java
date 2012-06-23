begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
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
package|;
end_package

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
name|Configuration
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
name|libs
operator|.
name|Akka
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
name|play
operator|.
name|mvc
operator|.
name|Http
operator|.
name|Request
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
name|akka
operator|.
name|util
operator|.
name|Duration
import|;
end_import

begin_import
import|import
name|akka
operator|.
name|util
operator|.
name|FiniteDuration
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
name|exceptions
operator|.
name|AuthException
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
name|AuthProvider
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
name|user
operator|.
name|AuthUser
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
name|NameIdentity
import|;
end_import

begin_import
import|import
name|com
operator|.
name|typesafe
operator|.
name|plugin
operator|.
name|MailerAPI
import|;
end_import

begin_import
import|import
name|com
operator|.
name|typesafe
operator|.
name|plugin
operator|.
name|MailerPlugin
import|;
end_import

begin_class
specifier|public
specifier|abstract
class|class
name|UsernamePasswordAuthProvider
parameter_list|<
name|R
parameter_list|,
name|UL
extends|extends
name|UsernamePasswordAuthUser
parameter_list|,
name|US
extends|extends
name|UsernamePasswordAuthUser
parameter_list|,
name|L
extends|extends
name|UsernamePasswordAuthProvider
operator|.
name|UsernamePassword
parameter_list|,
name|S
extends|extends
name|UsernamePasswordAuthProvider
operator|.
name|UsernamePassword
parameter_list|>
extends|extends
name|AuthProvider
block|{
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_MAIL
init|=
literal|"mail"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_MAIL_FROM_EMAIL
init|=
literal|"email"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_MAIL_FROM_NAME
init|=
literal|"name"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_MAIL_DELAY
init|=
literal|"delay"
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|String
name|SETTING_KEY_MAIL_FROM
init|=
literal|"from"
decl_stmt|;
specifier|public
specifier|static
class|class
name|Mailer
block|{
specifier|private
specifier|final
name|MailerPlugin
name|plugin
decl_stmt|;
specifier|private
specifier|final
name|FiniteDuration
name|delay
decl_stmt|;
specifier|private
specifier|final
name|String
name|sender
decl_stmt|;
specifier|public
name|Mailer
parameter_list|(
specifier|final
name|MailerPlugin
name|plugin
parameter_list|,
specifier|final
name|Configuration
name|config
parameter_list|)
block|{
name|this
operator|.
name|plugin
operator|=
name|plugin
expr_stmt|;
name|delay
operator|=
name|Duration
operator|.
name|create
argument_list|(
name|config
operator|.
name|getLong
argument_list|(
name|SETTING_KEY_MAIL_DELAY
argument_list|)
argument_list|,
name|TimeUnit
operator|.
name|SECONDS
argument_list|)
expr_stmt|;
specifier|final
name|Configuration
name|fromConfig
init|=
name|config
operator|.
name|getConfig
argument_list|(
name|SETTING_KEY_MAIL_FROM
argument_list|)
decl_stmt|;
name|sender
operator|=
name|getEmailName
argument_list|(
name|fromConfig
operator|.
name|getString
argument_list|(
name|SETTING_KEY_MAIL_FROM_EMAIL
argument_list|)
argument_list|,
name|fromConfig
operator|.
name|getString
argument_list|(
name|SETTING_KEY_MAIL_FROM_NAME
argument_list|)
argument_list|)
expr_stmt|;
block|}
specifier|public
specifier|static
class|class
name|Mail
block|{
specifier|public
specifier|static
class|class
name|Body
block|{
specifier|private
specifier|final
name|String
name|html
decl_stmt|;
specifier|private
specifier|final
name|String
name|text
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|isHtml
decl_stmt|;
specifier|private
specifier|final
name|boolean
name|isText
decl_stmt|;
specifier|public
name|Body
parameter_list|(
specifier|final
name|String
name|text
parameter_list|)
block|{
name|this
argument_list|(
name|text
argument_list|,
literal|null
argument_list|)
expr_stmt|;
block|}
specifier|public
name|Body
parameter_list|(
specifier|final
name|String
name|text
parameter_list|,
specifier|final
name|String
name|html
parameter_list|)
block|{
name|this
operator|.
name|isHtml
operator|=
name|html
operator|!=
literal|null
operator|&&
operator|!
name|html
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
expr_stmt|;
name|this
operator|.
name|isText
operator|=
name|text
operator|!=
literal|null
operator|&&
operator|!
name|text
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
expr_stmt|;
if|if
condition|(
operator|!
name|this
operator|.
name|isHtml
operator|&&
operator|!
name|this
operator|.
name|isText
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Text and HTML cannot both be empty or null"
argument_list|)
throw|;
block|}
name|this
operator|.
name|html
operator|=
operator|(
name|this
operator|.
name|isHtml
operator|)
condition|?
name|html
else|:
literal|null
expr_stmt|;
name|this
operator|.
name|text
operator|=
operator|(
name|this
operator|.
name|isText
operator|)
condition|?
name|text
else|:
literal|null
expr_stmt|;
block|}
specifier|public
name|boolean
name|isHtml
parameter_list|()
block|{
return|return
name|isHtml
return|;
block|}
specifier|public
name|boolean
name|isText
parameter_list|()
block|{
return|return
name|isText
return|;
block|}
specifier|public
name|boolean
name|isBoth
parameter_list|()
block|{
return|return
name|isText
argument_list|()
operator|&&
name|isHtml
argument_list|()
return|;
block|}
specifier|public
name|String
name|getHtml
parameter_list|()
block|{
return|return
name|html
return|;
block|}
specifier|public
name|String
name|getText
parameter_list|()
block|{
return|return
name|text
return|;
block|}
block|}
specifier|private
specifier|final
name|String
name|subject
decl_stmt|;
specifier|private
specifier|final
name|String
index|[]
name|recipients
decl_stmt|;
specifier|private
name|String
name|from
decl_stmt|;
specifier|private
specifier|final
name|Body
name|body
decl_stmt|;
specifier|public
name|Mail
parameter_list|(
specifier|final
name|String
name|subject
parameter_list|,
specifier|final
name|Body
name|body
parameter_list|,
specifier|final
name|String
index|[]
name|recipients
parameter_list|)
block|{
if|if
condition|(
name|subject
operator|==
literal|null
operator|||
name|subject
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Subject must not be null or empty"
argument_list|)
throw|;
block|}
name|this
operator|.
name|subject
operator|=
name|subject
expr_stmt|;
if|if
condition|(
name|body
operator|==
literal|null
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"Body must not be null or empty"
argument_list|)
throw|;
block|}
name|this
operator|.
name|body
operator|=
name|body
expr_stmt|;
if|if
condition|(
name|recipients
operator|==
literal|null
operator|||
name|recipients
operator|.
name|length
operator|==
literal|0
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"There must be at least one recipient"
argument_list|)
throw|;
block|}
name|this
operator|.
name|recipients
operator|=
name|recipients
expr_stmt|;
block|}
specifier|public
name|String
name|getSubject
parameter_list|()
block|{
return|return
name|subject
return|;
block|}
specifier|public
name|String
index|[]
name|getRecipients
parameter_list|()
block|{
return|return
name|recipients
return|;
block|}
specifier|public
name|String
name|getFrom
parameter_list|()
block|{
return|return
name|from
return|;
block|}
specifier|private
name|void
name|setFrom
parameter_list|(
specifier|final
name|String
name|from
parameter_list|)
block|{
name|this
operator|.
name|from
operator|=
name|from
expr_stmt|;
block|}
specifier|public
name|Body
name|getBody
parameter_list|()
block|{
return|return
name|body
return|;
block|}
block|}
specifier|private
class|class
name|MailJob
implements|implements
name|Runnable
block|{
specifier|private
name|Mail
name|mail
decl_stmt|;
specifier|public
name|MailJob
parameter_list|(
specifier|final
name|Mail
name|m
parameter_list|)
block|{
name|mail
operator|=
name|m
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|void
name|run
parameter_list|()
block|{
specifier|final
name|MailerAPI
name|api
init|=
name|plugin
operator|.
name|email
argument_list|()
decl_stmt|;
name|api
operator|.
name|setSubject
argument_list|(
name|mail
operator|.
name|getSubject
argument_list|()
argument_list|)
expr_stmt|;
name|api
operator|.
name|addRecipient
argument_list|(
name|mail
operator|.
name|getRecipients
argument_list|()
argument_list|)
expr_stmt|;
name|api
operator|.
name|addFrom
argument_list|(
name|mail
operator|.
name|getFrom
argument_list|()
argument_list|)
expr_stmt|;
if|if
condition|(
name|mail
operator|.
name|getBody
argument_list|()
operator|.
name|isBoth
argument_list|()
condition|)
block|{
comment|// sends both text and html
name|api
operator|.
name|send
argument_list|(
name|mail
operator|.
name|getBody
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|,
name|mail
operator|.
name|getBody
argument_list|()
operator|.
name|getHtml
argument_list|()
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|mail
operator|.
name|getBody
argument_list|()
operator|.
name|isText
argument_list|()
condition|)
block|{
comment|// sends text/text
name|api
operator|.
name|send
argument_list|(
name|mail
operator|.
name|getBody
argument_list|()
operator|.
name|getText
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
comment|// if(mail.isHtml())
comment|// sends html
name|api
operator|.
name|sendHtml
argument_list|(
name|mail
operator|.
name|getBody
argument_list|()
operator|.
name|getHtml
argument_list|()
argument_list|)
expr_stmt|;
block|}
block|}
block|}
specifier|public
name|void
name|sendMail
parameter_list|(
specifier|final
name|Mail
name|email
parameter_list|)
block|{
name|email
operator|.
name|setFrom
argument_list|(
name|sender
argument_list|)
expr_stmt|;
name|Akka
operator|.
name|system
argument_list|()
operator|.
name|scheduler
argument_list|()
operator|.
name|scheduleOnce
argument_list|(
name|delay
argument_list|,
operator|new
name|MailJob
argument_list|(
name|email
argument_list|)
argument_list|)
expr_stmt|;
block|}
block|}
specifier|protected
name|Mailer
name|mailer
decl_stmt|;
specifier|static
specifier|final
name|String
name|PROVIDER_KEY
init|=
literal|"password"
decl_stmt|;
specifier|private
enum|enum
name|Case
block|{
name|SIGNUP
block|,
name|LOGIN
block|}
specifier|protected
enum|enum
name|SignupResult
block|{
name|USER_EXISTS
block|,
name|USER_CREATED_UNVERIFIED
block|,
name|USER_CREATED
block|,
name|USER_EXISTS_UNVERIFIED
block|}
specifier|protected
enum|enum
name|LoginResult
block|{
name|USER_UNVERIFIED
block|,
name|USER_LOGGED_IN
block|,
name|NOT_FOUND
block|,
name|WRONG_PASSWORD
block|}
specifier|public
specifier|static
interface|interface
name|UsernamePassword
block|{
specifier|public
name|String
name|getEmail
parameter_list|()
function_decl|;
specifier|public
name|String
name|getPassword
parameter_list|()
function_decl|;
block|}
specifier|public
name|UsernamePasswordAuthProvider
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
name|void
name|onStart
parameter_list|()
block|{
name|super
operator|.
name|onStart
argument_list|()
expr_stmt|;
name|mailer
operator|=
operator|new
name|Mailer
argument_list|(
name|play
operator|.
name|Play
operator|.
name|application
argument_list|()
operator|.
name|plugin
argument_list|(
name|MailerPlugin
operator|.
name|class
argument_list|)
argument_list|,
name|getConfiguration
argument_list|()
operator|.
name|getConfig
argument_list|(
name|SETTING_KEY_MAIL
argument_list|)
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getKey
parameter_list|()
block|{
return|return
name|PROVIDER_KEY
return|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|authenticate
parameter_list|(
specifier|final
name|Context
name|context
parameter_list|,
specifier|final
name|Object
name|payload
parameter_list|)
throws|throws
name|AuthException
block|{
if|if
condition|(
name|payload
operator|==
name|Case
operator|.
name|SIGNUP
condition|)
block|{
specifier|final
name|S
name|signup
init|=
name|getSignup
argument_list|(
name|context
operator|.
name|request
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|US
name|authUser
init|=
name|buildSignupAuthUser
argument_list|(
name|signup
argument_list|)
decl_stmt|;
specifier|final
name|SignupResult
name|r
init|=
name|signupUser
argument_list|(
name|authUser
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|r
condition|)
block|{
case|case
name|USER_EXISTS
case|:
comment|// The user exists already
return|return
name|userExists
argument_list|(
name|authUser
argument_list|)
operator|.
name|url
argument_list|()
return|;
case|case
name|USER_EXISTS_UNVERIFIED
case|:
comment|// TODO: resend validation email after X minutes?
return|return
name|userUnverified
argument_list|(
name|authUser
argument_list|)
operator|.
name|url
argument_list|()
return|;
case|case
name|USER_CREATED_UNVERIFIED
case|:
comment|// User got created as unverified
comment|// Send validation email
name|sendVerifyEmailMailing
argument_list|(
name|context
argument_list|,
name|authUser
argument_list|)
expr_stmt|;
return|return
name|userUnverified
argument_list|(
name|authUser
argument_list|)
operator|.
name|url
argument_list|()
return|;
case|case
name|USER_CREATED
case|:
comment|// continue to login...
return|return
name|authUser
return|;
default|default:
throw|throw
operator|new
name|AuthException
argument_list|(
literal|"Something in signup went wrong"
argument_list|)
throw|;
block|}
block|}
elseif|else
if|if
condition|(
name|payload
operator|==
name|Case
operator|.
name|LOGIN
condition|)
block|{
specifier|final
name|L
name|login
init|=
name|getLogin
argument_list|(
name|context
operator|.
name|request
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|UL
name|authUser
init|=
name|buildLoginAuthUser
argument_list|(
name|login
argument_list|)
decl_stmt|;
specifier|final
name|LoginResult
name|r
init|=
name|loginUser
argument_list|(
name|authUser
argument_list|)
decl_stmt|;
switch|switch
condition|(
name|r
condition|)
block|{
case|case
name|USER_UNVERIFIED
case|:
comment|// The email of the user is not verified, yet - we won't allow
comment|// him to log in
return|return
name|userUnverified
argument_list|(
name|authUser
argument_list|)
operator|.
name|url
argument_list|()
return|;
case|case
name|USER_LOGGED_IN
case|:
comment|// The user exists and the given password was correct
return|return
name|authUser
return|;
case|case
name|WRONG_PASSWORD
case|:
comment|// don't expose this - it might harm users privacy if anyone
comment|// knows they signed up for our service
case|case
name|NOT_FOUND
case|:
comment|// forward to login page
return|return
name|onLoginUserNotFound
argument_list|(
name|context
argument_list|)
return|;
default|default:
throw|throw
operator|new
name|AuthException
argument_list|(
literal|"Something in login went wrong"
argument_list|)
throw|;
block|}
block|}
else|else
block|{
return|return
name|PlayAuthenticate
operator|.
name|getResolver
argument_list|()
operator|.
name|login
argument_list|()
operator|.
name|url
argument_list|()
return|;
block|}
block|}
specifier|protected
name|String
name|onLoginUserNotFound
parameter_list|(
name|Context
name|context
parameter_list|)
block|{
return|return
name|PlayAuthenticate
operator|.
name|getResolver
argument_list|()
operator|.
name|login
argument_list|()
operator|.
name|url
argument_list|()
return|;
block|}
specifier|public
specifier|static
name|Result
name|handleLogin
parameter_list|(
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
return|return
name|PlayAuthenticate
operator|.
name|handleAuthentication
argument_list|(
name|PROVIDER_KEY
argument_list|,
name|ctx
argument_list|,
name|Case
operator|.
name|LOGIN
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|AuthUser
name|getSessionAuthUser
parameter_list|(
specifier|final
name|String
name|id
parameter_list|,
specifier|final
name|long
name|expires
parameter_list|)
block|{
comment|// TODO implement expiry and use a custom impl
return|return
operator|new
name|DefaultUsernamePasswordAuthUser
argument_list|(
literal|null
argument_list|,
name|id
argument_list|)
return|;
block|}
specifier|public
specifier|static
name|Result
name|handleSignup
parameter_list|(
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
return|return
name|PlayAuthenticate
operator|.
name|handleAuthentication
argument_list|(
name|PROVIDER_KEY
argument_list|,
name|ctx
argument_list|,
name|Case
operator|.
name|SIGNUP
argument_list|)
return|;
block|}
specifier|private
name|S
name|getSignup
parameter_list|(
specifier|final
name|Request
name|request
parameter_list|)
block|{
specifier|final
name|Form
argument_list|<
name|S
argument_list|>
name|filledForm
init|=
name|getSignupForm
argument_list|()
operator|.
name|bindFromRequest
argument_list|(
name|request
argument_list|)
decl_stmt|;
return|return
name|filledForm
operator|.
name|get
argument_list|()
return|;
block|}
specifier|private
name|L
name|getLogin
parameter_list|(
specifier|final
name|Request
name|request
parameter_list|)
block|{
specifier|final
name|Form
argument_list|<
name|L
argument_list|>
name|filledForm
init|=
name|getLoginForm
argument_list|()
operator|.
name|bindFromRequest
argument_list|(
name|request
argument_list|)
decl_stmt|;
return|return
name|filledForm
operator|.
name|get
argument_list|()
return|;
block|}
comment|/** 	 * You might overwrite this to provide your own recipient format 	 * implementation, 	 * however the default should be fine for most cases 	 *  	 * @param user 	 * @return 	 */
specifier|protected
name|String
name|getEmailName
parameter_list|(
specifier|final
name|US
name|user
parameter_list|)
block|{
name|String
name|name
init|=
literal|null
decl_stmt|;
if|if
condition|(
name|user
operator|instanceof
name|NameIdentity
condition|)
block|{
name|name
operator|=
operator|(
operator|(
name|NameIdentity
operator|)
name|user
operator|)
operator|.
name|getName
argument_list|()
expr_stmt|;
block|}
return|return
name|getEmailName
argument_list|(
name|user
operator|.
name|getEmail
argument_list|()
argument_list|,
name|name
argument_list|)
return|;
block|}
specifier|protected
specifier|static
name|String
name|getEmailName
parameter_list|(
specifier|final
name|String
name|email
parameter_list|,
specifier|final
name|String
name|name
parameter_list|)
block|{
if|if
condition|(
name|email
operator|==
literal|null
operator|||
name|email
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
throw|throw
operator|new
name|RuntimeException
argument_list|(
literal|"email must not be null"
argument_list|)
throw|;
block|}
specifier|final
name|StringBuilder
name|sb
init|=
operator|new
name|StringBuilder
argument_list|()
decl_stmt|;
specifier|final
name|boolean
name|hasName
init|=
name|name
operator|!=
literal|null
operator|&&
operator|!
name|name
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
decl_stmt|;
if|if
condition|(
name|hasName
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|"\""
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
name|name
argument_list|)
expr_stmt|;
name|sb
operator|.
name|append
argument_list|(
literal|"\"<"
argument_list|)
expr_stmt|;
block|}
name|sb
operator|.
name|append
argument_list|(
name|email
argument_list|)
expr_stmt|;
if|if
condition|(
name|hasName
condition|)
block|{
name|sb
operator|.
name|append
argument_list|(
literal|">"
argument_list|)
expr_stmt|;
block|}
return|return
name|sb
operator|.
name|toString
argument_list|()
return|;
block|}
specifier|protected
specifier|abstract
name|R
name|generateVerificationRecord
parameter_list|(
specifier|final
name|US
name|user
parameter_list|)
function_decl|;
specifier|private
name|void
name|sendVerifyEmailMailing
parameter_list|(
specifier|final
name|Context
name|ctx
parameter_list|,
specifier|final
name|US
name|user
parameter_list|)
block|{
specifier|final
name|String
name|subject
init|=
name|getVerifyEmailMailingSubject
argument_list|(
name|user
argument_list|,
name|ctx
argument_list|)
decl_stmt|;
specifier|final
name|R
name|record
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
name|getVerifyEmailMailingBody
argument_list|(
name|record
argument_list|,
name|user
argument_list|,
name|ctx
argument_list|)
decl_stmt|;
specifier|final
name|Mail
name|verifyMail
init|=
operator|new
name|Mail
argument_list|(
name|subject
argument_list|,
name|body
argument_list|,
operator|new
name|String
index|[]
block|{
name|getEmailName
argument_list|(
name|user
argument_list|)
block|}
argument_list|)
decl_stmt|;
name|mailer
operator|.
name|sendMail
argument_list|(
name|verifyMail
argument_list|)
expr_stmt|;
block|}
specifier|protected
specifier|abstract
name|String
name|getVerifyEmailMailingSubject
parameter_list|(
specifier|final
name|US
name|user
parameter_list|,
specifier|final
name|Context
name|ctx
parameter_list|)
function_decl|;
specifier|protected
specifier|abstract
name|Body
name|getVerifyEmailMailingBody
parameter_list|(
specifier|final
name|R
name|verificationRecord
parameter_list|,
specifier|final
name|US
name|user
parameter_list|,
specifier|final
name|Context
name|ctx
parameter_list|)
function_decl|;
specifier|protected
specifier|abstract
name|UL
name|buildLoginAuthUser
parameter_list|(
specifier|final
name|L
name|login
parameter_list|)
function_decl|;
specifier|protected
specifier|abstract
name|US
name|buildSignupAuthUser
parameter_list|(
specifier|final
name|S
name|signup
parameter_list|)
function_decl|;
specifier|protected
specifier|abstract
name|LoginResult
name|loginUser
parameter_list|(
specifier|final
name|UL
name|authUser
parameter_list|)
function_decl|;
specifier|protected
specifier|abstract
name|SignupResult
name|signupUser
parameter_list|(
specifier|final
name|US
name|user
parameter_list|)
function_decl|;
specifier|protected
specifier|abstract
name|Form
argument_list|<
name|S
argument_list|>
name|getSignupForm
parameter_list|()
function_decl|;
specifier|protected
specifier|abstract
name|Form
argument_list|<
name|L
argument_list|>
name|getLoginForm
parameter_list|()
function_decl|;
specifier|protected
specifier|abstract
name|Call
name|userExists
parameter_list|(
specifier|final
name|UsernamePasswordAuthUser
name|authUser
parameter_list|)
function_decl|;
specifier|protected
specifier|abstract
name|Call
name|userUnverified
parameter_list|(
specifier|final
name|UsernamePasswordAuthUser
name|authUser
parameter_list|)
function_decl|;
block|}
end_class

end_unit

