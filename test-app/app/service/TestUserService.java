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
name|util
operator|.
name|HashMap
import|;
end_import

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Map
import|;
end_import

begin_class
annotation|@
name|Singleton
specifier|public
class|class
name|TestUserService
extends|extends
name|AbstractUserService
block|{
specifier|private
specifier|final
name|Map
argument_list|<
name|AuthUserIdentity
argument_list|,
name|AuthUser
argument_list|>
name|users
init|=
operator|new
name|HashMap
argument_list|<>
argument_list|()
decl_stmt|;
annotation|@
name|Inject
specifier|public
name|TestUserService
parameter_list|(
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
name|void
name|onStart
parameter_list|()
block|{
name|this
operator|.
name|auth
operator|.
name|setUserService
argument_list|(
name|this
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
name|users
operator|.
name|put
argument_list|(
operator|new
name|ImmutableAuthUserIdentity
argument_list|(
name|authUser
argument_list|)
argument_list|,
name|authUser
argument_list|)
expr_stmt|;
return|return
name|authUser
return|;
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
return|return
name|users
operator|.
name|get
argument_list|(
operator|new
name|ImmutableAuthUserIdentity
argument_list|(
name|identity
argument_list|)
argument_list|)
return|;
block|}
annotation|@
name|Override
specifier|public
name|AuthUser
name|merge
parameter_list|(
name|AuthUser
name|newUser
parameter_list|,
name|AuthUser
name|oldUser
parameter_list|)
block|{
comment|// Not Implemented
return|return
name|newUser
return|;
block|}
annotation|@
name|Override
specifier|public
name|AuthUser
name|link
parameter_list|(
name|AuthUser
name|oldUser
parameter_list|,
name|AuthUser
name|newUser
parameter_list|)
block|{
comment|// Not Implemented
return|return
name|newUser
return|;
block|}
specifier|private
class|class
name|ImmutableAuthUserIdentity
implements|implements
name|AuthUserIdentity
block|{
specifier|private
specifier|final
name|String
name|id
decl_stmt|;
specifier|private
specifier|final
name|String
name|provider
decl_stmt|;
specifier|public
name|ImmutableAuthUserIdentity
parameter_list|(
specifier|final
name|String
name|id
parameter_list|,
specifier|final
name|String
name|provider
parameter_list|)
block|{
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|this
operator|.
name|provider
operator|=
name|provider
expr_stmt|;
block|}
specifier|public
name|ImmutableAuthUserIdentity
parameter_list|(
specifier|final
name|AuthUserIdentity
name|aui
parameter_list|)
block|{
name|this
argument_list|(
name|aui
operator|.
name|getId
argument_list|()
argument_list|,
name|aui
operator|.
name|getProvider
argument_list|()
argument_list|)
expr_stmt|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getId
parameter_list|()
block|{
return|return
name|id
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getProvider
parameter_list|()
block|{
return|return
name|provider
return|;
block|}
annotation|@
name|Override
specifier|public
name|int
name|hashCode
parameter_list|()
block|{
specifier|final
name|int
name|prime
init|=
literal|31
decl_stmt|;
name|int
name|result
init|=
literal|1
decl_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
name|getOuterType
argument_list|()
operator|.
name|hashCode
argument_list|()
expr_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
operator|(
operator|(
name|id
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|id
operator|.
name|hashCode
argument_list|()
operator|)
expr_stmt|;
name|result
operator|=
name|prime
operator|*
name|result
operator|+
operator|(
operator|(
name|provider
operator|==
literal|null
operator|)
condition|?
literal|0
else|:
name|provider
operator|.
name|hashCode
argument_list|()
operator|)
expr_stmt|;
return|return
name|result
return|;
block|}
annotation|@
name|Override
specifier|public
name|boolean
name|equals
parameter_list|(
name|Object
name|obj
parameter_list|)
block|{
if|if
condition|(
name|this
operator|==
name|obj
condition|)
return|return
literal|true
return|;
if|if
condition|(
name|obj
operator|==
literal|null
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|getClass
argument_list|()
operator|!=
name|obj
operator|.
name|getClass
argument_list|()
condition|)
return|return
literal|false
return|;
name|ImmutableAuthUserIdentity
name|other
init|=
operator|(
name|ImmutableAuthUserIdentity
operator|)
name|obj
decl_stmt|;
if|if
condition|(
operator|!
name|getOuterType
argument_list|()
operator|.
name|equals
argument_list|(
name|other
operator|.
name|getOuterType
argument_list|()
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|id
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|id
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|id
operator|.
name|equals
argument_list|(
name|other
operator|.
name|id
argument_list|)
condition|)
return|return
literal|false
return|;
if|if
condition|(
name|provider
operator|==
literal|null
condition|)
block|{
if|if
condition|(
name|other
operator|.
name|provider
operator|!=
literal|null
condition|)
return|return
literal|false
return|;
block|}
elseif|else
if|if
condition|(
operator|!
name|provider
operator|.
name|equals
argument_list|(
name|other
operator|.
name|provider
argument_list|)
condition|)
return|return
literal|false
return|;
return|return
literal|true
return|;
block|}
specifier|private
name|TestUserService
name|getOuterType
parameter_list|()
block|{
return|return
name|TestUserService
operator|.
name|this
return|;
block|}
block|}
block|}
end_class

end_unit

