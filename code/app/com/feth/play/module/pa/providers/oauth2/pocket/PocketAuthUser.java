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
operator|.
name|pocket
package|;
end_package

begin_import
import|import
name|org
operator|.
name|codehaus
operator|.
name|jackson
operator|.
name|JsonNode
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
name|oauth2
operator|.
name|BasicOAuth2AuthUser
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
name|EmailIdentity
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
name|FirstLastNameIdentity
import|;
end_import

begin_class
specifier|public
class|class
name|PocketAuthUser
extends|extends
name|BasicOAuth2AuthUser
implements|implements
name|EmailIdentity
implements|,
name|FirstLastNameIdentity
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
specifier|static
specifier|abstract
class|class
name|Constants
block|{
specifier|public
specifier|static
specifier|final
name|String
name|ID
init|=
literal|"id"
decl_stmt|;
comment|// "616473731"
specifier|public
specifier|static
specifier|final
name|String
name|FIRST_NAME
init|=
literal|"first_name"
decl_stmt|;
comment|// "Joscha"
specifier|public
specifier|static
specifier|final
name|String
name|LAST_NAME
init|=
literal|"last_name"
decl_stmt|;
comment|// "Feth"
specifier|public
specifier|static
specifier|final
name|String
name|USERNAME
init|=
literal|"username"
decl_stmt|;
comment|// "joscha.feth"
specifier|public
specifier|static
specifier|final
name|String
name|EMAIL
init|=
literal|"email"
decl_stmt|;
comment|// "joscha@feth.com"
block|}
specifier|private
name|String
name|firstName
decl_stmt|;
specifier|private
name|String
name|lastName
decl_stmt|;
specifier|private
name|String
name|username
decl_stmt|;
specifier|private
name|String
name|email
decl_stmt|;
specifier|public
name|PocketAuthUser
parameter_list|(
specifier|final
name|JsonNode
name|node
parameter_list|,
specifier|final
name|PocketAuthInfo
name|info
parameter_list|,
specifier|final
name|String
name|state
parameter_list|)
block|{
name|super
argument_list|(
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|ID
argument_list|)
operator|.
name|asText
argument_list|()
argument_list|,
name|info
argument_list|,
name|state
argument_list|)
expr_stmt|;
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|FIRST_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|firstName
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|FIRST_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|LAST_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|lastName
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|LAST_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|USERNAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|username
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|USERNAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|EMAIL
argument_list|)
condition|)
block|{
name|this
operator|.
name|email
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|EMAIL
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
block|}
annotation|@
name|Override
specifier|public
name|String
name|getProvider
parameter_list|()
block|{
return|return
name|PocketAuthProvider
operator|.
name|PROVIDER_KEY
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getFirstName
parameter_list|()
block|{
return|return
name|firstName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getLastName
parameter_list|()
block|{
return|return
name|lastName
return|;
block|}
specifier|public
name|String
name|getUsername
parameter_list|()
block|{
return|return
name|username
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getEmail
parameter_list|()
block|{
return|return
name|email
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
literal|null
return|;
block|}
block|}
end_class

end_unit

