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
name|user
operator|.
name|AuthUser
import|;
end_import

begin_import
import|import
name|models
operator|.
name|User
import|;
end_import

begin_comment
comment|/**  * Service operating on User entity.  */
end_comment

begin_class
specifier|public
class|class
name|UserService
block|{
specifier|public
name|User
name|getLocalUser
parameter_list|(
specifier|final
name|AuthUser
name|authUser
parameter_list|)
block|{
return|return
name|User
operator|.
name|findByAuthUserIdentity
argument_list|(
name|authUser
argument_list|)
return|;
block|}
block|}
end_class

end_unit

