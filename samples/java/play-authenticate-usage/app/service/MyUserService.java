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
name|service
operator|.
name|AbstractUserService
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
name|AuthUserIdentity
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

begin_class
annotation|@
name|Singleton
specifier|public
class|class
name|MyUserService
extends|extends
name|AbstractUserService
block|{
annotation|@
name|Inject
specifier|public
name|MyUserService
parameter_list|(
specifier|final
name|PlayAuthenticate
name|auth
parameter_list|)
block|{
name|super
argument_list|(
name|auth
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|Object
name|save
parameter_list|(
specifier|final
name|AuthUser
name|authUser
parameter_list|)
block|{
specifier|final
name|boolean
name|isLinked
init|=
name|User
operator|.
name|existsByAuthUserIdentity
argument_list|(
name|authUser
argument_list|)
decl_stmt|;
if|if
condition|(
operator|!
name|isLinked
condition|)
block|{
return|return
name|User
operator|.
name|create
argument_list|(
name|authUser
argument_list|)
operator|.
name|id
return|;
block|}
else|else
block|{
comment|// we have this user already, so return null
return|return
literal|null
return|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|Object
name|getLocalIdentity
parameter_list|(
specifier|final
name|AuthUserIdentity
name|identity
parameter_list|)
block|{
comment|// For production: Caching might be a good idea here...
comment|// ...and dont forget to sync the cache when users get deactivated/deleted
specifier|final
name|User
name|u
init|=
name|User
operator|.
name|findByAuthUserIdentity
argument_list|(
name|identity
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
name|id
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
name|AuthUser
name|merge
parameter_list|(
specifier|final
name|AuthUser
name|newUser
parameter_list|,
specifier|final
name|AuthUser
name|oldUser
parameter_list|)
block|{
if|if
condition|(
operator|!
name|oldUser
operator|.
name|equals
argument_list|(
name|newUser
argument_list|)
condition|)
block|{
name|User
operator|.
name|merge
argument_list|(
name|oldUser
argument_list|,
name|newUser
argument_list|)
expr_stmt|;
block|}
return|return
name|oldUser
return|;
block|}
annotation|@
name|Override
specifier|public
name|AuthUser
name|link
parameter_list|(
specifier|final
name|AuthUser
name|oldUser
parameter_list|,
specifier|final
name|AuthUser
name|newUser
parameter_list|)
block|{
name|User
operator|.
name|addLinkedAccount
argument_list|(
name|oldUser
argument_list|,
name|newUser
argument_list|)
expr_stmt|;
return|return
name|newUser
return|;
block|}
annotation|@
name|Override
specifier|public
name|AuthUser
name|update
parameter_list|(
specifier|final
name|AuthUser
name|knownUser
parameter_list|)
block|{
comment|// User logged in again, bump last login date
name|User
operator|.
name|setLastLoginDate
argument_list|(
name|knownUser
argument_list|)
expr_stmt|;
return|return
name|knownUser
return|;
block|}
block|}
end_class

end_unit

