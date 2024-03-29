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
name|oauth2
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

begin_class
specifier|public
specifier|abstract
class|class
name|OAuth2AuthUser
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
name|OAuth2AuthInfo
name|info
decl_stmt|;
specifier|private
name|String
name|id
decl_stmt|;
specifier|private
specifier|final
name|String
name|state
decl_stmt|;
specifier|public
name|OAuth2AuthUser
parameter_list|(
specifier|final
name|String
name|id
parameter_list|,
specifier|final
name|OAuth2AuthInfo
name|info
parameter_list|,
specifier|final
name|String
name|state
parameter_list|)
block|{
name|this
operator|.
name|info
operator|=
name|info
expr_stmt|;
name|this
operator|.
name|id
operator|=
name|id
expr_stmt|;
name|this
operator|.
name|state
operator|=
name|state
expr_stmt|;
block|}
specifier|public
name|OAuth2AuthInfo
name|getOAuth2AuthInfo
parameter_list|()
block|{
return|return
name|info
return|;
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
name|long
name|expires
parameter_list|()
block|{
return|return
name|getOAuth2AuthInfo
argument_list|()
operator|.
name|getExpiration
argument_list|()
return|;
block|}
specifier|public
name|String
name|getState
parameter_list|()
block|{
return|return
name|state
return|;
block|}
block|}
end_class

end_unit

