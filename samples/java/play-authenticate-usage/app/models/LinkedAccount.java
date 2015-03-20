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
name|javax
operator|.
name|persistence
operator|.
name|ManyToOne
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
annotation|@
name|Entity
specifier|public
class|class
name|LinkedAccount
extends|extends
name|AppModel
block|{
comment|/** 	 *  	 */
specifier|private
specifier|static
specifier|final
name|long
name|serialVersionUID
init|=
literal|1L
decl_stmt|;
annotation|@
name|Id
specifier|public
name|Long
name|id
decl_stmt|;
annotation|@
name|ManyToOne
specifier|public
name|User
name|user
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
specifier|final
name|Finder
argument_list|<
name|Long
argument_list|,
name|LinkedAccount
argument_list|>
name|find
init|=
operator|new
name|Finder
argument_list|<
name|Long
argument_list|,
name|LinkedAccount
argument_list|>
argument_list|(
name|Long
operator|.
name|class
argument_list|,
name|LinkedAccount
operator|.
name|class
argument_list|)
decl_stmt|;
specifier|public
specifier|static
name|LinkedAccount
name|findByProviderKey
parameter_list|(
specifier|final
name|User
name|user
parameter_list|,
name|String
name|key
parameter_list|)
block|{
return|return
name|find
operator|.
name|where
argument_list|()
operator|.
name|eq
argument_list|(
literal|"user"
argument_list|,
name|user
argument_list|)
operator|.
name|eq
argument_list|(
literal|"providerKey"
argument_list|,
name|key
argument_list|)
operator|.
name|findUnique
argument_list|()
return|;
block|}
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
name|update
argument_list|(
name|authUser
argument_list|)
expr_stmt|;
return|return
name|ret
return|;
block|}
specifier|public
name|void
name|update
parameter_list|(
specifier|final
name|AuthUser
name|authUser
parameter_list|)
block|{
name|this
operator|.
name|providerKey
operator|=
name|authUser
operator|.
name|getProvider
argument_list|()
expr_stmt|;
name|this
operator|.
name|providerUserId
operator|=
name|authUser
operator|.
name|getId
argument_list|()
expr_stmt|;
block|}
specifier|public
specifier|static
name|LinkedAccount
name|create
parameter_list|(
specifier|final
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

