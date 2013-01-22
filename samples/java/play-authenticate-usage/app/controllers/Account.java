begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|controllers
package|;
end_package

begin_import
import|import
name|models
operator|.
name|User
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
name|actions
operator|.
name|Restrict
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
name|actions
operator|.
name|RoleHolderPresent
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
name|user
operator|.
name|AuthUser
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
name|format
operator|.
name|Formats
operator|.
name|NonEmpty
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
name|MyUsernamePasswordAuthUser
import|;
end_import

begin_import
import|import
name|views
operator|.
name|html
operator|.
name|account
operator|.
name|*
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
specifier|public
class|class
name|Account
extends|extends
name|Controller
block|{
specifier|public
specifier|static
class|class
name|Accept
block|{
annotation|@
name|Required
annotation|@
name|NonEmpty
specifier|public
name|Boolean
name|accept
decl_stmt|;
specifier|public
name|Boolean
name|getAccept
parameter_list|()
block|{
return|return
name|accept
return|;
block|}
specifier|public
name|void
name|setAccept
parameter_list|(
name|Boolean
name|accept
parameter_list|)
block|{
name|this
operator|.
name|accept
operator|=
name|accept
expr_stmt|;
block|}
block|}
specifier|public
specifier|static
class|class
name|PasswordChange
block|{
annotation|@
name|MinLength
argument_list|(
literal|5
argument_list|)
annotation|@
name|Required
specifier|public
name|String
name|password
decl_stmt|;
annotation|@
name|MinLength
argument_list|(
literal|5
argument_list|)
annotation|@
name|Required
specifier|public
name|String
name|repeatPassword
decl_stmt|;
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
literal|"playauthenticate.change_password.error.passwords_not_same"
argument_list|)
return|;
block|}
return|return
literal|null
return|;
block|}
block|}
specifier|private
specifier|static
specifier|final
name|Form
argument_list|<
name|Accept
argument_list|>
name|ACCEPT_FORM
init|=
name|form
argument_list|(
name|Accept
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|private
specifier|static
specifier|final
name|Form
argument_list|<
name|Account
operator|.
name|PasswordChange
argument_list|>
name|PASSWORD_CHANGE_FORM
init|=
name|form
argument_list|(
name|Account
operator|.
name|PasswordChange
operator|.
name|class
argument_list|)
decl_stmt|;
annotation|@
name|RoleHolderPresent
specifier|public
specifier|static
name|Result
name|link
parameter_list|()
block|{
return|return
name|ok
argument_list|(
name|link
operator|.
name|render
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Restrict
argument_list|(
name|Application
operator|.
name|USER_ROLE
argument_list|)
specifier|public
specifier|static
name|Result
name|verifyEmail
parameter_list|()
block|{
specifier|final
name|User
name|user
init|=
name|Application
operator|.
name|getLocalUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|user
operator|.
name|emailValidated
condition|)
block|{
comment|// E-Mail has been validated already
name|flash
argument_list|(
name|Application
operator|.
name|FLASH_MESSAGE_KEY
argument_list|,
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.verify_email.error.already_validated"
argument_list|)
argument_list|)
expr_stmt|;
block|}
elseif|else
if|if
condition|(
name|user
operator|.
name|email
operator|!=
literal|null
operator|&&
operator|!
name|user
operator|.
name|email
operator|.
name|trim
argument_list|()
operator|.
name|isEmpty
argument_list|()
condition|)
block|{
name|flash
argument_list|(
name|Application
operator|.
name|FLASH_MESSAGE_KEY
argument_list|,
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.verify_email.message.instructions_sent"
argument_list|,
name|user
operator|.
name|email
argument_list|)
argument_list|)
expr_stmt|;
name|MyUsernamePasswordAuthProvider
operator|.
name|getProvider
argument_list|()
operator|.
name|sendVerifyEmailMailingAfterSignup
argument_list|(
name|user
argument_list|,
name|ctx
argument_list|()
argument_list|)
expr_stmt|;
block|}
else|else
block|{
name|flash
argument_list|(
name|Application
operator|.
name|FLASH_MESSAGE_KEY
argument_list|,
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.verify_email.error.set_email_first"
argument_list|,
name|user
operator|.
name|email
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|redirect
argument_list|(
name|routes
operator|.
name|Application
operator|.
name|profile
argument_list|()
argument_list|)
return|;
block|}
annotation|@
name|Restrict
argument_list|(
name|Application
operator|.
name|USER_ROLE
argument_list|)
specifier|public
specifier|static
name|Result
name|changePassword
parameter_list|()
block|{
specifier|final
name|User
name|u
init|=
name|Application
operator|.
name|getLocalUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|u
operator|.
name|emailValidated
condition|)
block|{
return|return
name|ok
argument_list|(
name|unverified
operator|.
name|render
argument_list|()
argument_list|)
return|;
block|}
else|else
block|{
return|return
name|ok
argument_list|(
name|password_change
operator|.
name|render
argument_list|(
name|PASSWORD_CHANGE_FORM
argument_list|)
argument_list|)
return|;
block|}
block|}
annotation|@
name|Restrict
argument_list|(
name|Application
operator|.
name|USER_ROLE
argument_list|)
specifier|public
specifier|static
name|Result
name|doChangePassword
parameter_list|()
block|{
specifier|final
name|Form
argument_list|<
name|Account
operator|.
name|PasswordChange
argument_list|>
name|filledForm
init|=
name|PASSWORD_CHANGE_FORM
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
comment|// User did not select whether to link or not link
return|return
name|badRequest
argument_list|(
name|password_change
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
specifier|final
name|User
name|user
init|=
name|Application
operator|.
name|getLocalUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
specifier|final
name|String
name|newPassword
init|=
name|filledForm
operator|.
name|get
argument_list|()
operator|.
name|password
decl_stmt|;
name|user
operator|.
name|changePassword
argument_list|(
operator|new
name|MyUsernamePasswordAuthUser
argument_list|(
name|newPassword
argument_list|)
argument_list|,
literal|true
argument_list|)
expr_stmt|;
name|flash
argument_list|(
name|Application
operator|.
name|FLASH_MESSAGE_KEY
argument_list|,
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.change_password.success"
argument_list|)
argument_list|)
expr_stmt|;
return|return
name|redirect
argument_list|(
name|routes
operator|.
name|Application
operator|.
name|profile
argument_list|()
argument_list|)
return|;
block|}
block|}
annotation|@
name|RoleHolderPresent
specifier|public
specifier|static
name|Result
name|askLink
parameter_list|()
block|{
specifier|final
name|AuthUser
name|u
init|=
name|PlayAuthenticate
operator|.
name|getLinkUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|==
literal|null
condition|)
block|{
comment|// account to link could not be found, silently redirect to login
return|return
name|redirect
argument_list|(
name|routes
operator|.
name|Application
operator|.
name|index
argument_list|()
argument_list|)
return|;
block|}
return|return
name|ok
argument_list|(
name|ask_link
operator|.
name|render
argument_list|(
name|ACCEPT_FORM
argument_list|,
name|u
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|RoleHolderPresent
specifier|public
specifier|static
name|Result
name|doLink
parameter_list|()
block|{
specifier|final
name|AuthUser
name|u
init|=
name|PlayAuthenticate
operator|.
name|getLinkUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|u
operator|==
literal|null
condition|)
block|{
comment|// account to link could not be found, silently redirect to login
return|return
name|redirect
argument_list|(
name|routes
operator|.
name|Application
operator|.
name|index
argument_list|()
argument_list|)
return|;
block|}
specifier|final
name|Form
argument_list|<
name|Accept
argument_list|>
name|filledForm
init|=
name|ACCEPT_FORM
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
comment|// User did not select whether to link or not link
return|return
name|badRequest
argument_list|(
name|ask_link
operator|.
name|render
argument_list|(
name|filledForm
argument_list|,
name|u
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
comment|// User made a choice :)
specifier|final
name|boolean
name|link
init|=
name|filledForm
operator|.
name|get
argument_list|()
operator|.
name|accept
decl_stmt|;
if|if
condition|(
name|link
condition|)
block|{
name|flash
argument_list|(
name|Application
operator|.
name|FLASH_MESSAGE_KEY
argument_list|,
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.accounts.link.success"
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|PlayAuthenticate
operator|.
name|link
argument_list|(
name|ctx
argument_list|()
argument_list|,
name|link
argument_list|)
return|;
block|}
block|}
annotation|@
name|RoleHolderPresent
specifier|public
specifier|static
name|Result
name|askMerge
parameter_list|()
block|{
comment|// this is the currently logged in user
specifier|final
name|AuthUser
name|aUser
init|=
name|PlayAuthenticate
operator|.
name|getUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
comment|// this is the user that was selected for a login
specifier|final
name|AuthUser
name|bUser
init|=
name|PlayAuthenticate
operator|.
name|getMergeUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|bUser
operator|==
literal|null
condition|)
block|{
comment|// user to merge with could not be found, silently redirect to login
return|return
name|redirect
argument_list|(
name|routes
operator|.
name|Application
operator|.
name|index
argument_list|()
argument_list|)
return|;
block|}
comment|// You could also get the local user object here via
comment|// User.findByAuthUserIdentity(newUser)
return|return
name|ok
argument_list|(
name|ask_merge
operator|.
name|render
argument_list|(
name|ACCEPT_FORM
argument_list|,
name|aUser
argument_list|,
name|bUser
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|RoleHolderPresent
specifier|public
specifier|static
name|Result
name|doMerge
parameter_list|()
block|{
comment|// this is the currently logged in user
specifier|final
name|AuthUser
name|aUser
init|=
name|PlayAuthenticate
operator|.
name|getUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
comment|// this is the user that was selected for a login
specifier|final
name|AuthUser
name|bUser
init|=
name|PlayAuthenticate
operator|.
name|getMergeUser
argument_list|(
name|session
argument_list|()
argument_list|)
decl_stmt|;
if|if
condition|(
name|bUser
operator|==
literal|null
condition|)
block|{
comment|// user to merge with could not be found, silently redirect to login
return|return
name|redirect
argument_list|(
name|routes
operator|.
name|Application
operator|.
name|index
argument_list|()
argument_list|)
return|;
block|}
specifier|final
name|Form
argument_list|<
name|Accept
argument_list|>
name|filledForm
init|=
name|ACCEPT_FORM
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
comment|// User did not select whether to merge or not merge
return|return
name|badRequest
argument_list|(
name|ask_merge
operator|.
name|render
argument_list|(
name|filledForm
argument_list|,
name|aUser
argument_list|,
name|bUser
argument_list|)
argument_list|)
return|;
block|}
else|else
block|{
comment|// User made a choice :)
specifier|final
name|boolean
name|merge
init|=
name|filledForm
operator|.
name|get
argument_list|()
operator|.
name|accept
decl_stmt|;
if|if
condition|(
name|merge
condition|)
block|{
name|flash
argument_list|(
name|Application
operator|.
name|FLASH_MESSAGE_KEY
argument_list|,
name|Messages
operator|.
name|get
argument_list|(
literal|"playauthenticate.accounts.merge.success"
argument_list|)
argument_list|)
expr_stmt|;
block|}
return|return
name|PlayAuthenticate
operator|.
name|merge
argument_list|(
name|ctx
argument_list|()
argument_list|,
name|merge
argument_list|)
return|;
block|}
block|}
block|}
end_class

end_unit

