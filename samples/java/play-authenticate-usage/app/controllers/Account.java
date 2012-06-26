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
name|Required
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
name|views
operator|.
name|html
operator|.
name|account
operator|.
name|*
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
block|}
specifier|public
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
literal|"message"
argument_list|,
literal|"Account linked successfully"
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
literal|"message"
argument_list|,
literal|"Accounts merged successfully"
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

