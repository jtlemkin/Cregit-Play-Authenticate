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
name|eventbrite
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
name|FirstLastNameIdentity
import|;
end_import

begin_import
import|import
name|play
operator|.
name|Logger
import|;
end_import

begin_import
import|import
name|com
operator|.
name|fasterxml
operator|.
name|jackson
operator|.
name|databind
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

begin_class
specifier|public
class|class
name|EventBriteAuthUser
extends|extends
name|BasicOAuth2AuthUser
implements|implements
name|FirstLastNameIdentity
block|{
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
specifier|public
specifier|static
specifier|final
name|String
name|NAME
init|=
literal|"name"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|FIRST_NAME
init|=
literal|"first_name"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|LAST_NAME
init|=
literal|"last_name"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EMAILS_EMAIL
init|=
literal|"email"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EMAILS
init|=
literal|"emails"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|PRIMARY
init|=
literal|"primary"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|EMAILS_EMAIL_VERIFIED
init|=
literal|"verified"
decl_stmt|;
block|}
specifier|private
name|String
name|name
decl_stmt|;
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
name|email
decl_stmt|;
specifier|private
name|boolean
name|verified
init|=
literal|false
decl_stmt|;
specifier|public
name|EventBriteAuthUser
parameter_list|(
specifier|final
name|JsonNode
name|node
parameter_list|,
specifier|final
name|EventBriteAuthInfo
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
name|NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|name
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|NAME
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
name|EMAILS
argument_list|)
condition|)
block|{
specifier|final
name|JsonNode
name|eMailsNode
init|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|EMAILS
argument_list|)
decl_stmt|;
name|Logger
operator|.
name|debug
argument_list|(
literal|"Emails : "
operator|+
name|eMailsNode
argument_list|)
expr_stmt|;
for|for
control|(
specifier|final
name|JsonNode
name|jsonNode
range|:
name|eMailsNode
control|)
block|{
if|if
condition|(
name|jsonNode
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|PRIMARY
argument_list|)
operator|.
name|asBoolean
argument_list|(
literal|false
argument_list|)
condition|)
block|{
name|this
operator|.
name|email
operator|=
name|jsonNode
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|EMAILS_EMAIL
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
name|this
operator|.
name|verified
operator|=
name|jsonNode
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|EMAILS_EMAIL_VERIFIED
argument_list|)
operator|.
name|asBoolean
argument_list|()
expr_stmt|;
name|Logger
operator|.
name|debug
argument_list|(
literal|"Found primary email: "
operator|+
name|this
operator|.
name|email
argument_list|)
expr_stmt|;
break|break;
block|}
elseif|else
if|if
condition|(
name|this
operator|.
name|email
operator|==
literal|null
condition|)
block|{
name|this
operator|.
name|email
operator|=
name|jsonNode
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|EMAILS_EMAIL
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
name|this
operator|.
name|verified
operator|=
name|jsonNode
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|EMAILS_EMAIL_VERIFIED
argument_list|)
operator|.
name|asBoolean
argument_list|()
expr_stmt|;
name|Logger
operator|.
name|debug
argument_list|(
literal|"First email: "
operator|+
name|this
operator|.
name|email
argument_list|)
expr_stmt|;
block|}
block|}
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
name|EventBriteAuthProvider
operator|.
name|PROVIDER_KEY
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
name|name
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
specifier|public
name|boolean
name|isVerified
parameter_list|()
block|{
return|return
name|verified
return|;
block|}
block|}
end_class

end_unit

