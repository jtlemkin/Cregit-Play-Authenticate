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
name|untappd
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
name|BasicIdentity
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
name|PicturedIdentity
import|;
end_import

begin_comment
comment|/**  * https://untappd.com/api/docs#user_info  */
end_comment

begin_class
specifier|public
class|class
name|UntappdAuthUser
extends|extends
name|BasicOAuth2AuthUser
implements|implements
name|BasicIdentity
implements|,
name|FirstLastNameIdentity
implements|,
name|PicturedIdentity
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
class|class
name|Constants
block|{
specifier|public
specifier|static
specifier|final
name|String
name|EMAIL
init|=
literal|"email_address"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|DISPLAY_NAME
init|=
literal|"user_name"
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
name|ID
init|=
literal|"id"
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
name|AVATAR_URL
init|=
literal|"user_avatar"
decl_stmt|;
specifier|public
specifier|static
specifier|final
name|String
name|SETTINGS
init|=
literal|"settings"
decl_stmt|;
block|}
specifier|private
name|String
name|displayName
decl_stmt|;
specifier|private
name|String
name|email
decl_stmt|;
specifier|private
name|String
name|firstName
decl_stmt|;
specifier|private
name|String
name|avatar
decl_stmt|;
specifier|private
name|String
name|lastName
decl_stmt|;
specifier|public
name|UntappdAuthUser
parameter_list|(
specifier|final
name|JsonNode
name|n
parameter_list|,
specifier|final
name|UntappdAuthInfo
name|info
parameter_list|,
specifier|final
name|String
name|state
parameter_list|)
block|{
name|super
argument_list|(
name|n
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
name|n
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
name|n
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
name|n
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
name|n
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
name|n
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|DISPLAY_NAME
argument_list|)
condition|)
block|{
name|this
operator|.
name|displayName
operator|=
name|n
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|DISPLAY_NAME
argument_list|)
operator|.
name|asText
argument_list|()
expr_stmt|;
block|}
if|if
condition|(
name|n
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|SETTINGS
argument_list|)
condition|)
block|{
name|JsonNode
name|settingsNode
init|=
name|n
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|SETTINGS
argument_list|)
decl_stmt|;
if|if
condition|(
name|settingsNode
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
name|settingsNode
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
if|if
condition|(
name|n
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|AVATAR_URL
argument_list|)
condition|)
block|{
name|this
operator|.
name|avatar
operator|=
name|n
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|AVATAR_URL
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
name|getName
parameter_list|()
block|{
return|return
name|displayName
return|;
block|}
annotation|@
name|Override
specifier|public
name|String
name|getPicture
parameter_list|()
block|{
return|return
name|avatar
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
name|UntappdAuthProvider
operator|.
name|PROVIDER_KEY
return|;
block|}
block|}
end_class

end_unit

