begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
name|models
package|;
end_package

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|Entity
import|;
end_import

begin_import
import|import
name|javax
operator|.
name|persistence
operator|.
name|Id
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
name|providers
operator|.
name|AuthUserIdentity
import|;
end_import

begin_import
import|import
name|play
operator|.
name|db
operator|.
name|ebean
operator|.
name|Model
import|;
end_import

begin_class
annotation|@
name|Entity
specifier|public
class|class
name|LinkedAccount
extends|extends
name|Model
block|{
annotation|@
name|Id
specifier|public
name|Long
name|id
decl_stmt|;
specifier|public
name|String
name|providerUserId
decl_stmt|;
specifier|public
name|String
name|providerKey
decl_stmt|;
specifier|public
specifier|static
name|LinkedAccount
name|create
parameter_list|(
specifier|final
name|AuthUser
name|authUser
parameter_list|)
block|{
specifier|final
name|LinkedAccount
name|ret
init|=
operator|new
name|LinkedAccount
argument_list|()
decl_stmt|;
name|ret
operator|.
name|providerKey
operator|=
name|authUser
operator|.
name|getProvider
argument_list|()
expr_stmt|;
name|ret
operator|.
name|providerUserId
operator|=
name|authUser
operator|.
name|getId
argument_list|()
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
specifier|static
name|LinkedAccount
name|create
parameter_list|(
name|LinkedAccount
name|acc
parameter_list|)
block|{
specifier|final
name|LinkedAccount
name|ret
init|=
operator|new
name|LinkedAccount
argument_list|()
decl_stmt|;
name|ret
operator|.
name|providerKey
operator|=
name|acc
operator|.
name|providerKey
expr_stmt|;
name|ret
operator|.
name|providerUserId
operator|=
name|acc
operator|.
name|providerUserId
expr_stmt|;
return|return
name|ret
return|;
block|}
block|}
end_class

end_unit

