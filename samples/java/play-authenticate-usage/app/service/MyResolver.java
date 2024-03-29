begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|service
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
name|Resolver
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
name|AccessDeniedException
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
name|controllers
operator|.
name|routes
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

begin_comment
comment|/**  * Concrete resolver implementation.  */
end_comment

begin_class
specifier|public
class|class
name|MyResolver
extends|extends
name|Resolver
block|{
annotation|@
name|Override
specifier|public
name|Call
name|login
parameter_list|()
block|{
comment|// Your login page
return|return
name|routes
operator|.
name|Application
operator|.
name|login
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Call
name|afterAuth
parameter_list|()
block|{
comment|// The user will be redirected to this page after authentication
comment|// if no original URL was saved
return|return
name|routes
operator|.
name|Application
operator|.
name|index
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Call
name|afterLogout
parameter_list|()
block|{
return|return
name|routes
operator|.
name|Application
operator|.
name|index
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Call
name|auth
parameter_list|(
specifier|final
name|String
name|provider
parameter_list|)
block|{
comment|// You can provide your own authentication implementation,
comment|// however the default should be sufficient for most cases
return|return
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
name|routes
operator|.
name|Authenticate
operator|.
name|authenticate
argument_list|(
name|provider
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|Call
name|askMerge
parameter_list|()
block|{
return|return
name|routes
operator|.
name|Account
operator|.
name|askMerge
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Call
name|askLink
parameter_list|()
block|{
return|return
name|routes
operator|.
name|Account
operator|.
name|askLink
argument_list|()
return|;
block|}
annotation|@
name|Override
specifier|public
name|Call
name|onException
parameter_list|(
specifier|final
name|AuthException
name|e
parameter_list|)
block|{
if|if
condition|(
name|e
operator|instanceof
name|AccessDeniedException
condition|)
block|{
return|return
name|routes
operator|.
name|Signup
operator|.
name|oAuthDenied
argument_list|(
operator|(
operator|(
name|AccessDeniedException
operator|)
name|e
operator|)
operator|.
name|getProviderKey
argument_list|()
argument_list|)
return|;
block|}
comment|// more custom problem handling here...
return|return
name|super
operator|.
name|onException
argument_list|(
name|e
argument_list|)
return|;
block|}
block|}
end_class

end_unit

