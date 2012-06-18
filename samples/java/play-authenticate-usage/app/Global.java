begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Arrays
import|;
end_import

begin_import
import|import
name|models
operator|.
name|SecurityRole
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
name|PlayAuthenticate
operator|.
name|Resolver
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
name|Application
import|;
end_import

begin_import
import|import
name|play
operator|.
name|GlobalSettings
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

begin_class
specifier|public
class|class
name|Global
extends|extends
name|GlobalSettings
block|{
specifier|public
name|void
name|onStart
parameter_list|(
name|Application
name|app
parameter_list|)
block|{
name|PlayAuthenticate
operator|.
name|setResolver
argument_list|(
operator|new
name|Resolver
argument_list|()
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
name|AuthenticateController
operator|.
name|authenticate
argument_list|(
name|provider
argument_list|)
return|;
block|}
block|}
argument_list|)
expr_stmt|;
name|initialData
argument_list|()
expr_stmt|;
block|}
specifier|private
name|void
name|initialData
parameter_list|()
block|{
if|if
condition|(
name|SecurityRole
operator|.
name|find
operator|.
name|findRowCount
argument_list|()
operator|==
literal|0
condition|)
block|{
for|for
control|(
specifier|final
name|String
name|roleName
range|:
name|Arrays
operator|.
name|asList
argument_list|(
name|controllers
operator|.
name|Application
operator|.
name|USER_ROLE
argument_list|)
control|)
block|{
specifier|final
name|SecurityRole
name|role
init|=
operator|new
name|SecurityRole
argument_list|()
decl_stmt|;
name|role
operator|.
name|roleName
operator|=
name|roleName
expr_stmt|;
name|role
operator|.
name|save
argument_list|()
expr_stmt|;
block|}
block|}
block|}
block|}
end_class

end_unit

