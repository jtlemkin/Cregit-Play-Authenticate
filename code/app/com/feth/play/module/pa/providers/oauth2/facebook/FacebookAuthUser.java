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
name|facebook
package|;
end_package

begin_import
import|import
name|java
operator|.
name|util
operator|.
name|Locale
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
name|ExtendedIdentity
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
name|LocaleIdentity
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
name|ProfiledIdentity
import|;
end_import

begin_class
specifier|public
class|class
name|FacebookAuthUser
extends|extends
name|BasicOAuth2AuthUser
implements|implements
name|ExtendedIdentity
implements|,
name|PicturedIdentity
implements|,
name|ProfiledIdentity
implements|,
name|LocaleIdentity
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
name|NAME
init|=
literal|"name"
decl_stmt|;
comment|// "Joscha Feth"
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
name|LINK
init|=
literal|"link"
decl_stmt|;
comment|// "http://www.facebook.com/joscha.feth"
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
name|GENDER
init|=
literal|"gender"
decl_stmt|;
comment|// "male"
specifier|public
specifier|static
specifier|final
name|String
name|EMAIL
init|=
literal|"email"
decl_stmt|;
comment|// "joscha@feth.com"
specifier|public
specifier|static
specifier|final
name|String
name|TIME_ZONE
init|=
literal|"timezone"
decl_stmt|;
comment|// 2
specifier|public
specifier|static
specifier|final
name|String
name|LOCALE
init|=
literal|"locale"
decl_stmt|;
comment|// "de_DE"
specifier|public
specifier|static
specifier|final
name|String
name|VERIFIED
init|=
literal|"verified"
decl_stmt|;
comment|// true
specifier|public
specifier|static
specifier|final
name|String
name|UPDATE_TIME
init|=
literal|"updated_time"
decl_stmt|;
comment|// "2012-04-26T20:22:52+0000"
specifier|public
specifier|static
specifier|final
name|String
name|BIRTHDAY
init|=
literal|"birthday"
decl_stmt|;
comment|// "25/12/1980"}
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
name|link
decl_stmt|;
specifier|private
name|String
name|username
decl_stmt|;
specifier|private
name|String
name|gender
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
specifier|private
name|int
name|timezone
decl_stmt|;
specifier|private
name|String
name|locale
decl_stmt|;
specifier|private
name|String
name|updateTime
decl_stmt|;
specifier|private
name|String
name|birthday
decl_stmt|;
specifier|public
name|FacebookAuthUser
parameter_list|(
specifier|final
name|JsonNode
name|node
parameter_list|,
specifier|final
name|FacebookAuthInfo
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
name|LINK
argument_list|)
condition|)
block|{
name|this
operator|.
name|link
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|LINK
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
name|GENDER
argument_list|)
condition|)
block|{
name|this
operator|.
name|gender
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|GENDER
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
if|if
condition|(
name|node
operator|.
name|has
argument_list|(
name|Constants
operator|.
name|VERIFIED
argument_list|)
condition|)
block|{
name|this
operator|.
name|verified
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|VERIFIED
argument_list|)
operator|.
name|asBoolean
argument_list|(
literal|false
argument_list|)
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
name|TIME_ZONE
argument_list|)
condition|)
block|{
name|this
operator|.
name|timezone
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|TIME_ZONE
argument_list|)
operator|.
name|asInt
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
name|LOCALE
argument_list|)
condition|)
block|{
name|this
operator|.
name|locale
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|LOCALE
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
name|UPDATE_TIME
argument_list|)
condition|)
block|{
name|this
operator|.
name|updateTime
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|UPDATE_TIME
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
name|BIRTHDAY
argument_list|)
condition|)
block|{
name|this
operator|.
name|birthday
operator|=
name|node
operator|.
name|get
argument_list|(
name|Constants
operator|.
name|BIRTHDAY
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
name|FacebookAuthProvider
operator|.
name|PROVIDER_KEY
return|;
block|}
specifier|public
name|String
name|getName
parameter_list|()
block|{
return|return
name|name
return|;
block|}
specifier|public
name|String
name|getFirstName
parameter_list|()
block|{
return|return
name|firstName
return|;
block|}
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
name|getProfileLink
parameter_list|()
block|{
return|return
name|link
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
specifier|public
name|String
name|getGender
parameter_list|()
block|{
return|return
name|gender
return|;
block|}
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
specifier|public
name|int
name|getTimezone
parameter_list|()
block|{
return|return
name|timezone
return|;
block|}
specifier|public
name|String
name|getPicture
parameter_list|()
block|{
comment|// According to
comment|// https://developers.facebook.com/docs/reference/api/#pictures
return|return
name|String
operator|.
name|format
argument_list|(
literal|"https://graph.facebook.com/%s/picture"
argument_list|,
name|getId
argument_list|()
argument_list|)
return|;
block|}
specifier|public
name|Locale
name|getLocale
parameter_list|()
block|{
return|return
name|AuthUser
operator|.
name|getLocaleFromString
argument_list|(
name|locale
argument_list|)
return|;
block|}
specifier|public
name|String
name|getUpdateTime
parameter_list|()
block|{
return|return
name|updateTime
return|;
block|}
specifier|public
name|String
name|getBirthday
parameter_list|()
block|{
return|return
name|birthday
return|;
block|}
block|}
end_class

end_unit

