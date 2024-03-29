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
name|providers
operator|.
name|password
package|;
end_package

begin_class
specifier|public
class|class
name|DefaultUsernamePasswordAuthUser
extends|extends
name|UsernamePasswordAuthUser
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
specifier|public
name|DefaultUsernamePasswordAuthUser
parameter_list|(
specifier|final
name|String
name|clearPassword
parameter_list|,
specifier|final
name|String
name|email
parameter_list|)
block|{
name|super
argument_list|(
name|clearPassword
argument_list|,
name|email
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
name|super
operator|.
name|getEmail
argument_list|()
return|;
block|}
comment|/** 	 * This MUST be overwritten by an extending class. 	 * The default implementation stores a clear string, which is NOT recommended. 	 *  	 * Should return null if the clearString given is null. 	 *  	 * @return 	 */
annotation|@
name|Override
specifier|protected
name|String
name|createPassword
parameter_list|(
specifier|final
name|String
name|clearString
parameter_list|)
block|{
return|return
name|clearString
return|;
block|}
block|}
end_class

end_unit

