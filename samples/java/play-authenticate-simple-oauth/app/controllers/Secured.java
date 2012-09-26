begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|controllers
package|;
end_package

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
name|Result
import|;
end_import

begin_import
import|import
name|play
operator|.
name|mvc
operator|.
name|Security
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

begin_class
specifier|public
class|class
name|Secured
extends|extends
name|Security
operator|.
name|Authenticator
block|{
annotation|@
name|Override
specifier|public
name|String
name|getUsername
parameter_list|(
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
specifier|final
name|AuthUser
name|u
init|=
name|PlayAuthenticate
operator|.
name|getUser
argument_list|(
name|ctx
operator|.
name|session
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
return|return
name|u
operator|.
name|getId
argument_list|()
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
specifier|public
name|Result
name|onUnauthorized
parameter_list|(
specifier|final
name|Context
name|ctx
parameter_list|)
block|{
name|ctx
operator|.
name|flash
argument_list|()
operator|.
name|put
argument_list|(
name|Application
operator|.
name|FLASH_MESSAGE_KEY
argument_list|,
literal|"Nice try, but you need to log in first!"
argument_list|)
expr_stmt|;
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
block|}
end_class

end_unit

