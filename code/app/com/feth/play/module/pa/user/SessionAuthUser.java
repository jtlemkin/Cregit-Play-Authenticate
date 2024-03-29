begin_unit|revision:0.9.5;language:Java;cregit-version:0.0.1
begin_package
package|package
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
package|;
end_package

begin_class
specifier|public
class|class
name|SessionAuthUser
extends|extends
name|AuthUser
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
specifier|private
specifier|final
name|long
name|expires
decl_stmt|;
specifier|private
specifier|final
name|String
name|provider
decl_stmt|;
specifier|private
specifier|final
name|String
name|id
decl_stmt|;
specifier|public
name|SessionAuthUser
parameter_list|(
specifier|final
name|String
name|provider
parameter_list|,
specifier|final
name|String
name|id
parameter_list|,
specifier|final
name|long
name|expires
parameter_list|)
block|{
name|this
operator|.
name|expires
operator|=
name|expires
expr_stmt|;
name|this
operator|.
name|provider
operator|=
name|provider
expr_stmt|;
name|this
operator|.
name|id
operator|=
name|id
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
name|long
name|expires
parameter_list|()
block|{
return|return
name|expires
return|;
block|}
block|}
end_class

end_unit

